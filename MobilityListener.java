package bouzid.spyme.mosaic.bouzid;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MobilityListener implements LocationListener {

    public ArrayList<MobilityInfo> getMobilityInfos() {
        return mobilityInfos;
    }

    public MobilityInfo getLastMobilityInfo(){
        return mobilityInfos.get(mobilityInfos.size()-1);
    }

    private ArrayList<MobilityInfo> mobilityInfos;

    public MobilityListener() {
        this.mobilityInfos = new ArrayList<>();
    }

    @Override
    public void onLocationChanged(Location location) {

        String latitude     = location.getLatitude() + "";
        String longitude    = location.getLongitude() + "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        String date = sdf.format(d);

        sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(d);

        MobilityInfo mobilityInfo = new MobilityInfo(latitude,longitude,date,time);
        Log.i("MobilityListener", mobilityInfo.toString());
        MainActivity.LocationHere.add(mobilityInfo.toString());


        // conctat the server and store location info from the object mobilityInfo

        mobilityInfos.add(mobilityInfo);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i("MobilityListener", "status change");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i("MobilityListener", "Provider Enabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i("MobilityListener", "Provider Disabled");
    }







}
