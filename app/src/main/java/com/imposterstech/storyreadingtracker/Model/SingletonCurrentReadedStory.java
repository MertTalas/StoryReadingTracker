package com.imposterstech.storyreadingtracker.Model;

import com.imposterstech.storyreadingtracker.Model.Response.SimpleStoryUserModel;
import com.imposterstech.storyreadingtracker.Model.Response.StoryUserModel;

public class SingletonCurrentReadedStory {

    private SimpleStoryUserModel simpleStoryUserModel;

    private static SingletonCurrentReadedStory singletonCurrentReadedStory;

    private SingletonCurrentReadedStory(){

    }

    public SimpleStoryUserModel getSimpleStoryUserModel() {
        return simpleStoryUserModel;
    }
    public void setSimpleStoryUserModel(SimpleStoryUserModel simpleStoryUserModel){
        this.simpleStoryUserModel=simpleStoryUserModel;
    }

    public static SingletonCurrentReadedStory getInstance(){
        if(singletonCurrentReadedStory==null){
            singletonCurrentReadedStory=new SingletonCurrentReadedStory();
        }
        return singletonCurrentReadedStory;
    }

}
