package com.example.warcards.objects;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.warcards.R;
import com.example.warcards.callBacks.mapCallBack;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.LinkedList;

public class WinnersListAdapter extends RecyclerView.Adapter<WinnersListAdapter.WinnersListViewHolder> {

    View view;

    private String date;

    private LinkedList<Winner> winnersList;

    TypedArray profilePics;

    // ================================================================

    public WinnersListAdapter(LinkedList<Winner> winnersList){
        this.winnersList = winnersList;
        refreshDate();
    }

    @NonNull
    @Override
    public WinnersListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.winners_list_item, parent, false);
        profilePics = view.getResources().obtainTypedArray(R.array.playerImages);

        return new WinnersListViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull WinnersListViewHolder holder, int position) {
        Winner winner = winnersList.get(position);
        holder.winner_position.setText("" + (position+1));
        holder.winner_img.setImageResource(profilePics.getResourceId(winner.getImgIndex(),-1));
        holder.winner_name.setText("Name - " + winner.getName());
        holder.winner_score.setText("Score - " + winner.getScore());
        holder.winner_date.setText(date);
        holder.itemView.setOnClickListener(v -> {
            // add here map location ping
            Toast.makeText(view.getContext(),"Winner Clicked!",Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return winnersList.size();
    }

    // ================================================================
    // viewHolder for each item in list

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

    // ================================================================

    void refreshDate(){
        date = DateFormat.format(" dd.MM.yy - HH:mm ", System.currentTimeMillis()).toString();
    }
}
