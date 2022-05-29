package com.imposterstech.storyreadingtracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imposterstech.storyreadingtracker.Model.Response.SimpleStoryUserModel;
import com.imposterstech.storyreadingtracker.Model.Response.StoryModel;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentEditableStory;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentReadedStory;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.view.AdminEditStoryDetailActivity;
import com.imposterstech.storyreadingtracker.view.PastReadingDetailActivity;

import java.util.ArrayList;

public class RVAdminUpdateStoryAdapter extends RecyclerView.Adapter<RVAdminUpdateStoryAdapter.OptionHolder>{

    ArrayList<StoryModel> allStories;




    public RVAdminUpdateStoryAdapter(ArrayList<StoryModel> allStories) {
        this.allStories = allStories;
    }

    @NonNull
    @Override
    public OptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_admin_edit_story_row, parent, false);
        return new RVAdminUpdateStoryAdapter.OptionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionHolder holder, int position) {
        holder.textViewStoryTitle.setText(allStories.get(position).getTitle());
        holder.textViewStoryDate.setText(allStories.get(position).getUpdatedOn().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(holder.itemView.getContext(), AdminEditStoryDetailActivity.class);
                SingletonCurrentEditableStory singletonCurrentEditableStory = SingletonCurrentEditableStory.getInstance();
                singletonCurrentEditableStory.setStoryModel(allStories.get(position));
                holder.itemView.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return allStories.size();
    }

    public class OptionHolder extends RecyclerView.ViewHolder{

        public TextView textViewStoryTitle,textViewStoryDate;

        public OptionHolder(@NonNull View itemView) {
            super(itemView);

            textViewStoryTitle=(TextView)itemView.findViewById(R.id.admin_story_editpage_title);
            textViewStoryDate=(TextView)itemView.findViewById(R.id.admin_story_editpage_date);

        }
    }
}
