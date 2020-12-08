package com.example.warcards.objects;

import android.graphics.Bitmap;
import android.location.Location;

public class Winner {

    private String name;
    private int score;
    private int imgIndex;
    private Location location;

    // ================================================================

    public Winner(String name, int score, int imgIndex){
        this.name = name;
        this.score = score;
        this.imgIndex = imgIndex;
    }

    // ================================================================

    public String getName() { return name; }

    public int getScore() { return score; }

    public int getImgIndex() { return imgIndex; }

    public Location getLocation() { return location; }


}
