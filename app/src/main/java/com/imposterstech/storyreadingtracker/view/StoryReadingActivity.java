package com.imposterstech.storyreadingtracker.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceContour;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.imposterstech.storyreadingtracker.Model.Contour;
import com.imposterstech.storyreadingtracker.Model.Request.ReadingExperienceRequestModel;
import com.imposterstech.storyreadingtracker.Model.Response.FaceExperienceModel;
import com.imposterstech.storyreadingtracker.Model.Response.StoryModel;
import com.imposterstech.storyreadingtracker.Model.Response.StoryUserModel;
import com.imposterstech.storyreadingtracker.Model.Response.UserModel;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentUser;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.service.FaceExperienceAPI;
import com.imposterstech.storyreadingtracker.service.StoryAPI;
import com.imposterstech.storyreadingtracker.service.StoryUserAPI;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StoryReadingActivity extends AppCompatActivity {

    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;


    UserModel user;
    private String BASE_URL="http://192.168.1.21:8080/story-app-ws/";
    Retrofit retrofit;
    private String token;
    private StoryModel storyModel;

    FaceExperienceAPI faceExperienceAPI;
    SingletonCurrentUser currentUser;
    FaceExperienceModel faceExperienceModel;

    private int failAttemp;
    private int successfullAttemp;


    private TextView textViewTitle,textViewStoryText;
    private ImageButton imageButtonRestart,imageButtonSkip,imageButtonExit,imageButtonCamera;
    private PreviewView previewView;
    private Button buttonStartReading;

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    ImageCapture imageCapture;
    FaceDetectorOptions realTimeOpts;
    FaceDetector detector;

    private boolean isStarted;

    List<Contour> contourList;
    long i=1;
    long timeMilsec=50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_story_reading);



        init();
     /*   if (hasCameraPermission()) {  //MANAGED AT LOGIN

        } else {
            requestPermission();
            enableCamera();
        }*/


        //Retrofit & JSON

        Gson gson=new GsonBuilder().setLenient().create();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        getRandomStory();









        cameraProviderFuture = ProcessCameraProvider.getInstance(this);


        imageCapture =
                new ImageCapture.Builder()
                        .setTargetResolution(new Size(120,60))
