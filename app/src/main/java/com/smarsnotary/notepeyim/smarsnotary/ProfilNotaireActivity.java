package com.smarsnotary.notepeyim.smarsnotary;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfilNotaireActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.smarsnotary.notepeyim.smarsnotary.R.layout.activity_profil_notaire);


        sharedPreferences = getSharedPreferences("LOGIN_USER_INFO", Context.MODE_PRIVATE);
        //tvFullName tvEmail  tvPhone tvReviews btnFollowNotaire tvAddress
        TextView tvFullName = (TextView)  findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvFullName);
        TextView tvEmail = (TextView)  findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvEmail);
        TextView tvPhone = (TextView) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvPhone);
        TextView tvReviews = (TextView)  findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvReviews);
        TextView tvAddress = (TextView)  findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvAddress);
        TextView tvCommision = (TextView)  findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvCommision);
        TextView tvStarCount = (TextView)  findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvStarCount);


        tvFullName.setText("");
        tvEmail.setText(sharedPreferences.getString("email", null));
        tvPhone.setText(sharedPreferences.getString("telephone", null));
        tvAddress.setText("");
        tvCommision.setText(sharedPreferences.getString("create_user", null));
        tvStarCount.setText("0.0");
    }
}
