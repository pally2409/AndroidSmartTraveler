package com.example.pallaksingh.androidsmarttraveler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private Button buttonLogout;
    private CardView image_button_android_questionnaire;




    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



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
            startActivity(new Intent(this, AndroidGooglePlaces.class));
        }

    }
}
