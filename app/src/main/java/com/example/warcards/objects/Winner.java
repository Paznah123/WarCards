package com.example.warcards.objects;

import android.graphics.Bitmap;
import android.location.Location;

public class Winner {

    private String name;
    private int score;
    private Bitmap imgBitmap;
    private Location location;

    // ================================================================

    public Winner(String name, int score, Bitmap imgBitmap){
        this.name = name;
        this.score = score;
        this.imgBitmap = imgBitmap;
    }

    // ================================================================

    public String getName() { return name; }

    public int getScore() { return score; }

    public Bitmap getImgBitmap() { return imgBitmap; }

    public Location getLocation() { return location; }


}
