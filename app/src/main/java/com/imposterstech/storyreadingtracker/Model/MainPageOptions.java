package com.imposterstech.storyreadingtracker.Model;

public class MainPageOptions {
    private String name;
    private int image;
    private String description;

    public MainPageOptions(String name,int image, String description){
        this.name=name;
        this.image=image;
        this.description=description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
