package com.imposterstech.storyreadingtracker.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imposterstech.storyreadingtracker.BASEURL;
import com.imposterstech.storyreadingtracker.Model.AvatarOptions;
import com.imposterstech.storyreadingtracker.Model.Request.AvatarRequestModel;
import com.imposterstech.storyreadingtracker.Model.Response.AvatarModel;

import com.imposterstech.storyreadingtracker.Model.SingletonCurrentUser;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.service.AvatarAPI;
import com.imposterstech.storyreadingtracker.service.StoryAPI;


import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RVAvatarOptionAdapter extends RecyclerView.Adapter<RVAvatarOptionAdapter.OptionHolder> {

    ArrayList<AvatarModel> allAvatars;
    ArrayList<AvatarModel> usersAvatars;
    Context context;

    int i = 1;

    private String BASE_URL= BASEURL.BASE_URL.getBase_URL();

    Retrofit retrofit;
    AvatarAPI avatarAPI;
    SingletonCurrentUser currentUser;

    public RVAvatarOptionAdapter(ArrayList<AvatarModel> allAvatars) {
        this.allAvatars = allAvatars;
        Gson gson=new GsonBuilder().setLenient().create();
        currentUser=SingletonCurrentUser.getInstance();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        avatarAPI=retrofit.create(AvatarAPI.class);

    }

    @NonNull
    @Override
    public RVAvatarOptionAdapter.OptionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_avatar_option_row, parent, false);
        context = parent.getContext();
        return new RVAvatarOptionAdapter.OptionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RVAvatarOptionAdapter.OptionHolder holder, int position) {

        holder.textViewAvatarName.setText(allAvatars.get(position).getAvatarName());
        holder.textViewAvatarPrice.setText("Avatar Price: "+String.valueOf(allAvatars.get(position).getAvatarPrice()));

        Call<List<AvatarModel>> call=avatarAPI.getUserAvatars(currentUser.getToken());
        call.enqueue(new Callback<List<AvatarModel>>() {
            @Override
            public void onResponse(Call<List<AvatarModel>> call, Response<List<AvatarModel>> response) {
                if(response.isSuccessful()){
                    List<AvatarModel>temp;
                    temp=response.body();
                    usersAvatars=(ArrayList<AvatarModel>) temp;

                    AvatarModel avatartempModel= new AvatarModel();
                    avatartempModel.setAvatarId(allAvatars.get(position).getAvatarId());
                    avatartempModel.setAvatarPrice(allAvatars.get(position).getAvatarPrice());
                    avatartempModel.setAvatarURL(allAvatars.get(position).getAvatarURL());
                    avatartempModel.setAvatarId(allAvatars.get(position).getAvatarId());
                    avatartempModel.setAvatarName(allAvatars.get(position).getAvatarName());

                    for(AvatarModel avatar:usersAvatars){
                        if(avatartempModel.getAvatarName().equals(avatar.getAvatarName())){
                            holder.imageViewAvatarLock.setImageResource(0);
                            holder.isBought=true;

                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<List<AvatarModel>> call, Throwable t) {

            }
        });
        Resources res = holder.itemView.getContext().getResources();

        String fnm = "avatar"+i; //  this is image file name
        String PACKAGE_NAME = context.getPackageName();
        int imgId = res.getIdentifier(PACKAGE_NAME+":drawable/"+fnm , null, null);
        holder.imageViewAvatar.setImageBitmap(BitmapFactory.decodeResource(res,imgId));



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                holder.selectedAvatar=currentUser.getLoggedUser().getChosenAvatarUrl();
                if(holder.isBought&&!holder.selectedAvatar.equals(allAvatars.get(position).getAvatarURL())){
                    View viewAlert = inflater.inflate( R.layout.av_select_avatar, null );

                    ImageView imageView =viewAlert.findViewById(R.id.imageView_buy_avatar_image);
                    imageView.setImageBitmap(BitmapFactory.decodeResource(res,imgId));
                    AlertDialog.Builder ad = new AlertDialog.Builder(context);
                    ad.setView(viewAlert);
                    ad.setNegativeButton("Cancel",null);
                    ad.setPositiveButton("Select", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AvatarRequestModel avatarRequestModel = new AvatarRequestModel();
                            avatarRequestModel.setAvatarName(allAvatars.get(position).getAvatarName());
                            avatarRequestModel.setAvatarPrice(allAvatars.get(position).getAvatarPrice());
                            avatarRequestModel.setAvatarURL(allAvatars.get(position).getAvatarURL());

                            Call<AvatarModel> call = avatarAPI.selectAvatar(avatarRequestModel, currentUser.getToken());
                            call.enqueue(new Callback<AvatarModel>() {
                                @Override
                                public void onResponse(Call<AvatarModel> call, Response<AvatarModel> response) {
                                    if(response.isSuccessful()){
                                        currentUser.getLoggedUser().setChosenAvatarUrl(avatarRequestModel.getAvatarURL());
                                        Toast.makeText(view.getContext(), "Avatar selected!!", Toast.LENGTH_LONG).show();
                                        holder.selectedAvatar=currentUser.getLoggedUser().getChosenAvatarUrl();
                                    }
                                }

                                @Override
                                public void onFailure(Call<AvatarModel> call, Throwable t) {
                                    Toast.makeText(view.getContext(), "Unable to select avatar!!", Toast.LENGTH_LONG).show();

                                }
                            });


                        }
                    });
                    ad.create().show();

                }
                else if (!holder.isBought){
                    View viewAlert = inflater.inflate(R.layout.av_buy_avatar, null);


                    ImageView imageView = viewAlert.findViewById(R.id.imageView_buy_avatar_image);
                    imageView.setImageBitmap(BitmapFactory.decodeResource(res, imgId));
                    AlertDialog.Builder ad = new AlertDialog.Builder(context);
                    ad.setView(viewAlert);
                    ad.setNegativeButton("Cancel", null);

                    ad.setPositiveButton("Buy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            AvatarRequestModel avatarRequestModel = new AvatarRequestModel();
                            avatarRequestModel.setAvatarName(allAvatars.get(position).getAvatarName());
                            avatarRequestModel.setAvatarPrice(allAvatars.get(position).getAvatarPrice());
                            avatarRequestModel.setAvatarURL(allAvatars.get(position).getAvatarURL());

                            Call<AvatarModel> call = avatarAPI.buyAvatar(avatarRequestModel, currentUser.getToken());
                            call.enqueue(new Callback<AvatarModel>() {
                                @Override
                                public void onResponse(Call<AvatarModel> call, Response<AvatarModel> response) {
                                    if (response.isSuccessful()) {
                                        //allAvatars.add(allAvatars.get(position));
                                        Toast.makeText(view.getContext(), "Avatar bought!!", Toast.LENGTH_LONG).show();
                                        holder.imageViewAvatarLock.setImageResource(0);
                                        holder.isBought=true;
                                        currentUser.getLoggedUser().setPoints(currentUser.getLoggedUser().getPoints() - avatarRequestModel.getAvatarPrice());
                                    } else {
                                        try {
                                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                                            Toast.makeText(context, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
                                        } catch (Exception e) {
                                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }

                                    }


                                }

                                @Override
                                public void onFailure(Call<AvatarModel> call, Throwable t) {
                                    Toast.makeText(view.getContext(), "Cant retrieve data", Toast.LENGTH_LONG).show();


                                }
                            });
                        }
                    });
                    ad.create().show();
                }
            }
        });

        i++;
    }

    @Override
    public int getItemCount() {
        return allAvatars.size();
    }

    public class OptionHolder extends RecyclerView.ViewHolder{

        public TextView textViewAvatarName,textViewAvatarPrice;
        public ImageView imageViewAvatar,imageViewAvatarLock;
        public Boolean isBought;
        public String selectedAvatar;



        public OptionHolder(@NonNull View itemView) {
            super(itemView);
            textViewAvatarPrice=(TextView)itemView.findViewById(R.id.textView_avatar_price);
            textViewAvatarName=(TextView)itemView.findViewById(R.id.textView_avatar_name);
            imageViewAvatar=(ImageView) itemView.findViewById(R.id.imageView_avatar_picture);
            imageViewAvatarLock=itemView.findViewById(R.id.imageView_avatar_lock);
            isBought=false;
            selectedAvatar=currentUser.getLoggedUser().getChosenAvatarUrl();
        }
    }
}
