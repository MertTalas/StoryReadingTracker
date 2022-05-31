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
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentUser;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.adapter.RVAdminUpdateStoryAdapter;
import com.imposterstech.storyreadingtracker.adapter.RVStoryReadingPageAdapter;
import com.imposterstech.storyreadingtracker.service.StoryAPI;
import com.imposterstech.storyreadingtracker.service.StoryUserAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminEditStoryActivity extends AppCompatActivity {
    ArrayList<StoryModel> allStories;
    RecyclerView recyclerViewAllStories;

    private String BASE_URL="http://192.168.1.42:8080/story-app-ws/";
    Retrofit retrofit;
    StoryAPI storyAPI;
    SingletonCurrentUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_story);
        init();
    }


    void init(){
        recyclerViewAllStories=findViewById(R.id.recyclerview_admin_editstory_page);

        Gson gson=new GsonBuilder().setLenient().create();
        currentUser=SingletonCurrentUser.getInstance();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        storyAPI=retrofit.create(StoryAPI.class);

        Call<List<StoryModel>> call=storyAPI.getAllStories(currentUser.getToken());

        call.enqueue(new Callback<List<StoryModel>>() {
            @Override
            public void onResponse(Call<List<StoryModel>> call, Response<List<StoryModel>> response) {
                if(response.isSuccessful()){
                    List<StoryModel> tempList=response.body();
                    recyclerViewAllStories.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    allStories=new ArrayList<>(tempList);

                    RVAdminUpdateStoryAdapter rvAdminUpdateStoryAdapter=new RVAdminUpdateStoryAdapter(allStories);
                    recyclerViewAllStories.setAdapter(rvAdminUpdateStoryAdapter);



                }
            }

            @Override
            public void onFailure(Call<List<StoryModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"API connection problem",Toast.LENGTH_LONG).show();
                finish();
            }
        });






    }



}