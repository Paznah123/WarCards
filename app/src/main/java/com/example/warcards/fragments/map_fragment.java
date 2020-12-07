package com.example.warcards.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.warcards.R;
import com.example.warcards.callBacks.mapCallBack;
import com.example.warcards.objects.Winner;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class map_fragment extends Fragment {

    private static final String TAG = "MapFragment";

    private View view;

    SupportMapFragment supportMapFragment;

    GoogleMap map;

    // ================================================================

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.map_fragment, container, false);

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(googleMap -> googleMap.setOnMapClickListener(latLng -> {
            map = googleMap;
            MarkerOptions markerOptions=new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(latLng.latitude+":"+latLng.longitude);
            map.clear();
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
            map.addMarker(markerOptions);
        }));

        return view;
    }

    public GoogleMap getMap() { return map; }
}
