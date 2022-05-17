package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imposterstech.storyreadingtracker.Model.Request.LoginRequestModel;
import com.imposterstech.storyreadingtracker.Model.Response.UserModel;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentUser;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.service.UserAPI;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    UserModel user;
    private String BASE_URL="http://192.168.1.21:8080/story-app-ws/";
    Retrofit retrofit;
    private String token;

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin,buttonRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        //Retrofit & JSON

        Gson gson=new GsonBuilder().setLenient().create();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_register_intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(to_register_intent);
                //finish();
            }
        });

    }
    public void init(){
        editTextEmail = findViewById(R.id.editText_loginpage_email);
        editTextPassword = findViewById(R.id.editText_loginpage_password);
        buttonLogin = findViewById(R.id.button_loginpage_login);
        buttonRegister=findViewById(R.id.button_loginpage_register);
    }

    private void login(){
        //Intent to_main_intent = new Intent(LoginActivity.this, MainPageActivity.class);
        //to_main_intent.putExtra("token",token);

        //We will manage logged user as singleton instance
       // SingletonCurrentUser currentUser=SingletonCurrentUser.getInstance();
       // currentUser.setToken(token);
        //startActivity(to_main_intent);
       // finish();
        //şimdilik

        UserAPI userAPI=retrofit.create(UserAPI.class);

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Email cannot be empty!");
            if (TextUtils.isEmpty(password)) {
                editTextPassword.setError("Password cannot be empty!");
            }
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Password cannot be empty!");
            return;
        }
        LoginRequestModel loginRequestModel=new LoginRequestModel();
        loginRequestModel.setEmail(email);
        loginRequestModel.setPassword(password);
        Call<ResponseBody> call=userAPI.login(loginRequestModel);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Headers headers = response.headers();
                    String authorization = response.headers().get("Authorization");
                    token=authorization;
                    //Log.e("token",token);
                    Toast.makeText(getApplicationContext(),"Successful login!!",Toast.LENGTH_LONG).show();
                    Intent to_main_intent = new Intent(LoginActivity.this, MainPageActivity.class);
                    //to_main_intent.putExtra("token",token);

                    //We will manage logged user as singleton instance
                    SingletonCurrentUser currentUser=SingletonCurrentUser.getInstance();
                    currentUser.setToken(token);



                    //getIntent().getStringExtra("token") diğer tarafta
                    startActivity(to_main_intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Failed login.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed login.",Toast.LENGTH_LONG).show();
                //Log.e("fail","failyaw");
            }
        });


    }

}