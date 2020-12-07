package com.example.warcards.callBacks;

import com.example.warcards.objects.Winner;
import com.google.android.gms.maps.GoogleMap;

public interface mapCallBack {

    void displayLocationOnMap(GoogleMap map, Winner winner);
}
