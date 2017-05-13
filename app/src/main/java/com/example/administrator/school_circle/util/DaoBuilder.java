package com.example.administrator.school_circle.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.school_circle.model.DaoMaster;
import com.example.administrator.school_circle.model.DaoSession;

/**
 * Created by BigGod on 2017-05-05.
 */
public class DaoBuilder {
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static DaoBuilder instance;
    public static DaoBuilder getInstance(Context context){
        if(instance == null){
            instance = new DaoBuilder();
            instance.mHelper = new DaoMaster.DevOpenHelper(context, "school_circle_db", null);
            instance.db = instance.mHelper.getWritableDatabase();
            // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
            instance.mDaoMaster = new DaoMaster(instance.db);
            instance.mDaoSession = instance.mDaoMaster.newSession();
        }
        return instance;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
