package com.example.warcards;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.warcards.callBacks.IMainActivity;
import com.example.warcards.fragments.game_fragment;
import com.example.warcards.fragments.selector_fragment;
import com.example.warcards.fragments.topScores_fragment;
import com.example.warcards.fragments.winner_fragment;
import com.plattysoft.leonids.ParticleSystem;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    private ImageView main_backGround;

    private MediaPlayer mp;

    private ParticleSystem ps;

    // ================================================================

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (ps != null) // stops particles after win
            ps.stopEmitting();
    }

    // ================================================================

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(App.getAppContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                                    != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    44);
        }

        hideSystemUI();
        findViews();
        initFragment();

        Glide
            .with(this)
            .load(R.drawable.background)
            .into(main_backGround);
    }

    void findViews(){
        main_backGround = findViewById(R.id.main_backGround);
    }

    // ================================================================

    private void initFragment(){ // puts first view in main activity container
        selector_fragment fragment = new selector_fragment();
        doFragmentTransaction(fragment, getString(R.string.SelectorFragment), false);
    }

    // transfers between fragments
    private void doFragmentTransaction(Fragment fragment, String tag, boolean addToBackStack){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out
                )
                .replace(R.id.main_container, fragment, tag);

        if(addToBackStack)
            transaction.addToBackStack(tag);

        transaction.commit();
    }

    // creates new fragment for transaction
    @Override
    public void inflateFragment(String fragmentTag, boolean addToBackStack, Bundle bundle){
        Fragment fragment = new Fragment();

        if(fragmentTag.equals(getString(R.string.GameFragment)))
            fragment = new game_fragment();
        else if(fragmentTag.equals(getString(R.string.WinnerFragment))) {
            ps = emitParticles(Gravity.TOP, 200, 500,5);
            fragment = new winner_fragment();
        } else if(fragmentTag.equals(getString(R.string.TopScoresFragment)))
            fragment = new topScores_fragment();
        else if(fragmentTag.equals(getString(R.string.SelectorFragment)))
            fragment = new selector_fragment();

        doFragmentTransaction(fragment,fragmentTag,addToBackStack);
        fragment.setArguments(bundle);
    }

    // ================================================================

    @Override
    public void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY       // Set the content to appear under the system bars so that the
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE // content doesn't resize when the system bars hide and show.
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Hide the nav bar and status bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }

    @Override
    public void playSound(int rawSound) {
        mp = MediaPlayer.create(this,rawSound);
        mp.setOnCompletionListener(mp -> {
            mp.reset();
            mp.release();
            mp = null;
        });
        mp.start();
    }

    // ================================================================

    // always call after view is measured!!! (if not particles emit on pixel [0,0])
    ParticleSystem emitParticles (int gravity, int particlesPerSecond, int maxParticles, int timeInSec){
        ParticleSystem ps = new ParticleSystem(this, maxParticles, R.drawable.animated_confetti, timeInSec*1000);
        ps.setAcceleration(0.00113f, 90)
                .setSpeedByComponentsRange(0f, 0f, 0.05f, 0.1f)
                .setFadeOut(10, new AccelerateInterpolator())
                .emitWithGravity(findViewById(android.R.id.content).getRootView(), gravity, particlesPerSecond);
        return ps;
    }
}