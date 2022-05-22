package com.imposterstech.storyreadingtracker.Model.Request;

import com.google.gson.annotations.SerializedName;

public class ReadingExperienceRequestModel {
    @SerializedName("feedbackRate")
    private int feedbackRate;
    @SerializedName("gainedPoint")
    private int gainedPoint;
    @SerializedName("successRate")
    private int successRate;
    @SerializedName("faceExperienceDocumentId")
    private String faceExperienceDocumentId;


    public int getFeedbackRate() {
        return feedbackRate;
    }

    public void setFeedbackRate(int feedbackRate) {
        this.feedbackRate = feedbackRate;
    }

    public int getGainedPoint() {
        return gainedPoint;
    }

    public void setGainedPoint(int gainedPoint) {
        this.gainedPoint = gainedPoint;
    }

    public int getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(int successRate) {
        this.successRate = successRate;
    }

    public String getFaceExperienceDocumentId() {
        return faceExperienceDocumentId;
    }

    public void setFaceExperienceDocumentId(String faceExperienceDocumentId) {
        this.faceExperienceDocumentId = faceExperienceDocumentId;
    }
}
