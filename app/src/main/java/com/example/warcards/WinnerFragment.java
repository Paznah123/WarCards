package com.example.warcards;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.plattysoft.leonids.ParticleSystem;
import com.plattysoft.leonids.modifiers.AlphaModifier;
import com.plattysoft.leonids.modifiers.ScaleModifier;

public class WinnerFragment extends Fragment {

    private static final String TAG = "WinnerFragment";

    View view;

    IMainActivity iMainActivity;

    ImageView leftPlayerImg;
    ImageView rightPlayerImg;

    String leftName;
    String rightName;

    TextView winner_LBL_leftScore;
    TextView winner_LBL_rightScore;
    TextView winner_LBL_msg;

    Button restart;

    int leftScore = 0;
    int rightScore = 0;


    // ================================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_winner, container, false);
        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();

        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    emitParticles(Gravity.TOP,200,500);
                }
            });
        }

        setPlayerDataInFragment("left",R.id.winner_IMG_leftPlayer,R.id.winner_LBL_leftScore);
        setPlayerDataInFragment("right",R.id.winner_IMG_rightPlayer,R.id.winner_LBL_rightScore);

        winner_LBL_msg = view.findViewById(R.id.winner_LBL_msg);
        winner_LBL_msg.setText(getGameWinner(leftScore, rightScore));

        restart = view.findViewById(R.id.winner_BTN_restart);
        restart.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
        });

        return view;
    }

    // ================================================================

    ImageView setPlayerImg(Bundle bundle, int viewId, String bundleKey){
        ImageView imgView = view.findViewById(viewId);

        byte[] imgByteArr = bundle.getByteArray(bundleKey);
        Bitmap imgBitmap = BitmapFactory.decodeByteArray(imgByteArr,0,imgByteArr.length);
        imgView.setImageBitmap(imgBitmap);

        return imgView;
    }

    String getGameWinner(int leftScore, int rightScore){
        String winnerMsg;

        if(leftScore > rightScore)
            winnerMsg = leftName +" won!";
        else if(leftScore < rightScore)
            winnerMsg = rightName +" won!";
        else {
            winnerMsg = "It's A Tie!";
        }
        return winnerMsg;
    }

    ParticleSystem emitParticles(int gravity, int particlesPerSecond, int maxParticles){
        ParticleSystem ps = new ParticleSystem(this.getActivity(), maxParticles, R.drawable.animated_confetti, 5000);
                ps.setSpeedRange(0.2f, 0.35f)
                .setRotationSpeedRange(90, 180)
                .setInitialRotationRange(0, 180)
                .emitWithGravity(view, gravity,particlesPerSecond,5000);
        return ps;
    }

    void setPlayerDataInFragment(String side, int playerImgID, int playerScoreLBL_ID){

        int score = getArguments().getInt(side+"Score", 0);
        ImageView imgView = setPlayerImg(getArguments(), playerImgID, side+"ImgBitmap");
        String name = getArguments().getString(side+"Name");
        TextView score_LBL = view.findViewById(playerScoreLBL_ID);
        score_LBL.setText(name +" - "+ score);

        if(side.equals("left")) {
            leftName = name;
            leftPlayerImg = imgView;
            leftScore = score;
            winner_LBL_leftScore = score_LBL;
        }
        if(side.equals("right")) {
            rightName = name;
            rightPlayerImg = imgView;
            rightScore = score;
            winner_LBL_rightScore = score_LBL;
        }
    }
}