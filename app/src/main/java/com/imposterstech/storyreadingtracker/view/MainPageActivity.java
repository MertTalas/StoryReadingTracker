package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imposterstech.storyreadingtracker.BASEURL;
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

    private String BASE_URL= BASEURL.BASE_URL.getBase_URL();
    Retrofit retrofit;
    SingletonCurrentUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        token=getIntent().getStringExtra("token");

        init();

        setAdapter();





    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to background the app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainPageActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();


    }

    public void init(){
        Gson gson=new GsonBuilder().setLenient().create();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

         userAPI=retrofit.create(UserAPI.class);
        currentUser=SingletonCurrentUser.getInstance();


        recyclerViewOptions=findViewById(R.id.recyclerview_main_page);




    }
    public void setAdapter(){
        mainPageOptions=new ArrayList<>();

        MainPageOptions readStory=new MainPageOptions("Read Story",R.drawable.ic_main_read_story_option,"You can read a few stories and improve\nyour reading ability");
        MainPageOptions pastReadings=new MainPageOptions("Past Readings",R.drawable.ic_main_past_readings_option, "You can review the stories you\nhave already read");
        MainPageOptions profile=new MainPageOptions("Profile",R.drawable.ic_main_profile_option, "You can view your profile and\nupdate your information");
        MainPageOptions ranking=new MainPageOptions("Ranking",R.drawable.ic_main_ranking_option, "You can see your rank and score");
        MainPageOptions settings=new MainPageOptions("Settings",R.drawable.ic_main_settings_option, "You can make changes on app and\nsee settings");
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