package com.example.administrator.school_circle.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.school_circle.R;
import com.example.administrator.school_circle.exception.ErrorPrint;
import com.example.administrator.school_circle.model.Result;
import com.example.administrator.school_circle.url.UrlBuilder;
import com.example.administrator.school_circle.util.RegexUtil;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function5;
import io.reactivex.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity{

    private CompositeDisposable composite;
    private TextView login;
    private Toolbar toolbar;
    private CircleProgressBar loading;
    private EditText account;
    private TextInputLayout accountLayout;
    private EditText password;
    private TextInputLayout pwdLayout;
    private EditText rPassword;
    private TextInputLayout rPwdLayout;
    private EditText userName;
    private TextInputLayout userNameLayout;
    private EditText emailName;
    private TextInputLayout emailLayout;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        composite = new CompositeDisposable();
        login = (TextView) findViewById(R.id.login);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        loading = (CircleProgressBar) findViewById(R.id.loading);
        account = (EditText) findViewById(R.id.account);
        accountLayout = (TextInputLayout) findViewById(R.id.account_layout);
        password = (EditText) findViewById(R.id.password);
        pwdLayout = (TextInputLayout) findViewById(R.id.pwd_layout);
        rPassword = (EditText) findViewById(R.id.r_password);
        rPwdLayout = (TextInputLayout) findViewById(R.id.r_pwd_layout);
        userName = (EditText) findViewById(R.id.user_name);
        userNameLayout = (TextInputLayout) findViewById(R.id.user_name_layout);
        emailName = (EditText) findViewById(R.id.email_name);
        emailLayout = (TextInputLayout) findViewById(R.id.email_layout);
        register = (Button) findViewById(R.id.register);

        setEditTextAction();
        setRegister();
        setLogin();
    }

    private void setEditTextAction(){
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

        Observable<Boolean> rPasswordTextChangeEvent = RxTextView.textChanges(rPassword).skip(1)
                .debounce(1, TimeUnit.SECONDS)
                .map(new Function<CharSequence, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull CharSequence charSequence) throws Exception {
                        return charSequence.toString().equals(password.getText().toString());
                    }
                });
        composite.add(rPasswordTextChangeEvent.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            rPwdLayout.setErrorEnabled(false);
                        } else {
                            rPwdLayout.setErrorEnabled(true);
                            rPwdLayout.setError("两次输入的密码不一致");
                        }
                    }
                }));

        Observable<Boolean> userNameTextChangeEvent = RxTextView.textChanges(userName).skip(1)
                .debounce(1, TimeUnit.SECONDS)
                .map(new Function<CharSequence, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull CharSequence charSequence) throws Exception {
                        return !userName.getText().toString().equals("");
                    }
                });
        composite.add(userNameTextChangeEvent.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            userNameLayout.setErrorEnabled(false);
                        } else {
                            userNameLayout.setErrorEnabled(true);
                            userNameLayout.setError("用户名不能为空");
                        }
                    }
                }));

        Observable<Boolean> emailTextChangeEvent = RxTextView.textChanges(emailName).skip(1)
                .debounce(1, TimeUnit.SECONDS)
                .map(new Function<CharSequence, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull CharSequence charSequence) throws Exception {
                        return RegexUtil.strCorrect(charSequence.toString(), RegexUtil.EMAIL_REGEX);
                    }
                });
        composite.add(emailTextChangeEvent.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            emailLayout.setErrorEnabled(false);
                        } else {
                            emailLayout.setErrorEnabled(true);
                            emailLayout.setError("邮箱格式错误");
                        }
                    }
                }));


        composite.add(Observable.combineLatest(accountTextChangeEvent, passwordTextChangeEvent,
                rPasswordTextChangeEvent, userNameTextChangeEvent, emailTextChangeEvent,
                new Function5<Boolean, Boolean, Boolean, Boolean, Boolean, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull Boolean aBoolean, @NonNull Boolean aBoolean2, @NonNull Boolean aBoolean3, @NonNull Boolean aBoolean4, @NonNull Boolean aBoolean5) throws Exception {
                        return aBoolean&&aBoolean2&&aBoolean3&&aBoolean4&&aBoolean5;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            register.setEnabled(true);
                            register.setBackgroundResource(R.drawable.button1);
                        } else {
                            register.setEnabled(false);
                            register.setBackgroundResource(R.drawable.button_unclick);
                        }
                    }
                }));
    }

    private void setRegister(){
        loading.setVisibility(View.INVISIBLE);
        register.setBackgroundResource(R.drawable.button_unclick);
        register.setEnabled(false);
        Disposable loginDisposable =  RxView.clicks(register)
                .throttleFirst(1, TimeUnit.SECONDS).map(new Function() {
                    @Override
                    public Object apply(@NonNull Object o) throws Exception {
                        loading.setVisibility(View.VISIBLE);
                        return register;
                    }
                }).subscribe(new Consumer() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {

                        String accountStr = account.getText().toString();
                        String passwordStr = password.getText().toString();
                        String userNameStr = userName.getText().toString();
                        String emailStr = emailName.getText().toString();
                        try {
                            UrlBuilder.build().register(accountStr, passwordStr,userNameStr,emailStr)
                                    .timeout(10, TimeUnit.SECONDS)
                                    .subscribeOn(Schedulers.io())
                                    .unsubscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<Result>() {
                                        @Override
                                        public void onSubscribe(@NonNull Disposable d) {
                                            composite.add(d);
                                        }

                                        @Override
                                        public void onNext(@NonNull Result userResult) {
                                            Toasty.success(RegisterActivity.this,"注册成功！", Toast.LENGTH_LONG,true).show();
                                        }

                                        @Override
                                        public void onError(@NonNull Throwable e) {
                                            loading.setVisibility(View.INVISIBLE);
                                            ErrorPrint.printOutTimeError(RegisterActivity.this);
                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                        }catch (Exception e){
                            ErrorPrint.printError("无法连接到服务器",RegisterActivity.this);
                            loading.setVisibility(View.INVISIBLE);
                        }
                    }
                });
        composite.add(loginDisposable);
    }

    private void setLogin(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        composite.dispose();
    }
}
