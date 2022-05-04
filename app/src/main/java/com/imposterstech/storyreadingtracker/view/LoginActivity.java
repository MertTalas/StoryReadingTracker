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
import com.imposterstech.storyreadingtracker.Model.UserModel;
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
    private String BASE_URL="http://10.0.2.2:8080/story-app-ws/";
    Retrofit retrofit;
    private String token;

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;


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


        //getUserData();
    }
    public void init(){
        editTextEmail = findViewById(R.id.editText_loginpage_email);
        editTextPassword = findViewById(R.id.editText_loginpage_password);
        buttonLogin = findViewById(R.id.button_loginpage_login);
    }

    private void login(){
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
                    Log.e("token",token);
                    Toast.makeText(getApplicationContext(),token,Toast.LENGTH_LONG).show();
                   /* Intent to_main_intent = new Intent(LoginActivity.this, MainPageActivity.class);
                    to_main_intent.putExtra("token",token);
                    //getIntent().getStringExtra("token") diğer tarafta
                    startActivity(to_main_intent);
                    finish();*/

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed login.",Toast.LENGTH_LONG).show();
                Log.e("fail","failyaw");
            }
        });


    }

    private void getUserData(){
        UserAPI userAPI=retrofit.create(UserAPI.class);

        Call<UserModel> call= userAPI.getUser("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmb3J0ZXN0MkB0ZXN0LmNvbSIsImV4cCI6MTY1MjQ3OTM0Nn0.Hm9HzQuEZZakFHdbHkgBn08wBB_lZqi7uGAtU2d4pfBiEpOyUkHgmj7Gd6r72ymknRC1SzlNCkM68WqLCGJIqw");

        //async
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    user=response.body();
                    System.out.println(user.getFirstName());
                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

                System.out.println("bbbbbbbbbb");
                t.printStackTrace();
            }
        });

    }
}