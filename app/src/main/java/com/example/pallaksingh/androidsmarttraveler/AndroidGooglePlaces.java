package com.example.pallaksingh.androidsmarttraveler;

import android.Manifest;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AndroidGooglePlaces extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView places_recommendations;
    private RecyclerView.Adapter adapter;
    //private List<Places> ;
    ArrayList<Places> placesList;

    public boolean amusement_park;
    public boolean library;
    public boolean art_gallery;
    public boolean museum;
    public boolean movie_theatre;
    public boolean shopping_mall;
    public boolean zoo;
    public boolean aquarium;
    public static long totalTime;
    public String last = "";
    public Boolean lastQuery = false;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    public int howMuchToParse;
    public int flag = 0;
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    public String latitude;
    public String longitude;

    public static ArrayList<Places> temp = new ArrayList<Places>();
    public static Boolean farOrNear = false;
    public ArrayList<Long> time = new ArrayList<Long>();
    private Button TimeFilterbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_google_places);

        places_recommendations = (RecyclerView) findViewById(R.id.places_recommendations);
        TimeFilterbutton = (Button) findViewById(R.id.TimeFilterbutton);
        TimeFilterbutton.setOnClickListener(this);
        places_recommendations.setHasFixedSize(true);
        places_recommendations.setLayoutManager(new LinearLayoutManager(this));







        if(aquarium) {
            last = "aquarium";
        } else if(zoo){
            last = "zoo";
        } else if(shopping_mall) {
            last = "shopping_mall";
        } else if(movie_theatre) {
            last = "movie_theatre";
        } else if(museum) {
            last = "museum";
        } else if(art_gallery) {
            last = "art_gallery";
        } else if(library) {
            last = "library";
        } else if(amusement_park) {
            last = "amusement_park";
        }



        String toPlace = "";
        String fromPlace = "Shree+Hospital,Dwarka";

        if (lastQuery) {

            for (int i = 0; i < temp.size(); i++) {
                toPlace = toPlace + "|" +  temp.get(i).getName() + temp.get(i).getShortDesc();
                toPlace = toPlace.replaceAll(" ", "%20");
            }


            //GoogleMaps googleMaps = new GoogleMaps(toPlace, fromPlace, new Callback());
            //googleMaps.execute();

//            if (farOrNear == true) {
//                finalPlaces.add(temp.get(i));
//            }
//
//            PlacesAdapter adapter = new PlacesAdapter(AndroidGooglePlaces.this, finalPlaces);
//            places_recommendations.setAdapter(adapter);


        }



//        for (int i = 0; i < temp.size(); i++) {
//            String toPlace = temp.get(i).getName() + temp.get(i).getShortDesc();
//            toPlace = toPlace.replaceAll(" ", "%20");
//            String fromPlace = "Shree+Hospital,Dwarka";
//
//            GoogleMaps googleMaps = new GoogleMaps(toPlace, fromPlace, new Callback());
//            googleMaps.execute();
//
//            if (farOrNear == true) {
//                finalPlaces.add(temp.get(i));
//            }
//
//            PlacesAdapter adapter = new PlacesAdapter(AndroidGooglePlaces.this, finalPlaces);
//            places_recommendations.setAdapter(adapter);
//
//        }




    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = user.getUid();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()) {

                    String ds = data.getKey().toString();
                    if (ds.equals(uid)) {

                        String temp;
                        temp = data.child("amusement_park").getValue().toString();
                        if(temp == "true") {
                            amusement_park = true;
                            flag = flag + 1;
                        } else {
                            amusement_park = false;
                        }

                        temp = data.child("library").getValue().toString();
                        if(temp == "true") {
                            library = true;
                            flag = flag + 1;
                        } else {
                            library = false;
                        }

                        temp = data.child("art_gallery").getValue().toString();
                        if(temp == "true") {
                            art_gallery = true;
                            flag = flag + 1;
                        } else {
                            art_gallery = false;
                        }

                        temp = data.child("museum").getValue().toString();
                        if(temp == "true") {
                            museum = true;
                            flag = flag + 1;
                        } else {
                            museum = false;
                        }

                        temp = data.child("movie_theatre").getValue().toString();
                        if(temp == "true") {
                            movie_theatre = true;
                            flag = flag + 1;
                        } else {
                            movie_theatre = false;
                        }


                        temp = data.child("shopping_mall").getValue().toString();
                        if(temp == "true") {
                            shopping_mall = true;
                            flag = flag + 1;
                        } else {
                            shopping_mall = false;
                        }

                        temp = data.child("zoo").getValue().toString();
                        if(temp == "true") {
                            zoo = true;
                            flag = flag + 1;
                        } else {
                            zoo = false;
                        }

                        temp = data.child("aquarium").getValue().toString();
                        if(temp == "aquarium") {
                            aquarium = true;
                            flag = flag + 1;
                        } else {
                            aquarium = false;
                        }

                        long time;
                        temp = data.child("unixTime").getValue().toString();
                        time = Long.parseLong(temp);
                        totalTime = time;

                        if (amusement_park) {
                            new GooglePlaces("amusement_park", new Callback()).execute();

                        }

                        if (library) {
                            new GooglePlaces("library", new Callback()).execute();
                        }

                        if (art_gallery) {
                            new GooglePlaces("art_gallery", new Callback()).execute();
                        }

                        if (museum) {
                            new GooglePlaces("museum", new Callback()).execute();
                        }

                        if (movie_theatre) {
                            new GooglePlaces("movie_theater", new Callback()).execute();
                        }

                        if (shopping_mall) {
                            new GooglePlaces("shopping_mall", new Callback()).execute();
                        }

                        if (zoo) {
                            new GooglePlaces("zoo", new Callback()).execute();
                        }

                        if (aquarium) {
                            new GooglePlaces("aquarium", new Callback()).execute();
                        }

                    }













                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }





    @Override
    public void onClick(View view) {
        if (view==TimeFilterbutton) {
            startActivity(new Intent(this, RecommendationsActivity.class));
        }

    }




