package com.example.warcards.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.warcards.R;
import com.example.warcards.callBacks.MapCallBack;
import com.example.warcards.objects.Winner;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class map_fragment extends Fragment  {

    private View view;

    private SupportMapFragment supportMapFragment;

    private static GoogleMap map;

    // ================================================================

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map_fragment, container, false);

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(googleMap -> {
            map = googleMap;
            map.setMyLocationEnabled(true);
    });

        return view;
    }

    // ================================================================

    public static MapCallBack getMapCallBack() { return mapCallBack; }

    private static MapCallBack mapCallBack = new MapCallBack() {
        @Override
        public void displayLocationOnMap(Winner winner){
            LatLng latLng = latLngCreator(winner);
            MarkerOptions markerOptions= new MarkerOptions();
            markerOptions.position(latLng);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
            map.addMarker(markerOptions);
        }

        @Override
        public LatLng latLngCreator(Winner winner){
            return new LatLng(winner.getLocation().getLatitude(),winner.getLocation().getLongitude());
        }
    };
}
