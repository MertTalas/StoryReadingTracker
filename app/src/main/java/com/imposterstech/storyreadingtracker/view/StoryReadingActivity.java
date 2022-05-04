package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imposterstech.storyreadingtracker.Model.Response.StoryModel;
import com.imposterstech.storyreadingtracker.Model.Response.UserModel;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentUser;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.service.StoryAPI;
import com.imposterstech.storyreadingtracker.service.UserAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StoryReadingActivity extends AppCompatActivity {
    UserModel user;
    private String BASE_URL="http://10.0.2.2:8080/story-app-ws/";
    Retrofit retrofit;
    private String token;
    private StoryModel storyModel;

    private TextView textViewTitle,textViewStoryText;
    private ImageButton imageButtonRestart,imageButtonSkip,imageButtonExit,imageButtonCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_reading);


        init();

        //Retrofit & JSON

        Gson gson=new GsonBuilder().setLenient().create();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        getRandomStory();
    }

    public void init(){
        textViewStoryText=findViewById(R.id.textView_story_reading_page_story_text);
        textViewTitle=findViewById(R.id.textView_story_reading_page_title);
        imageButtonRestart=findViewById(R.id.ImageButton_toolbar_restart);
        imageButtonCamera=findViewById(R.id.ImageButton_toolbar_camera);
        imageButtonExit=findViewById(R.id.ImageButton_toolbar_exit);
        imageButtonSkip=findViewById(R.id.ImageButton_toolbar_skip);


        imageButtonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRandomStory();
            }
        });


    }

    private void getRandomStory(){
        SingletonCurrentUser currentUser=SingletonCurrentUser.getInstance();


        StoryAPI storyAPI=retrofit.create(StoryAPI.class);
        Call<StoryModel> call=storyAPI.getRandomStory(currentUser.getToken());

        call.enqueue(new Callback<StoryModel>() {
            @Override
            public void onResponse(Call<StoryModel> call, Response<StoryModel> response) {
                if(response.isSuccessful()){
                    storyModel= response.body();

                    textViewStoryText.setText(response.body().getStoryText());
                    textViewTitle.setText(response.body().getTitle());
                }
                else{
                    Toast.makeText(getApplicationContext(),"Something is wrong restart app.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StoryModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Something is wrong restart app.",Toast.LENGTH_LONG).show();

            }
        });

    }
}