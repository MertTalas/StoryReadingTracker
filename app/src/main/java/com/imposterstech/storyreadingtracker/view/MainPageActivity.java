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
import com.imposterstech.storyreadingtracker.adapter.RVMainPageOptionAdapter;
import com.imposterstech.storyreadingtracker.service.UserAPI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPageActivity extends AppCompatActivity {
    private String token;
    ArrayList<MainPageOptions> mainPageOptions;

    RecyclerView recyclerViewOptions;
    UserAPI userAPI;
    private String BASE_URL="http://192.168.1.42:8080/story-app-ws/";
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        token=getIntent().getStringExtra("token");

        init();
        setCurrentUser();
        setAdapter();





    }
    public void setCurrentUser(){

        SingletonCurrentUser currentUser=SingletonCurrentUser.getInstance();
        Call<UserModel> currentUserCall= userAPI.getCurrentUser(currentUser.getToken());
        currentUserCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    SingletonCurrentUser currentUser=SingletonCurrentUser.getInstance();
                    currentUser.setLoggedUser(response.body());
               }

            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("hataaa","noluyoo");
            }
        });
    }

    public void init(){
        Gson gson=new GsonBuilder().setLenient().create();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

         userAPI=retrofit.create(UserAPI.class);



        recyclerViewOptions=findViewById(R.id.recyclerview_main_page);




    }
    public void setAdapter(){
        mainPageOptions=new ArrayList<>();

        MainPageOptions readStory=new MainPageOptions("Read Story",R.drawable.ic_main_read_story_option);
        MainPageOptions pastReadings=new MainPageOptions("Past Readings",R.drawable.ic_main_past_readings_option);
        MainPageOptions profile=new MainPageOptions("Profile",R.drawable.ic_main_profile_option);
        MainPageOptions ranking=new MainPageOptions("Ranking",R.drawable.ic_main_ranking_option);
        MainPageOptions settings=new MainPageOptions("Settings",R.drawable.ic_main_settings_option);
        mainPageOptions.add(readStory);
        mainPageOptions.add(pastReadings);
        mainPageOptions.add(profile);
        mainPageOptions.add(ranking);
        mainPageOptions.add(settings);

        recyclerViewOptions.setLayoutManager(new LinearLayoutManager(this));
        RVMainPageOptionAdapter optionsAdapter=new RVMainPageOptionAdapter(mainPageOptions);
        recyclerViewOptions.setAdapter(optionsAdapter);
    }

}