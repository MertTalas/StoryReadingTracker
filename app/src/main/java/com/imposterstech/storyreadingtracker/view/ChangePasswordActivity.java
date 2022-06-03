package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imposterstech.storyreadingtracker.Model.Request.PasswordChangeRequestModel;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.service.PasswordChangeAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePasswordActivity extends AppCompatActivity {


    private String BASE_URL="http://192.168.1.21:8080/story-app-ws/";
    Retrofit retrofit;
    PasswordChangeAPI passwordChangeAPI;



    private EditText editTextVerificationCode,editTextNewPassword;
    private Button buttonResetPassword;

    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        email=getIntent().getStringExtra("email");
        init();

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordChangeRequestModel passwordChangeRequestModel=new PasswordChangeRequestModel();
                passwordChangeRequestModel.setNewPassword(editTextNewPassword.getText().toString());
                passwordChangeRequestModel.setEmail(email);
                passwordChangeRequestModel.setVerificationCode(editTextVerificationCode.getText().toString());

                Call<Void> call=passwordChangeAPI.changePassword(passwordChangeRequestModel);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Intent to_login_intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                            startActivity(to_login_intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"PROBLEM!!#>!PROBLEM",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });



    }





    public void init(){
        Gson gson=new GsonBuilder().setLenient().create();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        passwordChangeAPI= retrofit.create(PasswordChangeAPI.class);

        editTextNewPassword=findViewById(R.id.editText_changepassword_page_new_password);
        editTextVerificationCode=findViewById(R.id.editText_changepassword_page_verification_code);
        buttonResetPassword=findViewById(R.id.button_changepassword_page_reset);
    }




}