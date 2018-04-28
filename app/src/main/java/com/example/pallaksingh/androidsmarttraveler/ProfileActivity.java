package com.example.pallaksingh.androidsmarttraveler;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private CardView image_button_android_questionnaire;
    private CardView show_on_map;
    private CardView showMyRecs;
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    static public String latitude;
    static public String longitude;






    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        getLocation();


        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()==null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));

        }

//        Boolean newUser = getIntent().getBooleanExtra("First_time_login",false);
//        if (newUser==true){
//            finish();
//            startActivity(new Intent(this, LoginActivity.class));
//        }


        FirebaseUser user = firebaseAuth.getCurrentUser();
        buttonLogout = (Button) findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(this);
        image_button_android_questionnaire = (CardView) findViewById(R.id.image_button_android_questionnaire);
        image_button_android_questionnaire.setOnClickListener(this);
        show_on_map = (CardView) findViewById(R.id.show_on_map);
        show_on_map.setOnClickListener(this);
        showMyRecs = (CardView) findViewById(R.id.showMyRecs);
        showMyRecs.setOnClickListener(this);
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, new android.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    latitude = String.valueOf(location.getLatitude());
                    longitude = String.valueOf(location.getLongitude());
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
            });
                    //requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onClick(View view) {
        if(view == buttonLogout) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


//        if(view == image_button_android_questionnaire) {
//            finish();
//            startActivity(new Intent(this, CurrentLocationActivity.class));
//        }

        if(view == image_button_android_questionnaire) {
            finish();
            startActivity(new Intent(this, Questionnaire.class));
            //startActivity(new Intent(this, Questionnaire.class));
        }

        if(view == show_on_map) {
            finish();
            startActivity(new Intent(this, MapActivity.class));
        }

        if(view == showMyRecs) {
            finish();
            startActivity(new Intent(this, AndroidGooglePlaces.class));
        }







    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());
    }
}
