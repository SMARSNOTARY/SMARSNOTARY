package com.smarsnotary.notepeyim.smarsnotary;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.smarsnotary.notepeyim.smarsnotary.model.Notaire;

public class ProfilActivity extends AppCompatActivity {

    private TextView mTextMessage;

    Notaire objNotaire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.smarsnotary.notepeyim.smarsnotary.R.layout.activity_profil);
        //if (savedInstanceState == null) {
          // bottomNavigation.setSelectedItemId(R.id.navigation_home); // change to whichever id should be default
        //}

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mTextMessage = (TextView) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.navigation);

        navigation.setSelectedItemId(com.smarsnotary.notepeyim.smarsnotary.R.id.navigation_home); // change to whichever id should be default
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        objNotaire = (Notaire) getIntent().getSerializableExtra("selected_notaire");
        getSupportActionBar().setTitle("Notaire "+objNotaire.getPrenom_notaire()+" "+objNotaire.getNom_notaie());
        open_fragment();
    }

    private void open_fragment() {
        Fragment fragment = new ProfilFragment().newInstance(objNotaire);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(com.smarsnotary.notepeyim.smarsnotary.R.id.frContent, fragment)
                .commit();
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case com.smarsnotary.notepeyim.smarsnotary.R.id.navigation_home:
                    fragment = new ProfilFragment().newInstance(objNotaire);

                    // mTextMessage.setText(R.string.title_home);
                    break;

                case com.smarsnotary.notepeyim.smarsnotary.R.id.navigation_dashboard:

                    fragment = new MessageFragment().newInstance(objNotaire);
                    // mTextMessage.setText(R.string.title_dashboard);
                    break;
                case com.smarsnotary.notepeyim.smarsnotary.R.id.navigation_notifications:

                    fragment = new localisationFragment().newInstance(objNotaire);
                    //  mTextMessage.setText(R.string.title_notifications);
                    break;
            }
            return loadFragment(fragment);
        }

        private boolean loadFragment(Fragment fragment) {
            //switching fragment
            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(com.smarsnotary.notepeyim.smarsnotary.R.id.frContent, fragment)
                        .commit();
                return true;
            }
            return false;
        }

    };
}
