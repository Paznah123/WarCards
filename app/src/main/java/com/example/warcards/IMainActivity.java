package com.example.warcards;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

public interface IMainActivity {

    void hideSystemUI();

    void inflateFragment(String fragmentTag, boolean addToBackStack,  Bundle bundle);

    void playSound(int rawSound);

    void stopSound();
}
