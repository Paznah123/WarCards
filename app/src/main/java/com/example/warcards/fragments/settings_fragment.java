package com.example.warcards.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.example.warcards.R;

public class settings_fragment extends Fragment {

    private static final String TAG = "settings_Fragment";

    View view;

    ToggleButton timerToggle;

    static boolean TIMER_MODE = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        if(savedInstanceState != null)
            timerToggle.setChecked(savedInstanceState.getBoolean("TIMER_MODE"));

        timerToggle = view.findViewById(R.id.settings_timer_toggle);
        timerToggle.setOnClickListener(v -> TIMER_MODE = !TIMER_MODE);

        return view;
    }

}