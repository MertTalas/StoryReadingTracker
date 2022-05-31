package com.imposterstech.storyreadingtracker.Model.Request;

import com.google.gson.annotations.SerializedName;

public class AvatarRequestModel {
    @SerializedName("avatarName")
    private String avatarName;
    @SerializedName("avatarURL")
    private String avatarURL;
    @SerializedName("avatarPrice")
    private int avatarPrice;

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public int getAvatarPrice() {
        return avatarPrice;
    }

    public void setAvatarPrice(int avatarPrice) {
        this.avatarPrice = avatarPrice;
    }
}
