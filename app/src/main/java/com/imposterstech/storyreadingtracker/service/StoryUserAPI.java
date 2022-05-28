package com.imposterstech.storyreadingtracker.service;

import com.imposterstech.storyreadingtracker.Model.Request.ReadingExperienceRequestModel;
import com.imposterstech.storyreadingtracker.Model.Request.RegisterRequestModel;
import com.imposterstech.storyreadingtracker.Model.Response.SimpleStoryUserModel;
import com.imposterstech.storyreadingtracker.Model.Response.StoryUserModel;
import com.imposterstech.storyreadingtracker.Model.Response.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface StoryUserAPI {

    @POST("storiesUsers/{storyId}")
    Call<StoryUserModel> createReadingExperience(@Body ReadingExperienceRequestModel readingExperienceRequestModel, @Path("storyId") String storyId, @Header("Authorization") String authorization);


    @GET("storiesUsers/getStories/{userId}")
    Call<List<SimpleStoryUserModel>> getUserStories(@Path("userId") String storyId, @Header("Authorization") String authorization);





}
