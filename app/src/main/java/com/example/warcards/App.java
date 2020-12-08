package com.example.warcards;

import android.app.Application;

import com.example.warcards.objects.SharedPrefs;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPrefs.initPrefs(this);
    }

}
