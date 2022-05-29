package com.imposterstech.storyreadingtracker.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import com.imposterstech.storyreadingtracker.Model.Response.RoleModel;
import com.imposterstech.storyreadingtracker.Model.Response.SimpleStoryUserModel;
import com.imposterstech.storyreadingtracker.Model.Response.UserModel;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentUser;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.service.RoleAPI;
import com.imposterstech.storyreadingtracker.service.UserAPI;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    UserModel user;
    private String BASE_URL="http://192.168.1.42:8080/story-app-ws/";
    Retrofit retrofit;
    private String token;

    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin,buttonRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        checkPermission(Manifest.permission.CAMERA, CAMERA_REQUEST_CODE);

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


    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(LoginActivity.this, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(LoginActivity.this, new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(LoginActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(LoginActivity.this, "Camera Permission Granted", Toast.LENGTH_SHORT) .show();
            }
            else {
                Toast.makeText(LoginActivity.this, "Camera Permission Denied", Toast.LENGTH_SHORT) .show();
            }
        }
       /* else if (requestCode == STORAGE_PERMISSION_CODE) {     //later for microphone perm  //TODO
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }*/
    }

    private void createIntent(){

        RoleAPI roleAPI=retrofit.create(RoleAPI.class);
        SingletonCurrentUser currentUser=SingletonCurrentUser.getInstance();
        Call<List<RoleModel>> call=roleAPI.getRolesOfCurrentUser(currentUser.getToken());


        call.enqueue(new Callback<List<RoleModel>>() {
            @Override
            public void onResponse(Call<List<RoleModel>> call, Response<List<RoleModel>> response) {
                if(response.isSuccessful()){
                    List<RoleModel> tempList=response.body();
                    ArrayList<RoleModel> roleModels=new ArrayList(tempList);
                    boolean adminFlag=false;
                    for( RoleModel roleModel:roleModels){
                        if(roleModel.getName().equals("ROLE_ADMIN")){
                            adminFlag=true;
                            break;
                        }
                    }

                    if(adminFlag){
                        Intent to_admin_main_intent = new Intent(LoginActivity.this, AdminMainPageActivity.class);

                        startActivity(to_admin_main_intent);
                        finish();
                    }
                    else{
                        Intent to_main_intent = new Intent(LoginActivity.this, MainPageActivity.class);

                        startActivity(to_main_intent);
                        finish();
                    }



                }
            }

            @Override
            public void onFailure(Call<List<RoleModel>> call, Throwable t) {

            }
        });




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
                    //to_main_intent.putExtra("token",token);

                    //We will manage logged user as singleton instance
                    SingletonCurrentUser currentUser=SingletonCurrentUser.getInstance();
                    currentUser.setToken(token);

                    createIntent();

                    //getIntent().getStringExtra("token") diğer tarafta

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