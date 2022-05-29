package com.imposterstech.storyreadingtracker.Model.Request;

import com.google.gson.annotations.SerializedName;

public class StoryRequestModel {
    @SerializedName("storyText")
    private String storyText;
    @SerializedName("title")
    private String title;

    public String getStoryText() {
        return storyText;
    }

    public void setStoryText(String storyText) {
        this.storyText = storyText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
