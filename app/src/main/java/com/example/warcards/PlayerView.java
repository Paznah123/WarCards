package com.example.warcards;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class PlayerView {

    private View view;

    private ImageView playerImg;
    private ImageView cardImg;
    private TextView playerScore;

    private Random rand;
    private Card currCard;
    private int gameScore;

    private int playerImgArrIndex;
    private boolean gameRunning = false;

    //===========================================

    public PlayerView(View view, int player_IMG_ID, int card_IMG_ID, int score_LBL_ID) {
        this.view = view;

        this.playerImg = view.findViewById(player_IMG_ID);
        this.cardImg = view.findViewById(card_IMG_ID);
        this.playerScore = view.findViewById(score_LBL_ID);

        this.rand = new Random();
        this.currCard = new Card();
        this.gameScore = 0;

        // gets a random number in the range of playerImages indexes
        this.playerImgArrIndex = rand.nextInt(view.getResources().obtainTypedArray(R.array.playerImages).length()-1);

        setImgChangeListener();
    }

    //===========================================

    int drawNewCard(ArrayList<Card> cardStack) {
        Card randCard = cardStack.get(rand.nextInt(cardStack.size()));
        cardStack.remove(randCard);

        // sets new card in imageView
        int img_id = view.getResources().getIdentifier("card_"+ randCard.getImageName(), "drawable", view.getContext().getPackageName());
        getPlayerCardView().setImageResource(img_id);
        setCurrCard(randCard);

        return getCurrCard().getValue();
    }

    //===========================================

    // change player img listener
    private void setImgChangeListener(){
        playerImg.setOnClickListener(v -> {
            if (!gameRunning)
                changePlayerImg();
        });
    }

    private void changePlayerImg(){
        TypedArray images = view.getResources().obtainTypedArray(R.array.playerImages);
        playerImg.setImageResource(images.getResourceId(playerImgArrIndex,-1));

        if(playerImgArrIndex < images.length()-1)
            playerImgArrIndex++;
        else
            playerImgArrIndex = 0;
    }

    byte[] getCurrImgBitmap(){
        playerImg.setDrawingCacheEnabled(true);
        Bitmap playerImgBitmap = playerImg.getDrawingCache();

        ByteArrayOutputStream playerImgByteArr = new ByteArrayOutputStream();
        playerImgBitmap.compress(Bitmap.CompressFormat.PNG, 50, playerImgByteArr);

        return playerImgByteArr.toByteArray();
    }

    //===========================================

    public void setGameRunning(boolean gameRunning) { this.gameRunning = gameRunning; }

    public void setCurrCard(Card currCard) { this.currCard = currCard; }

    public void resetGameScore() { this.gameScore = 0; }

    //===========================================

    public int increaseReturnGameScore() { return ++this.gameScore; }

    public int getGameScore() { return gameScore; }

    public boolean isGameRunning() { return gameRunning; }

    //===========================================

    public Card getCurrCard() { return currCard; }

    public ImageView getPlayerCardView() { return cardImg; }

    public TextView getPlayerScoreView() { return playerScore; }

}
