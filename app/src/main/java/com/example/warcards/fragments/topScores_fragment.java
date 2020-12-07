package com.example.warcards.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.warcards.R;
import com.example.warcards.callBacks.mapCallBack;
import com.example.warcards.objects.Winner;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class topScores_fragment extends Fragment {

    private static final String TAG = "TopScoresFragment";

    private View view;

    private map_fragment map = new map_fragment();
    private list_fragment list = new list_fragment();

    // ================================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_top_scores, container, false);

        putFragmentInView(R.id.map_right, map);
        putFragmentInView(R.id.list_left, list);

        return view;
    }

    // ================================================================

    public void putFragmentInView(int layout_id, Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(layout_id, fragment).commit();
    }

}