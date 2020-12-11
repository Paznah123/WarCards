package com.example.warcards.objects;

import android.location.Location;

public class Winner {

    private String name;
    private int score;
    private int imgIndex;
    private Location location;
    private String date;

    // ================================================================

    public Winner(String name, int score, int imgIndex, Location location, String date){
        this.name = name;
        this.score = score;
        this.imgIndex = imgIndex;
        this.location = location;
        this.date = date;
    }

    // ================================================================

    public String getName() { return name; }

    public int getScore() { return score; }

    public int getImgIndex() { return imgIndex; }

    public Location getLocation() { return location; }

    public String getDate() { return date; }

    public void setLocation(Location location) { this.location = location; }
}
