package com.imposterstech.storyreadingtracker.FaceTracking;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceContour;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.google.mlkit.vision.face.FaceLandmark;
import com.imposterstech.storyreadingtracker.Model.Contour;
import com.imposterstech.storyreadingtracker.Model.Response.StoryModel;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentStoryReading;
import com.imposterstech.storyreadingtracker.Model.SingletonCurrentUser;
import com.imposterstech.storyreadingtracker.service.FaceExperienceAPI;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FaceDetectorProcessor  extends VisionProcessorBase<List<Face>> {
    private static final String TAG = "FaceDetectorProcessor";

    private final FaceDetector detector;
    SingletonCurrentUser currentUser;
    SingletonCurrentStoryReading singletonCurrentStoryReading;


    private String BASE_URL="http://192.168.1.21:8080/story-app-ws/";
    Retrofit retrofit;
    FaceExperienceAPI faceExperienceAPI;



    public FaceDetectorProcessor(Context context) {
        super(context);
        singletonCurrentStoryReading=SingletonCurrentStoryReading.getInstance();
        currentUser= SingletonCurrentUser.getInstance();

        Gson gson=new GsonBuilder().setLenient().create();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        faceExperienceAPI=retrofit.create(FaceExperienceAPI.class);


        FaceDetectorOptions faceDetectorOptions = PreferenceUtils.getFaceDetectorOptions(context);
        Log.v(MANUAL_TESTING_LOG, "Face detector options: " + faceDetectorOptions);
        detector = FaceDetection.getClient(faceDetectorOptions);
    }

    @Override
    public void stop() {
        super.stop();
        detector.close();
    }

    @Override
    protected Task<List<Face>> detectInImage(InputImage image) {
        return detector.process(image);
    }

    @Override
    protected void onSuccess(@NonNull List<Face> faces, @NonNull GraphicOverlay graphicOverlay) {
        for (Face face : faces) {
            graphicOverlay.add(new FaceGraphic(graphicOverlay, face));
            logExtrasForTesting(face);
            saveContour(face);
        }
    }

    private void saveContour(Face face){
        Rect bounds = face.getBoundingBox();
        float rotY = face.getHeadEulerAngleY();  // Head is rotated to the right rotY degrees
        float rotZ = face.getHeadEulerAngleZ();  // Head is tilted sideways rotZ degrees

        Log.e("say abi","sayıyomabi");

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
        contour.setReadingMilisecond(11);  //willbeupdated later TODO


        Call<Void> call=faceExperienceAPI.addContour(currentUser.getToken(),singletonCurrentStoryReading.getFaceExperienceModel().getFaceExperienceDocumentId(),contour);
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



    }



    private static void logExtrasForTesting(Face face) {
        if (face != null) {
            Log.v(MANUAL_TESTING_LOG, "face bounding box: " + face.getBoundingBox().flattenToString());
            Log.v(MANUAL_TESTING_LOG, "face Euler Angle X: " + face.getHeadEulerAngleX());
            Log.v(MANUAL_TESTING_LOG, "face Euler Angle Y: " + face.getHeadEulerAngleY());
            Log.v(MANUAL_TESTING_LOG, "face Euler Angle Z: " + face.getHeadEulerAngleZ());

            // All landmarks
            int[] landMarkTypes =
                    new int[] {
                            FaceLandmark.MOUTH_BOTTOM,
                            FaceLandmark.MOUTH_RIGHT,
                            FaceLandmark.MOUTH_LEFT,
                            FaceLandmark.RIGHT_EYE,
                            FaceLandmark.LEFT_EYE,
                            FaceLandmark.RIGHT_EAR,
                            FaceLandmark.LEFT_EAR,
                            FaceLandmark.RIGHT_CHEEK,
                            FaceLandmark.LEFT_CHEEK,
                            FaceLandmark.NOSE_BASE
                    };
            String[] landMarkTypesStrings =
                    new String[] {
                            "MOUTH_BOTTOM",
                            "MOUTH_RIGHT",
                            "MOUTH_LEFT",
                            "RIGHT_EYE",
                            "LEFT_EYE",
                            "RIGHT_EAR",
                            "LEFT_EAR",
                            "RIGHT_CHEEK",
                            "LEFT_CHEEK",
                            "NOSE_BASE"
                    };
            for (int i = 0; i < landMarkTypes.length; i++) {
                FaceLandmark landmark = face.getLandmark(landMarkTypes[i]);
                if (landmark == null) {
                    Log.v(
                            MANUAL_TESTING_LOG,
                            "No landmark of type: " + landMarkTypesStrings[i] + " has been detected");
                } else {
                    PointF landmarkPosition = landmark.getPosition();
                    String landmarkPositionStr =
                            String.format(Locale.US, "x: %f , y: %f", landmarkPosition.x, landmarkPosition.y);
                    Log.v(
                            MANUAL_TESTING_LOG,
                            "Position for face landmark: "
                                    + landMarkTypesStrings[i]
                                    + " is :"
                                    + landmarkPositionStr);
                }
            }
            Log.v(
                    MANUAL_TESTING_LOG,
                    "face left eye open probability: " + face.getLeftEyeOpenProbability());
            Log.v(
                    MANUAL_TESTING_LOG,
                    "face right eye open probability: " + face.getRightEyeOpenProbability());
            Log.v(MANUAL_TESTING_LOG, "face smiling probability: " + face.getSmilingProbability());
            Log.v(MANUAL_TESTING_LOG, "face tracking id: " + face.getTrackingId());
        }
    }

    @Override
    protected void onFailure(@NonNull Exception e) {
        Log.e(TAG, "Face detection failed " + e);
    }

}
