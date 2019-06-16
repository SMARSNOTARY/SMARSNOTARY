package com.smarsnotary.notepeyim.smarsnotary;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor ;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.smarsnotary.notepeyim.smarsnotary.R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Acceuil");

        sharedPreferences = getApplicationContext().getSharedPreferences("LOGIN_USER_INFO", Context.MODE_PRIVATE);


        progressDialog =new ProgressDialog(MainActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, com.smarsnotary.notepeyim.smarsnotary.R.string.navigation_drawer_open, com.smarsnotary.notepeyim.smarsnotary.R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);
        TextView tvEmail=(TextView)header.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvEmail);
        tvEmail.setText(sharedPreferences.getString("email", null));

        TextView tvName=(TextView)header.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvName);
        tvName.setText(sharedPreferences.getString("telephone", null));


        CardView btnMeeting = (CardView) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.btnMeeting);
        btnMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RendezVousActivity.class);
                startActivity(intent);
            }
        });


        CardView btnNewsPaper = (CardView) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.btnNewsPaper);
        btnNewsPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                //startActivity(intent);
            }
        });

        CardView btnAnnuaire = (CardView) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.btnAnnuaire);
        btnAnnuaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ListeDepartementActivity.class);
                startActivity(intent);
            }
        });

        CardView btnApropos = (CardView) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.btnApropos);
        btnApropos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.fab);
        fab.setVisibility(View.GONE);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.smarsnotary.notepeyim.smarsnotary.R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.smarsnotary.notepeyim.smarsnotary.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id ==  com.smarsnotary.notepeyim.smarsnotary.R.id.nav_share){
            shareWithText();
        }

        else if (id == com.smarsnotary.notepeyim.smarsnotary.R.id.nav_playstore) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + getApplicationContext().getPackageName()));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }

        else if (id == com.smarsnotary.notepeyim.smarsnotary.R.id.nav_logout) {
            alerteMessage("Confirmer","Voulez-vous vraiment déconnecter?");
        }

        else if (id == com.smarsnotary.notepeyim.smarsnotary.R.id.nav_info) {
           Intent intent = new Intent(getApplicationContext(), InformationActivity.class);
           startActivity(intent);
        }




       /*else if (id == R.id.nav_secondFragment) {
            // Handle the camera action
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new SecondFragment())
                    .commit();


        } else if (id == R.id.nav_thirdFragment) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new ThirdFragment())
                    .commit();

        } else if (id == R.id.nav_thirdFragment) {
            FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame, new ThirdFragment())
                            .commit();

        } */
        DrawerLayout drawer = (DrawerLayout) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void shareWithText() {

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBodyText = "Merci de partager le lien de téléchargement de l'application sur tous les réseaux sociaux https://play.google.com/store/apps/details?id=com.notepeyim.smarsnotary et visiter notre site web http://notepeyim.esy.es";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Partager SMARSNOTARY/NOTĖPEYIM");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(sharingIntent, "Option de partage : "));
    }


    private void alerteMessage(String title,String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

        dialog.setTitle(title)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(message)
                .setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.cancel();
                        progressDialog.setMessage("Déconnexion en cours...");
                        progressDialog.show();
                        logoutUserFunction();
                    }
                });
        dialog.setNegativeButton("NON", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogN, int which) {
                dialogN.cancel();
            }
        });
        dialog.show();
    }

    private void logoutUserFunction() {
        progressDialog.dismiss();
        editor = sharedPreferences.edit();
        editor.clear().apply();
        finishAffinity();
        startActivity(new Intent(getApplicationContext(), SplashSceenActivity.class));
    }
}
