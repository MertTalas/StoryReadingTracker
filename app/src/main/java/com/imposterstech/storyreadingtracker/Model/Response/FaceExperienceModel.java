package com.imposterstech.storyreadingtracker.Model.Response;

import com.google.gson.annotations.SerializedName;
import com.imposterstech.storyreadingtracker.Model.Contour;

import java.util.Date;
import java.util.List;

public class FaceExperienceModel {
    @SerializedName("faceExperienceDocumentId")
    private String faceExperienceDocumentId;
    @SerializedName("userId")
    private String userId;
    @SerializedName("storyId")
    private String storyId;
    @SerializedName("Points")
    private List<Contour> Points;
    @SerializedName("readingDate")
    private Date readingDate;


    public String getFaceExperienceDocumentId() {
        return faceExperienceDocumentId;
    }

    public void setFaceExperienceDocumentId(String faceExperienceDocumentId) {
        this.faceExperienceDocumentId = faceExperienceDocumentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public List<Contour> getPoints() {
        return Points;
    }

    public void setPoints(List<Contour> points) {
        Points = points;
    }

    public Date getReadingDate() {
        return readingDate;
    }

    public void setReadingDate(Date readingDate) {
        this.readingDate = readingDate;
    }
}
