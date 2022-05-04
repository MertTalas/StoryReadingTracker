package com.imposterstech.storyreadingtracker.service;

import com.imposterstech.storyreadingtracker.Model.Request.LoginRequestModel;
import com.imposterstech.storyreadingtracker.Model.UserModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserAPI {

    @GET("users/iremercin@test.com")
    Call<UserModel> getUser(@Header("Authorization") String authorization);

    @POST("users/login")
    Call<ResponseBody> login(@Body LoginRequestModel loginRequestModel);
}
