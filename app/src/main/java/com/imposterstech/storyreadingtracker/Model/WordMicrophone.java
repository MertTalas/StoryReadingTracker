package com.imposterstech.storyreadingtracker.Model;

import com.google.gson.annotations.SerializedName;

public class WordMicrophone {
    @SerializedName("expectedWord")
    private String expectedWord ;
    @SerializedName("readedWord")
    private String readedWord;
    @SerializedName("readingMilisecond")
    private long readingMilisecond;


    public String getExpectedWord() {
        return expectedWord;
    }

    public void setExpectedWord(String expectedWord) {
        this.expectedWord = expectedWord;
    }

    public String getReadedWord() {
        return readedWord;
    }

    public void setReadedWord(String readedWord) {
        this.readedWord = readedWord;
    }

    public long getReadingMilisecond() {
        return readingMilisecond;
    }

    public void setReadingMilisecond(long readingMilisecond) {
        this.readingMilisecond = readingMilisecond;
    }
}
