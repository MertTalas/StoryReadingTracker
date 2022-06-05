package com.imposterstech.storyreadingtracker;

public enum BASEURL {

    BASE_URL("http://192.168.1.21:8080/story-app-ws/");
    private String base_URL;

    BASEURL(String base_URL) {
        this.base_URL=base_URL;
    }

    public String getBase_URL() {
        return base_URL;
    }

    public void setBase_URL(String base_URL) {
        this.base_URL = base_URL;
    }
}
