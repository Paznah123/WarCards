package com.example.warcards.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.warcards.IMainActivity;
import com.example.warcards.R;

public class selector_fragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "SelectorFragment";

    Button startGame, topScores, settings;

    private IMainActivity iMainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selector, container, false);

        startGame = view.findViewById(R.id.game_button);
        topScores = view.findViewById(R.id.topScores_button);
        settings = view.findViewById(R.id.settings_button);

        startGame.setOnClickListener(this);
        topScores.setOnClickListener(this);
        settings.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.game_button:
                iMainActivity.inflateFragment(getString(R.string.GameFragment),true, null);
                break;
            case R.id.topScores_button:
                iMainActivity.inflateFragment(getString(R.string.TopScoresFragment), true, null);
                break;
            case R.id.settings_button:
                iMainActivity.inflateFragment(getString(R.string.SettingsFragment), true, null);
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMainActivity = (IMainActivity) getActivity();
    }
}