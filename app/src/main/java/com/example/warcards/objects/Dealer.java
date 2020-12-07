package com.example.warcards.objects;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.warcards.callBacks.IMainActivity;
import com.example.warcards.R;
import com.example.warcards.fragments.player_fragment;

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

    public Dealer(View view, int leftCard_view_id, int rightCard_view_id, int playBtn_view_id, int progBar_view_id) {
        this.view = view;

        iMainActivity = (IMainActivity) view.getContext();

        initCardStack(cardStack);

        this.left_cardImg = view.findViewById(leftCard_view_id);
        this.right_cardImg = view.findViewById(rightCard_view_id);

        this.playButton = view.findViewById(playBtn_view_id);
        this.progressBar = view.findViewById(progBar_view_id);
    }

    // ================================================================

    void initCardStack(ArrayList<Card> cardStack) {  // initializes the deck
        for (int i =  1 ; i <= 4 ; i++) { // gets type name from arrays.xml
            int typeId = view.getResources().obtainTypedArray(R.array.names).getResourceId(i, 0);
            String type = view.getResources().getResourceEntryName(typeId);
            for (int j = 2 ; j <= 14 ; j++) {
                cardStack.add(new Card(type, j));
            }
        }
    }

    // ================================================================

    public void dealCards_toPlayers(player_fragment leftPlayer, player_fragment rightPlayer){
        if(!cardStack.isEmpty()) {
            iMainActivity.playSound(R.raw.card_dealing);
            determine_roundWinner(leftPlayer,rightPlayer);
        } else {
            Bundle matchBundle = createWinnerBundle(leftPlayer,rightPlayer);
            iMainActivity.inflateFragment("winner_fragment", true, matchBundle);
            progressBar.setProgress(0);
        }
    }

    void determine_roundWinner(player_fragment leftPlayer, player_fragment rightPlayer) { // determines which player gets a point
        int leftCardVal = leftPlayer.getCard_fromDealer(this);
        int rightCardVal = rightPlayer.getCard_fromDealer(this);

        if (leftCardVal > rightCardVal)
            leftPlayer.incrementScore();
        else if (leftCardVal < rightCardVal)
            rightPlayer.incrementScore();

        progressBar.incrementProgressBy(1);
    }

    // ================================================================

    public void resetGameProgress(player_fragment leftPlayer, player_fragment rightPlayer) {
        initCardStack(cardStack);
        leftPlayer.resetGameScore();
        rightPlayer.resetGameScore();
        progressBar.setProgress(0);
    }

    // ================================================================

    Bundle createWinnerBundle(player_fragment leftPlayer, player_fragment rightPlayer) { // creates data for WinnerFragment
        Bundle bundle = new Bundle();

        if(leftPlayer.getGameScore() > rightPlayer.getGameScore())
            addData_toBundle(leftPlayer, bundle);
        else if(leftPlayer.getGameScore() < rightPlayer.getGameScore())
            addData_toBundle(rightPlayer, bundle);
        else
            bundle.putString("winner", "It's A Tie!");

        return bundle;
    }

    String addData_toBundle(player_fragment player, Bundle bundle){
        bundle.putString("name",player.getPlayerName());
        bundle.putInt("score",player.getGameScore());
        bundle.putByteArray("img_byteArr", player.getCurrImgByteArr());

        return player.getPlayerName();
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
