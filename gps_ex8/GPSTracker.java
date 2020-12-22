package com.example.gps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import static android.content.Context.LOCATION_SERVICE;

public class GPSTracker implements LocationListener {

    Context context;

    public GPSTracker(Context context) {
        this.context = context;
    }

    public Location getLocation(){
        if (ContextCompat.checkSelfPermission( context, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            //PERMISSION_GRANTED IS 0; PERMISSION_DENIED is -1
            Toast.makeText(context,"Location Permission not granted",Toast.LENGTH_LONG).show();
            return null;
        }

        LocationManager lm = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //boolean isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(!isGPSEnabled){
            Toast.makeText(context,"GPS not enabled!!!\nPlease enable it",Toast.LENGTH_LONG).show();
            return null;
        }

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000,10,this);
        Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //Log.i("TIME","VAL: "+loc.getTime());
        return loc;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
