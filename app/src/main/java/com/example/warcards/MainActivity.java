package com.example.warcards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideSystemUI();
        initFragment();
    }

    // ================================================================

    private void initFragment(){
        SelectorFragment fragment = new SelectorFragment();
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
            fragment = new GameFragment();
        else if(fragmentTag.equals(getString(R.string.WinnerFragment)))
            fragment = new WinnerFragment();
        else if(fragmentTag.equals(getString(R.string.TopScoresFragment)))
            fragment = new TopScoresFragment();
        else if(fragmentTag.equals(getString(R.string.SettingsFragment)))
            fragment = new SettingsFragment();
        else if(fragmentTag.equals(getString(R.string.SelectorFragment)))
            fragment = new SelectorFragment();

        doFragmentTransaction(fragment,fragmentTag,addToBackStack);
        fragment.setArguments(bundle);
    }

    // ================================================================

    MediaPlayer mp;

    @Override
    public void playSound(int rawSound) {
        mp = MediaPlayer.create(this,rawSound);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.release();
                mp = null;
            }
        });
        mp.start();
    }

    @Override
    public void stopSound() {
    }

}