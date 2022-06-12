package com.imposterstech.storyreadingtracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imposterstech.storyreadingtracker.Model.WordMicrophone;
import com.imposterstech.storyreadingtracker.R;

import java.util.ArrayList;

public class RVPastReadingWordAdapter extends RecyclerView.Adapter<RVPastReadingWordAdapter.OptionHolder>{

    ArrayList<WordMicrophone> allWords;



    public RVPastReadingWordAdapter(ArrayList<WordMicrophone> allWords) {
        this.allWords = allWords;
    }



    @NonNull
    @Override
    public OptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_reading_detail_row, parent, false);
        return new RVPastReadingWordAdapter.OptionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionHolder holder, int position) {
        holder.textViewExpected.setText(allWords.get(position).getExpectedWord());
        holder.textViewGiven.setText(allWords.get(position).getReadedWord());
    }

    @Override
    public int getItemCount() {
        return allWords.size();
    }








    public class OptionHolder extends RecyclerView.ViewHolder {

        public TextView textViewExpected,textViewGiven;


        public OptionHolder(@NonNull View itemView) {
            super(itemView);

            textViewExpected = (TextView) itemView.findViewById(R.id.textView_detail_words_expected);
            textViewGiven = (TextView) itemView.findViewById(R.id.textView_detail_words_given);

        }

    }
}
