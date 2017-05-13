package com.example.administrator.school_circle.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.school_circle.R;
import com.example.administrator.school_circle.customUi.CourseTableAdapter;
import com.example.administrator.school_circle.exception.ErrorPrint;
import com.example.administrator.school_circle.model.CourseTable;
import com.example.administrator.school_circle.model.CourseTableDao;
import com.example.administrator.school_circle.model.LeftCell;
import com.example.administrator.school_circle.model.Result;
import com.example.administrator.school_circle.model.TopCell;
import com.example.administrator.school_circle.url.UrlBuilder;
import com.example.administrator.school_circle.util.DaoBuilder;
import com.example.administrator.school_circle.util.FinalDataController;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.zhouchaoyuan.excelpanel.ExcelPanel;
import es.dmoral.toasty.Toasty;
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

/**
 * Created by BigGod on 2017-05-11.
 */
public class CourseTableFragment extends Fragment implements ExcelPanel.OnLoadMoreListener{
    private EditText account;

    private Date firstWeekMonthDayDate;

    private CompositeDisposable composite;
    private TextInputLayout accountLayout;
    private EditText password;
    private TextInputLayout pwdLayout;
    private Button get;
    private LinearLayout addCourseTable;
    private CircleProgressBar loading;
    private LinearLayout showCourseTable;
    private ExcelPanel courseTable;
    private CourseTableAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // do something such as init data
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frame_course_table, container, false);
        // init view in the fragment
        initView(view);
        return view;
    }

    private void initView(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            firstWeekMonthDayDate = sdf.parse("2017-02-20");
        } catch (ParseException e) {
            Toasty.error(CourseTableFragment.this.getContext(),"日期格式化错误", Toast.LENGTH_LONG).show();
        }


        account = (EditText) view.findViewById(R.id.account);
        accountLayout = (TextInputLayout) view.findViewById(R.id.account_layout);
        password = (EditText) view.findViewById(R.id.password);
        pwdLayout = (TextInputLayout) view.findViewById(R.id.pwd_layout);
        get = (Button) view.findViewById(R.id.get);
        addCourseTable = (LinearLayout) view.findViewById(R.id.addCourseTable);

        loading = (CircleProgressBar) view.findViewById(R.id.loading);
        composite = new CompositeDisposable();
        showCourseTable = (LinearLayout) view.findViewById(R.id.showCourseTable);

        courseTable = (ExcelPanel) view.findViewById(R.id.course_table);
        adapter = new CourseTableAdapter(this.getContext());
        courseTable.setAdapter(adapter);
        courseTable.setOnLoadMoreListener(this);//your Activity or Fragment implement ExcelPanel.OnLoadMoreListener
        setAddCourseTable();

        setGetButtonAction();
        setEditTextAction();


    }

    private void setAddCourseTable(){
        CourseTableDao dao = DaoBuilder.getInstance(CourseTableFragment.this.getContext()).getDaoSession().getCourseTableDao();

        List<CourseTable> courseTables = dao.loadAll();
        if(courseTables.size()==0) {
            addCourseTable.setVisibility(View.VISIBLE);
            addCourseTable.setEnabled(true);
            showCourseTable.setEnabled(false);
            showCourseTable.setVisibility(View.INVISIBLE);
        }else {
            addCourseTable.setVisibility(View.INVISIBLE);
            addCourseTable.setEnabled(false);
            showCourseTable.setEnabled(true);
            showCourseTable.setVisibility(View.VISIBLE);
            Date current = new Date();
            long time = current.getTime()-firstWeekMonthDayDate.getTime();
            int week = (int)(time/(24*60*60*1000*7));
            setCourseTableAdapterData(courseTables,12);
        }
    }

    private void setCourseTableAdapterData(List<CourseTable> courseTables,int week){
        List<LeftCell> leftCells = new ArrayList<>();

        String[] times = new String[]{
                "8:20","9:15", "10:20","11:15", "14:00","14:55", "16:00","16:55", "19:00","19:55", "20:55"
        };

        for (int i =1;i<=times.length;i++){
            LeftCell leftCell = new LeftCell(i+"",times[i-1]);
            leftCells.add(leftCell);
        }

        List<TopCell> topCells = new ArrayList<>();

        long oneDay = 24*60*60*1000;

        long date = firstWeekMonthDayDate.getTime()+week*7*oneDay;

        String[] weekday = new String[]{
                "周一", "周二","周三","周四","周五","周六","周日"
        };


        for (int i =0;i<weekday.length;i++){
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            String firstDate = sdf.format(date);
            topCells.add(new TopCell(firstDate,weekday[i]));
        }

        List<List<CourseTable>> tablesData = new ArrayList<>();

        for(int i = 0;i<times.length;i++){
            List<CourseTable> list = new ArrayList<>();
            for(int j = 0;j<7;j++){
                list.add(null);
            }
            tablesData.add(list);
        }

        for(CourseTable courseTable:courseTables){
            if(courseTable.getWeek()==week){
                tablesData.get(courseTable.getLesson()).set(courseTable.getWeekday(),courseTable);
            }
        }

        adapter.setAllData(leftCells, topCells, tablesData);
        adapter.enableFooter();//load more, you can also call disableFooter()----default
        adapter.enableHeader();
    }

    private void setGetButtonAction() {
        loading.setVisibility(View.INVISIBLE);
        get.setBackgroundResource(R.drawable.button_unclick);
        get.setEnabled(false);
        Disposable loginDisposable = RxView.clicks(get)
                .throttleFirst(1, TimeUnit.SECONDS).map(new Function() {
                    @Override
                    public Object apply(@NonNull Object o) throws Exception {
                        loading.setVisibility(View.VISIBLE);
                        return get;
                    }
                }).subscribe(new Consumer() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {

                        String accountStr = account.getText().toString();
                        String passwordStr = password.getText().toString();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(new Date());
                        Integer year =  calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH) + 1;
                        Integer term = 1;
                        if (month > 7 || term < 2) {
                            term = 1;
                        } else {
                            return;
                        }
                        try {
                            UrlBuilder.build().getCourseTable(accountStr, passwordStr, (year - 1) + "-" + year, term.toString(), FinalDataController.getUserId().toString())
                                    .timeout(30, TimeUnit.SECONDS)
                                    .subscribeOn(Schedulers.io())
                                    .unsubscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<Result<List<CourseTable>>>() {
                                        @Override
                                        public void onSubscribe(@NonNull Disposable d) {
                                            composite.add(d);
                                        }

                                        @Override
                                        public void onNext(@NonNull Result<List<CourseTable>> userResult) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                CourseTableDao dao = DaoBuilder.getInstance(CourseTableFragment.this.getContext()).getDaoSession().getCourseTableDao();
                                                dao.deleteAll();
                                                List<CourseTable> courses = userResult.getRet();
                                                for (int i = 0;i<courses.size();i++) {
                                                    CourseTable course = courses.get(i);
                                                    course.setId(i);
                                                    dao.insert(course);
                                                }
                                            }

                                            loading.setVisibility(View.INVISIBLE);
                                            setAddCourseTable();
                                        }

                                        @Override
                                        public void onError(@NonNull Throwable e) {
                                            loading.setVisibility(View.INVISIBLE);
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                ErrorPrint.printOutTimeError(CourseTableFragment.this.getContext());
                                            }
                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                        } catch (Exception e) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                ErrorPrint.printError("无法连接到服务器", CourseTableFragment.this.getContext());
                            }
                            loading.setVisibility(View.INVISIBLE);
                        }
                    }
                });
        composite.add(loginDisposable);
    }


    private void setEditTextAction() {
        Observable<Boolean> accountTextChangeEvent = RxTextView.textChanges(account).skip(1)
                .debounce(1, TimeUnit.SECONDS)
                .map(new Function<CharSequence, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull CharSequence c) throws Exception {
                        return c != "";
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
                        return charSequence != "";
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
                            get.setEnabled(true);
                            get.setBackgroundResource(R.drawable.button1);
                        } else {
                            get.setEnabled(false);
                            get.setBackgroundResource(R.drawable.button_unclick);
                        }
                    }
                }));

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onLoadHistory() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        composite.dispose();
    }
}
