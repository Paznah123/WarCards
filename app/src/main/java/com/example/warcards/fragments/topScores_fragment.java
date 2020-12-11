package com.example.warcards.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.warcards.R;

public class topScores_fragment extends Fragment {

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