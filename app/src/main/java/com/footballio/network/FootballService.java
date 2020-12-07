package com.footballio.retrofit;

import com.footballio.model.login.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FootballService {

    @POST("user/signup")
    Call<User> createUser(@Body User user);

    @GET("user/login")
    Call<User> getLogindetails(@Query("email") String email, @Query("password") String password);

    @GET("user/ForgotPassword")
    Call<User> ForgotPassword(@Query("email") String email);

    @POST("user/UpdatePassword")
    Call<User> UpadatePassword(@Body User user);
}
