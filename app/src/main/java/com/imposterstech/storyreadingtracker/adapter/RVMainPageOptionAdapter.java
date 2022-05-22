package com.imposterstech.storyreadingtracker.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holder.textViewOptionImage.setImageResource(options.get(position).getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),options.get(position).getName(),Toast.LENGTH_LONG).show();

                if(options.get(position).getName().equals("Read Story")){
                    Intent to_readstory_intent=new Intent(holder.itemView.getContext(), StoryReadingActivity.class);
                    holder.itemView.getContext().startActivity(to_readstory_intent);

                }
                if(options.get(position).getName().equals("Settings")){
                    Intent to_readstory_intent=new Intent(holder.itemView.getContext(), SettingsActivity.class);
                    holder.itemView.getContext().startActivity(to_readstory_intent);

                }


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

        public TextView textViewOptionName;
        public ImageView textViewOptionImage;

        public OptionHolder(@NonNull View itemView) {
            super(itemView);

            textViewOptionName=(TextView)itemView.findViewById(R.id.main_page_rv_textview_name);
            textViewOptionImage=(ImageView)itemView.findViewById(R.id.main_page_rv_imageview_name);

        }
    }

}
