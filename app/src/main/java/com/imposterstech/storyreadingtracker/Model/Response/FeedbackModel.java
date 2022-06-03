package com.imposterstech.storyreadingtracker.Model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class FeedbackModel {
    @SerializedName("feedbackId")
    private String feedbackId;
    @SerializedName("feedbackText")
    private String feedbackText;
    @SerializedName("feedbackRead")
    private boolean feedbackRead;
    @SerializedName("userDetails")
    private UserModel userDetails;  //later make it rest model of user
    @SerializedName("storyDetails")
    private StoryModel storyDetails;
    @SerializedName("createdOn")
    private Date createdOn;

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public boolean isFeedbackRead() {
        return feedbackRead;
    }

    public void setFeedbackRead(boolean feedbackRead) {
        this.feedbackRead = feedbackRead;
    }

    public UserModel getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserModel userDetails) {
        this.userDetails = userDetails;
    }

    public StoryModel getStoryDetails() {
        return storyDetails;
    }

    public void setStoryDetails(StoryModel storyDetails) {
        this.storyDetails = storyDetails;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
