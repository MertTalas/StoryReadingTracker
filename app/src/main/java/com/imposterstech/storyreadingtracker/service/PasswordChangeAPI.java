package com.imposterstech.storyreadingtracker.service;

import com.imposterstech.storyreadingtracker.Model.Request.PasswordChangeRequestModel;
import com.imposterstech.storyreadingtracker.Model.Request.StoryRequestModel;
import com.imposterstech.storyreadingtracker.Model.Response.PasswordChangeModel;
import com.imposterstech.storyreadingtracker.Model.Response.StoryModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PasswordChangeAPI {

    @POST("passwordChanges/email/{email}")
    Call<PasswordChangeModel> createVerificationCode( @Path("email") String email);


    @PUT("passwordChanges")
    Call<Void> changePassword( @Body PasswordChangeRequestModel passwordChangeRequestModel);




}
