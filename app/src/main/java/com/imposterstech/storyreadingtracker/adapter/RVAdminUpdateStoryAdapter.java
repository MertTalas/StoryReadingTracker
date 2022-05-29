package com.imposterstech.storyreadingtracker.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imposterstech.storyreadingtracker.R;

public class RVAdminUpdateStoryAdapter extends RecyclerView.Adapter<RVAdminUpdateStoryAdapter.OptionHolder>{


    @NonNull
    @Override
    public OptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull OptionHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class OptionHolder extends RecyclerView.ViewHolder{

        public TextView textViewOptionName;

        public OptionHolder(@NonNull View itemView) {
            super(itemView);

            textViewOptionName=(TextView)itemView.findViewById(R.id.admin_main_page_rv_textview_name);

        }
    }
}
