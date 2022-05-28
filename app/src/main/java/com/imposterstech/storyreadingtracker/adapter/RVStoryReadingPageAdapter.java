package com.imposterstech.storyreadingtracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imposterstech.storyreadingtracker.Model.MainPageOptions;
import com.imposterstech.storyreadingtracker.Model.Response.SimpleStoryUserModel;
import com.imposterstech.storyreadingtracker.Model.Response.StoryUserModel;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentReadedStory;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.view.PastReadingDetailActivity;

import java.util.ArrayList;

import javax.inject.Singleton;

public class RVStoryReadingPageAdapter extends RecyclerView.Adapter<RVStoryReadingPageAdapter.OptionHolder>{

    ArrayList<SimpleStoryUserModel> pastReadings;
    private Context context;



    public RVStoryReadingPageAdapter(ArrayList<SimpleStoryUserModel> pastReadings) {
        this.pastReadings = pastReadings;
    }





    @NonNull
    @Override
    public OptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_past_reading_row, parent, false);
        return new OptionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionHolder holder, int position) {

        holder.textViewStoryReadingDate.setText(pastReadings.get(position).getReadOnDate().toString());
        holder.textViewStoryTitle.setText(pastReadings.get(position).getStoryDetails().getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(holder.itemView.getContext(), PastReadingDetailActivity.class);
                SingletonCurrentReadedStory singletonCurrentReadedStory = SingletonCurrentReadedStory.getInstance();
                singletonCurrentReadedStory.setSimpleStoryUserModel(pastReadings.get(position));
                holder.itemView.getContext().startActivity(intent);

            }
        });




    }

    @Override
    public int getItemCount() {
        return pastReadings.size();
    }



    public class OptionHolder extends RecyclerView.ViewHolder{

        public TextView textViewStoryTitle,textViewStoryReadingDate;


        public OptionHolder(@NonNull View itemView) {
            super(itemView);

            textViewStoryTitle=(TextView)itemView.findViewById(R.id.main_page_rv_textview_story_title);
            textViewStoryReadingDate=(TextView)itemView.findViewById(R.id.main_page_rv_textview_story_reading_date);

        }
    }






}
