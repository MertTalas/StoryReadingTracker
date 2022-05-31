package com.imposterstech.storyreadingtracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.imposterstech.storyreadingtracker.Model.Response.AvatarModel;

import com.imposterstech.storyreadingtracker.R;


import java.util.ArrayList;
import java.util.List;


public class RVAvatarOptionAdapter extends RecyclerView.Adapter<RVAvatarOptionAdapter.OptionHolder> {

    ArrayList<AvatarModel> allAvatars;

    public RVAvatarOptionAdapter(ArrayList<AvatarModel> allAvatars) {
        this.allAvatars = allAvatars;

    }

    @NonNull
    @Override
    public RVAvatarOptionAdapter.OptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_avatar_option_row, parent, false);
        return new RVAvatarOptionAdapter.OptionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAvatarOptionAdapter.OptionHolder holder, int position) {
        holder.textViewAvatarName.setText(allAvatars.get(position).getAvatarName());
      //  holder.imageViewAvatar.setImageResource(allAvatars.get(position).getAvatarURL());


    }

    @Override
    public int getItemCount() {
        return allAvatars.size();
    }

    public class OptionHolder extends RecyclerView.ViewHolder{

        public TextView textViewAvatarName;
        public ImageView imageViewAvatar;

        public OptionHolder(@NonNull View itemView) {
            super(itemView);

            textViewAvatarName=(TextView)itemView.findViewById(R.id.textView_avatar_name);
            imageViewAvatar=(ImageView) itemView.findViewById(R.id.imageView_avatar_picture);

        }
    }
}
