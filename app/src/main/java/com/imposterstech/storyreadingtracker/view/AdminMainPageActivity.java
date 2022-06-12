package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.imposterstech.storyreadingtracker.adapter.RVAdminPageOptionAdapter;
import com.imposterstech.storyreadingtracker.adapter.RVMainPageOptionAdapter;
import com.imposterstech.storyreadingtracker.service.UserAPI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminMainPageActivity extends AppCompatActivity {

    ArrayList<String> adminMainPageOptions;

    RecyclerView recyclerViewOptions;
    UserAPI userAPI;

    private String BASE_URL= BASEURL.BASE_URL.getBase_URL();

    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_page);
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
                        AdminMainPageActivity.super.onBackPressed();
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
        SingletonCurrentUser currentUser=SingletonCurrentUser.getInstance();


        recyclerViewOptions=findViewById(R.id.recyclerview_admin_main_page);




    }

    public void setAdapter(){
        adminMainPageOptions=new ArrayList<>();


        adminMainPageOptions.add("Add Story");
        adminMainPageOptions.add("Update Story");
        adminMainPageOptions.add("Remove Story");
        adminMainPageOptions.add("List Feedbacks");
        adminMainPageOptions.add("Logout");




        recyclerViewOptions.setLayoutManager(new LinearLayoutManager(this));
        RVAdminPageOptionAdapter optionsAdapter=new RVAdminPageOptionAdapter(adminMainPageOptions);
        recyclerViewOptions.setAdapter(optionsAdapter);
    }



}