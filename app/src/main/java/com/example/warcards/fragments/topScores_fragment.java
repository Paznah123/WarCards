package com.example.warcards.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.warcards.R;

public class topScores_fragment extends Fragment  {

    private static final String TAG = "TopScoresFragment";

    View view;

    map_fragment map = new map_fragment();
    list_fragment winners_list = new list_fragment();

    // ================================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_top_scores, container, false);

        putFragmentInView(R.id.map_right, map);
        putFragmentInView(R.id.list_left, winners_list);

        return view;
    }

    // ================================================================

    public void putFragmentInView(int layout_id, Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(layout_id, fragment).commit();
    }

    public list_fragment getWinners_list() { return winners_list; }
}