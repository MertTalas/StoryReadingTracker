package com.imposterstech.storyreadingtracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imposterstech.storyreadingtracker.BASEURL;
import com.imposterstech.storyreadingtracker.FaceTracking.GraphicOverlay;
import com.imposterstech.storyreadingtracker.FaceTracking.NonFaceGraphic;
import com.imposterstech.storyreadingtracker.Model.Contour;
import com.imposterstech.storyreadingtracker.Model.Response.SimpleStoryUserModel;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentReadedStory;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentUser;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.service.FaceExperienceAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CameraRevizualize extends AppCompatActivity {
    SingletonCurrentReadedStory singletonCurrentReadedStory;
    SimpleStoryUserModel simpleStoryUserModel;
    private String BASE_URL= BASEURL.BASE_URL.getBase_URL();
    Retrofit retrofit;
    FaceExperienceAPI faceExperienceAPI;
    SingletonCurrentUser currentUser;
    private List<Contour> partialContour;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_revizualize);
        partialContour= new ArrayList<>();


        Gson gson=new GsonBuilder().setLenient().create();
        currentUser=SingletonCurrentUser.getInstance();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        faceExperienceAPI=retrofit.create(FaceExperienceAPI.class);

        singletonCurrentReadedStory=SingletonCurrentReadedStory.getInstance();
        simpleStoryUserModel=singletonCurrentReadedStory.getSimpleStoryUserModel();

        init();

    }

    void init(){
        Call<List<Contour>> call=faceExperienceAPI.getAllContour(currentUser.getToken(),singletonCurrentReadedStory.getSimpleStoryUserModel().getFaceExperienceDocumentId() );

     /*   call.enqueue(new Callback<List<Contour>>() {
            @Override
            public void onResponse(Call<List<Contour>> call, Response<List<Contour>> response) {
                if(response.isSuccessful()){
                    List<Contour>temp=response.body();
                    partialContour= new ArrayList<>(temp);

                }
            }

            @Override
            public void onFailure(Call<List<Contour>> call, Throwable t) {

            }
        });*/
    }



    public class CameraView extends View {
        public CameraView(Context context){
            super(context);

        }

        @Override
        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);
            int radius = 10;
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            paint.setColor(Color.parseColor("#da4747"));
            int counter=0;

            for(Contour contour:partialContour){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(PointF point:contour.getFaceOvalContour()){
                    canvas.drawCircle(
                            (point.x), (point.y), radius, paint);
                }
                for(PointF point:contour.getUpperLipBottomContour()){
                    canvas.drawCircle(
                            (point.x), (point.y), radius, paint);
                }
                for(PointF point:contour.getLeftEyeContour()){
                    canvas.drawCircle(
                            (point.x), (point.y), radius, paint);
                }
                for(PointF point:contour.getLeftCheekContour()){
                    canvas.drawCircle(
                            (point.x), (point.y), radius, paint);
                }
                for(PointF point:contour.getLeftEyebrowBotContour()){
                    canvas.drawCircle(
                            (point.x), (point.y), radius, paint);
                }
                for(PointF point:contour.getRightEyeContour()){
                    canvas.drawCircle(
                            (point.x), (point.y), radius, paint);
                }
                for(PointF point:contour.getLeftEyebrowTopContour()){
                    canvas.drawCircle(
                            (point.x), (point.y), radius, paint);
                }
                for(PointF point:contour.getLowerLipBotContour()){
                    canvas.drawCircle(
                            (point.x), (point.y), radius, paint);
                }
                for(PointF point:contour.getLowerLipTopContour()){
                    canvas.drawCircle(
                            (point.x), (point.y), radius, paint);
                }
                for(PointF point:contour.getNoseBotContour()){
                    canvas.drawCircle(
                            (point.x), (point.y), radius, paint);
                }
                for(PointF point:contour.getNoseBridgeContour()){
                    canvas.drawCircle(
                            (point.x), (point.y), radius, paint);
                }
                for(PointF point:contour.getUpperLipTopContour()){
                    canvas.drawCircle(
                            (point.x), (point.y), radius, paint);
                }
                for(PointF point:contour.getRightCeekContour()){
                    canvas.drawCircle(
                            (point.x), (point.y), radius, paint);
                }
                for(PointF point:contour.getRightEyebrowBotContour()){
                    canvas.drawCircle(
                            (point.x), (point.y), radius, paint);
                }
                for(PointF point:contour.getRightEyebrowTopContour()){
                    canvas.drawCircle(
                            (point.x), (point.y), radius, paint);
                }


                counter++;

            }





        }

    }
}