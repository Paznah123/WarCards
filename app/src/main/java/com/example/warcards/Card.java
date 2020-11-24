package com.example.warcards;

import android.widget.ImageView;

public class Card {

    private int value;
    private String imageName;

    //====================================================

    public Card() {
    }

    public Card(String type, int value) {
        this.imageName= type + value;
        this.value = value;
    }

    //====================================================

    public int getValue() {
        return value;
    }

    public String getImageName() {
        return imageName;
    }

    //====================================================

    public void setValue(int value) {
        this.value = value;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

}
