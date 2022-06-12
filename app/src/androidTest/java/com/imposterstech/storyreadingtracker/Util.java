package com.imposterstech.storyreadingtracker;

import android.util.Log;

public class Util {

    static boolean logEnabled = true;
    public static void logTest(String log){
        if (logEnabled) {
            Log.v("EspressoTest", log);
        } } }
