package com.example.warcards.objects;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;

public class SharedPrefs {

    private String TAG = "SharedPrefs";

    public interface KEYS {
        String WINNERS_LIST = "WINNERS_LIST";
        String LOCATION_LIST = "LOCATION_LIST";
        String WINNER = "WINNER";
        String PLAYER_NAME = "PLAYER_NAME";
        String PLAYER_SCORE = "PLAYER_SCORE";
        String PLAYER_IMG_INDEX = "PROFILE_PIC_INDEX";
    }

    private static SharedPrefs instance;
    private SharedPreferences prefs;

    public static final int LIST_SIZE = 15;

    private static LinkedList<Winner> winnersList;

    private boolean TIMER_MODE = false;

    //====================================================

    private SharedPrefs(Context appContext){
        prefs = appContext.getApplicationContext().getSharedPreferences(TAG,Context.MODE_PRIVATE);
        winnersList = new LinkedList<>();
    }

    private static void retrieveWinnersList(){
        Type listType = new TypeToken<LinkedList<Winner>>() {}.getType();
        LinkedList<Winner> gsonList = new Gson().fromJson(SharedPrefs.getInstance().getString(KEYS.WINNERS_LIST,""),listType);
        if (gsonList != null) {
            winnersList = gsonList;
        }
    }

    public static void initPrefs(Context appContext){
        if(instance == null) {
            instance = new SharedPrefs(appContext);
            retrieveWinnersList();
        }
    }

    public void addWinner(Winner winner){
        if(winnersList.size() < LIST_SIZE)
            winnersList.add(winner);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEYS.WINNERS_LIST,new Gson().toJson(winnersList));

        editor.apply();
    }

    public void removeKey(String key){
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(key).apply();
    }

    //====================================================

    public static SharedPrefs getInstance() { return instance; }

    public boolean isTIMER_MODE() { return TIMER_MODE; }

    public void invertTimerMode() { TIMER_MODE = !TIMER_MODE; }

    //====================================================

    public static LinkedList<Winner> getWinnersList() { return winnersList; }

    public String getString(String key, String defValue){ return prefs.getString(key, defValue); }

    //====================================================

}
