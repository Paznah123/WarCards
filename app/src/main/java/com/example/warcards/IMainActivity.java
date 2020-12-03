package com.example.warcards;

import android.os.Bundle;
import android.view.View;

import com.example.warcards.fragments.player_fragment;
import com.example.warcards.objects.Dealer;

public interface IMainActivity {

    void hideSystemUI();

    void inflateFragment(String fragmentTag, boolean addToBackStack, Bundle bundle);

    void playSound(int rawSound);

    void stopSound();

}
