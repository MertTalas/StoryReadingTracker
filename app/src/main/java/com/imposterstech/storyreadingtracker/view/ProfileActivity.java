package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imposterstech.storyreadingtracker.BASEURL;
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
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.lang.reflect.Array;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    private ImageButton imageButtonUserPP;
    private EditText editTextUsername, editTextLastName, editTextAge, editTextGender;
    private Button buttonApply;
    private TextView textViewEmail;
    ArrayAdapter ageArrayAdapter;
    ArrayAdapter genderArrayAdapter;
    private Spinner spinnerAge,spinnerGender;


    private String BASE_URL= BASEURL.BASE_URL.getBase_URL();

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
        editTextUsername=findViewById(R.id.editTextTextUsername);
        editTextLastName=findViewById(R.id.editTextTextLastName);
        editTextAge=findViewById(R.id.editText_ProfilePage_age);
        textViewEmail=findViewById(R.id.textView_ProfilePage_email);
        editTextGender=findViewById(R.id.editText_ProfilePage_gender);
        buttonApply=findViewById(R.id.profile_button_apply);
        spinnerAge=findViewById(R.id.spinner_profilePage_age);
        spinnerGender=findViewById(R.id.spinner_profilePage_gender);



        Gson gson=new GsonBuilder().setLenient().create();
        currentUser=SingletonCurrentUser.getInstance();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        userAPI=retrofit.create(UserAPI.class);
        SingletonCurrentUser currentUser = SingletonCurrentUser.getInstance();
        UserModel userModel = currentUser.getLoggedUser();

        textViewEmail.setText(userModel.getEmail());
        Resources res = getApplicationContext().getResources();

        if(currentUser.getLoggedUser().getChosenAvatarUrl()!=null){
            String [] avatarArraySplit=currentUser.getLoggedUser().getChosenAvatarUrl().split("\\.");
            String fnm = avatarArraySplit[0]; //  this is image file name
            String PACKAGE_NAME = getApplicationContext().getPackageName();
            int imgId = res.getIdentifier(PACKAGE_NAME+":drawable/"+fnm , null, null);
            imageButtonUserPP.setImageBitmap(BitmapFactory.decodeResource(res,imgId));
        }





        editTextUsername.setText(userModel.getFirstName());
        editTextLastName.setText(userModel.getLastName());
        editTextAge.setText(String.valueOf(userModel.getAge()));
        editTextGender.setText(userModel.getGender());

        int positionGender=0;
        int gender=0;
        String[] genders={"Male","Female"};
        for(int i=0;i<genders.length;i++){
            if((String.valueOf(editTextGender.getText()).equals(genders[i]))){
                positionGender=gender;
            }
            gender++;
        }
        genderArrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,genders);

        int positionAge=0;
        int age=0;
        ArrayList ages=new ArrayList();
        for(int i=7;i<45;i++){
            if(Integer.parseInt(String.valueOf(editTextAge.getText()))==i){
                positionAge=age;
            }
            ages.add(i);
            age++;
        }
        ageArrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,ages);

        spinnerAge.setAdapter(ageArrayAdapter);
        spinnerGender.setAdapter(genderArrayAdapter);

        spinnerAge.setSelection(positionAge);
        spinnerGender.setSelection(positionGender);

        Intent to_main_page=new Intent(this, MainPageActivity.class);


        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateUserRequestModel updateUserRequestModel =new UpdateUserRequestModel();
                updateUserRequestModel.setFirstName(editTextUsername.getText().toString());
                updateUserRequestModel.setLastName(editTextLastName.getText().toString());
                updateUserRequestModel.setGender(spinnerGender.getSelectedItem().toString());
                updateUserRequestModel.setAge(Integer.parseInt(spinnerAge.getSelectedItem().toString()));


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


        imageButtonUserPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_avatar_page=new Intent(ProfileActivity.this, AvatarActivity.class);
                startActivity(to_avatar_page);
                finish();


            }
        });





    }
}