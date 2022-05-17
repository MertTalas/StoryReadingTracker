package com.imposterstech.storyreadingtracker.service;

import com.imposterstech.storyreadingtracker.Model.Contour;
import com.imposterstech.storyreadingtracker.Model.Request.LoginRequestModel;
import com.imposterstech.storyreadingtracker.Model.Response.FaceExperienceModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FaceExperienceAPI {

    @POST("faceExperiences/story/{storyId}")
    Call<FaceExperienceModel> createReading(@Header("Authorization") String authorization, @Path("storyId") String storyId);

    @PUT("faceExperiences/{faceExperienceId}")
    Call<Void> addContour(@Header("Authorization") String authorization, @Path("faceExperienceId") String faceExperienceId,@Body Contour contour);

}
