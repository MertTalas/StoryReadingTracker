package com.imposterstech.storyreadingtracker.service;

import com.imposterstech.storyreadingtracker.Model.Request.StoryRequestModel;
import com.imposterstech.storyreadingtracker.Model.Response.StoryModel;
import com.imposterstech.storyreadingtracker.Model.Response.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface StoryAPI {

    @GET("stories/randomStory")
    Call<StoryModel> getRandomStory(@Header("Authorization") String authorization);

    @POST("stories")
    Call<StoryModel> createStory(@Body StoryRequestModel storyRequestModel, @Header("Authorization") String authorization);


}
