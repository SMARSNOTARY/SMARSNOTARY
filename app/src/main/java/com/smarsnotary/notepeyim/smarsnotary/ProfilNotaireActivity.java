package com.smarsnotary.notepeyim.smarsnotary;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ProfilNotaireActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.smarsnotary.notepeyim.smarsnotary.R.layout.activity_profil_notaire);

        Toolbar toolbar = (Toolbar) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Profil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sharedPreferences = getSharedPreferences("LOGIN_USER_INFO", Context.MODE_PRIVATE);
        //tvFullName tvEmail  tvPhone tvReviews btnFollowNotaire tvAddress
        TextView tvFullName = (TextView)  findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvFullName);
        TextView tvEmail = (TextView)  findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvEmail);
        TextView tvPhone = (TextView) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvPhone);
        TextView tvReviews = (TextView)  findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvReviews);
        TextView tvAddress = (TextView)  findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvAddress);
        TextView tvCommision = (TextView)  findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvCommision);
        TextView tvStarCount = (TextView)  findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvStarCount);

        tvFullName.setText(sharedPreferences.getString("email", null));
        tvEmail.setText(sharedPreferences.getString("email", null));
        tvPhone.setText(sharedPreferences.getString("telephone", null));
        tvAddress.setText("");
        tvCommision.setText(sharedPreferences.getString("create_user", null));
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.smarsnotary.notepeyim.smarsnotary.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}