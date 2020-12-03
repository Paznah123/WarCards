package com.example.warcards.fragments;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.warcards.objects.Dealer;
import com.example.warcards.R;
import com.google.gson.Gson;
import com.plattysoft.leonids.ParticleSystem;

public class winner_fragment extends Fragment {

    private static final String TAG = "winner_fragment";

    View view;

    TextView winner_score;
    ImageView winner_img;

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

                    emitParticles(Gravity.TOP, 200, 500,5);
                }
            });
        }

        init_views();

        TypedArray images = view.getResources().obtainTypedArray(R.array.playerImages);
        winner_img.setImageResource(images.getResourceId(getArguments().getInt("img_id"),-1));
        winner_score.setText(""+ getArguments().getInt("score"));

        winner_LBL_msg.setText(getArguments().getString("winner"));

        restart = view.findViewById(R.id.winner_BTN_restart);
        restart.setOnClickListener(v -> getFragmentManager().popBackStack());

        return view;
    }

    // ================================================================

    void init_views () {
        winner_img = view.findViewById(R.id.winner_img);
        winner_score = view.findViewById(R.id.winner_score);
        winner_LBL_msg = view.findViewById(R.id.winner_LBL_msg);
    }

    void emitParticles (int gravity, int particlesPerSecond, int maxParticles, int timeInSec){
        new ParticleSystem(this.getActivity(), maxParticles, R.drawable.animated_confetti, timeInSec*1000)
                .setAcceleration(0.00113f, 90)
                .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                .setFadeOut(10, new AccelerateInterpolator())
                .emitWithGravity(view, gravity, particlesPerSecond);
    }

}