package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Preview;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Size;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imposterstech.storyreadingtracker.BASEURL;
import com.imposterstech.storyreadingtracker.FaceTracking.CameraXViewModel;
import com.imposterstech.storyreadingtracker.FaceTracking.FaceGraphic;
import com.imposterstech.storyreadingtracker.FaceTracking.GraphicOverlay;
import com.imposterstech.storyreadingtracker.FaceTracking.NonFaceGraphic;
import com.imposterstech.storyreadingtracker.FaceTracking.PreferenceUtils;
import com.imposterstech.storyreadingtracker.Model.Contour;
import com.imposterstech.storyreadingtracker.Model.Response.SimpleStoryUserModel;
import com.imposterstech.storyreadingtracker.Model.Response.StoryUserModel;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentReadedStory;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentUser;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.service.FaceExperienceAPI;
import com.imposterstech.storyreadingtracker.service.StoryUserAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PastReadingDetailActivity extends AppCompatActivity {
    TextView textViewTitle,textViewReadOnDate;
    SingletonCurrentReadedStory singletonCurrentReadedStory;
    SimpleStoryUserModel simpleStoryUserModel;
    private GraphicOverlay graphicOverlay;



    private String BASE_URL= BASEURL.BASE_URL.getBase_URL();

    Retrofit retrofit;
    FaceExperienceAPI faceExperienceAPI;
    SingletonCurrentUser currentUser;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_reading_detail);

        init();


    }

    public void init(){

        Gson gson=new GsonBuilder().setLenient().create();
        currentUser=SingletonCurrentUser.getInstance();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        faceExperienceAPI=retrofit.create(FaceExperienceAPI.class);



        textViewTitle=findViewById(R.id.textView_reading_detail_page_title);
        textViewReadOnDate=findViewById(R.id.textView_reading_detail_page_readon_date);
        singletonCurrentReadedStory=SingletonCurrentReadedStory.getInstance();
        simpleStoryUserModel=singletonCurrentReadedStory.getSimpleStoryUserModel();

        textViewTitle.setText(simpleStoryUserModel.getStoryDetails().getTitle());
        textViewReadOnDate.setText(simpleStoryUserModel.getReadOnDate().toString());
        graphicOverlay = findViewById(R.id.graphic_overlay_past_reading);




/*
        Preview.Builder builder = new Preview.Builder();


            builder.setTargetResolution(new Size(500,500));

        previewUseCase = builder.build();
        previewUseCase.setSurfaceProvider(previewView.getSurfaceProvider());



        new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                .get(CameraXViewModel.class)
                .getProcessCameraProvider()
                .observe(
                        this,
                        provider -> {
                            cameraProvider = provider;
                            bindAllCameraUseCases();
                        });*/




        graphicOverlay.setImageSourceInfo(
                200, 200, false);

        Call<List<Contour>> call=faceExperienceAPI.getAllContour(currentUser.getToken(),singletonCurrentReadedStory.getSimpleStoryUserModel().getFaceExperienceDocumentId() );

        call.enqueue(new Callback<List<Contour>>() {
            @Override
            public void onResponse(Call<List<Contour>> call, Response<List<Contour>> response) {
                if(response.isSuccessful()){
                    for(Contour contour: response.body()){


                        graphicOverlay.add(new NonFaceGraphic(graphicOverlay, contour));

                    }
                }
            }

            @Override
            public void onFailure(Call<List<Contour>> call, Throwable t) {

            }
        });



        //buraya servisten çekilen overlayler
        //graphicOverlay.add(new NonFaceGraphic(graphicOverlay, face));

    }
}