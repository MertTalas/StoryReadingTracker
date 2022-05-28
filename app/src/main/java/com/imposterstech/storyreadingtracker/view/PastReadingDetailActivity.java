package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.imposterstech.storyreadingtracker.Model.Response.SimpleStoryUserModel;
import com.imposterstech.storyreadingtracker.Model.Response.StoryUserModel;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentReadedStory;
import com.imposterstech.storyreadingtracker.R;

public class PastReadingDetailActivity extends AppCompatActivity {
    TextView textViewTitle,textViewReadOnDate;
    SingletonCurrentReadedStory singletonCurrentReadedStory;
    SimpleStoryUserModel simpleStoryUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_reading_detail);

        init();


    }

    public void init(){
        textViewTitle=findViewById(R.id.textView_reading_detail_page_title);
        textViewReadOnDate=findViewById(R.id.textView_reading_detail_page_readon_date);
        singletonCurrentReadedStory=SingletonCurrentReadedStory.getInstance();
        simpleStoryUserModel=singletonCurrentReadedStory.getSimpleStoryUserModel();

        textViewTitle.setText(simpleStoryUserModel.getStoryDetails().getTitle());
        textViewReadOnDate.setText(simpleStoryUserModel.getReadOnDate().toString());

    }
}