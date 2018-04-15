package com.example.pallaksingh.androidsmarttraveler;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

public class AndroidFoursquare extends ListActivity {
    ArrayList<FoursquareVenue> venuesList;

    // the foursquare client_id and the client_secret

    // ============== YOU SHOULD MAKE NEW KEYS ====================//
    final String CLIENT_ID = "VTW0PAYLKEIM5TS35L0HEGMYQB12PT35B5F4WGNLEINRX1BL";
    final String CLIENT_SECRET = "4FPOIYYC4DWRIFFNHFXIO5AAN0QN5YQGJ2JOZZRSBY4KRGDA";

    // we will need to take the latitude and the logntitude from a certain point
    // this is the center of New York
    final String latitude = "40.7463956";
    final String longtitude = "-73.9852992";



    ArrayAdapter<String> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_foursquare);

        // start the AsyncTask that makes the call for the venus search.
        new fourquare().execute();
    }

    private class fourquare extends AsyncTask<View, Void, String> {

        String data = "";

        @Override
        protected String doInBackground(View... urls) {

            try {
                // make Call to the url
                URL url = new URL("https://api.foursquare.com/v2/venues/search?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=20130815&ll=28.598136,77.041142");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";

                while(line!=null) {
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
            if (data == null) {
                // we have an error to the call
                // we can also stop the progress bar
            } else {
                // all things went right

                // parseFoursquare venues search result
                venuesList = (ArrayList<FoursquareVenue>) parseFoursquare(data);

                List<String> listTitle = new ArrayList<String>();

                for (int i = 0; i < venuesList.size(); i++) {
                    // make a list of the venus that are loaded in the list.
                    // show the name, the category and the city
                    listTitle.add(i, venuesList.get(i).getName() + ", " + venuesList.get(i).getCategory() + "" + venuesList.get(i).getCity());
                }

                // set the results to the list
                // and show them in the xml
                myAdapter = new ArrayAdapter<String>(AndroidFoursquare.this, R.layout.row_layout, R.id.listText, listTitle);
                setListAdapter(myAdapter);
            }
        }
    }

//    public static String makeCall(String url) {
//
//        // string buffers the url
//        StringBuffer buffer_string = new StringBuffer(url);
//        String replyString = "";
//
//        // instanciate an HttpClient
//        HttpClient httpclient = new DefaultHttpClient();
//        // instanciate an HttpGet
//        HttpGet httpget = new HttpGet(buffer_string.toString());
//
//        try {
//            // get the responce of the httpclient execution of the url
//            HttpResponse response = httpclient.execute(httpget);
//            InputStream is = response.getEntity().getContent();
//
//            // buffer input stream the result
//            BufferedInputStream bis = new BufferedInputStream(is);
//            ByteArrayBuffer baf = new ByteArrayBuffer(20);
//            int current = 0;
//            while ((current = bis.read()) != -1) {
//                baf.append((byte) current);
//            }
//            // the result as a string is ready for parsing
//            replyString = new String(baf.toByteArray());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // trim the whitespaces
//        return replyString.trim();
//    }

    private static ArrayList<FoursquareVenue> parseFoursquare(final String response) {

        ArrayList<FoursquareVenue> temp = new ArrayList<FoursquareVenue>();
        try {

            // make an jsonObject in order to parse the response
            JSONObject jsonObject = new JSONObject(response);

            // make an jsonObject in order to parse the response
            if (jsonObject.has("response")) {
                if (jsonObject.getJSONObject("response").has("venues")) {
                    JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("venues");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        FoursquareVenue poi = new FoursquareVenue();
                        if (jsonArray.getJSONObject(i).has("name")) {
                            poi.setName(jsonArray.getJSONObject(i).getString("name"));

                            if (jsonArray.getJSONObject(i).has("location")) {
                                if (jsonArray.getJSONObject(i).getJSONObject("location").has("address")) {
                                    if (jsonArray.getJSONObject(i).getJSONObject("location").has("city")) {
                                        poi.setCity(jsonArray.getJSONObject(i).getJSONObject("location").getString("city"));
                                    }
                                    if (jsonArray.getJSONObject(i).has("categories")) {
                                        if (jsonArray.getJSONObject(i).getJSONArray("categories").length() > 0) {
                                            if (jsonArray.getJSONObject(i).getJSONArray("categories").getJSONObject(0).has("icon")) {
                                                poi.setCategory(jsonArray.getJSONObject(i).getJSONArray("categories").getJSONObject(0).getString("name"));
                                            }
                                        }
                                    }
                                    temp.add(poi);
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<FoursquareVenue>();
        }
        return temp;


    }
}
