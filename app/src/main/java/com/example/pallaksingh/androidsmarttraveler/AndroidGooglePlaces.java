package com.example.pallaksingh.androidsmarttraveler;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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

public class AndroidGooglePlaces extends AppCompatActivity {

    private RecyclerView places_recommendations;
    private RecyclerView.Adapter adapter;
    //private List<Places> ;
    ArrayList<Places> placesList;

    public boolean amusement_park = true;
    public boolean library = false;
    public boolean art_gallery = true;
    public boolean museum = true;
    public boolean movie_theatre = false ;
    public boolean shopping_mall = false;
    public boolean zoo = false;
    public boolean aquarium = false;
    public long totalTime = 10800;
    public String last = "";
    public Boolean lastQuery = false;


    public String latitude = "28.5981355";
    public String longitude = "77.04114219999997";

    public ArrayList<Places> temp = new ArrayList<Places>();
    public ArrayList<Places> finalPlaces = new ArrayList<Places>();
    public Boolean farOrNear;
    public ArrayList<Long> time = new ArrayList<Long>();
    public ArrayList<String> lessThanPlaces = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_google_places);

        places_recommendations = (RecyclerView) findViewById(R.id.places_recommendations);
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
            new GooglePlaces("movie_theatre", new Callback()).execute();
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

        String toPlace = "";
        String fromPlace = "Shree+Hospital,Dwarka";

        if (lastQuery) {

            for (int i = 0; i < temp.size(); i++) {
                toPlace = toPlace + "|" +  temp.get(i).getName() + temp.get(i).getShortDesc();
                toPlace = toPlace.replaceAll(" ", "%20");
            }


            GoogleMaps googleMaps = new GoogleMaps(toPlace, fromPlace, new Callback());
            googleMaps.execute();

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
//                            .getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("duration");
//                    time = jsonObject.get("value").toString();


//
//                    // make an jsonObject in order to parse the response
//                    if (jsonObject.has("rows")) {
//                        JSONArray jsonArray = jsonObject.getJSONArray("rows");
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            Places poi = new Places();
//                            if (jsonArray.getJSONObject(i).has("name")) {
//                                poi.setName(jsonArray.getJSONObject(i).optString("name"));
//                                poi.setDistance(jsonArray.getJSONObject(i).optString("rating", " "));
//                                if (jsonArray.getJSONObject(i).has("vicinity")) {
//                                    poi.setShortDesc(jsonArray.getJSONObject(i).optString("vicinity"));
//                                }
////                                if (jsonArray.getJSONObject(i).has("types")) {
////                                    JSONArray typesArray = jsonArray.getJSONObject(i).getJSONArray("types");
////                                    for (int j = 0; j < typesArray.length(); j++) {
////                                        poi.setPlace_type(typesArray.getString(j) + ", " + poi.getPlace_type());
////                                    }
////                                }
//
//                                poi.setPlace_type(query);
//                            }
//                            //if(temp.size()<5)
//                            temp.add(poi);
//                        }
//                    }
//                    PlacesAdapter adapter = new PlacesAdapter(AndroidGooglePlaces.this, temp);
//                    places_recommendations.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }


//                long unixSeconds = Long.parseLong(time, 10);
//                Date date = new Date(unixSeconds*1000L);
//                SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss z");
//                sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
//                String formattedDate = sdf.format(date);




            }

        }


    }


    private class GooglePlaces extends AsyncTask<View, Void, String> {

        String data = "";
        String query = "";
        private OnTaskCompleted listener;



        public GooglePlaces(String query, OnTaskCompleted listener) {


            this.query = query;
            this.listener = listener;
        }

        @Override
        protected String doInBackground(View... urls) {

            try {




                // make Call to the url
                URL url = new URL("https://maps.googleapis.com/maps/api/place/search/json?radius=50000&sensor=false&key=AIzaSyBaFUPSS2M3_wTQT5aJ6rqMDGbNKb2NRAE&location=28.5981355,77.04114219999997&type=" + query);
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



                    // make an jsonObject in order to parse the response
//                    if (jsonObject.has("results")) {
//                        JSONArray jsonArray = jsonObject.getJSONArray("results");
//                        for (int i = 0; i < 5; i++) {
//                            Places poi = new Places();
//                            if (jsonArray.getJSONObject(i).has("name")) {
//                                poi.setName(jsonArray.getJSONObject(i).optString("name"));
//                                poi.setDistance(jsonArray.getJSONObject(i).optString("rating", " "));
//                                if (jsonArray.getJSONObject(i).has("vicinity")) {
//                                    poi.setShortDesc(jsonArray.getJSONObject(i).optString("vicinity"));
//                                }
////                                if (jsonArray.getJSONObject(i).has("types")) {
////                                    JSONArray typesArray = jsonArray.getJSONObject(i).getJSONArray("types");
////                                    for (int j = 0; j < typesArray.length(); j++) {
////                                        poi.setPlace_type(typesArray.getString(j) + ", " + poi.getPlace_type());
////                                    }
////                                }
//
//                                poi.setPlace_type(query);
//                            }
//                            //if(temp.size()<5)
//                            temp.add(poi);
//                        }
//                    }
//                    PlacesAdapter adapter = new PlacesAdapter(AndroidGooglePlaces.this, temp);
//                    places_recommendations.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        }
    }


    private static ArrayList<Places> parseAndroidPlaces(final String response) {

        ArrayList<Places> temp = new ArrayList<Places>();
        try {

            // make an jsonObject in order to parse the response
            JSONObject jsonObject = new JSONObject(response);

            // make an jsonObject in order to parse the response
            if (jsonObject.has("results")) {
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Places poi = new Places();
                    if (jsonArray.getJSONObject(i).has("name")) {
                        poi.setName(jsonArray.getJSONObject(i).optString("name"));
                        poi.setDistance(jsonArray.getJSONObject(i).optString("rating", " "));
                        if (jsonArray.getJSONObject(i).has("vicinity")) {
                            poi.setShortDesc(jsonArray.getJSONObject(i).optString("vicinity"));
                        }


//                        if (jsonArray.getJSONObject(i).has("types")) {
//                            JSONArray typesArray = jsonArray.getJSONObject(i).getJSONArray("types");
//                            for (int j = 0; j < typesArray.length(); j++) {
//                                poi.setPlace_type(typesArray.getString(j) + ", " + poi.getPlace_type());
//                            }
//                        }
//

                    }


                    //if(temp.size()<5)
                    temp.add(poi);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
        return temp;

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
                for (int i = 0; i< jsonArray.length(); i++) {

                    tempTime = Long.parseLong(jsonArray.getJSONObject(0).getJSONArray("elements").getJSONObject(i).getJSONObject("duration").get("value").toString(), 10);
                    address = jsonObject.getJSONArray("destination_address").get(i).toString();
                    if (tempTime+ 3600 > totalTime) {
                        lessThanPlaces.add(address);

                    } else {
                    }

                }









            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onTaskCompleted(JSONObject jsonObject, String query) {
            // do something with result here!
            try {


                // make an jsonObject in order to parse the response

                // make an jsonObject in order to parse the response
                if (jsonObject.has("results")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < 5; i++) {
                        Places poi = new Places();
                        if (jsonArray.getJSONObject(i).has("name")) {
                            poi.setName(jsonArray.getJSONObject(i).optString("name"));
                            poi.setDistance(jsonArray.getJSONObject(i).optString("rating", " "));
                            if (jsonArray.getJSONObject(i).has("vicinity")) {
                                poi.setShortDesc(jsonArray.getJSONObject(i).optString("vicinity"));
                            }
//                                if (jsonArray.getJSONObject(i).has("types")) {
//                                    JSONArray typesArray = jsonArray.getJSONObject(i).getJSONArray("types");
//                                    for (int j = 0; j < typesArray.length(); j++) {
//                                        poi.setPlace_type(typesArray.getString(j) + ", " + poi.getPlace_type());
//                                    }
//                                }

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

