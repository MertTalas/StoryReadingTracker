package com.imposterstech.storyreadingtracker.Model.Response;

import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("userId")
    private String userId;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("email")
    private String email;
    @SerializedName("age")
    private int age;
    @SerializedName("gender")
    private String gender;
    @SerializedName("points")
    private int points;
    @SerializedName("termsAndPoliciesAccepted")
    private boolean termsAndPoliciesAccepted;
    @SerializedName("chosenAvatarUrl")
    private String chosenAvatarUrl;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean getTermsAndPoliciesAccepted() {
        return termsAndPoliciesAccepted;
    }

    public void setTermsAndPoliciesAccepted(boolean termsAndPoliciesAccepted) {
        this.termsAndPoliciesAccepted = termsAndPoliciesAccepted;
    }

    public String getChosenAvatarUrl() {
        return chosenAvatarUrl;
    }

    public void setChosenAvatarUrl(String chosenAvatarUrl) {
        this.chosenAvatarUrl = chosenAvatarUrl;
    }
}
