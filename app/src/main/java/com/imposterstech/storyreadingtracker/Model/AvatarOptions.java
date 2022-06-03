package com.imposterstech.storyreadingtracker.Model;

public class AvatarOptions {

    String avatarName;
    String avatarURL;
    int avatarPrice;

    public int getAvatarPrice() {
        return avatarPrice;
    }

    public void setAvatarPrice(int avatarPrice) {
        this.avatarPrice = avatarPrice;
    }

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
}
