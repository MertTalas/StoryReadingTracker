package com.imposterstech.storyreadingtracker.service;

import com.imposterstech.storyreadingtracker.Model.Request.AvatarRequestModel;
import com.imposterstech.storyreadingtracker.Model.Request.StoryRequestModel;
import com.imposterstech.storyreadingtracker.Model.Response.AvatarModel;
import com.imposterstech.storyreadingtracker.Model.Response.StoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface AvatarAPI {

    @POST("avatars/buy")
    Call<AvatarModel> buyAvatar(@Body AvatarRequestModel avatarRequestModel, @Header("Authorization") String authorization);

    @GET("avatars/allAvatars")
    Call<List<AvatarModel>> getAllAvatars(@Header("Authorization") String authorization);

    @GET("avatars/userAvatars")
    Call<List<AvatarModel>> getUserAvatars(@Header("Authorization") String authorization);

    @PUT("avatars/selectAvatar")
    Call<AvatarModel> selectAvatar(@Body AvatarRequestModel avatarRequestModel, @Header("Authorization") String authorization);



}
