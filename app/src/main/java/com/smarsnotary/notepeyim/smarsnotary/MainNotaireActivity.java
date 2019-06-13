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
import android.support.design.widget.Snackbar;
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

public class MainNotaireActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor ;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.smarsnotary.notepeyim.smarsnotary.R.layout.activity_main_notaire);
        Toolbar toolbar = (Toolbar) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.toolbar);
        setSupportActionBar(toolbar);


        sharedPreferences = getApplicationContext().getSharedPreferences("LOGIN_USER_INFO", Context.MODE_PRIVATE);


        progressDialog =new ProgressDialog(MainNotaireActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);


        CardView btnAcceuil = (CardView) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.btnAcceuil);
        btnAcceuil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListeDepartementActivity.class);
                startActivity(intent);
            }
        });


        CardView btnProfil = (CardView) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.btnProfil);
        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(getApplicationContext(), ProfilNotaireActivity.class);
               startActivity(intent);
            }
        });

        CardView btnRendezvous = (CardView) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.btnRendezvous);
        btnRendezvous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RendezVousActivity.class);
                startActivity(intent);
            }
        });

        CardView btnMessage = (CardView) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.btnMessage);
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
        getMenuInflater().inflate(com.smarsnotary.notepeyim.smarsnotary.R.menu.main_notaire, menu);
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


        DrawerLayout drawer = (DrawerLayout) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void alerteMessage(String title,String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainNotaireActivity.this);

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

    private void shareWithText() {

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBodyText = "Partager l'application sur tous les réseaux sociaux SMARSNOTARY/NOTĖPEYIM....";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Partager SMARSNOTARY/NOTĖPEYIM");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
        startActivity(Intent.createChooser(sharingIntent, "Partager : "));
    }

}
