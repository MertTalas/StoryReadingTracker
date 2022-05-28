package com.imposterstech.storyreadingtracker.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class SimpleStoryUserModel {
    @SerializedName("storyUserId")
    private String storyUserId;
    @SerializedName("feedbackRate")
    private int feedbackRate;
    @SerializedName("gainedPoint")
    private int gainedPoint;
    @SerializedName("successRate")
    private int successRate;
    @SerializedName("readOnDate")
    private Date readOnDate;
    @SerializedName("storyDetails")
    private StoryModel storyDetails;
    @SerializedName("faceExperienceDocumentId")
    private String faceExperienceDocumentId;

    public String getStoryUserId() {
        return storyUserId;
    }

    public void setStoryUserId(String storyUserId) {
        this.storyUserId = storyUserId;
    }

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

    public Date getReadOnDate() {
        return readOnDate;
    }

    public void setReadOnDate(Date readOnDate) {
        this.readOnDate = readOnDate;
    }

    public StoryModel getStoryDetails() {
        return storyDetails;
    }

    public void setStoryDetails(StoryModel storyDetails) {
        this.storyDetails = storyDetails;
    }

    public String getFaceExperienceDocumentId() {
        return faceExperienceDocumentId;
    }

    public void setFaceExperienceDocumentId(String faceExperienceDocumentId) {
        this.faceExperienceDocumentId = faceExperienceDocumentId;
    }
}
