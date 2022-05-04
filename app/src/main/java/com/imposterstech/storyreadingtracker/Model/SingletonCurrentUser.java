package com.imposterstech.storyreadingtracker.Model;

import com.imposterstech.storyreadingtracker.Model.Response.UserModel;

public class SingletonCurrentUser {
    private UserModel loggedUser;
    private String token;

    private static SingletonCurrentUser singletonCurrentUser;

    private SingletonCurrentUser(){

    }

    public UserModel getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(UserModel loggedUser) {
        this.loggedUser = loggedUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static SingletonCurrentUser getInstance(){
        if(singletonCurrentUser==null){
            singletonCurrentUser=new SingletonCurrentUser();
        }
        return singletonCurrentUser;
    }

}
