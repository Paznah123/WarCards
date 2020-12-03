package com.example.warcards.fragments;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.warcards.objects.Card;
import com.example.warcards.objects.Dealer;
import com.example.warcards.R;

import java.util.ArrayList;
import java.util.Random;

public class player_fragment extends Fragment {

    private static final String TAG = "fragment_player";

    private View view;

    private Dealer.Side side;

    private ImageView img;
    private TextView score;
    private EditText name;

    private Random rand;
    private Card currCard;
    private int gameScore;

    private int playerImgArrIndex;
    private boolean gameRunning;

    //====================================================

    public player_fragment(View view, Dealer.Side side) {
        this.view = view;
        this.side = side;
        this.rand = new Random();
        this.currCard = new Card();
        this.gameScore = 0;
        this.gameRunning = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_player, container, false);

        findViews();

        TypedArray profilePics = view.getResources().obtainTypedArray(R.array.playerImages);
        this.playerImgArrIndex = rand.nextInt(profilePics.length() - 1);
        setImgChangeListener(profilePics);
        changePlayerImg(profilePics);

        return view;
    }

    //=============================================================================================================

    void findViews(){
        img = view.findViewById(R.id.fragment_player_img);
        score = view.findViewById(R.id.fragment_player_score);
        name = view.findViewById(R.id.fragment_player_editText);
    }

    // change player img listener
    private void setImgChangeListener(TypedArray images){
        playerImgArrIndex = 0;
        img.setOnClickListener(v -> {
            if (!gameRunning)
                changePlayerImg(images);
        });
    }

    void changePlayerImg(TypedArray images){
        img.setImageResource(images.getResourceId(playerImgArrIndex,-1));

        if(playerImgArrIndex < images.length()-1)
            playerImgArrIndex++;
        else
            playerImgArrIndex = 0;
    }

    public int getCard_fromDealer(Dealer dealer) {
        ArrayList<Card> cardStack = dealer.getCardStack();
        Card randCard = cardStack.get(rand.nextInt(cardStack.size()));
        cardStack.remove(randCard);

        // sets new card in imageView
        int img_id = view.getResources().getIdentifier("card_"+ randCard.getImageName(), "drawable", view.getContext().getPackageName());
        dealer.getCardView_bySide(side).setImageResource(img_id);
        currCard = randCard;

        return currCard.getValue();
    }

    //======================================================

    private void lockEditText() {
        boolean state = !gameRunning;
        name.setFocusable(state);
        name.setEnabled(state);
        name.setCursorVisible(state);
        name.setFocusableInTouchMode(state);
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
        lockEditText();
    }

    //======================================================

    public void resetGameScore() { this.gameScore = 0; }

    public void incrementScore() { this.score.setText("" + ++gameScore); }

    public void setName(String name) {
        this.name = new EditText(view.getContext());
        this.name.setText(name);
    }

    //======================================================

    public String getPlayerName() { return name.getText().toString(); }

    public int getGameScore() { return gameScore; }

    public boolean isGameRunning() { return gameRunning; }

    public int getProfilePic_id() { return playerImgArrIndex; }
}
