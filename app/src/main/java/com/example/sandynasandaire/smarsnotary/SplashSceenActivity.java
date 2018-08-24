package com.example.sandynasandaire.smarsnotary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashSceenActivity extends AppCompatActivity {


    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sceen);
        final SharedPreferences mSettings = getApplicationContext().getSharedPreferences("LOGIN_USER_INFO", Context.MODE_PRIVATE);


        //HideNotificationBar
               // requestWindowFeature(Window.FEATURE_NO_TITLE);
                //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                   //     WindowManager.LayoutParams.FLAG_FULLSCREEN);
                //getSupportActionBar().hide();

        //etContentView(R.layout.activity_main);

                //HideActionBar
                // ActionBar actionbar = getSupportActionBar();
                // getSupportActionBar().hide();




	        /* New Handler to start the Menu-Activity
	         * and close this Splash-Screen after some seconds.*/
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
	                /* Create an Intent that will start the Menu-Activity. */
	                if(mSettings.getString("iduser", null) == null){
                        Intent mainIntent = new Intent(SplashSceenActivity.this, LoginActivity.class);
                        SplashSceenActivity.this.startActivity(mainIntent);
                        SplashSceenActivity.this.finish();
                    }else{
                        Intent mainIntent = new Intent(SplashSceenActivity.this, MainActivity.class);
                        SplashSceenActivity.this.startActivity(mainIntent);
                        SplashSceenActivity.this.finish();
                    }
                       // overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    }
                }, SPLASH_DISPLAY_LENGTH);
            }
        }

