package com.imposterstech.storyreadingtracker.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imposterstech.storyreadingtracker.Model.MainPageOptions;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.view.LoginActivity;
import com.imposterstech.storyreadingtracker.view.MainPageActivity;
import com.imposterstech.storyreadingtracker.view.PastReadingActivity;
import com.imposterstech.storyreadingtracker.view.ProfileActivity;
import com.imposterstech.storyreadingtracker.view.RankingActivity;
import com.imposterstech.storyreadingtracker.view.SettingsActivity;
import com.imposterstech.storyreadingtracker.view.StoryReadingActivity;

import java.util.ArrayList;

public class RVMainPageOptionAdapter extends RecyclerView.Adapter<RVMainPageOptionAdapter.OptionHolder> {

    ArrayList<MainPageOptions> options;

    public RVMainPageOptionAdapter(ArrayList<MainPageOptions> options) {
        this.options = options;
    }

    @NonNull
    @Override
    public OptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_main_option_row, parent, false);
        return new OptionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionHolder holder, int position) {
        holder.textViewOptionName.setText(options.get(position).getName());
        holder.textViewOptionDesc.setText(options.get(position).getDescription());
        holder.textViewOptionImage.setImageResource(options.get(position).getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(),options.get(position).getName(),Toast.LENGTH_LONG).show();

                if(options.get(position).getName().equals("Read Story")){
                    Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_animation);
                    Intent to_readstory_intent=new Intent(holder.itemView.getContext(), StoryReadingActivity.class);
                    holder.itemView.getContext().startActivity(to_readstory_intent);
                    holder.itemView.setVisibility(view.VISIBLE);
                    holder.itemView.startAnimation(animation);

                }
                if(options.get(position).getName().equals("Settings")){
                    Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_animation);
                    Intent to_settings_intent=new Intent(holder.itemView.getContext(), SettingsActivity.class);
                    holder.itemView.getContext().startActivity(to_settings_intent);
                    holder.itemView.setVisibility(view.VISIBLE);
                    holder.itemView.startAnimation(animation);

                }
                if(options.get(position).getName().equals("Past Readings")){
                    Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_animation);
                    Intent to_pastreadings_intent=new Intent(holder.itemView.getContext(), PastReadingActivity.class);
                    holder.itemView.getContext().startActivity(to_pastreadings_intent);
                    holder.itemView.setVisibility(view.VISIBLE);
                    holder.itemView.startAnimation(animation);

                }
                if(options.get(position).getName().equals("Profile")){
                    Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_animation);
                    Intent to_profile_intent=new Intent(holder.itemView.getContext(), ProfileActivity.class);
                    holder.itemView.getContext().startActivity(to_profile_intent);
                    holder.itemView.setVisibility(view.VISIBLE);
                    holder.itemView.startAnimation(animation);

                }
                /*if(options.get(position).getName().equals("Ranking")){
                    Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_animation);
                    Intent to_ranking_intent=new Intent(holder.itemView.getContext(), RankingActivity.class);
                    holder.itemView.getContext().startActivity(to_ranking_intent);
                    holder.itemView.setVisibility(view.VISIBLE);
                    holder.itemView.startAnimation(animation);

                }*\



               /* Toast.makeText(getApplicationContext(),"Successful login!!",Toast.LENGTH_LONG).show();
                Intent to_main_intent = new Intent(LoginActivity.this, MainPageActivity.class);
                to_main_intent.putExtra("token",token);
                //getIntent().getStringExtra("token") diğer tarafta
                startActivity(to_main_intent);
                finish();*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    public class OptionHolder extends RecyclerView.ViewHolder{

        public TextView textViewOptionName, textViewOptionDesc;
        public ImageView textViewOptionImage;

        public OptionHolder(@NonNull View itemView) {
            super(itemView);

            textViewOptionName=(TextView)itemView.findViewById(R.id.main_page_rv_textview_name);
            textViewOptionImage=(ImageView)itemView.findViewById(R.id.main_page_rv_imageview_name);
            textViewOptionDesc=(TextView)itemView.findViewById(R.id.main_page_rv_textview_desc);
        }
    }

}
