package com.example.administrator.school_circle.url;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;


/**
 * Created by BigGod on 2017-05-05.
 */
public class UrlBuilder {
    private static final String ROUTE = "http://192.168.191.1:9090/";
    private static SchoolCircleUrl instance;
    public static SchoolCircleUrl build(){
        if (instance==null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ROUTE)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            instance = retrofit.create(SchoolCircleUrl.class);
        }
        return instance;
    }
}
