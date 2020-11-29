package com.example.warcards;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

public class TopScoresFragment extends Fragment  {

    private static final String TAG = "TopScoresFragment";

    View view;

    LinkedList<Winner> winnersList = new LinkedList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_top_scores, container, false);

        return view;
    }
}