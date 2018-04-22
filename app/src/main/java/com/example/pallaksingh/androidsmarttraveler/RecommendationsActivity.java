package com.example.pallaksingh.androidsmarttraveler;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.ArrayList;
import java.util.List;

public class RecommendationsActivity extends AppCompatActivity {

    private RecyclerView places_recommendations;
    private RecyclerView.Adapter adapter;
    //private RecyclerView.LayoutManager mLayoutManager;
    List<Places> placesLists = new ArrayList<>();
    public long totalTime = 10800;
    public boolean farOrNear = false;
    public int position;
    List<Integer> indexes = new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);

        places_recommendations = (RecyclerView) findViewById(R.id.places_recommendations);
        places_recommendations.setHasFixedSize(true);
        places_recommendations.setLayoutManager(new LinearLayoutManager(this));
        String toPlace = "";
        String fromPlace = "Shree+Hospital,Dwarka";
        int i = 0;
        while(i<AndroidGooglePlaces.temp.size()) {
            toPlace = toPlace + "|" + AndroidGooglePlaces.temp.get(i).getName() + "%20" + AndroidGooglePlaces.temp.get(i).getShortDesc();
            toPlace = toPlace.replaceAll(" ", "%20");
            i++;

        }


        GoogleMaps currentTask = new GoogleMaps(fromPlace, toPlace, new Callbacks());
        currentTask.execute();
//
//        while(currentTask.getStatus() == AsyncTask.Status.PENDING || currentTask.getStatus() == AsyncTask.Status.RUNNING) {
//
//        }
//
//        if(currentTask.getStatus() == AsyncTask.Status.FINISHED) {
//
//        }








    }

    private class GoogleMaps extends AsyncTask<View, Void, String> {

        String data = "";
        String fromPlace;
        String toPlace;
        private OnTaskCompleted listener;
        BufferedReader buffReader;


        public GoogleMaps(String fromPlace, String toPlace, OnTaskCompleted listener) {
            this.fromPlace = fromPlace;
            this.toPlace = toPlace;
            this.listener = listener;
        }

        @Override
        protected String doInBackground(View... views) {


            try {
                // make Call to the url
                URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=" + fromPlace + "&destinations=" + toPlace + "&key=AIzaSyA8fzevvg1LdtgkZmSNwgsdw2gWbDQtQaE");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();



                //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                buffReader = new BufferedReader(new InputStreamReader(inputStream));
                Log.d("DEBUGGNG", buffReader.toString());



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

//            if (data == null) {
//                // we have an error to the call
//                // we can also stop the progress bar
//            } else
                try {


                String line = "";

                //while (line != null)

                while (line!=null){
                    line = buffReader.readLine();
                    data = data + line;
                }

                // make an jsonObject in order to parse the response
                JSONObject jsonObject = new JSONObject(data);
                JSONArray jsonArray = jsonObject.getJSONArray("rows").getJSONObject(0).getJSONArray("elements");
                System.out.print(jsonArray.length());

                int m = jsonArray.length();


                long tempTime;
                int i;
                Integer j;

                for (i = 0; i < jsonArray.length(); i++) {

                    String tempTimeString = jsonArray.getJSONObject(i).getJSONObject("duration").get("value").toString();

                    tempTime = Long.parseLong(tempTimeString, 10);
                    if (tempTime + 3600 <= totalTime) {
                        j = Integer.parseInt(Integer.toString(i));
                        boolean indexesAdd = indexes.add(j);

                    }
                }
                listener.onTaskCompletion(jsonObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public interface OnTaskCompleted{
        void onTaskCompletion(JSONObject jsonObject);
    }

    public class Callbacks implements OnTaskCompleted{

        @Override
        public void onTaskCompletion(JSONObject jsonObject) {

            try {

                for(int i = 0; i<indexes.size(); i++) {

                    int val = indexes.get(i).intValue();
                    new Places();
                    Places plx;
                    plx = AndroidGooglePlaces.temp.get(val);
                    placesLists.add(plx);
                }

                adapter = new PlacesAdapter(RecommendationsActivity.this, placesLists);
                places_recommendations.setAdapter(adapter);


                // }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }





    }

}
