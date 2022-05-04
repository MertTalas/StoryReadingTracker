package com.imposterstech.storyreadingtracker.service;

import java.util.Date;

public class APIError {
    private String message;
    private Date timeStamp;

    public String getMessage() {
        return message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }
}
