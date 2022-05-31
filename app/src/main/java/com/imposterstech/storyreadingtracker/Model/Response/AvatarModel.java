package com.imposterstech.storyreadingtracker.Model.Response;

import com.google.gson.annotations.SerializedName;

public class AvatarModel {

    @SerializedName("avatarName")
    private String avatarName;
    @SerializedName("avatarPrice")
    private int avatarPrice;
    @SerializedName("avatarURL")
    private String avatarURL;
    @SerializedName("avatarId")
    private String avatarId;


    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public int getAvatarPrice() {
        return avatarPrice;
    }

    public void setAvatarPrice(int avatarPrice) {
        this.avatarPrice = avatarPrice;
    }


}
