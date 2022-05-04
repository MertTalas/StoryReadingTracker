package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.imposterstech.storyreadingtracker.R;

public class MainPageActivity extends AppCompatActivity {
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        init();

    }
    public void init(){
        token=getIntent().getStringExtra("token");
    }
}