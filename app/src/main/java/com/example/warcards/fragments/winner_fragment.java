package com.example.warcards.fragments;

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
import com.example.warcards.objects.Winner;

public class winner_fragment extends Fragment {

    private static final String TAG = "WinnerFragment";

    private View view;

    private String winnerMsg;

    private TextView winner_score;
    private ImageView winner_img;

    private TextView winner_LBL_msg;

    // ================================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_winner, container, false);

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
        if(winnerMsg != "It's A Tie!") { // if a winner exists
            Winner winner = getWinnerFromBundle();
            setWinner_toView(winner);
            list_fragment.winnersList.add(winner);
        } else
            winner_LBL_msg.setText(winnerMsg);
    }

    Winner getWinnerFromBundle(){
        // gets winner img bitmap from bundle
        byte[] byteArray = getArguments().getByteArray("img_byteArr");
        Bitmap winner_imgBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        return new Winner(getArguments().getString("name"), getArguments().getInt("score"), winner_imgBitmap);
    }

    void setWinner_toView(Winner winner){
        winner_img.setImageBitmap(winner.getImgBitmap());
        winner_score.setText("" + winner.getScore());
        winner_LBL_msg.setText(winner.getName() + " Won!");
    }

}