package com.imposterstech.storyreadingtracker.Model.Request;

import com.google.gson.annotations.SerializedName;

public class FeedbackRequestModel {

    @SerializedName("feedbackText")
    private String feedbackText;
    @SerializedName("feedbackRead")
    private boolean feedbackRead=false;
    @SerializedName("feedbackRate")
    private float feedbackRate;

    public float getFeedbackRate() {
        return feedbackRate;
    }

    public void setFeedbackRate(float feedbackRate) {
        this.feedbackRate = feedbackRate;
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
}
