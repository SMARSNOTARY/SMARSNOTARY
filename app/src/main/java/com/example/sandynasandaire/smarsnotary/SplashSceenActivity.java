package com.example.sandynasandaire.smarsnotary;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashSceenActivity extends AppCompatActivity {

    private int time=5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sceen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashSceenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

        }, time);
    }
}
