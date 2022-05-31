package com.imposterstech.storyreadingtracker.Model;

import com.imposterstech.storyreadingtracker.Model.Response.FaceExperienceModel;
import com.imposterstech.storyreadingtracker.Model.Response.StoryModel;
import com.imposterstech.storyreadingtracker.Model.Response.UserModel;

public class SingletonCurrentStoryReading {

    private StoryModel storyModel;
    private FaceExperienceModel faceExperienceModel;


    private static SingletonCurrentStoryReading singletonCurrentStoryReading;

    private SingletonCurrentStoryReading(){

    }

    public FaceExperienceModel getFaceExperienceModel() {
        return faceExperienceModel;
    }

    public void setFaceExperienceModel(FaceExperienceModel faceExperienceModel) {
        this.faceExperienceModel = faceExperienceModel;
    }

    public StoryModel getStoryModel() {
        return storyModel;
    }

    public void setStoryModel(StoryModel storyModel) {
        this.storyModel = storyModel;
    }



    public static SingletonCurrentStoryReading getInstance(){
        if(singletonCurrentStoryReading==null){
            singletonCurrentStoryReading=new SingletonCurrentStoryReading();
        }
        return singletonCurrentStoryReading;
    }


}
