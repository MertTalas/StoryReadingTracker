package com.imposterstech.storyreadingtracker.Model.Response;

import com.google.gson.annotations.SerializedName;

public class PasswordChangeModel {
    @SerializedName("code")
    private String code;
    @SerializedName("email")
    private String email;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
