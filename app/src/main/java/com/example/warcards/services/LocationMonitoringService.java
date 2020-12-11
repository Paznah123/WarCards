package com.example.warcards.services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.warcards.App;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class LocationMonitoringService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    GoogleApiClient mLocationClient;
    LocationRequest mLocationRequest = new LocationRequest();

    // ================================================================

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(App.getAppContext(),"service started",Toast.LENGTH_SHORT).show();
        mLocationClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest.setInterval(30000);
        mLocationRequest.setFastestInterval(5000);

        int priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;

        mLocationRequest.setPriority(priority);
        mLocationClient.connect();

        return START_STICKY;
    }

    // ================================================================

    @Override
    public void onConnected(Bundle dataBundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, mLocationRequest, this);

    }

    //to get the location change
    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            App.updateLocation(location);
        }
    }

    // ================================================================

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnectionSuspended(int i) { }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) { }
}