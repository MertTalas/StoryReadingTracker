package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imposterstech.storyreadingtracker.BASEURL;
import com.imposterstech.storyreadingtracker.Model.AvatarOptions;
import com.imposterstech.storyreadingtracker.Model.Response.AvatarModel;
import com.imposterstech.storyreadingtracker.Model.Response.SimpleStoryUserModel;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentUser;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.adapter.RVAvatarOptionAdapter;
import com.imposterstech.storyreadingtracker.adapter.RVSettingsPageOptionAdapter;
import com.imposterstech.storyreadingtracker.service.AvatarAPI;
import com.imposterstech.storyreadingtracker.service.StoryAPI;
import com.imposterstech.storyreadingtracker.service.StoryUserAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AvatarActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<AvatarModel> allAvatars;

    private String BASE_URL= BASEURL.BASE_URL.getBase_URL();
    Retrofit retrofit;
    AvatarAPI avatarAPI;
    SingletonCurrentUser currentUser;
    List<AvatarModel> avatars;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        init();
    }

    void init(){
        recyclerView=findViewById(R.id.rv_avatar);

        Gson gson=new GsonBuilder().setLenient().create();
        currentUser=SingletonCurrentUser.getInstance();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        avatarAPI=retrofit.create(AvatarAPI.class);




        Call<List<AvatarModel>> getCall=avatarAPI.getAllAvatars(currentUser.getToken());
        getCall.enqueue(new Callback<List<AvatarModel>>() {
            @Override
            public void onResponse(Call<List<AvatarModel>> call, Response<List<AvatarModel>> response) {
                if(response.isSuccessful()){
                    List<AvatarModel> tempList=response.body();
                    allAvatars=new ArrayList<>(tempList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    RVAvatarOptionAdapter optionsAdapter=new RVAvatarOptionAdapter(allAvatars);
                    recyclerView.setAdapter(optionsAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<AvatarModel>> call, Throwable t) {



            }
        });






    }


}