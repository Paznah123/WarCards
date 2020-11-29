package com.example.warcards;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import com.plattysoft.leonids.ParticleSystem;

public class WinnerFragment extends Fragment {

    private static final String TAG = "WinnerFragment";

    View view;

    PlayerView leftPlayer;
    PlayerView rightPlayer;

    TextView winner_LBL_msg;

    Button restart;

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

        leftPlayer = new PlayerView(view, Dealer.Side.LEFT, R.id.winner_IMG_leftPlayer, R.id.winner_LBL_leftScore,0);
        rightPlayer = new PlayerView(view, Dealer.Side.RIGHT, R.id.winner_IMG_rightPlayer, R.id.winner_LBL_rightScore,0);

        setPlayerDataInFragment(leftPlayer);
        setPlayerDataInFragment(rightPlayer);

        winner_LBL_msg = view.findViewById(R.id.winner_LBL_msg);
        winner_LBL_msg.setText(getArguments().getString("winner"));

        restart = view.findViewById(R.id.winner_BTN_restart);
        restart.setOnClickListener(v ->  getFragmentManager().popBackStack() );

        return view;
    }

    // ================================================================

    ParticleSystem emitParticles(int gravity, int particlesPerSecond, int maxParticles){
        ParticleSystem ps = new ParticleSystem(this.getActivity(), maxParticles, R.drawable.animated_confetti, 5000);
                ps.setSpeedRange(0.2f, 0.35f)
                .setRotationSpeedRange(90, 180)
                .setInitialRotationRange(0, 180)
                .emitWithGravity(view, gravity,particlesPerSecond,5000);
        return ps;
    }

    void setPlayerDataInFragment(PlayerView playerView){
        int score = getArguments().getInt(playerView.getSide()+"_score", 0);
        String name = getArguments().getString(playerView.getSide()+"_name");

        playerView.setPlayerImg(getArguments());
        playerView.getPlayerScoreView().setText(name +" - "+ score);
    }

}