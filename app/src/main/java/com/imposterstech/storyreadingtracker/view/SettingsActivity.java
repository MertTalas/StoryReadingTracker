package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.imposterstech.storyreadingtracker.Model.MainPageOptions;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.adapter.RVSettingsPageOptionAdapter;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {
    ArrayList<MainPageOptions> mainPageOptions;

    RecyclerView recyclerViewOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
    }
    public void init(){
        recyclerViewOptions=findViewById(R.id.recyclerview_settings_page);

        mainPageOptions=new ArrayList<>();

        MainPageOptions changeLanguage=new MainPageOptions("Change Language",R.drawable.ic_settings_change_language,"yapılacak kardeş");
        MainPageOptions giveFeedback=new MainPageOptions("Give Feedback",R.drawable.ic_settings_give_feedback,"yapılacak kardeş");
        MainPageOptions fontSize=new MainPageOptions("Font Size",R.drawable.ic_settings_font_size,"yapılacak kardeş");
        MainPageOptions about=new MainPageOptions("About",R.drawable.ic_settings_about,"yapılacak kardeş");
        MainPageOptions logout=new MainPageOptions("Logout",R.drawable.ic_settings_logout,"yapılacak kardeş");
        mainPageOptions.add(changeLanguage);
        mainPageOptions.add(giveFeedback);
        mainPageOptions.add(fontSize);
        mainPageOptions.add(about);
        mainPageOptions.add(logout);

        recyclerViewOptions.setLayoutManager(new LinearLayoutManager(this));
        RVSettingsPageOptionAdapter optionsAdapter=new RVSettingsPageOptionAdapter(mainPageOptions);
        recyclerViewOptions.setAdapter(optionsAdapter);

    }
}