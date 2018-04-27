package com.example.pallaksingh.androidsmarttraveler;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser {

    private HashMap<String, String> getPlace(JSONObject googlePlaceJson) {
        HashMap<String, String> googlePlacesMap = new HashMap<>();
        String placeName = "-NA";
        String vicinity = "-NA";
        String latitude = "";
        String longitude = "";
        String reference = "";


            try {

                if(!googlePlaceJson.isNull("name")) {
                    placeName = googlePlaceJson.getString("name");

                }

                if(!googlePlaceJson.isNull("vicinity")) {
                    vicinity = googlePlaceJson.getString("vicinity");
                }

                latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
                longitude= googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");

//                reference = googlePlaceJson.getString("reference");
//                googlePlacesMap.put("place_name", placeName);
//                googlePlacesMap.put("vicinity", vicinity);
//                googlePlacesMap.put("latitude", latitude);
//                googlePlacesMap.put("longitude", longitude);
//                googlePlacesMap.put("reference", reference);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return googlePlacesMap;


    }

    private List<HashMap<String, String>>getPlaces(JSONArray jsonArray)
    {
        int count = jsonArray.length();
        List<HashMap<String, String>> placelist = new ArrayList<>();
        HashMap<String, String> placeMap = null;

        for(int i = 0; i<count;i++)
        {
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placelist.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return placelist;
    }


    public List<HashMap<String, String>> parse(String jsonData)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        Log.d("json data", jsonData);

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }

}
