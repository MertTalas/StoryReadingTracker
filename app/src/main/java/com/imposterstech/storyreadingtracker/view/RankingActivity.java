package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imposterstech.storyreadingtracker.BASEURL;
import com.imposterstech.storyreadingtracker.Model.Response.FeedbackModel;
import com.imposterstech.storyreadingtracker.Model.Response.UserModel;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentUser;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.adapter.RVAdminFeedbackAdapter;
import com.imposterstech.storyreadingtracker.adapter.RVRankingPageAdapter;
import com.imposterstech.storyreadingtracker.service.FeedbackAPI;
import com.imposterstech.storyreadingtracker.service.UserAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RankingActivity extends AppCompatActivity {

    ArrayList<UserModel> allUsers;

    RecyclerView recyclerViewRankings;
    UserAPI userAPI;
    private String BASE_URL= BASEURL.BASE_URL.getBase_URL();
    Retrofit retrofit;
    SingletonCurrentUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
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
        currentUser=SingletonCurrentUser.getInstance();


        recyclerViewRankings=findViewById(R.id.recyclerview_ranking_page);




    }

    public void setAdapter(){
        allUsers=new ArrayList<>();

        Call<List<UserModel>> call=userAPI.getAllNormalUserListByPoints(currentUser.getToken());

        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if(response.isSuccessful()){
                    List<UserModel> tempList=response.body();
                    recyclerViewRankings.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    allUsers=new ArrayList<>(tempList);

                    RVRankingPageAdapter rankingPageAdapter=new RVRankingPageAdapter(allUsers);
                    recyclerViewRankings.setAdapter(rankingPageAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

            }
        });







    }
}