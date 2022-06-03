package com.imposterstech.storyreadingtracker.service;

import com.imposterstech.storyreadingtracker.Model.Request.FeedbackRequestModel;
import com.imposterstech.storyreadingtracker.Model.Request.StoryRequestModel;
import com.imposterstech.storyreadingtracker.Model.Response.FeedbackModel;
import com.imposterstech.storyreadingtracker.Model.Response.StoryModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FeedbackAPI {

    @POST("feedbacks/{storyId}")
    Call<FeedbackModel> createFeedback(@Body FeedbackRequestModel feedbackRequestModel,@Header("Authorization") String authorization, @Path("storyId") String storyId);
}
