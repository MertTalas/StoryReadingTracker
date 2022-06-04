package com.imposterstech.storyreadingtracker.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.imposterstech.storyreadingtracker.Model.Response.UserModel;
import com.imposterstech.storyreadingtracker.R;


import java.util.ArrayList;



public class RVRankingPageAdapter extends RecyclerView.Adapter<RVRankingPageAdapter.OptionHolder>{

    ArrayList<UserModel> allUsers;
    Context context;



    public RVRankingPageAdapter(ArrayList<UserModel> allUsers) {
        this.allUsers = allUsers;
    }



    @NonNull
    @Override
    public OptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_ranking_row, parent, false);
        return new RVRankingPageAdapter.OptionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionHolder holder, int position) {
        holder.textViewOrder.setText(position+"");
        holder.textViewUsername.setText(allUsers.get(position).getFirstName()+ " "+allUsers.get(position).getLastName());
        holder.textViewPoint.setText(allUsers.get(position).getPoints()+"");




        if(allUsers.get(position).getChosenAvatarUrl()==null){

        }
        else{
            Resources res = holder.itemView.getContext().getResources();

            String fnm = allUsers.get(position).getChosenAvatarUrl(); //  this is image file name
            String PACKAGE_NAME = context.getPackageName();
            int imgId = res.getIdentifier(PACKAGE_NAME+":drawable/"+fnm , null, null);
            holder.imageViewAvatar.setImageBitmap(BitmapFactory.decodeResource(res,imgId));
        }

    }

    @Override
    public int getItemCount() {
        return allUsers.size();
    }

    public class OptionHolder extends RecyclerView.ViewHolder {

        public TextView textViewOrder,textViewUsername,textViewPoint;
        public ImageView imageViewAvatar;


        public OptionHolder(@NonNull View itemView) {
            super(itemView);

            textViewOrder = (TextView) itemView.findViewById(R.id.ranking_page_textview_order);
            textViewUsername = (TextView) itemView.findViewById(R.id.ranking_page_textview_username);
            textViewPoint = (TextView) itemView.findViewById(R.id.ranking_page_textview_points);
            imageViewAvatar=(ImageView) itemView.findViewById(R.id.ranking_page_imageView_avatar);

        }

    }
}
