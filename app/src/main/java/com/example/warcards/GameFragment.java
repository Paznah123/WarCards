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
import android.widget.Toast;

import com.plattysoft.leonids.ParticleSystem;

import java.util.ArrayList;
import java.util.HashMap;
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
        if(cardStack.isEmpty()) {
            initCardStack();
            leftPlayer.resetGameScore();
            rightPlayer.resetGameScore();
            progressBar.setProgress(0);
        }
    }

    // ================================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game, container, false);

        initCardStack();
        init_players();

        progressBar = view.findViewById(R.id.main_progressBar);

        main_BTN_play = view.findViewById(R.id.main_BTN_play);

        // start and pause game by pressing play button
        main_BTN_play.setOnClickListener(v -> {
            if(!leftPlayer.getPlayerName().isEmpty()
                    && !rightPlayer.getPlayerName().isEmpty()) {
                if (!SettingsFragment.setTimer) {
                    if (cardStack.size() == 52)
                        lockPlayersImgListener(true);
                    clickMode();
                } else // when timer is on
                    timerMode();
            } else {
                Toast toast = Toast.makeText(this.getActivity(),"Enter Names!",Toast.LENGTH_LONG);
                toast.show();
            }

        });

        onBackPressedListener();

        return view;
    }

    //======================================================

    private void onBackPressedListener(){
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
    }

    //======================================================

    // changes cards by timer
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable, 200);
            if(cardStack.isEmpty())
                handler.removeCallbacks(runnable);
            clickMode();
        }
    };

    //======================================================

    void clickMode(){
        if(!cardStack.isEmpty()) {
            iMainActivity.playSound(R.raw.card_dealing);
            updateCardsAndScoreView();
        } else {
            iMainActivity.inflateFragment("WinnerFragment", true, createWinnerBundle());
            progressBar.setProgress(0);
        }
    }

    private void timerMode() {
        if (!leftPlayer.isGameRunning()) {
            handler.postDelayed(runnable, 1000);
            lockPlayersImgListener(true);
        } else {
            handler.removeCallbacks(runnable);
            lockPlayersImgListener(false);
        }
    }

    //======================================================

    void lockPlayersImgListener(boolean key){
        lockListenerHelper(leftPlayer, key);
        lockListenerHelper(rightPlayer, key);
    }

    private void lockListenerHelper(PlayerView playerView, boolean key){
        playerView.setGameRunning(key);
        playerView.lockEditText();
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

    // ================================================================

    void init_players(){
        leftPlayer = init_playerViewHelper(R.id.main_IMG_leftPlayer, R.id.main_EditTXT_left, R.id.main_IMG_leftCard, R.id.main_LBL_leftScore);
        rightPlayer = init_playerViewHelper(R.id.main_IMG_rightPlayer, R.id.main_EditTXT_right, R.id.main_IMG_rightCard, R.id.main_LBL_rightScore);
    }

    private PlayerView init_playerViewHelper(int imgId, int editTextId, int cardId, int scoreId){
        // initializes player view
        HashMap<String, Integer> player_keys = createPlayerHashMap(imgId, editTextId, cardId, scoreId);
        PlayerView playerView = new PlayerView(view, player_keys);

        return playerView;
    }

    private HashMap<String,Integer> createPlayerHashMap(int imgId, int editTextId, int cardId, int scoreId){
        // gives keys to each view id for player
        HashMap<String, Integer> hashMap = new HashMap<>();
                                hashMap.put("image",imgId);
                                hashMap.put("name",editTextId);
                                hashMap.put("card",cardId);
                                hashMap.put("score",scoreId);
        return hashMap;
    }

    // ================================================================

    void updateCardsAndScoreView() {
        int leftCardVal = leftPlayer.drawNewCard(cardStack);
        int rightCardVal = rightPlayer.drawNewCard(cardStack);

        if (leftCardVal > rightCardVal)
            leftPlayer.getPlayerScoreView().setText(""+ leftPlayer.increaseReturnGameScore());
        else if (leftCardVal < rightCardVal)
            rightPlayer.getPlayerScoreView().setText(""+ rightPlayer.increaseReturnGameScore());

        progressBar.incrementProgressBy(1);
    }

    // ================================================================

    Bundle createWinnerBundle() {
        Bundle bundle = new Bundle();

        createBundleHelper("left", leftPlayer, bundle);
        createBundleHelper("right", rightPlayer, bundle);

        return bundle;
    }

    private void createBundleHelper(String side, PlayerView playerView, Bundle bundle){
        bundle.putInt(side +"Score", playerView.getGameScore());
        bundle.putString(side +"Name", playerView.getPlayerName());
        bundle.putByteArray(side +"ImgBitmap", playerView.getCurrImgBitmap());
    }

    //======================================================

}