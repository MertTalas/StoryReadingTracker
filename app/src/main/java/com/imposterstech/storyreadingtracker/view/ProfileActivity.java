package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imposterstech.storyreadingtracker.Model.Request.UpdateUserRequestModel;
import com.imposterstech.storyreadingtracker.Model.Response.AvatarModel;
import com.imposterstech.storyreadingtracker.Model.Response.SimpleStoryUserModel;
import com.imposterstech.storyreadingtracker.Model.Response.UserModel;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentUser;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.service.AvatarAPI;
import com.imposterstech.storyreadingtracker.service.StoryUserAPI;
import com.imposterstech.storyreadingtracker.service.UserAPI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    private ImageButton imageButtonUserPP;
    private EditText editTextUsername,editTextBirthday;
    private RadioButton radioButton;
    private RadioGroup radioGroup;
    private Button buttonApply;

    private String BASE_URL="http://192.168.1.42:8080/story-app-ws/";
    Retrofit retrofit;
    UserAPI userAPI;
    AvatarAPI avatarAPI;
    SingletonCurrentUser currentUser;
    List<AvatarModel> allAvatars;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        init();
    }
    void init(){
        imageButtonUserPP=findViewById(R.id.imageButtonPersonPP);
        editTextBirthday=findViewById(R.id.editTextTextBirthday);
        editTextUsername=findViewById(R.id.editTextTextUsername);
        radioGroup=findViewById(R.id.radio_group);
        buttonApply=findViewById(R.id.profile_button_apply);


        Gson gson=new GsonBuilder().setLenient().create();
        currentUser=SingletonCurrentUser.getInstance();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        userAPI=retrofit.create(UserAPI.class);
        SingletonCurrentUser currentUser = SingletonCurrentUser.getInstance();
        UserModel userModel = currentUser.getLoggedUser();

        editTextUsername.setText(userModel.getFirstName());
        editTextBirthday.setText(String.valueOf(userModel.getAge()));
        String gender = userModel.getGender();
        if(gender.equals("Female"))
            radioGroup.check(R.id.radioButton_female);
        else
            radioGroup.check(R.id.radioButton_male);

        Intent to_main_page=new Intent(this, MainPageActivity.class);


        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton=findViewById(radioId);

                UpdateUserRequestModel updateUserRequestModel =new UpdateUserRequestModel();
                updateUserRequestModel.setAge(Integer.parseInt(editTextBirthday.getText().toString()));
                updateUserRequestModel.setFirstName(editTextUsername.getText().toString());
                updateUserRequestModel.setGender(radioButton.getText().toString());
                updateUserRequestModel.setLastName(currentUser.getLoggedUser().getLastName());


                Call<UserModel> call = userAPI.updateUser(currentUser.getToken(),updateUserRequestModel);
                call.enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        if(response.isSuccessful()) {
                            currentUser.setLoggedUser(response.body());
                            Toast.makeText(getApplicationContext(),"Changes Applied!!",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Failed to Change!!",Toast.LENGTH_SHORT).show();


                    }
                });

                startActivity(to_main_page);
                finish();

            }
        });

        Intent to_avatar_page=new Intent(ProfileActivity.this, AvatarActivity.class);
        imageButtonUserPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(to_avatar_page);
                // finish();


            }
        });





    }
}