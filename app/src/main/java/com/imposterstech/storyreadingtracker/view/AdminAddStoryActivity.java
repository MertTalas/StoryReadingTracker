package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imposterstech.storyreadingtracker.Model.Request.StoryRequestModel;
import com.imposterstech.storyreadingtracker.Model.Response.StoryModel;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentUser;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.service.StoryAPI;
import com.imposterstech.storyreadingtracker.service.UserAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminAddStoryActivity extends AppCompatActivity {
    UserAPI userAPI;
    StoryAPI storyAPI;
    private String BASE_URL="http://192.168.1.42:8080/story-app-ws/";
    Retrofit retrofit;
    SingletonCurrentUser currentUser;

    EditText editTextStoryTitle,editTextStoryContent;
    Button buttonAddStory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_story);
        init();

        buttonAddStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String storyTitle=editTextStoryTitle.getText().toString();
                String storyContent=editTextStoryContent.getText().toString();

                if(storyTitle.equals(null)&storyContent.equals(null)){
                    Toast.makeText(getApplicationContext(),"Title and content cannot be null!!",Toast.LENGTH_LONG).show();
                    return;
                }

                StoryRequestModel storyRequestModel=new StoryRequestModel();
                storyRequestModel.setStoryText(storyContent);
                storyRequestModel.setTitle(storyTitle);

                Call<StoryModel> call=storyAPI.createStory(storyRequestModel,currentUser.getToken());

                call.enqueue(new Callback<StoryModel>() {
                    @Override
                    public void onResponse(Call<StoryModel> call, Response<StoryModel> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Story added!!",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<StoryModel> call, Throwable t) {

                    }
                });


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
        storyAPI=retrofit.create(StoryAPI.class);
        currentUser=SingletonCurrentUser.getInstance();

        editTextStoryTitle=findViewById(R.id.editText_story_add_page_title);
        editTextStoryContent=findViewById(R.id.editText_story_add_page_content);
        buttonAddStory=findViewById(R.id.button_story_add_page_addstory);




    }
}