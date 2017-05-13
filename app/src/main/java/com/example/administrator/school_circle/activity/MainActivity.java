package com.example.administrator.school_circle.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.administrator.school_circle.R;
import com.example.administrator.school_circle.customUi.NoScrollViewPager;
import com.example.administrator.school_circle.exception.ErrorPrint;
import com.example.administrator.school_circle.model.Result;
import com.example.administrator.school_circle.model.User;
import com.example.administrator.school_circle.model.UserDao;
import com.example.administrator.school_circle.url.UrlBuilder;
import com.example.administrator.school_circle.util.DaoBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import devlight.io.library.ntb.NavigationTabBar;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private NavigationTabBar navigationTabBar;
    private ArrayList<NavigationTabBar.Model> models;
    private List<Fragment> viewContain;
    private String[] colors;
    private NoScrollViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getLoginStatus();
        initUi();
        initNavigationTabBar();
    }

    private void initUi(){
        navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        viewPager = (NoScrollViewPager) findViewById(R.id.vp_horizontal_ntb);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewContain = new ArrayList<>();
        viewContain.add(new Fragment());
        viewContain.add(new Fragment());
        viewContain.add(new Fragment());
        viewContain.add(new CourseTableFragment());
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return viewContain.size();
            }

            @Override
            public Fragment getItem(int position) {
                return viewContain.get(position);
            }
        });
    }

    private void getLoginStatus() {
        UserDao userDao = DaoBuilder.getInstance(this).getDaoSession().getUserDao();
        List<User> users = userDao.loadAll();
        if (users.size() == 0) {
            turnToLogin();
        } else {
            String token = users.get(0).getToken();
            UrlBuilder.build().loginByToken(token)
                    .throttleFirst(2, TimeUnit.SECONDS).timeout(60, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Result>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(@NonNull Result result) {
                            if (result.getRc() != 0) {
                                turnToLogin();
                            }
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            ErrorPrint.printOutTimeError(MainActivity.this);
                            turnToLogin();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    private void initNavigationTabBar(){
        models = new ArrayList<>();
        colors = new String[]{"#df5a55", "#f9bb72", "#76afcf","#dd6495"};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            models.add(
                    new NavigationTabBar.Model.Builder(
                            getResources().getDrawable(R.drawable.ic_third, null),
                            Color.parseColor(colors[0])
                    ).title("动态")
                            .badgeTitle("NTB")
                            .build()
            );
            models.add(
                    new NavigationTabBar.Model.Builder(
                            getResources().getDrawable(R.drawable.ic_second, null),
                            Color.parseColor(colors[1])
                    ).title("消息")
                            .badgeTitle("state")
                            .build()
            );
            models.add(
                    new NavigationTabBar.Model.Builder(
                            getResources().getDrawable(R.drawable.ic_seventh, null),
                            Color.parseColor(colors[2])
                    ).title("好友")
                            .badgeTitle("state")
                            .build()
            );
            models.add(
                    new NavigationTabBar.Model.Builder(
                            getResources().getDrawable(R.drawable.ic_sixth, null),
                            Color.parseColor(colors[3])
                    ).title("课程表")
                            .badgeTitle("icon")
                            .build()
            );
        }
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 2);
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);
    }

    private void turnToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

}