//                        .setTargetRotation(previewView.getDisplay().getRotation())
                        .build();


        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(this));













    }

    public void onTakePicture(long i) {
        imageCapture.takePicture(ContextCompat.getMainExecutor(getApplicationContext()), new ImageCapture.OnImageCapturedCallback() {
                    @SuppressLint("UnsafeOptInUsageError")
                    @Override
                    public void onCaptureSuccess(@NonNull ImageProxy image) {
                        super.onCaptureSuccess(image);
                        detectFaces(InputImage.fromMediaImage(image.getImage(),0),i);
                        image.close();

                       // Toast.makeText(getApplicationContext(),"image captured.",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        super.onError(exception);

                       // Toast.makeText(getApplicationContext(),"image couldn't captured.",Toast.LENGTH_LONG).show();
                    }
                }


        );
    }



    private void detectFaces(InputImage image,long readingMiliSec){
        // [START run_detector]
        Task<List<Face>> result =
                detector.process(image)
                        .addOnSuccessListener(
                                new OnSuccessListener<List<Face>>() {
                                    @Override
                                    public void onSuccess(List<Face> faces) {
                                        successfullAttemp++;
                                        // Task completed successfully
                                        // [START_EXCLUDE]
                                        // [START get_face_info]
                                        for (Face face : faces) {
                                            Rect bounds = face.getBoundingBox();
                                            float rotY = face.getHeadEulerAngleY();  // Head is rotated to the right rotY degrees
                                            float rotZ = face.getHeadEulerAngleZ();  // Head is tilted sideways rotZ degrees



                                            // If classification was enabled:
                                            if (face.getSmilingProbability() != null) {
                                                float smileProb = face.getSmilingProbability();
                                            }
                                            if (face.getRightEyeOpenProbability() != null) {
                                                float rightEyeOpenProb = face.getRightEyeOpenProbability();
                                            }
                                            if (face.getLeftEyeOpenProbability() != null) {
                                                float leftEyeOpenProb = face.getRightEyeOpenProbability();
                                            }


                                            // If contour detection was enabled:
                                            /*
                                            Contour contour =new Contour();
                                            List<PointF> faceOvalContour=
                                                    face.getContour(FaceContour.FACE).getPoints();
                                            Log.e("faceoval",faceOvalContour.toString());
                                            List<PointF> upperLipBottomContour =
                                                    face.getContour(FaceContour.UPPER_LIP_BOTTOM).getPoints();
                                            List<PointF> leftEyeContour =
                                                    face.getContour(FaceContour.LEFT_EYE).getPoints();
                                            List<PointF> leftCheekContour=
                                                    face.getContour(FaceContour.LEFT_CHEEK).getPoints();
                                            List<PointF> leftEyebrowBotContour=
                                                    face.getContour(FaceContour.LEFT_EYEBROW_BOTTOM).getPoints();
                                            List<PointF> rightEyeContour=
                                                    face.getContour(FaceContour.RIGHT_EYE).getPoints();
                                            List<PointF> leftEyebrowTopContour=
                                                    face.getContour(FaceContour.LEFT_EYEBROW_TOP).getPoints();
                                            List<PointF> lowerLipBotContour=
                                                    face.getContour(FaceContour.LOWER_LIP_BOTTOM).getPoints();
                                            List<PointF> lowerLipTopContour =
                                                    face.getContour(FaceContour.LOWER_LIP_TOP).getPoints();
                                            List<PointF> noseBotContour =
                                                    face.getContour(FaceContour.NOSE_BOTTOM).getPoints();
                                            List<PointF> noseBridgeContour =
                                                    face.getContour(FaceContour.NOSE_BRIDGE).getPoints();
                                            List<PointF> upperLipTopContour =
                                                    face.getContour(FaceContour.UPPER_LIP_TOP).getPoints();
                                            List<PointF> rightCeekContour =
                                                    face.getContour(FaceContour.RIGHT_CHEEK).getPoints();
                                            List<PointF> rightEyebrowBotContour =
                                                    face.getContour(FaceContour.RIGHT_EYEBROW_BOTTOM).getPoints();
                                            List<PointF> rightEyebrowTopContour =
                                                    face.getContour(FaceContour.RIGHT_EYEBROW_TOP).getPoints();

                                            contour.setFaceOvalContour(faceOvalContour);
                                            contour.setUpperLipBottomContour(upperLipBottomContour);
                                            contour.setLeftEyeContour(leftEyeContour);
                                            contour.setLeftCheekContour(leftCheekContour);
                                            contour.setLeftEyebrowBotContour(leftEyebrowBotContour);
                                            contour.setRightEyeContour(rightEyeContour);
                                            contour.setLeftEyebrowTopContour(leftEyebrowTopContour);
                                            contour.setLowerLipBotContour(lowerLipBotContour);
                                            contour.setLowerLipTopContour(lowerLipTopContour);
                                            contour.setNoseBotContour(noseBotContour);
                                            contour.setNoseBridgeContour(noseBridgeContour);
                                            contour.setUpperLipTopContour(upperLipTopContour);
                                            contour.setRightCeekContour(rightCeekContour);
                                            contour.setRightEyebrowBotContour(rightEyebrowBotContour);
                                            contour.setRightEyebrowTopContour(rightEyebrowTopContour);
                                            contour.setRotY(rotY);
                                            contour.setRotZ(rotZ);
                                            contour.setReadingMilisecond(readingMiliSec);
                                            */


                                                Contour contour =new Contour();
                                                contour.setFaceOvalContour(face.getContour(FaceContour.FACE).getPoints());
                                                contour.setUpperLipBottomContour(face.getContour(FaceContour.UPPER_LIP_BOTTOM).getPoints());
                                                contour.setLeftEyeContour(face.getContour(FaceContour.LEFT_EYE).getPoints());
                                                contour.setLeftCheekContour(face.getContour(FaceContour.LEFT_CHEEK).getPoints());
                                                contour.setLeftEyebrowBotContour(face.getContour(FaceContour.LEFT_EYEBROW_BOTTOM).getPoints());
                                                contour.setRightEyeContour(face.getContour(FaceContour.RIGHT_EYE).getPoints());
                                                contour.setLeftEyebrowTopContour(face.getContour(FaceContour.LEFT_EYEBROW_TOP).getPoints());
                                                contour.setLowerLipBotContour(face.getContour(FaceContour.LOWER_LIP_BOTTOM).getPoints());
                                                contour.setLowerLipTopContour(face.getContour(FaceContour.LOWER_LIP_TOP).getPoints());
                                                contour.setNoseBotContour( face.getContour(FaceContour.NOSE_BOTTOM).getPoints());
                                                contour.setNoseBridgeContour( face.getContour(FaceContour.NOSE_BRIDGE).getPoints());
                                                contour.setUpperLipTopContour(face.getContour(FaceContour.UPPER_LIP_TOP).getPoints());
                                                contour.setRightCeekContour(face.getContour(FaceContour.RIGHT_CHEEK).getPoints());
                                                contour.setRightEyebrowBotContour(face.getContour(FaceContour.RIGHT_EYEBROW_BOTTOM).getPoints());
                                                contour.setRightEyebrowTopContour(face.getContour(FaceContour.RIGHT_EYEBROW_TOP).getPoints());
                                                contour.setRotY(rotY);
                                                contour.setRotZ(rotZ);
                                                contour.setReadingMilisecond(readingMiliSec);
                                                contourList.add(contour);



/*
                                            Call<Void> call=faceExperienceAPI.addContour(currentUser.getToken(),faceExperienceModel.getFaceExperienceDocumentId(),contour);
                                            call.enqueue(new Callback<Void>() {
                                                @Override
                                                public void onResponse(Call<Void> call, Response<Void> response) {
                                                   // contourList.add(contour);
                                                }

                                                @Override
                                                public void onFailure(Call<Void> call, Throwable t) {
                                                    Log.e("facepoint","fail kayıt ");
                                                }
                                            });
*/





                                        }





                                        // [END get_face_info]
                                        // [END_EXCLUDE]
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(),"THERE IS NO FACE ON SCREEN.",Toast.LENGTH_LONG).show();
                                        failAttemp++;
                                    }
                                });


    }



    public void init(){
        isStarted=false;//to store started or not

        textViewStoryText=findViewById(R.id.textView_story_reading_page_story_text);
        textViewTitle=findViewById(R.id.textView_story_reading_page_title);
        imageButtonRestart=findViewById(R.id.ImageButton_toolbar_restart);
        imageButtonCamera=findViewById(R.id.ImageButton_toolbar_camera);
        imageButtonExit=findViewById(R.id.ImageButton_toolbar_exit);
        imageButtonSkip=findViewById(R.id.ImageButton_toolbar_skip);
        previewView=findViewById(R.id.previewViewCamera);
        buttonStartReading=findViewById(R.id.button_storyReadingStartReading);
        contourList =new ArrayList<>();
        //real time face detection
        realTimeOpts =
                new FaceDetectorOptions.Builder()
                        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL) // face contours
                        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)//eyes open or not
                        .build();
        // [START get_detector]
        detector = FaceDetection.getClient(realTimeOpts);


        setTextSize();
        buttonStartReading.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Timer timer = new Timer();



                if(isStarted){
                    isStarted=false;
                    timer.cancel();
                    finishReading();



                }
                else
                {
                    failAttemp=0;
                    successfullAttemp=0;
                    isStarted=true;



                currentUser=SingletonCurrentUser.getInstance();


                faceExperienceAPI=retrofit.create(FaceExperienceAPI.class);
             //   Call<FaceExperienceModel> call=faceExperienceAPI.createReading(currentUser.getToken(),storyModel.getStoryId()); this is correct way but debugging is another way
                Call<FaceExperienceModel> call=faceExperienceAPI.createReading(currentUser.getToken(),"MudJNxh47onhuZJ0avUeOTSaJVzL0t");

                call.enqueue(new Callback<FaceExperienceModel>() {
                    @Override
                    public void onResponse(Call<FaceExperienceModel> call, Response<FaceExperienceModel> response) {
                        faceExperienceModel= response.body();


                        timer.schedule(new TimerTask()
                        {
                            @Override
                            public void run()
                            {

                                //takes pictureevery 0,1 second
                                onTakePicture(i);
                                i=i+timeMilsec;
                            }
                        }, 0, timeMilsec);


                    }

                    @Override
                    public void onFailure(Call<FaceExperienceModel> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"The Story couldn't start.",Toast.LENGTH_LONG).show();
                    }
                });






                }

            }
        });


        imageButtonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRandomStory();
            }
        });


    }
    private void setTextSize(){
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(new FileInputStream("/data/data/com.imposterstech.storyreadingtracker/files/textsize.txt"), "UTF8"));
            String line = in.readLine();
            textViewStoryText.setTextSize(TypedValue.COMPLEX_UNIT_SP, Integer.parseInt(line));;
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void finishReading(){
        SingletonCurrentUser currentUser=SingletonCurrentUser.getInstance();
        // TODO READ COMMENTS BELOW

        StoryUserAPI storyUserAPI=retrofit.create(StoryUserAPI.class);
        faceExperienceAPI=retrofit.create(FaceExperienceAPI.class);
        ReadingExperienceRequestModel readingExperienceRequestModel=new ReadingExperienceRequestModel();
        readingExperienceRequestModel.setFeedbackRate(1); //will be updated with pop up
        readingExperienceRequestModel.setGainedPoint(1);//every story is 1 point
        readingExperienceRequestModel.setFaceExperienceDocumentId(faceExperienceModel.getFaceExperienceDocumentId());
     //   int totalAttemp=successfullAttemp+failAttemp;
      //  int succesRate=successfullAttemp/totalAttemp;
    //    succesRate=succesRate*100;
     //   int succesRate=successfullAttemp/totalAttemp;
     //   succesRate=succesRate*100;

        readingExperienceRequestModel.setSuccessRate(100); // will be updated via mic and camera


        Call<StoryUserModel> call=storyUserAPI.createReadingExperience(readingExperienceRequestModel, storyModel.getStoryId(), currentUser.getToken());

        call.enqueue(new Callback<StoryUserModel>() {
            @Override
            public void onResponse(Call<StoryUserModel> call, Response<StoryUserModel> response) {
                if(response.isSuccessful()){

                    Toast.makeText(getApplicationContext(),"Reading experience saved.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StoryUserModel> call, Throwable t) {

            }
        });


        Call<Void> callFaceExperience=faceExperienceAPI.addAllContour(currentUser.getToken(),faceExperienceModel.getFaceExperienceDocumentId(),contourList);

        callFaceExperience.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getApplicationContext(),"Face experience saved.",Toast.LENGTH_LONG).show();
                contourList=null;
                contourList=new ArrayList<>();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });


    }


    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                CAMERA_PERMISSION,
                CAMERA_REQUEST_CODE
        );
    }
    private void enableCamera() {
        Intent intent = new Intent(this, StoryReadingActivity.class);
        startActivity(intent);
    }


    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector,imageCapture, preview);

    }




    private void getRandomStory(){
        SingletonCurrentUser currentUser=SingletonCurrentUser.getInstance();


        StoryAPI storyAPI=retrofit.create(StoryAPI.class);
        Call<StoryModel> call=storyAPI.getRandomStory(currentUser.getToken());

        call.enqueue(new Callback<StoryModel>() {
            @Override
            public void onResponse(Call<StoryModel> call, Response<StoryModel> response) {
                if(response.isSuccessful()){
                    storyModel= response.body();

                    textViewStoryText.setText(response.body().getStoryText());
                    textViewTitle.setText(response.body().getTitle());
                }
                else{
                    Toast.makeText(getApplicationContext(),"Something is wrong restart app.",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StoryModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Something is wrong restart app.",Toast.LENGTH_LONG).show();

            }
        });

    }
}