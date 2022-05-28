package com.imposterstech.storyreadingtracker.service;

import com.imposterstech.storyreadingtracker.Model.Response.RoleModel;
import com.imposterstech.storyreadingtracker.Model.Response.SimpleStoryUserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface RoleAPI {


    @GET("roles")
    Call<List<RoleModel>> getRolesOfCurrentUser(@Header("Authorization") String authorization);

}
