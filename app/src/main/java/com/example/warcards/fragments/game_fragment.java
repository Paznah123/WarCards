package com.example.warcards.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.warcards.objects.Dealer;
import com.example.warcards.R;

public class game_fragment extends Fragment {

    private static final String TAG = "GameFragment";

    View view;

    Dealer dealer;

    player_fragment leftPlayer;
    player_fragment rightPlayer;

    //====================================================

    @Override
    public void onResume() { // resets any game progress
        super.onResume();
        if(dealer.getCardStack().isEmpty())
            dealer.resetGameProgress(leftPlayer,rightPlayer);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game, container, false);

        init_views();

        dealer.getPlayButton().setOnClickListener(v -> validate_play_click() );

        onBackPressedListener();

        return view;
    }

    //=============================================================================================================

    void init_views(){
        dealer = new Dealer(view, R.id.main_IMG_leftCard, R.id.main_IMG_rightCard, R.id.main_BTN_play, R.id.main_progressBar);

        leftPlayer = putPlayerInView(Dealer.Side.LEFT,R.id.game_player_left);
        rightPlayer = putPlayerInView(Dealer.Side.RIGHT,R.id.game_player_right);
    }


    player_fragment putPlayerInView(Dealer.Side side, int layout_id){
        player_fragment player_fragment = new player_fragment(view, side);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(layout_id, player_fragment).commit();

        return player_fragment;
    }

    //====================================

    void validate_play_click() {
        view.requestFocusFromTouch();
        if(!leftPlayer.getPlayerName().isEmpty() && !rightPlayer.getPlayerName().isEmpty())
            decide_mode();
        else
            Toast.makeText(this.getActivity(),"Enter Names!",Toast.LENGTH_SHORT).show();
    }

    void decide_mode() {
        if (!settings_fragment.TIMER_MODE) {
            if (dealer.getCardStack().size() == 52)
                game_ON(true);
            dealer.dealCards_toPlayers(leftPlayer,rightPlayer);
        } else // when timer is on
            requestCards_byTimer();
    }

    //=====================================

    void game_ON(boolean key){ // locks any changes in editText and player image after game starts
        leftPlayer.setGameRunning(key);
        rightPlayer.setGameRunning(key);
    }

    void requestCards_byTimer() { // enters here when SettingsFragment.TIMER_MODE = true
        if (!leftPlayer.isGameRunning()) {
            handler.postDelayed(runnable, 300);
            game_ON(true);
        } else {
            handler.removeCallbacks(runnable);
            game_ON(false);
        }
    }

    //======================================================

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() { // changes cards by timer
        @Override
        public void run() {
            handler.postDelayed(runnable, 1000);
            if(dealer.getCardStack().isEmpty())
                handler.removeCallbacks(runnable);
            dealer.dealCards_toPlayers(leftPlayer,rightPlayer); // runnable generates cards request every call
        }
    };

    private void onBackPressedListener(){ // stops runnable calls after back is pressed
        view.setFocusableInTouchMode(true);
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && settings_fragment.TIMER_MODE)
                handler.removeCallbacks(runnable);
            return false;
        });
    }

    //======================================================

}