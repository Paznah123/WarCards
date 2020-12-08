package com.example.warcards.fragments;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.warcards.R;
import com.example.warcards.objects.SharedPrefs;
import com.example.warcards.objects.Winner;

public class winner_fragment extends Fragment {

    private static final String TAG = "WinnerFragment";

    private View view;

    private String winnerMsg;

    private TextView winner_score;
    private ImageView winner_img;

    private TextView winner_LBL_msg;

    TypedArray profilePics;

    // ================================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_winner, container, false);

        profilePics = view.getResources().obtainTypedArray(R.array.playerImages);

        winnerMsg = getArguments().getString("winner");

        init_views();
        addWinner_toList();

        return view;
    }

    // ================================================================

    void init_views () {
        winner_img = view.findViewById(R.id.winner_img);
        winner_score = view.findViewById(R.id.winner_score);
        winner_LBL_msg = view.findViewById(R.id.winner_LBL_msg);
    }

    // ================================================================

    void addWinner_toList(){
        if(winnerMsg != "It's A Tie!") { // if winner exists
            Winner winner = getWinnerFromBundle();
            setWinner_toView(winner);
            SharedPrefs.getInstance().addWinner(winner);
        } else
            winner_LBL_msg.setText(winnerMsg);
    }

    Winner getWinnerFromBundle(){
        String name = getArguments().getString("name");
        int score = getArguments().getInt("score");
        int imgIndex = getArguments().getInt("imgIndex");

        return new Winner(name, score, imgIndex);
    }

    void setWinner_toView(Winner winner){
        winner_img.setImageResource(profilePics.getResourceId(winner.getImgIndex(),-1));
        winner_score.setText("" + winner.getScore());
        winner_LBL_msg.setText(winner.getName() + " Won!");
    }

}