package com.example.covidupdates;


import android.Manifest;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnPolygonClickListener;
import android.os.Parcelable;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;


public class MapsActivity extends AppCompatActivity
        implements
            OnMapReadyCallback,
            GoogleMap.OnPolygonClickListener {
    private GoogleMap mMap;
    private CameraPosition cameraPosition;
    private FusedLocationProviderClient fusedLocationClient;
    private final LatLng defaultLocation = new LatLng(-60, 100);
    private static final int DEFAULT_ZOOM = 12;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted = true;
    private Location lastKnownLocation;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    private String torranceTag = new String("Torrance -- Cases: 1329");
    private String carsonTag = new String("Carson -- Cases: 1726");
    private String inglewoodTag = new String("Inglewood -- Cases: 2696");

    /*
    private String[] torranceTag = new String [] {"Torrance","Cases: 1329", "Cases Per 100,000: 890", "Deaths: 68"};
    private String[] carsonTag = new String [] {"Carson","Cases: 1329", "Cases Per 100,000: 890", "Deaths: 68"};
    private String[] inglewoodTag = new String [] {"Inglewood","Cases: 1329", "Cases Per 100,000: 890", "Deaths: 68"};
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        setContentView(R.layout.activity_maps);

        // Construct a FusedLocationProviderClient.
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        this.mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        // Add a marker in Sydney and move the camera
        LatLng losang = new LatLng(34, 118);
        this.mMap.addMarker(new MarkerOptions().position(losang).title("Los Angeles"));
        this.mMap.moveCamera(CameraUpdateFactory.newLatLng(losang));

            Polygon torrance = googleMap.addPolygon(new PolygonOptions()
                    .clickable(true)
                    .add(
                            new LatLng (33.83,-118.38),
                            new LatLng( 33.86,-118.39),
                            new LatLng(33.87,-118.36),
                            new LatLng( 33.89,-118.35),
                            new LatLng( 33.89,-118.3),
                            new LatLng( 33.79,-118.3),
                            new LatLng( 33.79,-118.32),
                            new LatLng( 33.77,-118.32),
                            new LatLng(  33.79,-118.36)));
            torrance.setTag(torranceTag);
            stylePolygon(torrance);

        Polygon carson = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(33.86,-118.29),
                        new LatLng(33.9,-118.28),
                        new LatLng(33.9,-118.25),
                        new LatLng(33.86,-118.23),
                        new LatLng(33.86,-118.2),
                        new LatLng(33.78,-118.22),
                        new LatLng(33.79,-118.3),
                        new LatLng(33.86,-118.29)));
            carson.setTag(carsonTag);
            stylePolygon(carson);



        /*Polygon compton = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng(  33.88   ,  118.27  ),
                        new LatLng(  33.93   , 118.26   ),
                        new LatLng(  33.92   ,  118.18  ),
                        new LatLng(  33.9   ,   118.17 ),
                        new LatLng(  33.88  ,  118.17  ),
                        new LatLng(  33.85   ,  118.22  ),
                        new LatLng(  33.86   ,  118.26  ),
                        new LatLng(  33.88  ,  118.27  )));
        compton.setTag("Compton");
        stylePolygon(compton);
        */


        Polygon inglewood = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(
                        new LatLng( 33.96  , -118.39   ),
                        new LatLng(  33.97 , -118.38   ),
                        new LatLng(33.99   ,  -118.39  ),
                        new LatLng( 33.99  ,  -118.33  ),
                        new LatLng( 33.98 ,   -118.31 ),
                        new LatLng( 33.93  ,   -118.3 ),
                        new LatLng( 33.92  ,  -118.35  ),
                        new LatLng( 33.93  ,  -118.38  ),
                        new LatLng(33.96   ,   -118.39 )));
        inglewood.setTag(inglewoodTag);
        stylePolygon(inglewood);



        // Prompt the user for permission.
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();


        //Set polygon clicker
        googleMap.setOnPolygonClickListener(this);

    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                        } else {

                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;

                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (this.mMap == null) {
            return;
        }
        if (locationPermissionGranted) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            this.mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }

    }

    private static final int COLOR_WHITE_ARGB = 0xffffffff;
    private static final int COLOR_LIGHTESTBLUE_ARGB = 0x4087CEFA;
    private static final int COLOR_MEDIUMBLUE_ARGB = 0x404169E1;
    private static final int COLOR__DARKBLUE_ARGB = 0x4000008B;
    private static final int COLOR_DARKESTBLUE_ARGB = 0x40F9A825;


    private void stylePolygon(Polygon polygon) {
        String type = "";
        // Get the data object stored with the polygon.
        if (polygon.getTag() != null) {
            type = polygon.getTag().toString();
        }

        int fillColor = COLOR_WHITE_ARGB;

        if (polygon.getTag() == torranceTag)
                fillColor = COLOR_LIGHTESTBLUE_ARGB;

        else if (polygon.getTag() == carsonTag)
                fillColor = COLOR_MEDIUMBLUE_ARGB;
        else if (polygon.getTag() == inglewoodTag)
            fillColor = COLOR__DARKBLUE_ARGB;


        polygon.setFillColor(fillColor);
        polygon.setClickable(true);
    }

    public void onPolygonClick(Polygon polygon) {
        Toast.makeText(getApplicationContext(), polygon.getTag().toString(),
                Toast.LENGTH_LONG).show();

    }




}