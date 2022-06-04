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

        MainPageOptions changeLanguage=new MainPageOptions("Change Language",R.drawable.ic_settings_change_language,"You can change the language of the\napplication as you wish");
        MainPageOptions giveFeedback=new MainPageOptions("Give Feedback",R.drawable.ic_settings_give_feedback,"You can let us know feedback\nabout the application");
        MainPageOptions fontSize=new MainPageOptions("Font Size",R.drawable.ic_settings_font_size,"you can change the font size\nof the application");
        MainPageOptions about=new MainPageOptions("About",R.drawable.ic_settings_about,"You can learn more about\nthe application");
        MainPageOptions logout=new MainPageOptions("Logout",R.drawable.ic_settings_logout,"You can safely exit the application");
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