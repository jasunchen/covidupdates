package com.example.covidupdates;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.LinkedHashMap;
import java.util.Map;

public class LocationService extends Service {

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    private static final long INTERVAL = 5000;
    private static final long MILLISECOND_IN_TWO_WEEKS = 1000*60*60*24*14;
    private static final long NUM_TIMES_IN_PAST_TWO_WEEKS = MILLISECOND_IN_TWO_WEEKS / INTERVAL;

    private LinkedHashMap<Long, Location> timesOfPastTwoWeeks = new LinkedHashMap<Long, Location>() {
        protected boolean removeEldestEntry(Map.Entry<Long, Location> eldest) {
            return size() > (int) NUM_TIMES_IN_PAST_TWO_WEEKS;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Log.d("logLocation", "Lat: "+locationResult.getLastLocation().getLatitude()+", Long: "+locationResult.getLastLocation().getLongitude());
                timesOfPastTwoWeeks.put(locationResult.getLastLocation().getTime(), locationResult.getLastLocation());
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        requestLocation();
        return super.onStartCommand(intent, flags, startId);
    }

    private void requestLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }
}
