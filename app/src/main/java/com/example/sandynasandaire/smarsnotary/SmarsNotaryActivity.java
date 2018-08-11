package com.example.sandynasandaire.smarsnotary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;


public class SmarsNotaryActivity extends AppCompatActivity {

    private int time = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smars_notary);

    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            Intent intent=new Intent(SmarsNotaryActivity.this,login_Smarnotary.class);
            startActivity(intent);
            finish();

        }
    },time);

    }
}