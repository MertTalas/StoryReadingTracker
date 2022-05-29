package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imposterstech.storyreadingtracker.Model.MainPageOptions;
import com.imposterstech.storyreadingtracker.Model.Response.UserModel;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentUser;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.adapter.RVAdminPageOptionAdapter;
import com.imposterstech.storyreadingtracker.adapter.RVMainPageOptionAdapter;
import com.imposterstech.storyreadingtracker.service.UserAPI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminMainPageActivity extends AppCompatActivity {

    ArrayList<String> adminMainPageOptions;

    RecyclerView recyclerViewOptions;
    UserAPI userAPI;
    private String BASE_URL="http://192.168.1.21:8080/story-app-ws/";
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_page);
        init();
        setAdapter();


    }




    public void init(){
        Gson gson=new GsonBuilder().setLenient().create();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        userAPI=retrofit.create(UserAPI.class);
        SingletonCurrentUser currentUser=SingletonCurrentUser.getInstance();


        recyclerViewOptions=findViewById(R.id.recyclerview_admin_main_page);




    }

    public void setAdapter(){
        adminMainPageOptions=new ArrayList<>();


        adminMainPageOptions.add("Add Story");
        adminMainPageOptions.add("Update Story");
        adminMainPageOptions.add("Remove Story");
        adminMainPageOptions.add("List Feedbacks");




        recyclerViewOptions.setLayoutManager(new LinearLayoutManager(this));
        RVAdminPageOptionAdapter optionsAdapter=new RVAdminPageOptionAdapter(adminMainPageOptions);
        recyclerViewOptions.setAdapter(optionsAdapter);
    }



}