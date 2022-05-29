package com.imposterstech.storyreadingtracker.Model;

import com.imposterstech.storyreadingtracker.Model.Response.SimpleStoryUserModel;
import com.imposterstech.storyreadingtracker.Model.Response.StoryModel;

public class SingletonCurrentEditableStory {

    private StoryModel storyModel;

    private static SingletonCurrentEditableStory singletonCurrentEditableStory;

    private SingletonCurrentEditableStory(){

    }

    public StoryModel getStoryModel() {
        return storyModel;
    }
    public void setStoryModel(StoryModel storyModel){
        this.storyModel=storyModel;
    }

    public static SingletonCurrentEditableStory getInstance(){
        if(singletonCurrentEditableStory==null){
            singletonCurrentEditableStory=new SingletonCurrentEditableStory();
        }
        return singletonCurrentEditableStory;
    }
}
