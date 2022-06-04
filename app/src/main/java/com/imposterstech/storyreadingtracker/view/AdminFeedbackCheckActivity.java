package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imposterstech.storyreadingtracker.Model.Response.FeedbackModel;
import com.imposterstech.storyreadingtracker.Model.Response.StoryModel;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentUser;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.adapter.RVAdminFeedbackAdapter;
import com.imposterstech.storyreadingtracker.adapter.RVAdminPageOptionAdapter;
import com.imposterstech.storyreadingtracker.adapter.RVAdminRemoveStoryAdapter;
import com.imposterstech.storyreadingtracker.service.FeedbackAPI;
import com.imposterstech.storyreadingtracker.service.UserAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminFeedbackCheckActivity extends AppCompatActivity {


    ArrayList<FeedbackModel> allFeedbacks;

    RecyclerView recyclerViewFeedbacks;
    FeedbackAPI feedbackAPI;
    private String BASE_URL="http://192.168.1.21:8080/story-app-ws/";
    Retrofit retrofit;
    SingletonCurrentUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_feedback_check);
        init();
        setAdapter();
    }






    public void init(){
        Gson gson=new GsonBuilder().setLenient().create();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        feedbackAPI=retrofit.create(FeedbackAPI.class);
        currentUser=SingletonCurrentUser.getInstance();


        recyclerViewFeedbacks=findViewById(R.id.recyclerview_admin_feedback_page);




    }

    public void setAdapter(){
        allFeedbacks=new ArrayList<>();

        Call<List<FeedbackModel>> call=feedbackAPI.getAllFeedbacks(currentUser.getToken());

        call.enqueue(new Callback<List<FeedbackModel>>() {
            @Override
            public void onResponse(Call<List<FeedbackModel>> call, Response<List<FeedbackModel>> response) {
                if(response.isSuccessful()){
                    List<FeedbackModel> tempList=response.body();
                    recyclerViewFeedbacks.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    allFeedbacks=new ArrayList<>(tempList);

                    RVAdminFeedbackAdapter feedbackAdapter=new RVAdminFeedbackAdapter(allFeedbacks);
                    recyclerViewFeedbacks.setAdapter(feedbackAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<FeedbackModel>> call, Throwable t) {

            }
        });





    }



}