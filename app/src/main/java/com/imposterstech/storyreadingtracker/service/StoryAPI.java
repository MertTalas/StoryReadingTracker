package com.imposterstech.storyreadingtracker.service;

import com.imposterstech.storyreadingtracker.Model.Response.StoryModel;
import com.imposterstech.storyreadingtracker.Model.Response.UserModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface StoryAPI {

    @GET("stories/randomStory")
    Call<StoryModel> getRandomStory(@Header("Authorization") String authorization);
}
