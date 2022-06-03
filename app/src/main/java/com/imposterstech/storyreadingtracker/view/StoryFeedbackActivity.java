package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imposterstech.storyreadingtracker.Model.Request.FeedbackRequestModel;
import com.imposterstech.storyreadingtracker.Model.Request.StoryRequestModel;
import com.imposterstech.storyreadingtracker.Model.Response.FeedbackModel;
import com.imposterstech.storyreadingtracker.Model.Response.StoryModel;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentStoryReading;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentUser;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.service.FeedbackAPI;
import com.imposterstech.storyreadingtracker.service.StoryAPI;
import com.imposterstech.storyreadingtracker.service.UserAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StoryFeedbackActivity extends AppCompatActivity {

    RatingBar ratingBar;
    EditText textViewFeedback;
    Button buttonSubmit;

    FeedbackAPI feedbackAPI;

    private String BASE_URL="http://192.168.1.42:8080/story-app-ws/";
    Retrofit retrofit;
    SingletonCurrentUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        init();
    }

    void init(){

        Gson gson=new GsonBuilder().setLenient().create();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        feedbackAPI=retrofit.create(FeedbackAPI.class);
        currentUser=SingletonCurrentUser.getInstance();

        ratingBar=findViewById(R.id.ratingBar_feedback);
        textViewFeedback=findViewById(R.id.editTextText_feedback_body);
        buttonSubmit=findViewById(R.id.submit_feedback_button);


        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float feedbackRate=ratingBar.getRating();
                String feedbackBody=textViewFeedback.getText().toString();

                FeedbackRequestModel feedbackRequestModel=new FeedbackRequestModel();
                feedbackRequestModel.setFeedbackText(feedbackBody);
                feedbackRequestModel.setFeedbackRate(feedbackRate);

                SingletonCurrentStoryReading singletonCurrentStoryReading=SingletonCurrentStoryReading.getInstance();
                StoryModel storyModel = singletonCurrentStoryReading.getStoryModel();
                Call<FeedbackModel> call=feedbackAPI.createFeedback(feedbackRequestModel, currentUser.getToken(), storyModel.getStoryId());
                call.enqueue(new Callback<FeedbackModel>() {
                    @Override
                    public void onResponse(Call<FeedbackModel> call, Response<FeedbackModel> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Feedback submit is completed!!",Toast.LENGTH_LONG).show();
                            finish();
                        }

                    }

                    @Override
                    public void onFailure(Call<FeedbackModel> call, Throwable t) {

                    }
                });


       //         if(feedbackRate==0){
       //             Toast.makeText(getApplicationContext(),"Please rate the app!",Toast.LENGTH_LONG).show();
       //             return;
       //         }


            }
        });


    }
}