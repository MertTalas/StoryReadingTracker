package com.imposterstech.storyreadingtracker.Model.Request;

import com.google.gson.annotations.SerializedName;

public class PasswordChangeRequestModel {
    @SerializedName("email")
    private String email;
    @SerializedName("newPassword")
    private String newPassword;
    @SerializedName("verificationCode")
    private String verificationCode;


    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
