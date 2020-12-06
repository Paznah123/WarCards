package com.example.warcards.objects;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.Image;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warcards.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedList;

public class WinnersListAdapter extends RecyclerView.Adapter<WinnersListAdapter.WinnersListViewHolder> {

    String date;

    private LinkedList<Winner> winnersList;

    public WinnersListAdapter(LinkedList<Winner> winnersList){
        this.winnersList = winnersList;
        refreshDate();
    }

    @NonNull
    @Override
    public WinnersListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.winners_list_item, parent, false);
        return new WinnersListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WinnersListViewHolder holder, int position) {
        Winner winner = winnersList.get(position);
        holder.winner_position.setText("" + (position+1));
        holder.winner_img.setImageBitmap(winner.getImgBitmap());
        holder.winner_name.setText("" + winner.getName());
        holder.winner_score.setText("" + winner.getScore());
        holder.winner_date.setText("" + date);
    }

    @Override
    public int getItemCount() {
        return winnersList.size();
    }

    public class WinnersListViewHolder extends RecyclerView.ViewHolder {
        TextView winner_position;
        ImageView winner_img;
        TextView winner_name;
        TextView winner_score;
        TextView winner_date;

        public WinnersListViewHolder(View itemView){
            super(itemView);
            winner_position = itemView.findViewById(R.id.winnerList_item_position);
            winner_img = itemView.findViewById(R.id.winnerList_item_img);
            winner_name = itemView.findViewById(R.id.winnerList_item_name);
            winner_score = itemView.findViewById(R.id.winnerList_item_score);
            winner_date = itemView.findViewById(R.id.winnerList_item_date);
        }
    }

    void refreshDate(){
        date = DateFormat.format(" dd.MM.yy - HH:mm ", System.currentTimeMillis()).toString();
    }
}
