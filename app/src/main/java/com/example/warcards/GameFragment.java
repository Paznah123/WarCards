package com.example.warcards;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.plattysoft.leonids.ParticleSystem;

import java.util.ArrayList;
import java.util.Map;

public class GameFragment extends Fragment {

    private static final String TAG = "GameFragment";

    View view;

    IMainActivity iMainActivity;

    ImageView main_BTN_play;

    PlayerView leftPlayer;
    PlayerView rightPlayer;

    ProgressBar progressBar;

    // ================================================================

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iMainActivity = (IMainActivity) getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(cardsDealt == 52) {
            initCardStack();
            leftPlayer.resetGameScore();
            rightPlayer.resetGameScore();
            progressBar.setProgress(0);
            cardsDealt = 0;
        }
    }

    // ================================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game, container, false);

        initCardStack();

        leftPlayer = new PlayerView(view, R.id.main_IMG_leftPlayer, R.id.main_IMG_leftCard, R.id.main_LBL_leftScore, R.id.main_EditTXT_left);
        rightPlayer = new PlayerView(view, R.id.main_IMG_rightPlayer, R.id.main_IMG_rightCard, R.id.main_LBL_rightScore, R.id.main_EditTXT_right);

        progressBar = view.findViewById(R.id.main_progressBar);

        main_BTN_play = view.findViewById(R.id.main_BTN_play);

        // start and pause game by pressing play button
        main_BTN_play.setOnClickListener(v -> {
            if(!SettingsFragment.setTimer){
                if(cardsDealt < 52) {
                    iMainActivity.playSound(R.raw.card_dealing);
                    lockPlayersImgListener(true);
                    updateCardsAndScoreView();
                } else {
                    Bundle bundle = createWinnerBundle();
                    iMainActivity.inflateFragment("WinnerFragment", true, bundle);
                    //iMainActivity.playSound(R.raw.victory_sound);
                }
            } else { // when timer is on
                if (!leftPlayer.isGameRunning()) {
                    handler.postDelayed(runnable, 500);
                    lockPlayersImgListener(true);
                } else {
                    handler.removeCallbacks(runnable);
                    lockPlayersImgListener(false);
                }
            }
        });

        // stops runnable calls after back is pressed
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener() {
            @Override
            public boolean onKey( View v, int keyCode,KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK)
                    if(SettingsFragment.setTimer)
                        handler.removeCallbacks(runnable);
                return false;
            }
        });

        return view;
    }

    //======================================================

    void lockPlayersImgListener(boolean key){
        leftPlayer.setGameRunning(key);
        leftPlayer.lockEditText(!key);
        rightPlayer.setGameRunning(key);
        rightPlayer.lockEditText(!key);

    }

    ArrayList<Card> cardStack = new ArrayList<>();
    void initCardStack() {  // initializes the deck
        for (int i =  1 ; i <= 4 ; i++) { // gets type name from arrays.xml
            int typeId = getResources().obtainTypedArray(R.array.names).getResourceId(i, 0);
            String type = getResources().getResourceEntryName(typeId);
            for (int j = 2 ; j <= 14 ; j++) {
                cardStack.add(new Card(type, j));
            }
        }
    }

    //======================================================

    // changes cards by timer
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable, 1000);
            if(cardsDealt < 52) {
                iMainActivity.playSound(R.raw.card_dealing);
                updateCardsAndScoreView();
            } else {
                Bundle bundle = createWinnerBundle();
                iMainActivity.inflateFragment("WinnerFragment", true, bundle);
                //iMainActivity.playSound(R.raw.victory_sound);
                handler.removeCallbacks(runnable);
            }
        }
    };

    // ================================================================

    int cardsDealt = 0;
    void updateCardsAndScoreView() {
        int leftCardVal = leftPlayer.drawNewCard(cardStack);
        int rightCardVal = rightPlayer.drawNewCard(cardStack);

        if (leftCardVal > rightCardVal)
            leftPlayer.getPlayerScoreView().setText(""+ leftPlayer.increaseReturnGameScore());
        else if (leftCardVal < rightCardVal)
            rightPlayer.getPlayerScoreView().setText(""+ rightPlayer.increaseReturnGameScore());

        progressBar.incrementProgressBy(1);

        cardsDealt += 2;
    }

    // ================================================================

    Bundle createWinnerBundle() {
        Bundle bundle = new Bundle();
        // left player data
        bundle.putInt("leftScore", leftPlayer.getGameScore());
        bundle.putString("leftName", leftPlayer.getPlayerName());
        bundle.putByteArray("leftImgBitmap", leftPlayer.getCurrImgBitmap());
        // right player data
        bundle.putInt("rightScore", rightPlayer.getGameScore());
        bundle.putByteArray("rightImgBitmap", rightPlayer.getCurrImgBitmap());
        bundle.putString("rightName", rightPlayer.getPlayerName());

        return bundle;
    }

    //======================================================

}