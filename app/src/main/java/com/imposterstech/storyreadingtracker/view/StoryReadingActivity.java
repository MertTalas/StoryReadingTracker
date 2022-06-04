package com.imposterstech.storyreadingtracker.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.Size;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.mlkit.common.MlKitException;
import com.imposterstech.storyreadingtracker.BASEURL;
import com.imposterstech.storyreadingtracker.FaceTracking.CameraXViewModel;
import com.imposterstech.storyreadingtracker.FaceTracking.FaceDetectorProcessor;
import com.imposterstech.storyreadingtracker.FaceTracking.GraphicOverlay;
import com.imposterstech.storyreadingtracker.FaceTracking.PreferenceUtils;
import com.imposterstech.storyreadingtracker.FaceTracking.VisionImageProcessor;
import com.imposterstech.storyreadingtracker.Model.Contour;
import com.imposterstech.storyreadingtracker.Model.Request.ReadingExperienceRequestModel;
import com.imposterstech.storyreadingtracker.Model.Response.FaceExperienceModel;
import com.imposterstech.storyreadingtracker.Model.Response.StoryModel;
import com.imposterstech.storyreadingtracker.Model.Response.StoryUserModel;
import com.imposterstech.storyreadingtracker.Model.Response.UserModel;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentStoryReading;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentUser;
import com.imposterstech.storyreadingtracker.R;
import com.imposterstech.storyreadingtracker.service.FaceExperienceAPI;
import com.imposterstech.storyreadingtracker.service.StoryAPI;
import com.imposterstech.storyreadingtracker.service.StoryUserAPI;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StoryReadingActivity extends AppCompatActivity {

    private static final String[] CAMERA_PERMISSION = new String[]{Manifest.permission.CAMERA};
    private static final int CAMERA_REQUEST_CODE = 10;


    UserModel user;

    private String BASE_URL = BASEURL.BASE_URL.getBase_URL();

    Retrofit retrofit;
    private String token;
    private StoryModel storyModel;

    FaceExperienceAPI faceExperienceAPI;
    SingletonCurrentUser currentUser;
    FaceExperienceModel faceExperienceModel;



    private int failAttemp;
    private int successfullAttemp;

    TextView textViewSpeechText;
    ImageView imageViewStoryMic;
    int count = 0;
    SpeechRecognizer speechRecognizer;
    String sentenceControl="";
    int wordCounter=0;
    int charCounter = 0;

    private TextView textViewTitle, textViewStoryText;
    private ImageButton imageButtonRestart, imageButtonSkip, imageButtonExit, imageButtonCamera;
    //private PreviewView previewView;
    private Button buttonStartReading;

    private static final String TAG = "CameraXLivePreview";


    private static final String FACE_DETECTION = "Face Detection";


    private static final String STATE_SELECTED_MODEL = "selected_model";

    private PreviewView previewView;
    private GraphicOverlay graphicOverlay;

    @Nullable
    private ProcessCameraProvider cameraProvider;
    @Nullable
    private Preview previewUseCase;
    @Nullable
    private ImageAnalysis analysisUseCase;
    @Nullable
    private VisionImageProcessor imageProcessor;
    private boolean needUpdateGraphicOverlayImageSourceInfo;

    private String selectedModel = FACE_DETECTION;
    private int lensFacing = CameraSelector.LENS_FACING_FRONT;
    private CameraSelector cameraSelector;

    private boolean isStarted;

    List<Contour> contourList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            selectedModel = savedInstanceState.getString(STATE_SELECTED_MODEL, FACE_DETECTION);
        }
        cameraSelector = new CameraSelector.Builder().requireLensFacing(lensFacing).build();

        setContentView(R.layout.activity_story_reading);
        isStarted=false;
        init();
        graphicOverlay = findViewById(R.id.graphic_overlay);





     /*   if (hasCameraPermission()) {  //MANAGED AT LOGIN

        } else {
            requestPermission();
            enableCamera();
        }*/


        //Retrofit & JSON

        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        getRandomStory();

        buttonStartReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isStarted){


                    faceExperienceAPI=retrofit.create(FaceExperienceAPI.class);
                    Call<FaceExperienceModel> call=faceExperienceAPI.createReading(currentUser.getToken(),storyModel.getStoryId());
                    call.enqueue(new Callback<FaceExperienceModel>() {
                        @Override
                        public void onResponse(Call<FaceExperienceModel> call, Response<FaceExperienceModel> response) {
                            if(response.isSuccessful()){
                                faceExperienceModel= response.body();
                                SingletonCurrentStoryReading singletonCurrentStoryReading=SingletonCurrentStoryReading.getInstance();
                                singletonCurrentStoryReading.setFaceExperienceModel(faceExperienceModel);




                                ReadingExperienceRequestModel readingExperienceRequestModel=new ReadingExperienceRequestModel();
                                readingExperienceRequestModel.setFeedbackRate(1); //will be updated with pop up
                                readingExperienceRequestModel.setGainedPoint(1);//every story is 1 point
                                readingExperienceRequestModel.setFaceExperienceDocumentId(faceExperienceModel.getFaceExperienceDocumentId());
                                readingExperienceRequestModel.setSuccessRate(100); // will be updated via mic and camera

                                StoryUserAPI storyUserAPI=retrofit.create(StoryUserAPI.class);
                                Call<StoryUserModel> callStoryreadingAdd=storyUserAPI.createReadingExperience(readingExperienceRequestModel, storyModel.getStoryId(), currentUser.getToken());

                                callStoryreadingAdd.enqueue(new Callback<StoryUserModel>() {
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




                            }
                        }

                        @Override
                        public void onFailure(Call<FaceExperienceModel> call, Throwable t) {

                        }
                    });











                    new ViewModelProvider(StoryReadingActivity.this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()))
                            .get(CameraXViewModel.class)
                            .getProcessCameraProvider()
                            .observe(
                                    StoryReadingActivity.this,
                                    provider -> {
                                        cameraProvider = provider;
                                        bindAllCameraUseCases();
                                    });
                    isStarted=true;

                }
                else
                {
                    isStarted=false;
                    imageProcessor.stop();
                    Intent to_feedback_page=new Intent(StoryReadingActivity.this, StoryFeedbackActivity.class);
                    startActivity(to_feedback_page);
                    finish();

                }


            }
        });


    }
    private void bindAllCameraUseCases() {
        if (cameraProvider != null) {
            // As required by CameraX API, unbinds all use cases before trying to re-bind any of them.
            cameraProvider.unbindAll();
            bindPreviewUseCase();
            bindAnalysisUseCase();
        }
    }

    private void bindPreviewUseCase() {
        if (!PreferenceUtils.isCameraLiveViewportEnabled(this)) {
            return;
        }
        if (cameraProvider == null) {
            return;
        }
        if (previewUseCase != null) {
            cameraProvider.unbind(previewUseCase);
        }

        Preview.Builder builder = new Preview.Builder();
        Size targetResolution = PreferenceUtils.getCameraXTargetResolution(this, lensFacing);
        if (targetResolution != null) {
            builder.setTargetResolution(targetResolution);
        }
        previewUseCase = builder.build();
        previewUseCase.setSurfaceProvider(previewView.getSurfaceProvider());
        cameraProvider.bindToLifecycle(/* lifecycleOwner= */ this, cameraSelector, previewUseCase);
    }
    private void bindAnalysisUseCase() {
        if (cameraProvider == null) {
            return;
        }
        if (analysisUseCase != null) {
            cameraProvider.unbind(analysisUseCase);
        }
        if (imageProcessor != null) {
            imageProcessor.stop();
        }

        try {
            switch (selectedModel) {

                case FACE_DETECTION:
                    Log.i(TAG, "Using Face Detector Processor");
                    imageProcessor = new FaceDetectorProcessor(this);
                    break;

                default:
                    throw new IllegalStateException("Invalid model name");
            }
        } catch (Exception e) {
            Log.e(TAG, "Can not create image processor: " + selectedModel, e);
            Toast.makeText(
                    getApplicationContext(),
                    "Can not create image processor: " + e.getLocalizedMessage(),
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }

        ImageAnalysis.Builder builder = new ImageAnalysis.Builder();
        Size targetResolution = PreferenceUtils.getCameraXTargetResolution(this, lensFacing);
        if (targetResolution != null) {
            builder.setTargetResolution(targetResolution);
        }
        analysisUseCase = builder.build();

        needUpdateGraphicOverlayImageSourceInfo = true;
        analysisUseCase.setAnalyzer(
                // imageProcessor.processImageProxy will use another thread to run the detection underneath,
                // thus we can just runs the analyzer itself on main thread.
                ContextCompat.getMainExecutor(this),
                imageProxy -> {
                    if (needUpdateGraphicOverlayImageSourceInfo) {
                        boolean isImageFlipped = lensFacing == CameraSelector.LENS_FACING_FRONT;
                        int rotationDegrees = imageProxy.getImageInfo().getRotationDegrees();
                        if (rotationDegrees == 0 || rotationDegrees == 180) {
                            graphicOverlay.setImageSourceInfo(
                                    imageProxy.getWidth(), imageProxy.getHeight(), isImageFlipped);
                        } else {
                            graphicOverlay.setImageSourceInfo(
                                    imageProxy.getHeight(), imageProxy.getWidth(), isImageFlipped);
                        }
                        needUpdateGraphicOverlayImageSourceInfo = false;
                    }
                    try {
                        imageProcessor.processImageProxy(imageProxy, graphicOverlay);
                    } catch (MlKitException e) {
                        Log.e(TAG, "Failed to process image. Error: " + e.getLocalizedMessage());
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }
                });

        cameraProvider.bindToLifecycle(/* lifecycleOwner= */ this, cameraSelector, analysisUseCase);
    }


    public void init() {
        isStarted = false;//to store started or not

        textViewStoryText = findViewById(R.id.textView_story_reading_page_story_text);
        textViewTitle = findViewById(R.id.textView_story_reading_page_title);
        imageButtonRestart = findViewById(R.id.ImageButton_toolbar_restart);
        imageButtonCamera = findViewById(R.id.ImageButton_toolbar_camera);
        imageButtonExit = findViewById(R.id.ImageButton_toolbar_exit);
        imageButtonSkip = findViewById(R.id.ImageButton_toolbar_skip);
        previewView = findViewById(R.id.preview_view);
        buttonStartReading = findViewById(R.id.button_storyReadingStartReading);
        contourList = new ArrayList<>();
        currentUser=SingletonCurrentUser.getInstance();
        //real time face detection

        ArrayList<String> words = new ArrayList<>();

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS,20000);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,60000);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        SpannableString SS = new SpannableString(textViewStoryText.getText().toString());
        ForegroundColorSpan fcsGreen = new ForegroundColorSpan(Color.GREEN);
        ForegroundColorSpan fcsRed = new ForegroundColorSpan(Color.RED);

        setWords(words);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }

        imageViewStoryMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count == 0) {
                    count = 1;
                    imageViewStoryMic.setColorFilter(Color.RED);
                    speechRecognizer.startListening(speechRecognizerIntent);
                } else{
                    imageViewStoryMic.setColorFilter(Color.BLACK);
                    speechRecognizer.stopListening();
                    count = 0;
                }
            }
        });
        textViewSpeechText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String remainingSentence = String.valueOf(charSequence.subSequence(i1, i2));
                if (!remainingSentence.equals("")) {
                    if (remainingSentence.replaceAll("[^a-zA-Z]+", "").equals(words.get(wordCounter).replaceAll("[^a-zA-Z]+",""))){
                        SS.setSpan(fcsGreen,charCounter,charCounter+words.get(wordCounter).length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        textViewStoryText.setText(SS);
                        charCounter+=words.get(wordCounter).length()+1;

                    } if(remainingSentence.replaceAll("[^a-zA-Z]+", "").equals((words.get(wordCounter)+words.get(wordCounter+1)).replaceAll("[^a-zA-Z]+",""))){
                        SS.setSpan(fcsGreen,charCounter,charCounter+words.get(wordCounter).length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        textViewStoryText.setText(SS);
                        charCounter+=words.get(wordCounter).length()+words.get(wordCounter+1).length()+2;
                        wordCounter++;

                    }
                    else if(!remainingSentence.replaceAll("[^a-zA-Z]+", "").equals(words.get(wordCounter).replaceAll("[^a-zA-Z]+",""))){
                        SS.setSpan(fcsRed, charCounter, charCounter+words.get(wordCounter).length() , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        textViewStoryText.setText(SS);
                        charCounter+=words.get(wordCounter).length()+1;

                    }
                    wordCounter++;
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {


            }

            @Override
            public void onPartialResults(Bundle bundle) {
                ArrayList<String> data = bundle.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);
                if (!sentenceControl.equals(data.get(0))) {
                    textViewSpeechText.setText(data.get(0));
                    sentenceControl = data.get(0);
                }
            }
            @Override
            public void onEvent(int i, Bundle bundle) {

            }

        });


    }
    @Override
    protected void onActivityResult (int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     /*   switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data!=null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    ArrayList<String> copyResult = new ArrayList<>();
                    int counter = 0;
                    txt.setText(result.get(0));
                }
        }*/

    }
    private void setTextSize () {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(new FileInputStream("/data/data/com.imposterstech.storyreadingtracker/files/textsize.txt"), "UTF8"));
            String line = in.readLine();
            textViewStoryText.setTextSize(TypedValue.COMPLEX_UNIT_SP, Integer.parseInt(line));
            ;
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void finishReading () {
        SingletonCurrentUser currentUser = SingletonCurrentUser.getInstance();
        // TODO READ COMMENTS BELOW

        StoryUserAPI storyUserAPI = retrofit.create(StoryUserAPI.class);
        faceExperienceAPI = retrofit.create(FaceExperienceAPI.class);
        ReadingExperienceRequestModel readingExperienceRequestModel = new ReadingExperienceRequestModel();
        readingExperienceRequestModel.setFeedbackRate(1); //will be updated with pop up
        readingExperienceRequestModel.setGainedPoint(1);//every story is 1 point
        readingExperienceRequestModel.setFaceExperienceDocumentId(faceExperienceModel.getFaceExperienceDocumentId());
        //   int totalAttemp=successfullAttemp+failAttemp;
        //  int succesRate=successfullAttemp/totalAttemp;
        //    succesRate=succesRate*100;
        //   int succesRate=successfullAttemp/totalAttemp;
        //   succesRate=succesRate*100;

        readingExperienceRequestModel.setSuccessRate(100); // will be updated via mic and camera


        Call<StoryUserModel> call = storyUserAPI.createReadingExperience(readingExperienceRequestModel, storyModel.getStoryId(), currentUser.getToken());

        call.enqueue(new Callback<StoryUserModel>() {
            @Override
            public void onResponse(Call<StoryUserModel> call, Response<StoryUserModel> response) {
                if (response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(), "Reading experience saved.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StoryUserModel> call, Throwable t) {

            }
        });


        Call<Void> callFaceExperience = faceExperienceAPI.addAllContour(currentUser.getToken(), faceExperienceModel.getFaceExperienceDocumentId(), contourList);

        callFaceExperience.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getApplicationContext(), "Face experience saved.", Toast.LENGTH_LONG).show();
                contourList = null;
                contourList = new ArrayList<>();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });


    }


    private void getRandomStory () {
        SingletonCurrentUser currentUser = SingletonCurrentUser.getInstance();
        SingletonCurrentStoryReading singletonCurrentStoryReading=SingletonCurrentStoryReading.getInstance();

        StoryAPI storyAPI = retrofit.create(StoryAPI.class);
        Call<StoryModel> call = storyAPI.getRandomStory(currentUser.getToken());

        call.enqueue(new Callback<StoryModel>() {
            @Override
            public void onResponse(Call<StoryModel> call, Response<StoryModel> response) {
                if (response.isSuccessful()) {
                    storyModel = response.body();
                    singletonCurrentStoryReading.setStoryModel(storyModel);

                    textViewStoryText.setText(response.body().getStoryText());
                    textViewTitle.setText(response.body().getTitle());
                } else {
                    Toast.makeText(getApplicationContext(), "Something is wrong restart app.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<StoryModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something is wrong restart app.", Toast.LENGTH_LONG).show();

            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT);
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT);
            }
        }
    }

    public void setWords(ArrayList words){
        String [] allWords = textViewStoryText.getText().toString().split(" ");
        for(String word: allWords ){
            words.add(word);

        }

    }
}

