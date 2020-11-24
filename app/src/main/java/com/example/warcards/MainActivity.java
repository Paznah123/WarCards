package com.example.warcards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.plattysoft.leonids.ParticleSystem;

public class MainActivity extends AppCompatActivity implements IMainActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initFragment();
    }

    // ================================================================

    private void initFragment(){
        SelectorFragment fragment = new SelectorFragment();
        doFragmentTransaction(fragment, getString(R.string.SelectorFragment), false);
    }

    private void doFragmentTransaction(Fragment fragment, String tag, boolean addToBackStack){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.main_container, fragment, tag);

        if(addToBackStack)
            transaction.addToBackStack(tag);

        transaction.commit();
    }

    // ================================================================

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

}