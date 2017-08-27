package com.example.work.weather.utills;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

public class LocListener implements LocationListener {
    public static Location imHere;

    public static void setUpLocationListener(Context context)
    {
        LocationManager locationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocListener();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           return;
        }
        if(locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER)){
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                1000*3600,
                1000*101,
                locationListener);

        imHere = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);}else {
        if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER))
        {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                   1000*3600,
                   1000*101,
                   locationListener);

            imHere = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        }
    }

    @Override
    public void onLocationChanged(Location loc) {
        imHere = loc;
    }
    @Override
    public void onProviderDisabled(String provider) {}
    @Override
    public void onProviderEnabled(String provider) {}
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}
