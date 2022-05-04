package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.imposterstech.storyreadingtracker.Model.MainPageOptions;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.adapter.RVMainPageOptionAdapter;

import java.util.ArrayList;

public class MainPageActivity extends AppCompatActivity {
    private String token;
    ArrayList<MainPageOptions> mainPageOptions;

    RecyclerView recyclerViewOptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        token=getIntent().getStringExtra("token");
        init();

    }
    public void init(){

        recyclerViewOptions=findViewById(R.id.recyclerview_main_page);

        mainPageOptions=new ArrayList<>();

        MainPageOptions readStory=new MainPageOptions("Read Story",R.drawable.ic_main_read_story_option);
        MainPageOptions pastReadings=new MainPageOptions("Past Readings",R.drawable.ic_main_past_readings_option);
        MainPageOptions profile=new MainPageOptions("Profile",R.drawable.ic_main_profile_option);
        MainPageOptions ranking=new MainPageOptions("Ranking",R.drawable.ic_main_ranking_option);
        MainPageOptions settings=new MainPageOptions("Settings",R.drawable.ic_main_settings_option);
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