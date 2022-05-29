package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imposterstech.storyreadingtracker.Model.Response.SimpleStoryUserModel;
import com.imposterstech.storyreadingtracker.Model.Response.StoryModel;
import com.imposterstech.storyreadingtracker.Model.Response.StoryUserModel;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentUser;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.adapter.RVStoryReadingPageAdapter;
import com.imposterstech.storyreadingtracker.service.StoryUserAPI;
import com.imposterstech.storyreadingtracker.service.UserAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PastReadingActivity extends AppCompatActivity {

    ArrayList<SimpleStoryUserModel> pastReadings;
    RecyclerView recyclerViewPastReadings;

    private String BASE_URL="http://192.168.1.42:8080/story-app-ws/";
    Retrofit retrofit;
    StoryUserAPI storyUserAPI;
    SingletonCurrentUser currentUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_reading);

        init();




    }

    void init(){
        recyclerViewPastReadings=findViewById(R.id.recyclerview_past_reading_page);

        Gson gson=new GsonBuilder().setLenient().create();
        currentUser=SingletonCurrentUser.getInstance();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        storyUserAPI=retrofit.create(StoryUserAPI.class);

        Call<List<SimpleStoryUserModel>> call=storyUserAPI.getUserStories(currentUser.getLoggedUser().getUserId(),currentUser.getToken());

        call.enqueue(new Callback<List<SimpleStoryUserModel>>() {
            @Override
            public void onResponse(Call<List<SimpleStoryUserModel>> call, Response<List<SimpleStoryUserModel>> response) {
                if(response.isSuccessful()){
                    List<SimpleStoryUserModel> tempList=response.body();
                    recyclerViewPastReadings.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    pastReadings=new ArrayList(tempList);
                    RVStoryReadingPageAdapter rvStoryReadingPageAdapter= new RVStoryReadingPageAdapter(pastReadings);
                    recyclerViewPastReadings.setAdapter(rvStoryReadingPageAdapter);


                }
            }

            @Override
            public void onFailure(Call<List<SimpleStoryUserModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"API connection problem",Toast.LENGTH_LONG).show();
                finish();

            }
        });




    }

}