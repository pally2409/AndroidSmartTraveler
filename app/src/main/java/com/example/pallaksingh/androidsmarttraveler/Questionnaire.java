package com.example.pallaksingh.androidsmarttraveler;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Questionnaire extends AppCompatActivity implements View.OnClickListener {

    private TimePickerDialog.OnTimeSetListener mTimeListener;
    private TextView textViewDate;
    public long timeInUnix;
    private DatabaseReference databaseReference;
    private Button SaveButton;
    private FirebaseAuth firebaseAuth;
    private CheckBox checkZoo, checkAquarium, checkMovie_Theatre, checkShopping_Mall, checkArt_Gallery, checkMuseums,checkAmusement_Parks, checkLibrary;
    public boolean amusement_park = false;
    public boolean library = false;
    public boolean art_gallery = false;
    public boolean museum = false;
    public boolean movie_theatre = false;
    public boolean shopping_mall = false;
    public boolean zoo = false;
    public boolean aquarium = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        checkAmusement_Parks = (CheckBox) findViewById(R.id.checkAmusement_Parks);
        checkZoo = (CheckBox) findViewById(R.id.checkZoo);
        checkAquarium = (CheckBox) findViewById(R.id.checkAquarium);
        checkMovie_Theatre = (CheckBox) findViewById(R.id.checkMovie_Theatre);
        checkShopping_Mall = (CheckBox) findViewById(R.id.checkShopping_Mall);
        checkArt_Gallery = (CheckBox) findViewById(R.id.checkArt_Gallery);
        checkMuseums = (CheckBox) findViewById(R.id.checkMuseums);
        checkLibrary = (CheckBox) findViewById(R.id.checkLibrary);

        addListenerOnAmusementPark();
        addListenerOnZoo();
        addListenerOnAquarium();
        addListenerOnMovieTheatre();
        addListenerOnShoppingMall();
        addListenerOnArtGallery();
        addListenerOnMuseums();
        addListenerOnLibrary();

        SaveButton = (Button) findViewById(R.id.SaveButton);
        SaveButton.setOnClickListener(this);

        //databaseReference.
        databaseReference = FirebaseDatabase.getInstance().getReference();


        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = 00;
                int minute = 00;
                TimePickerDialog dialog = new TimePickerDialog(
                        Questionnaire.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeListener, hour, minute, true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeInUnix = hourOfDay*3600 + 50*minute;
                String date = hourOfDay + " hr " + minute + " mins";
                textViewDate.setText(date);
            }
        };

    }

    private void saveUserInformation() {

        UserInformation userInformation = new UserInformation(amusement_park, library, art_gallery, museum, movie_theatre, shopping_mall, zoo, aquarium, timeInUnix);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInformation);
        Toast.makeText(this, "Information Saved", Toast.LENGTH_LONG).show();
    }


    public void addListenerOnAmusementPark() {
        checkAmusement_Parks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    amusement_park = true;
                } else {
                    amusement_park = false;
                }
            }
        });

    }

    public void addListenerOnZoo() {
        checkZoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    zoo = true;
                } else {
                    zoo = false;
                }
            }
        });

    }

    public void addListenerOnAquarium() {
        checkAquarium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    aquarium = true;
                } else {
                    aquarium = false;
                }
            }
        });

    }

    public void addListenerOnMovieTheatre() {
        checkMovie_Theatre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    movie_theatre = true;
                } else {
                    movie_theatre = false;
                }
            }
        });

    }

    public void addListenerOnShoppingMall() {
        checkShopping_Mall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    shopping_mall = true;
                } else {
                    shopping_mall = false;
                }
            }
        });

    }

    public void addListenerOnArtGallery() {
        checkArt_Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    art_gallery = true;
                } else {
                    art_gallery = false;
                }
            }
        });

    }

    public void addListenerOnMuseums() {
        checkMuseums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    museum = true;
                } else {
                    museum = false;
                }
            }
        });

    }

    public void addListenerOnLibrary() {
        checkLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    library = true;
                } else {
                    library = false;
                }
            }
        });

    }

    @Override
    public void onClick(View v) {

        if(v == SaveButton) {
            saveUserInformation();
        }

    }
}
