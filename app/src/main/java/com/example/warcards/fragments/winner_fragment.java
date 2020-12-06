package com.example.warcards.fragments;

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
import com.example.warcards.IMainActivity;
import com.example.warcards.R;
import com.example.warcards.objects.Winner;
import com.plattysoft.leonids.ParticleSystem;

public class winner_fragment extends Fragment {

    private static final String TAG = "WinnerFragment";

    View view;

    TextView winner_score;
    ImageView winner_img;

    TextView winner_LBL_msg;

    public static ParticleSystem ps;

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

                    ps = emitParticles(Gravity.TOP, 200, 500,5);
                }
            });
        }

        init_views();

        // gets winner img bitmap from bundle
        byte[] byteArray = getArguments().getByteArray("img_byteArr");
        Bitmap winner_imgBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        Winner winner = new Winner(getArguments().getString("name"), getArguments().getInt("score"),winner_imgBitmap);

        setWinner_toView(winner);
        list_fragment.winnersList.add(winner);

        restart = view.findViewById(R.id.winner_BTN_restart);
        restart.setOnClickListener(v -> {
            getFragmentManager().popBackStack();
            ps.stopEmitting();
        });

        return view;
    }

    // ================================================================

    void init_views () {
        winner_img = view.findViewById(R.id.winner_img);
        winner_score = view.findViewById(R.id.winner_score);
        winner_LBL_msg = view.findViewById(R.id.winner_LBL_msg);
    }

    void setWinner_toView(Winner winner){
        winner_img.setImageBitmap(winner.getImgBitmap());
        winner_score.setText(""+ winner.getScore());
        winner_LBL_msg.setText(winner.getName());
    }

    ParticleSystem emitParticles (int gravity, int particlesPerSecond, int maxParticles, int timeInSec){
        ParticleSystem ps = new ParticleSystem(this.getActivity(), maxParticles, R.drawable.animated_confetti, timeInSec*1000);
        ps.setAcceleration(0.00113f, 90)
                .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                .setFadeOut(10, new AccelerateInterpolator())
                .emitWithGravity(view, gravity, particlesPerSecond);
        return ps;
    }

}