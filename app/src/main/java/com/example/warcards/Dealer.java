package com.example.warcards;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class Dealer {

    private View view;

    IMainActivity iMainActivity;

    private ArrayList<Card> cardStack = new ArrayList<>();

    private ImageView left_cardImg;
    private ImageView right_cardImg;

    private ImageView playButton;

    private ProgressBar progressBar;

    public enum Side { LEFT , RIGHT };

    // ================================================================

    public Dealer(){
    }

    public Dealer(View view) {
        this.view = view;

        iMainActivity = (IMainActivity) view.getContext();

        this.cardStack = initCardStack();

        this.left_cardImg = view.findViewById(R.id.main_IMG_leftCard);
        this.right_cardImg = view.findViewById(R.id.main_IMG_rightCard);

        this.playButton = view.findViewById(R.id.main_BTN_play);
        this.progressBar = view.findViewById(R.id.main_progressBar);
    }

    // ================================================================

    private ArrayList<Card> initCardStack() {  // initializes the deck
        for (int i =  1 ; i <= 4 ; i++) { // gets type name from arrays.xml
            int typeId = view.getResources().obtainTypedArray(R.array.names).getResourceId(i, 0);
            String type = view.getResources().getResourceEntryName(typeId);
            for (int j = 2 ; j <= 14 ; j++) {
                cardStack.add(new Card(type, j));
            }
        }
        return cardStack;
    }

    // ================================================================

    void dealCards_toPlayers(PlayerView leftPlayer, PlayerView rightPlayer){
        if(!cardStack.isEmpty()) {
            iMainActivity.playSound(R.raw.card_dealing);
            determine_roundWinner(leftPlayer,rightPlayer);
        } else {
            Bundle matchBundle = createWinnerBundle(leftPlayer,rightPlayer);
            iMainActivity.inflateFragment("WinnerFragment", true, matchBundle);
            progressBar.setProgress(0);
        }
    }

    private void determine_roundWinner(PlayerView leftPlayer, PlayerView rightPlayer) { // determines which player gets a point
        int leftCardVal = leftPlayer.getCard_fromDealer(this);
        int rightCardVal = rightPlayer.getCard_fromDealer(this);

        if (leftCardVal > rightCardVal)
            leftPlayer.incrementScore();
        else if (leftCardVal < rightCardVal)
            rightPlayer.incrementScore();

        progressBar.incrementProgressBy(1);
    }

    // ================================================================

    void resetGameProgress(PlayerView leftPlayer, PlayerView rightPlayer) {
        initCardStack();
        leftPlayer.resetGameScore();
        rightPlayer.resetGameScore();
        progressBar.setProgress(0);
    }

    // ================================================================

    private Bundle createWinnerBundle(PlayerView leftPlayer, PlayerView rightPlayer) { // creates data for WinnerFragment
        String winner;
        Bundle bundle = new Bundle();

        add_playerData_toBundle(bundle, leftPlayer);
        add_playerData_toBundle(bundle, rightPlayer);

        if(leftPlayer.getGameScore() > rightPlayer.getGameScore())
            winner = leftPlayer.getPlayerName() + " Won!";
        else if(leftPlayer.getGameScore() < rightPlayer.getGameScore())
            winner = rightPlayer.getPlayerName() + " Won!";
        else
            winner = "It's A Tie!";

        bundle.putString("winner", winner);

        return bundle;
    }

    private void add_playerData_toBundle(Bundle bundle, PlayerView playerView){
        Dealer.Side side = playerView.getSide();

        bundle.putInt(side +"_score", playerView.getGameScore());
        bundle.putString(side +"_name", playerView.getPlayerName());
        bundle.putByteArray(side +"_imgBitmap", playerView.getCurrImgBitmap());
    }

    // ================================================================

    public ImageView getCardView_bySide(Side side) {
        if(side.equals(Side.LEFT))
            return left_cardImg;
        if(side.equals(Side.RIGHT))
            return right_cardImg;
        return null;
    }

    public ArrayList<Card> getCardStack() { return cardStack; }

    public ImageView getPlayButton() { return playButton; }


}
