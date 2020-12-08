package com.example.warcards.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.warcards.R;
import com.example.warcards.objects.SharedPrefs;
import com.example.warcards.objects.Winner;
import com.example.warcards.objects.WinnersListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;

public class list_fragment extends Fragment { // add winner list adding logic by score

    private static final String TAG = "list_fragment";

    private View view;

    private WinnersListAdapter winners_list_adapter;

    private RecyclerView winners_list_layout;

    // ================================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_fragment, container, false);

        findViews();

        winners_list_layout.setLayoutManager(new LinearLayoutManager(this.getContext()));
        winners_list_adapter = new WinnersListAdapter(SharedPrefs.getWinnersList());
        winners_list_layout.setAdapter(winners_list_adapter);

        return view;
    }

    // ================================================================

    void findViews(){
        winners_list_layout = view.findViewById(R.id.topScores_recyclerView);
    }

}