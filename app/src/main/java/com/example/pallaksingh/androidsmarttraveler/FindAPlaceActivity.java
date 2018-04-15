package com.example.pallaksingh.androidsmarttraveler;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FindAPlaceActivity extends AppCompatActivity implements View.OnClickListener {

    private Button findAPlace;
    public static EditText queryFindAPlace;
    public static TextView fetchedPlaces;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_aplace);

        findAPlace = (Button) findViewById(R.id.findAPlace);
        queryFindAPlace = (EditText) findViewById(R.id.queryFindAPlace);
        fetchedPlaces = (TextView) findViewById(R.id.fetchedPlaces);
        progressDialog = new ProgressDialog(this);

        findAPlace.setOnClickListener(this);
    }


    private void enterPlaces() {
        String queryPlace = queryFindAPlace.getText().toString().trim();
        if(TextUtils.isEmpty(queryPlace)) {
            Toast.makeText(this,"Please enter a query", Toast.LENGTH_SHORT).show();
            return;

        }
        progressDialog.setMessage("Finding your query...");
        progressDialog.show();


        FoursquareFindQuery process = new FoursquareFindQuery();
        process.execute();

        progressDialog.dismiss();



    }

    @Override
    public void onClick(View view) {
        if(view ==findAPlace) {
            enterPlaces();
        }

    }
}
