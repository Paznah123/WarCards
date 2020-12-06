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
import com.example.warcards.objects.Winner;
import com.example.warcards.objects.WinnersListAdapter;

import java.util.ArrayList;
import java.util.LinkedList;

public class list_fragment extends Fragment {

    private static final String TAG = "list_fragment";

    View view;

    static LinkedList<Winner> winnersList = new LinkedList<>();

    WinnersListAdapter winners_list_adapter;

    RecyclerView winners_list_layout;

    // ================================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_fragment, container, false);

        findViews();

        winners_list_layout.setLayoutManager(new LinearLayoutManager(this.getContext()));
        winners_list_adapter = new WinnersListAdapter(winnersList);
        winners_list_layout.setAdapter(winners_list_adapter);

        return view;
    }

    // ================================================================

    void findViews(){
        winners_list_layout = view.findViewById(R.id.topScores_recyclerView);
    }

    public LinkedList<Winner> getWinnersList() {
        return winnersList;
    }
}