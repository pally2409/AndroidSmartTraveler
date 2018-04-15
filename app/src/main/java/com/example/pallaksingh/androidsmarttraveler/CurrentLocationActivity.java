package com.example.pallaksingh.androidsmarttraveler;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;


public class CurrentLocationActivity extends AppCompatActivity {
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;

    private EditText LocationLat;
    private EditText LocationLong;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);



        LocationLat = (EditText) findViewById(R.id.LocationLat);
        LocationLong = (EditText) findViewById(R.id.LocationLong);
        LocationLat.setText("waiting");
        LocationLong.setText("waiting");

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        getLocation();

    }

    void getLocation() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null){
                double latti = location.getLatitude();
                double longi = location.getLongitude();

                LocationLat.setText("Latitude: " + latti);
                LocationLong.setText("Longitude: " + longi);
            } else {
                LocationLat.setText("Unable to find correct location.");
                LocationLong.setText("Unable to find correct location. ");
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;
        }


   }
}