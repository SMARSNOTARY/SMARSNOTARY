package com.example.sandynasandaire.smarsnotary;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class ProfilActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;


            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new ProfilFragment();

                   // mTextMessage.setText(R.string.title_home);
                    break;

                case R.id.navigation_dashboard:

                    fragment = new MessageFragment();
                   // mTextMessage.setText(R.string.title_dashboard);
                    break;
                case R.id.navigation_notifications:

                    fragment = new localisationFragment();
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
                        .replace(R.id.frContent, fragment)
                        .commit();
                return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
