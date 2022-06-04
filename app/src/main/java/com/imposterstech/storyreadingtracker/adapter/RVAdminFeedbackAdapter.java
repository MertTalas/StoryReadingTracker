package com.imposterstech.storyreadingtracker.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imposterstech.storyreadingtracker.Model.Response.FeedbackModel;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentUser;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.service.FeedbackAPI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RVAdminFeedbackAdapter extends RecyclerView.Adapter<RVAdminFeedbackAdapter.OptionHolder> {


    ArrayList<FeedbackModel> allFeedbacks;
    private String BASE_URL="http://192.168.1.21:8080/story-app-ws/";
    Retrofit retrofit;
    FeedbackAPI feedbackAPI;
    SingletonCurrentUser currentUser;



    public RVAdminFeedbackAdapter(ArrayList<FeedbackModel> allFeedbacks) {
        this.allFeedbacks = allFeedbacks;
        Gson gson=new GsonBuilder().setLenient().create();
        currentUser= SingletonCurrentUser.getInstance();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        feedbackAPI=retrofit.create(FeedbackAPI.class);
    }




    @NonNull
    @Override
    public OptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_admin_feedback_row, parent, false);
        return new RVAdminFeedbackAdapter.OptionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionHolder holder, int position) {

        holder.textViewStoryTitle.setText(allFeedbacks.get(position).getStoryDetails().getTitle());
        holder.textViewFeedbackDate.setText(allFeedbacks.get(position).getCreatedOn().toString());
        holder.textViewFeedbackText.setText(allFeedbacks.get(position).getFeedbackText());

        holder.imageViewCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                Call<Void> checkCall=feedbackAPI.checkFeedback(currentUser.getToken(), allFeedbacks.get(position).getFeedbackId());

                                checkCall.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        if(response.isSuccessful()){
                                            allFeedbacks.remove(allFeedbacks.get(position));
                                            notifyItemRemoved(position);
                                            Toast.makeText(view.getContext(), "Feedback checked!!",Toast.LENGTH_LONG).show();
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
                builder.setMessage("Do you want to check the feedback as read ?? ").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();







            }
        });
    }

    @Override
    public int getItemCount() {
        return allFeedbacks.size();
    }

    public class OptionHolder extends RecyclerView.ViewHolder {

        public TextView textViewStoryTitle,textViewFeedbackDate,textViewFeedbackText;
        ImageView imageViewCheck;

        public OptionHolder(@NonNull View itemView) {
            super(itemView);

            textViewStoryTitle = (TextView) itemView.findViewById(R.id.textView_feedback_storytitle);
            textViewFeedbackDate = (TextView) itemView.findViewById(R.id.textView_feedback_feedbackdate);
            textViewFeedbackText = (TextView) itemView.findViewById(R.id.admin_feedback_page_rv_textview_feedbacktext);
            imageViewCheck = (ImageView) itemView.findViewById(R.id.admin_feedback_page_rv_imageview_check);

        }

    }

}
