package com.example.warcards.objects;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.LinkedList;

public class SharedPrefs {

    private String TAG = "SharedPrefs";

    private static final String KEY = "WinnersList";

    private static SharedPrefs instance;
    private SharedPreferences prefs;

    private static LinkedList<Winner> winnersList = new LinkedList<>();

    private boolean TIMER_MODE = false;

    //====================================================

    private SharedPrefs(Context appContext){
        prefs = appContext.getApplicationContext().getSharedPreferences(TAG,Context.MODE_PRIVATE);
    }

    public static void initPrefs(Context appContext){
        if(instance == null)
            instance = new SharedPrefs(appContext);
        Type listType = new TypeToken<LinkedList<Winner>>() {}.getType();
        LinkedList<Winner> gsonList = new Gson().fromJson(SharedPrefs.getInstance().getString(KEY,""),listType);
        if (gsonList != null)
            winnersList = gsonList;
    }

    public void addWinner(Winner winner){
        winnersList.add(winner);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY,new Gson().toJson(winnersList));
        editor.apply();
    }

    //====================================================

    public static SharedPrefs getInstance() {
        return instance;
    }

    public boolean isTIMER_MODE() { return TIMER_MODE; }

    public void reverseTimerMode() { TIMER_MODE = !TIMER_MODE; }

    //====================================================

    public static LinkedList<Winner> getWinnersList() { return winnersList; }

    public String getString(String key, String def){
        return prefs.getString(key, def);
    }

    //====================================================

    public void removeKey(String key){
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(key).apply();
    }
}
