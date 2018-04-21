package com.example.pallaksingh.androidsmarttraveler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecommendationsActivity extends AppCompatActivity {

    private RecyclerView places_recommendations;
    private RecyclerView.Adapter adapter;
    //private RecyclerView.LayoutManager mLayoutManager;

    private List<Places> placesList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);

        places_recommendations = (RecyclerView) findViewById(R.id.places_recommendations);
        places_recommendations.setHasFixedSize(true);
        places_recommendations.setLayoutManager(new LinearLayoutManager(this));
       // mLayoutManager = new LinearLayoutManager(this);
       // places_recommendations.setLayoutManager(mLayoutManager);
//        mAdapter = new MyAdapter(myDataset);
//        mRecyclerView.setAdapter(mAdapter);

        placesList = new ArrayList<>();
        adapter = new PlacesAdapter(this, placesList);
        places_recommendations.setAdapter(adapter);





    }
}