//        new GooglePlaces().execute();


    private class GoogleMaps extends AsyncTask<View, Void, String> {

        String data = "";
        String toPlace;
        String fromPlace;
        private OnTaskCompleted listener;



        public GoogleMaps(String toPlace, String fromPlace, OnTaskCompleted listener) {
            this.toPlace = toPlace;
            this.fromPlace = fromPlace;
            this.listener = listener;
        }


        @Override
        protected String doInBackground(View... views) {


            try {
                // make Call to the url
                URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + fromPlace + "&destinations=" + toPlace + "&key=AIzaSyA8fzevvg1LdtgkZmSNwgsdw2gWbDQtQaE");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";

                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            if (data == null) {
                // we have an error to the call
                // we can also stop the progress bar
            } else {

                try {

                    // make an jsonObject in order to parse the response
                    JSONObject jsonObject = new JSONObject(data);
                    listener.onTaskCompletedMaps(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }





            }

        }


    }


    private class GooglePlaces extends AsyncTask<View, Void, String> {

        String data = "";
        String query = "";
        private OnTaskCompleted listener;
        String latitude = ProfileActivity.latitude;
        String longitude = ProfileActivity.longitude;



        public GooglePlaces(String query, OnTaskCompleted listener) {


            this.query = query;
            this.listener = listener;
        }

        @Override
        protected String doInBackground(View... urls) {

            try {




                // make Call to the url
                URL url = new URL("https://maps.googleapis.com/maps/api/place/search/json?radius=50000&sensor=false&key=AIzaSyBaFUPSS2M3_wTQT5aJ6rqMDGbNKb2NRAE&location="+latitude+","+longitude+"&type=" + query);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";

                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            // we can start a progress bar here
        }

        @Override
        protected void onPostExecute(String result) {


            if(last == query) {
                lastQuery = true;

            } else {
                lastQuery = false;
            }


            if (data == null) {
                // we have an error to the call
                // we can also stop the progress bar
            } else {

                try {

                    // make an jsonObject in order to parse the response
                    JSONObject jsonObject = new JSONObject(data);
                    listener.onTaskCompleted(jsonObject, query);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        }
    }


    public interface OnTaskCompleted{
        void onTaskCompleted(JSONObject jsonObject, String query);
        void onTaskCompletedMaps(JSONObject jsonObject);
    }

    public class Callback implements OnTaskCompleted{

        @Override
        public void onTaskCompletedMaps(JSONObject jsonObject) {

            try {
                JSONObject jsonObjectTime = new JSONObject();
                JSONArray jsonArray = jsonObject.getJSONArray("rows");
                Long tempTime;
                String address;
                        //.getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("duration");
               // for (int i = 0; i< jsonArray.length(); i++) {

                    tempTime = Long.parseLong(jsonArray.getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("duration").get("value").toString(), 10);
                    //address = jsonObject.getJSONArray("destination_address").get(0).toString();
                    if (tempTime+ 3600 > totalTime) farOrNear = true;
                    else farOrNear = false;

               // }









            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onTaskCompleted(JSONObject jsonObject, String query) {


            if(flag > 3) {
                if(flag == 4) {
                    howMuchToParse = 3;
                } else if(flag == 5 || flag == 6) {
                    howMuchToParse = 2;
                } else {
                    howMuchToParse = 1;
                }


            } else {
                howMuchToParse = 4;
            }
            // do something with result here!
            try {


                // make an jsonObject in order to parse the response

                // make an jsonObject in order to parse the response
                if (jsonObject.has("results")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < howMuchToParse; i++) {
                        Places poi = new Places();
                        if (jsonArray.getJSONObject(i).has("name")) {
                            poi.setName(jsonArray.getJSONObject(i).optString("name"));
                            poi.setDistance(jsonArray.getJSONObject(i).optString("rating", " "));
                            if (jsonArray.getJSONObject(i).has("vicinity")) {
                                poi.setShortDesc(jsonArray.getJSONObject(i).optString("vicinity"));
                            }

                            poi.setPlace_type(query);
                        }


                        //if(temp.size()<5)
                        temp.add(poi);

                    }
                }
                    PlacesAdapter adapter = new PlacesAdapter(AndroidGooglePlaces.this, temp);
                    places_recommendations.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }



        }


    }


}




