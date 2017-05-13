package com.example.administrator.school_circle.url;

import com.example.administrator.school_circle.model.CourseTable;
import com.example.administrator.school_circle.model.Result;
import com.example.administrator.school_circle.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by BigGod on 2017-05-05.
 */
public interface SchoolCircleUrl {
    @GET("user/login")
    Observable<Result<User>> login(@Query("account") String account, @Query("password")String password);

    @GET("user/loginByToken")
    Observable<Result> loginByToken(@Query("token")String token);

    @FormUrlEncoded
    @POST("user/register")
    Observable<Result> register(@Field("account") String account, @Field("password")String password,
                                @Field("userName")String userName, @Field("email")String email);

    @GET("schoolData/courseTable")
    Observable<Result<List<CourseTable>>> getCourseTable(@Query("account") String account, @Query("password")String password,
                                                         @Query("year") String year, @Query("term")String term,
                                                         @Query("userId") String userId);
//    <!--url-->
//    <string name="route">localhost:9090</string>
//    <string name="register">/user/register</string>
//    <string name="login">/user/login?account=$param&amp;password=$param</string>
//    <string name="loginByToken">/user/loginByToken?token={}</string>
}
