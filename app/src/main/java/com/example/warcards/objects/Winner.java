package com.example.warcards.objects;

import android.location.Location;

public class Winner {

    String name;
    int score;
    Location location;

    // ================================================================

    public Winner() { }

    public Winner(String name, int score, Location location){
        this.name = name;
        this.score = score;
        this.location = location;
    }

    // ================================================================

    public String getName() { return name; }

    public int getScore() { return score; }

    public Location getLocation() { return location; }
}
