package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imposterstech.storyreadingtracker.BASEURL;
import com.imposterstech.storyreadingtracker.Model.Response.PasswordChangeModel;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentUser;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.service.PasswordChangeAPI;
import com.imposterstech.storyreadingtracker.service.StoryAPI;
import com.imposterstech.storyreadingtracker.service.StoryUserAPI;
import com.imposterstech.storyreadingtracker.service.UserAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForgotPasswordActivity extends AppCompatActivity {



    private String BASE_URL= BASEURL.BASE_URL.getBase_URL();
    Retrofit retrofit;
    PasswordChangeAPI passwordChangeAPI;



    private EditText editTextEmail;
    private Button buttonSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        init();

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=editTextEmail.getText().toString();

                Call<PasswordChangeModel> call=passwordChangeAPI.createVerificationCode(email);

                call.enqueue(new Callback<PasswordChangeModel>() {
                    @Override
                    public void onResponse(Call<PasswordChangeModel> call, Response<PasswordChangeModel> response) {
                        Intent to_changePassword_intent = new Intent(ForgotPasswordActivity.this, ChangePasswordActivity.class);
                        to_changePassword_intent.putExtra("email",email);
                        startActivity(to_changePassword_intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<PasswordChangeModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Wrong Verification Code",Toast.LENGTH_LONG).show();
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

        editTextEmail=findViewById(R.id.editText_forgotpassword_page_email);
        buttonSend=findViewById(R.id.button_forgotpassword_page_send);
    }
}