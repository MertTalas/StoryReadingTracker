package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imposterstech.storyreadingtracker.BASEURL;
import com.imposterstech.storyreadingtracker.Model.Request.RegisterRequestModel;
import com.imposterstech.storyreadingtracker.Model.Response.UserModel;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.service.APIError;
import com.imposterstech.storyreadingtracker.service.UserAPI;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword,editTextName,editTextSurname;
    private CheckBox checkBoxPrivacyAndPolicy;
    private Spinner spinnerAge,spinnerGender;
    private Button buttonRegister;
    ArrayAdapter ageArrayAdapter;
    ArrayAdapter genderArrayAdapter;

    private String BASE_URL= BASEURL.BASE_URL.getBase_URL();
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        Gson gson=new GsonBuilder().setLenient().create();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    public void init(){
        editTextEmail=findViewById(R.id.editText_RegisterPage_email);
        editTextPassword=findViewById(R.id.editText_RegisterPage_password);
        editTextName=findViewById(R.id.editText_RegisterPage_name);
        editTextSurname=findViewById(R.id.editText_RegisterPage_surname);
        checkBoxPrivacyAndPolicy=findViewById(R.id.checkBox_registerpage_termsPolicies);
        spinnerAge=findViewById(R.id.spinner_registerPage_age);
        spinnerGender=findViewById(R.id.spinner_registerPage_gender);
        buttonRegister=findViewById(R.id.button_registerpage_register);

        String[] genders={"Male","Female"};
        genderArrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,genders);
        ArrayList ages=new ArrayList();
        for(int i=7;i<45;i++){
            ages.add(i);
        }
        ageArrayAdapter=new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,ages);

        spinnerAge.setAdapter(ageArrayAdapter);
        spinnerGender.setAdapter(genderArrayAdapter);





    }

    private void register(){
        UserAPI userAPI=retrofit.create(UserAPI.class);

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String name=editTextName.getText().toString();
        String surname=editTextSurname.getText().toString();
        boolean termsAndPolicies=checkBoxPrivacyAndPolicy.isChecked();
        String gender=spinnerGender.getSelectedItem().toString();
        int age= Integer.parseInt(spinnerAge.getSelectedItem().toString());


        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Email cannot be empty!");
            return;
        }
        if (TextUtils.isEmpty(name)) {
            editTextPassword.setError("Name cannot be empty!");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Password cannot be empty!");
            return;
        }
        if (TextUtils.isEmpty(surname)) {
            editTextPassword.setError("Surname cannot be empty!");
            return;
        }
        if(!termsAndPolicies){
            Toast.makeText(getApplicationContext(),"You have to accept the privacy and policy.",Toast.LENGTH_LONG).show();
            return;
        }

        RegisterRequestModel registerRequestModel=new RegisterRequestModel();
        registerRequestModel.setEmail(email);
        registerRequestModel.setPassword(password);
        registerRequestModel.setFirstName(name);
        registerRequestModel.setLastName(surname);
        registerRequestModel.setTermsAndPoliciesAccepted(termsAndPolicies);
        registerRequestModel.setGender(gender);
        registerRequestModel.setAge(age);

        Call<UserModel> call=userAPI.register(registerRequestModel);

        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                //we can send directly to the main page but I will redirect to login page
                if(response.isSuccessful()){
                    Intent to_login_intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    to_login_intent.putExtra("email",response.body().getEmail());
                    startActivity(to_login_intent);
                    finish();
                    Toast.makeText(getApplicationContext(),response.body().getEmail()+" successful register",Toast.LENGTH_LONG).show();
                }
                else{
                    APIError message = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                    Toast.makeText(getApplicationContext(),message.getMessage(),Toast.LENGTH_LONG).show();

                   //Log.e("gsonlu olan",message.getMessage()+"  "+ message.getTimeStamp());


                }


            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Failed register.",Toast.LENGTH_LONG).show();
                Log.e("fail",t.toString());
                t.printStackTrace();
            }
        });


    }

}