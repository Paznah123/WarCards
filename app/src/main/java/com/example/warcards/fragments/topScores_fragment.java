package com.example.warcards.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.warcards.R;
import com.example.warcards.objects.Winner;

import java.util.LinkedList;

public class topScores_fragment extends Fragment  {

    private static final String TAG = "topScores_Fragment";

    View view;

    LinkedList<Winner> winnersList = new LinkedList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_top_scores, container, false);

        return view;
    }
}