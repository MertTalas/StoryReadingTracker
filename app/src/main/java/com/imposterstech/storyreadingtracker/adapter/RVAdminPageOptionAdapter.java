package com.imposterstech.storyreadingtracker.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imposterstech.storyreadingtracker.Model.MainPageOptions;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.view.AdminAddStoryActivity;
import com.imposterstech.storyreadingtracker.view.AdminEditStoryActivity;
import com.imposterstech.storyreadingtracker.view.PastReadingActivity;
import com.imposterstech.storyreadingtracker.view.ProfileActivity;
import com.imposterstech.storyreadingtracker.view.SettingsActivity;
import com.imposterstech.storyreadingtracker.view.StoryReadingActivity;

import java.util.ArrayList;

public class RVAdminPageOptionAdapter extends RecyclerView.Adapter<RVAdminPageOptionAdapter.OptionHolder> {
    ArrayList<String> options;

    public RVAdminPageOptionAdapter(ArrayList<String> options) {
        this.options = options;
    }



    @NonNull
    @Override
    public OptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_admin_main_option_row, parent, false);
        return new OptionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionHolder holder, int position) {
        holder.textViewOptionName.setText(options.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(),options.get(position).getName(),Toast.LENGTH_LONG).show();

                if(options.get(position).equals("Add Story")){
                    Intent to_addstory_intent=new Intent(holder.itemView.getContext(), AdminAddStoryActivity.class);
                    holder.itemView.getContext().startActivity(to_addstory_intent);

                }
                if(options.get(position).equals("Remove Story")){

                }
                if(options.get(position).equals("List Feedbacks")){
                  //  Intent to_readstory_intent=new Intent(holder.itemView.getContext(), PastReadingActivity.class);
                  //  holder.itemView.getContext().startActivity(to_readstory_intent);

                }
                if(options.get(position).equals("Update Story")){
                    Intent to_editstory_intent=new Intent(holder.itemView.getContext(), AdminEditStoryActivity.class);
                    holder.itemView.getContext().startActivity(to_editstory_intent);

                }


            }
        });
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public class OptionHolder extends RecyclerView.ViewHolder{

            public TextView textViewOptionName;

            public OptionHolder(@NonNull View itemView) {
                super(itemView);

                textViewOptionName=(TextView)itemView.findViewById(R.id.admin_main_page_rv_textview_name);

            }
        }
}
