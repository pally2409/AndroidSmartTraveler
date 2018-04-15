package com.example.pallaksingh.androidsmarttraveler;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
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

public class FoursquareFindQuery extends AsyncTask<Void, Void, Void> {

    String data = "";
    String dataParsed = "";
    String singleParsed = "";
    final String CLIENT_ID = "VTW0PAYLKEIM5TS35L0HEGMYQB12PT35B5F4WGNLEINRX1BL";
    final String CLIENT_SECRET = "4FPOIYYC4DWRIFFNHFXIO5AAN0QN5YQGJ2JOZZRSBY4KRGDA";
    ArrayList<FoursquareVenue> venuesList;




    @Override
    protected Void doInBackground(Void... voids) {

        try {
            // make Call to the url

            URL url = new URL("https://api.foursquare.com/v2/venues/search?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=20130815&ll=28.598136,77.041142&query="+FindAPlaceActivity.queryFindAPlace.getText());
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
    protected void onPostExecute(Void aVoid) {

        FindAPlaceActivity.fetchedPlaces.setText("");
        super.onPostExecute(aVoid);

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
//                listTitle.add(i, venuesList.get(i).getName() + ", " + venuesList.get(i).getCategory() + "" + venuesList.get(i).getCity());
                FindAPlaceActivity.fetchedPlaces.append(venuesList.get(i).getName() + ", " + venuesList.get(i).getCategory() + "" + venuesList.get(i).getCity()+"\n");
            }

//            FindAPlaceActivity.fetchedPlaces.setText("");
//            for (int j = 0; j < venuesList.size(); j++){
//                (venuesList.get(j) + "\n");
//            }

        }
    }

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
