package com.example.warcards;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

public class SettingsFragment extends Fragment {

    private static final String TAG = "SettingsFragment";

    View view;

    ToggleButton timerToggle;

    static boolean setTimer = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        if(savedInstanceState != null)
            timerToggle.setChecked(savedInstanceState.getBoolean("setTimer"));
        timerToggle = view.findViewById(R.id.settings_timer_toggle);
        timerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTimer = !setTimer;
            }
        });
        return view;
    }

}