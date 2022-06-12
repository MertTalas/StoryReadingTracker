package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.imposterstech.storyreadingtracker.R;

public class AboutActivity extends AppCompatActivity {
    private TextView textViewTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        textViewTips=findViewById(R.id.textViewTips);
        textViewTips.setText("First, we would like to mention the audience that our project addresses and can reach, The \n" +
                "International Dyslexic Association and the US National Institute determined that 10% of \n" +
                "people worldwide suffer from dyslexia. We aim not to ignore such a large community and to \n" +
                "contribute to their treatment and further research on this subject by analyzing both eye and \n" +
                "speech actions to help. Before starting our project, we gathered information about the eye \n" +
                "movements and reading habits of people with dyslexia, as the audience our application \n" +
                "addresses may exhibit unexpected eye movements and reading habits. These are people who \n" +
                "have trouble on focusing and distinguishing or pronouncing similar letters such as (b, q, d, m, \n" +
                "n, etc.), so we should shape and develop our project accordingly. Our application is basically \n" +
                "based on 2 different subsystems. One of them is reading tracking and the other is eye tracking. \n" +
                "Although physical reading strips are sold for people with dyslexia, we want to adapt it to \n" +
                "today's age and make it more dynamic. Apart from physical reading strips, there are a few \n" +
                "applications for dyslexic people, but unfortunately there are not enough applications with \n" +
                "sufficient capacity and scope for such a large community. We distinguish ourselves from \n" +
                "other applications by using up-to-date technologies such as Google ML Kit, both by eye \n" +
                "tracking and reading tracking, and by presenting a more understandable and easier interface to \n" +
                "the user. Our application generally asks the user to read a story. These stories are child\u0002friendly and uncomplicated texts. The user reads these stories and their eye movements, \n" +
                "speech tracking actions are recorded in our database to achieve the purposes we mentioned \n" +
                "before. In order to provide the user with a more comfortable reading experience, we will offer \n" +
                "options such as turning off the camera, turning the colorings that show the accuracy of \n" +
                "reading on and off. Since we cannot predict how these factors (camera and colors that indicate \n" +
                "reading accuracy) will affect the reading actions of dyslexic people, we will provide these \n" +
                "options so that we can reach the most accurate data and determination.\n" +
                "When enough data is collected from children to analyze dyslexia and create a model, the \n" +
                "project achieves its purpose");
                textViewTips.setMovementMethod(new ScrollingMovementMethod());

    }
}