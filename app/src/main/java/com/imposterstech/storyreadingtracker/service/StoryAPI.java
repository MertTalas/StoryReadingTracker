package com.imposterstech.storyreadingtracker.service;

import com.imposterstech.storyreadingtracker.Model.Request.StoryRequestModel;
import com.imposterstech.storyreadingtracker.Model.Response.StoryModel;
import com.imposterstech.storyreadingtracker.Model.Response.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StoryAPI {

    @GET("stories/randomStory")
    Call<StoryModel> getRandomStory(@Header("Authorization") String authorization);

    @POST("stories")
    Call<StoryModel> createStory(@Body StoryRequestModel storyRequestModel, @Header("Authorization") String authorization);

    @GET("stories/allStories")
    Call<List<StoryModel>> getAllStories(@Header("Authorization") String authorization);

    @PUT("stories/{storyId}")
    Call<StoryModel> updateStory(@Body StoryRequestModel storyRequestModel, @Header("Authorization") String authorization,@Path("storyId") String storyId);

    @PUT("stories/{storyId}/removeStory")
    Call<Void> removeStory(@Header("Authorization") String authorization,@Path("storyId") String storyId);


}
