package com.example.calendarapplication;

import android.app.Application;
import android.content.Context;

import com.beardedhen.androidbootstrap.TypefaceProvider;

public class MyApplication extends Application {

    private static Context context;

    public void onCreate(){
        super.onCreate();
        MyApplication.context = getApplicationContext();
        TypefaceProvider.registerDefaultIconSets();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
