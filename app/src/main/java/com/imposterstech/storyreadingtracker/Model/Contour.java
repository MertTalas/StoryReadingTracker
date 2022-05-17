package com.imposterstech.storyreadingtracker.Model;

import android.graphics.PointF;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.google.mlkit.vision.face.FaceContour;

import java.util.List;

public class Contour {
    @SerializedName("faceOvalContour")
    private List<PointF> faceOvalContour;
    @SerializedName("upperLipBottomContour")
    private List<PointF> upperLipBottomContour ;
    @SerializedName("leftEyeContour")
    private List<PointF> leftEyeContour ;
    @SerializedName("leftCheekContour")
    private List<PointF> leftCheekContour;
    @SerializedName("leftEyebrowBotContour")
    private List<PointF> leftEyebrowBotContour;
    @SerializedName("rightEyeContour")
    private List<PointF> rightEyeContour;
    @SerializedName("leftEyebrowTopContour")
    private List<PointF> leftEyebrowTopContour;
    @SerializedName("lowerLipBotContour")
    private List<PointF> lowerLipBotContour;
    @SerializedName("lowerLipTopContour")
    private List<PointF> lowerLipTopContour;
    @SerializedName("noseBotContour")
    private List<PointF> noseBotContour ;
    @SerializedName("noseBridgeContour")
    private List<PointF> noseBridgeContour ;
    @SerializedName("upperLipTopContour")
    private List<PointF> upperLipTopContour ;
    @SerializedName("rightCeekContour")
    private List<PointF> rightCeekContour ;
    @SerializedName("rightEyebrowBotContour")
    private List<PointF> rightEyebrowBotContour ;
    @SerializedName("rightEyebrowTopContour")
    private List<PointF> rightEyebrowTopContour ;
    @SerializedName("rotY")
    private float rotY ;
    @SerializedName("rotZ")
    private float rotZ;
    @SerializedName("readingMilisecond")
    private long readingMilisecond;


    public List<PointF> getFaceOvalContour() {
        return faceOvalContour;
    }

    public void setFaceOvalContour(List<PointF> faceOvalContour) {
        this.faceOvalContour = faceOvalContour;
    }

    public List<PointF> getUpperLipBottomContour() {
        return upperLipBottomContour;
    }

    public void setUpperLipBottomContour(List<PointF> upperLipBottomContour) {
        this.upperLipBottomContour = upperLipBottomContour;
    }

    public List<PointF> getLeftEyeContour() {
        return leftEyeContour;
    }

    public void setLeftEyeContour(List<PointF> leftEyeContour) {
        this.leftEyeContour = leftEyeContour;
    }

    public List<PointF> getLeftCheekContour() {
        return leftCheekContour;
    }

    public void setLeftCheekContour(List<PointF> leftCheekContour) {
        this.leftCheekContour = leftCheekContour;
    }

    public List<PointF> getLeftEyebrowBotContour() {
        return leftEyebrowBotContour;
    }

    public void setLeftEyebrowBotContour(List<PointF> leftEyebrowBotContour) {
        this.leftEyebrowBotContour = leftEyebrowBotContour;
    }

    public List<PointF> getRightEyeContour() {
        return rightEyeContour;
    }

    public void setRightEyeContour(List<PointF> rightEyeContour) {
        this.rightEyeContour = rightEyeContour;
    }

    public List<PointF> getLeftEyebrowTopContour() {
        return leftEyebrowTopContour;
    }

    public void setLeftEyebrowTopContour(List<PointF> leftEyebrowTopContour) {
        this.leftEyebrowTopContour = leftEyebrowTopContour;
    }

    public List<PointF> getLowerLipBotContour() {
        return lowerLipBotContour;
    }

    public void setLowerLipBotContour(List<PointF> lowerLipBotContour) {
        this.lowerLipBotContour = lowerLipBotContour;
    }

    public List<PointF> getLowerLipTopContour() {
        return lowerLipTopContour;
    }

    public void setLowerLipTopContour(List<PointF> lowerLipTopContour) {
        this.lowerLipTopContour = lowerLipTopContour;
    }

    public List<PointF> getNoseBotContour() {
        return noseBotContour;
    }

    public void setNoseBotContour(List<PointF> noseBotContour) {
        this.noseBotContour = noseBotContour;
    }

    public List<PointF> getNoseBridgeContour() {
        return noseBridgeContour;
    }

    public void setNoseBridgeContour(List<PointF> noseBridgeContour) {
        this.noseBridgeContour = noseBridgeContour;
    }

    public List<PointF> getUpperLipTopContour() {
        return upperLipTopContour;
    }

    public void setUpperLipTopContour(List<PointF> upperLipTopContour) {
        this.upperLipTopContour = upperLipTopContour;
    }

    public List<PointF> getRightCeekContour() {
        return rightCeekContour;
    }

    public void setRightCeekContour(List<PointF> rightCeekContour) {
        this.rightCeekContour = rightCeekContour;
    }

    public List<PointF> getRightEyebrowBotContour() {
        return rightEyebrowBotContour;
    }

    public void setRightEyebrowBotContour(List<PointF> rightEyebrowBotContour) {
        this.rightEyebrowBotContour = rightEyebrowBotContour;
    }

    public List<PointF> getRightEyebrowTopContour() {
        return rightEyebrowTopContour;
    }

    public void setRightEyebrowTopContour(List<PointF> rightEyebrowTopContour) {
        this.rightEyebrowTopContour = rightEyebrowTopContour;
    }

    public float getRotY() {
        return rotY;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public long getReadingMilisecond() {
        return readingMilisecond;
    }

    public void setReadingMilisecond(long readingMilisecond) {
        this.readingMilisecond = readingMilisecond;
    }
}
