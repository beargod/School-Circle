package com.example.administrator.school_circle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.school_circle.R;
import com.example.administrator.school_circle.exception.ErrorPrint;
import com.example.administrator.school_circle.model.Result;
import com.example.administrator.school_circle.model.User;
import com.example.administrator.school_circle.model.UserDao;
import com.example.administrator.school_circle.url.UrlBuilder;
import com.example.administrator.school_circle.util.DaoBuilder;
import com.example.administrator.school_circle.util.FinalDataController;
import com.example.administrator.school_circle.util.RegexUtil;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    private EditText account;
    private EditText password;
    private Button login;
    private TextView register;

    private CircleProgressBar loading;
    private Toolbar toolbar;
    private TextInputLayout accountLayout;
    private TextInputLayout pwdLayout;
    private CompositeDisposable composite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        composite.dispose();
    }

    private void initView() {
        composite = new CompositeDisposable();
        account = (EditText) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        loading = (CircleProgressBar) findViewById(R.id.loading);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        accountLayout = (TextInputLayout) findViewById(R.id.account_layout);
        pwdLayout = (TextInputLayout) findViewById(R.id.pwd_layout);

        setLoginButtonAction();
        setEditTextAction();
        setRegister();
    }

    private void setLoginButtonAction() {
        loading.setVisibility(View.INVISIBLE);
        login.setBackgroundResource(R.drawable.button_unclick);
        login.setEnabled(false);
        Disposable loginDisposable =  RxView.clicks(login)
                .throttleFirst(1, TimeUnit.SECONDS).map(new Function() {
                    @Override
                    public Object apply(@NonNull Object o) throws Exception {
                        loading.setVisibility(View.VISIBLE);
                        return login;
                    }
                }).subscribe(new Consumer() {
            @Override
            public void accept(@NonNull Object o) throws Exception {

                String accountStr = account.getText().toString();
                String passwordStr = password.getText().toString();
                try {
                    UrlBuilder.build().login(accountStr, passwordStr)
                            .timeout(10, TimeUnit.SECONDS)
                            .subscribeOn(Schedulers.io())
                            .unsubscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Result<User>>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {
                                    composite.add(d);
                                }

                                @Override
                                public void onNext(@NonNull Result<User> userResult) {
                                    UserDao dao = DaoBuilder.getInstance(LoginActivity.this).getDaoSession().getUserDao();
                                    dao.deleteAll();
                                    dao.insert(userResult.getRet());
                                    loading.setVisibility(View.INVISIBLE);
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    FinalDataController.setUserId(userResult.getRet().getId());
                                    startActivity(intent);
                                    LoginActivity.this.finish();
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {
                                    loading.setVisibility(View.INVISIBLE);
                                    ErrorPrint.printOutTimeError(LoginActivity.this);
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }catch (Exception e){
                    ErrorPrint.printError("无法连接到服务器",LoginActivity.this);
                    loading.setVisibility(View.INVISIBLE);
                }
            }
        });
        composite.add(loginDisposable);
    }

    private void setRegister() {
        RxView.clicks(register) .throttleFirst(1, TimeUnit.SECONDS).subscribe(new Consumer<Object>() {

            @Override
            public void accept(@NonNull Object o) throws Exception {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
    }

    private void setEditTextAction() {
        Observable<Boolean> accountTextChangeEvent = RxTextView.textChanges(account).skip(1)
                .debounce(1, TimeUnit.SECONDS)
                .map(new Function<CharSequence, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull CharSequence c) throws Exception {
                        return RegexUtil.strCorrect(c.toString(), RegexUtil.ACCOUNT_REGEX);
                    }
                });
        composite.add(accountTextChangeEvent.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            accountLayout.setErrorEnabled(false);
                        } else {
                            accountLayout.setErrorEnabled(true);
                            accountLayout.setError("账号格式错误");
                        }
                    }
                }));

        Observable<Boolean> passwordTextChangeEvent = RxTextView.textChanges(password).skip(1)
                .debounce(1, TimeUnit.SECONDS)
                .map(new Function<CharSequence, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull CharSequence charSequence) throws Exception {
                        return RegexUtil.strCorrect(charSequence.toString(), RegexUtil.PASSWORD_REGEX);
                    }
                });
        composite.add(passwordTextChangeEvent.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            pwdLayout.setErrorEnabled(false);
                        } else {
                            pwdLayout.setErrorEnabled(true);
                            pwdLayout.setError("密码格式错误");
                        }
                    }
                }));

        composite.add(Observable.combineLatest(accountTextChangeEvent, passwordTextChangeEvent, new BiFunction<Boolean, Boolean, Boolean>() {
            @Override
            public Boolean apply(@NonNull Boolean aBoolean, @NonNull Boolean aBoolean2) throws Exception {
                return aBoolean && aBoolean2;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            login.setEnabled(true);
                            login.setBackgroundResource(R.drawable.button1);
                        } else {
                            login.setEnabled(false);
                            login.setBackgroundResource(R.drawable.button_unclick);
                        }
                    }
                }));

    }

}
