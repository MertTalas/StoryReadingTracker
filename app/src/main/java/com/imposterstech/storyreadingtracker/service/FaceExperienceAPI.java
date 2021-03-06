package com.imposterstech.storyreadingtracker.service;

import com.imposterstech.storyreadingtracker.Model.Contour;
import com.imposterstech.storyreadingtracker.Model.Request.LoginRequestModel;
import com.imposterstech.storyreadingtracker.Model.Response.FaceExperienceModel;
import com.imposterstech.storyreadingtracker.Model.WordMicrophone;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FaceExperienceAPI {

    @POST("faceExperiences/story/{storyId}")
    Call<FaceExperienceModel> createReading(@Header("Authorization") String authorization, @Path("storyId") String storyId);

    @PUT("faceExperiences/{faceExperienceId}")
    Call<Void> addContour(@Header("Authorization") String authorization, @Path("faceExperienceId") String faceExperienceId,@Body Contour contour);

    @PUT("faceExperiences/{faceExperienceId}/all")
    Call<Void> addAllContour(@Header("Authorization") String authorization, @Path("faceExperienceId") String faceExperienceId,@Body List<Contour> contourList);


    @GET("faceExperiences/{faceExperienceId}/contours")
    Call<List<Contour>> getAllContour(@Header("Authorization") String authorization, @Path("faceExperienceId") String faceExperienceId);

    @GET("faceExperiences/{faceExperienceId}/microphone")
    Call<List<WordMicrophone>> getAllWords(@Header("Authorization") String authorization, @Path("faceExperienceId") String faceExperienceId);


    @PUT("faceExperiences/{faceExperienceId}/microphone")
    Call<Void> addWord(@Header("Authorization") String authorization, @Path("faceExperienceId") String faceExperienceId,@Body WordMicrophone wordMicrophone);




}
