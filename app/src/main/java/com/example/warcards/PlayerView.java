package com.example.warcards;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Random;

public class PlayerView {

    private View view;

    private Dealer.Side side;

    private ImageView img;
    private TextView score;
    private EditText name;

    private Random rand;
    private Card currCard;
    private int gameScore;

    private int playerImgArrIndex;
    private boolean gameRunning = false;

    //===========================================

    public PlayerView(){
    }

    public PlayerView(View view, Dealer.Side side, int imgId, int scoreId, int editTextId_orZero) {
        this.view = view;
        this.side = side;

        this.img = view.findViewById(imgId);
        this.score = view.findViewById(scoreId);

        if(editTextId_orZero != 0) { // 0 when these attributes not needed
            this.name = view.findViewById(editTextId_orZero);
            this.rand = new Random();
            this.currCard = new Card();
            this.gameScore = 0;
            // gets a random number in the range of playerImages indexes
            int profilePic_arrSize = view.getResources().obtainTypedArray(R.array.playerImages).length() - 1;
            this.playerImgArrIndex = rand.nextInt(profilePic_arrSize);
            setImgChangeListener();
        }
    }

    //===========================================

    int getCard_fromDealer(Dealer dealer) {
        ArrayList<Card> cardStack = dealer.getCardStack();
        Card randCard = cardStack.get(rand.nextInt(cardStack.size()));
        cardStack.remove(randCard);

        // sets new card in imageView
        int img_id = view.getResources().getIdentifier("card_"+ randCard.getImageName(), "drawable", view.getContext().getPackageName());
        dealer.getCardView_bySide(side).setImageResource(img_id);
        currCard = randCard;

        return currCard.getValue();
    }

    //===========================================

    // change player img listener
    private void setImgChangeListener(){
        img.setOnClickListener(v -> {
            if (!gameRunning)
                changePlayerImg();
        });
    }

    void changePlayerImg(){
        TypedArray images = view.getResources().obtainTypedArray(R.array.playerImages);
        img.setImageResource(images.getResourceId(playerImgArrIndex,-1));

        if(playerImgArrIndex < images.length()-1)
            playerImgArrIndex++;
        else
            playerImgArrIndex = 0;
    }

    void setPlayerImg(Bundle bundle){
        byte[] imgByteArr = bundle.getByteArray(getSide()+"_imgBitmap");
        Bitmap imgBitmap = BitmapFactory.decodeByteArray(imgByteArr,0,imgByteArr.length);
        img.setImageBitmap(imgBitmap);
    }

    //===========================================

    byte[] getCurrImgBitmap(){
        img.setDrawingCacheEnabled(true);
        Bitmap playerImgBitmap = img.getDrawingCache();

        ByteArrayOutputStream playerImgByteArr = new ByteArrayOutputStream();
        playerImgBitmap.compress(Bitmap.CompressFormat.PNG, 50, playerImgByteArr);

        return playerImgByteArr.toByteArray();
    }

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

    public boolean isGameRunning() {
        return gameRunning;
    }

    //===========================================

    public void incrementScore() { score.setText(""+ ++gameScore); }

    public void resetGameScore() { this.gameScore = 0; }

    //===========================================

    public int getGameScore() { return gameScore; }

    public Dealer.Side getSide() { return side; }

    public TextView getPlayerScoreView() { return score; }

    public String getPlayerName() { return name.getText().toString(); }
}
