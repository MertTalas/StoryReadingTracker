package com.imposterstech.storyreadingtracker.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.imposterstech.storyreadingtracker.Model.MainPageOptions;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.view.AboutActivity;
import com.imposterstech.storyreadingtracker.view.PastReadingActivity;
import com.imposterstech.storyreadingtracker.view.ProfileActivity;
import com.imposterstech.storyreadingtracker.view.SettingsActivity;
import com.imposterstech.storyreadingtracker.view.StoryFeedbackActivity;
import com.imposterstech.storyreadingtracker.view.StoryReadingActivity;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class RVSettingsPageOptionAdapter extends RecyclerView.Adapter<RVSettingsPageOptionAdapter.OptionHolder>{

    ArrayList<MainPageOptions> options;
    private Context context;
    int textsizeUnit;

    public RVSettingsPageOptionAdapter(ArrayList<MainPageOptions> options) {
        this.options = options;
    }
    @NonNull
    @Override
    public OptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_main_option_row, parent, false);
        context = parent.getContext();
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
                Toast.makeText(view.getContext(),options.get(position).getName(),Toast.LENGTH_LONG).show();

                if(options.get(position).getName().equals("Change Language")){
                    Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_animation);
                    /*Intent to_changelanguage_intent=new Intent(holder.itemView.getContext(), SettingsActivity.class);
                    holder.itemView.getContext().startActivity(to_changelanguage_intent);*/
                    holder.itemView.setVisibility(view.VISIBLE);
                    holder.itemView.startAnimation(animation);

                }
                if(options.get(position).getName().equals("About")){
                    Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_animation);
                    Intent to_about_intent=new Intent(holder.itemView.getContext(), AboutActivity.class);
                    holder.itemView.getContext().startActivity(to_about_intent);
                    holder.itemView.setVisibility(view.VISIBLE);
                    holder.itemView.startAnimation(animation);

                }
                if(options.get(position).getName().equals("Logout")){
                    Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_animation);
                    /*Intent to_profile_intent=new Intent(holder.itemView.getContext(), ProfileActivity.class);
                    holder.itemView.getContext().startActivity(to_profile_intent);*/
                    holder.itemView.setVisibility(view.VISIBLE);
                    holder.itemView.startAnimation(animation);

                }
                if(options.get(position).getName().equals("Font Size")){
                    Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_animation);
                    holder.itemView.setVisibility(view.VISIBLE);
                    holder.itemView.startAnimation(animation);

                    LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                    View viewAlert = inflater.inflate( R.layout.av_set_font_size, null );
                    TextView textViewFontSizeTemplate= viewAlert.findViewById(R.id.textViewTemplate);
                    SeekBar seekBar =viewAlert.findViewById(R.id.seekBar);
                    AlertDialog.Builder ad = new AlertDialog.Builder(context);
                    textsizeUnit=Integer.valueOf((int) textViewFontSizeTemplate.getTextSize());
                    ad.setView(viewAlert);
                    ad.setNegativeButton("Cancel",null);
                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                            textViewFontSizeTemplate.setTextSize(TypedValue.COMPLEX_UNIT_SP, i);
                            textsizeUnit=i;
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }
                    });
                    ad.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try{
                                FileOutputStream fos = context.openFileOutput("textsize.txt",Context.MODE_PRIVATE);
                                OutputStreamWriter writer = new OutputStreamWriter(fos);
                                writer.write(Integer.toString(textsizeUnit));
                                writer.close();
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                    ad.create().show();
                }
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
