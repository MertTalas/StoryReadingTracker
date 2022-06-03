package com.imposterstech.storyreadingtracker.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imposterstech.storyreadingtracker.Model.Response.StoryModel;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentEditableStory;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentUser;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.service.StoryAPI;
import com.imposterstech.storyreadingtracker.view.AdminEditStoryDetailActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RVAdminRemoveStoryAdapter extends RecyclerView.Adapter<RVAdminRemoveStoryAdapter.OptionHolder>{


    ArrayList<StoryModel> allStories;
    private String BASE_URL="http://192.168.1.42:8080/story-app-ws/";
    Retrofit retrofit;
    StoryAPI storyAPI;
    SingletonCurrentUser currentUser;




    public RVAdminRemoveStoryAdapter(ArrayList<StoryModel> allStories) {
        this.allStories = allStories;
        Gson gson=new GsonBuilder().setLenient().create();
        currentUser=SingletonCurrentUser.getInstance();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        storyAPI=retrofit.create(StoryAPI.class);
    }

    @NonNull
    @Override
    public OptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_admin_remove_story_row, parent, false);
        return new RVAdminRemoveStoryAdapter.OptionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionHolder holder, int position) {
        holder.textViewStoryTitle.setText(allStories.get(position).getTitle());
        holder.textViewStoryDate.setText(allStories.get(position).getUpdatedOn().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Call<Void> removeCall=storyAPI.removeStory(currentUser.getToken(), allStories.get(position).getStoryId());

                                removeCall.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(response.isSuccessful()){
                                            allStories.remove(allStories.get(position));
                                            notifyItemRemoved(position);

                                            Toast.makeText(view.getContext(), "Story deletedd!!",Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {
                                        Toast.makeText(view.getContext(), "Fail!!",Toast.LENGTH_LONG).show();
                                    }
                                });

                                //Yes button clicked
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked donothing
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Do you want to remove the story ?? "+allStories.get(position).getTitle()+" ??").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

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

            textViewStoryTitle=(TextView)itemView.findViewById(R.id.admin_story_removepage_title);
            textViewStoryDate=(TextView)itemView.findViewById(R.id.admin_story_removepage_date);

        }
    }
}
