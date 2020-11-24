package com.example.warcards;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.plattysoft.leonids.ParticleSystem;

public class WinnerFragment extends Fragment {

    private static final String TAG = "WinnerFragment";

    View view;

    ParticleSystem psLeft;
    ParticleSystem psRight;

    ImageView leftPlayerImg;
    ImageView rightPlayerImg;

    TextView winner_LBL_score;
    TextView winner_LBL_msg;

    Button restart;

    int leftScore = 0;
    int rightScore = 0;


    // ================================================================

    @Override
    public void onPause() {
        super.onPause();
        psRight.stopEmitting();
        psLeft.stopEmitting();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_winner, container, false);

        leftScore = getArguments().getInt("leftScore", 0);
        rightScore = getArguments().getInt("rightScore", 0);

        leftPlayerImg = setPlayerImg(getArguments(), R.id.winner_IMG_leftPlayer, "leftImgBitmap");
        rightPlayerImg = setPlayerImg(getArguments(), R.id.winner_IMG_rightPlayer, "rightImgBitmap");

        winner_LBL_score = view.findViewById(R.id.winner_LBL_finalScore);
        winner_LBL_score.setText("Left: " + leftScore + " | " + "Right: " + rightScore);

        winner_LBL_msg = view.findViewById(R.id.winner_LBL_msg);
        winner_LBL_msg.setText(getGameWinner(leftScore, rightScore));

        restart = view.findViewById(R.id.winner_BTN_restart);
        restart.setOnClickListener(v -> getFragmentManager().popBackStack());

        psRight = setParticleEmitter(R.id.emitter_top_right,180,180);
        psLeft = setParticleEmitter(R.id.emitter_top_left,0,0);

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
            winnerMsg = "Left Player won!";
        else if(leftScore < rightScore)
            winnerMsg = "Right Player won!";
        else {
            winnerMsg = "It's A Tie!";
        }
        return winnerMsg;
    }

    ParticleSystem setParticleEmitter(int emitterId, int minAngle, int maxAngle){
        ParticleSystem particleSystem = new ParticleSystem(this.getActivity(), 80, R.drawable.animated_confetti, 10000);
        particleSystem.setSpeedModuleAndAngleRange(0f, 0.3f, minAngle, maxAngle)
                .setRotationSpeed(144)
                .setAcceleration(0.000117f, 90)
                .emit(view.findViewById(emitterId), 15);
        return particleSystem;
    }
}