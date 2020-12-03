package com.example.warcards;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.warcards.fragments.game_fragment;
import com.example.warcards.fragments.player_fragment;
import com.example.warcards.fragments.selector_fragment;
import com.example.warcards.fragments.settings_fragment;
import com.example.warcards.fragments.topScores_fragment;
import com.example.warcards.fragments.winner_fragment;
import com.example.warcards.objects.Dealer;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    private static final String TAG = "MainActivity";

    ImageView main_backGround;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideSystemUI();
        initFragment();

        main_backGround = findViewById(R.id.main_backGround);
        Glide
            .with(this)
            .load(R.drawable.background)
            .into(main_backGround);
    }

    // ================================================================

    private void initFragment(){
        selector_fragment fragment = new selector_fragment();
        doFragmentTransaction(fragment, getString(R.string.SelectorFragment), false);
    }

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
    public void inflateFragment(String fragmentTag, boolean addToBackStack, Bundle bundle){
        Fragment fragment = new Fragment();
        if(fragmentTag.equals(getString(R.string.GameFragment)))
            fragment = new game_fragment();
        else if(fragmentTag.equals(getString(R.string.WinnerFragment)))
            fragment = new winner_fragment();
        else if(fragmentTag.equals(getString(R.string.TopScoresFragment)))
            fragment = new topScores_fragment();
        else if(fragmentTag.equals(getString(R.string.SettingsFragment)))
            fragment = new settings_fragment();
        else if(fragmentTag.equals(getString(R.string.SelectorFragment)))
            fragment = new selector_fragment();

        doFragmentTransaction(fragment,fragmentTag,addToBackStack);
        fragment.setArguments(bundle);
    }

    // ================================================================

    MediaPlayer mp;

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

    @Override
    public void stopSound() {
    }

}