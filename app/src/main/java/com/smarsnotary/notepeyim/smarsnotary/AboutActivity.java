package com.smarsnotary.notepeyim.smarsnotary;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.smarsnotary.notepeyim.smarsnotary.R.layout.activity_about);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("À Propos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView tvNotaires = findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvNotaires);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvNotaires.setText(Html.fromHtml("<p style='float:left'>Les notaires sont des officiers publics qui exercent une juridiction volontaire et amiable. Ils sont institués à vie et reçoivent tous les actes, contrats auxquels les parties doivent ou veulent donner le caractère d’authenticité attaché aux actes de l’autorité publique t pour en assurer la date, en conserver le dépôt, en délivrer des grosses expéditions et extraits.\n" +
                    "        <br />\n" +
                    "        1.2- Les conditions d’accès aux fonctions de notaire L’aspirant à la fonction de notaire doit remplir les conditions suivantes :\n" +
                    "        <br />\n" +
                    "        <ul>\n" +
                    "            <li>Etre Haïtien;</li>\n" +
                    "            <li>Etre âgé de 25 ans au moins ;</li>\n" +
                    "            <li>Jouir de l’exercice des droits civils et politiques ;</li>\n" +
                    "            <li>Etre licencié en droit</li>\n" +
                    "            <li>Etre porteur d’une licence d’aptitude dûment signée du Ministre de la Justice, à la suite d’un examen professionnel subi avec succès au Parquet compétent ;</li>\n" +
                    "            <li>Justifier, au surplus, d’un stage de trois années entières et consécutives en qualité de clerc de notaire, sur la production d’un certificat délivré par le notaire intéressé dûment contresigné par le chef du parquet compétent ;</li>\n" +
                    "            <li>Produire un certificat de bonnes vies et mœurs délivré par le juge de paix compétent.</li>\n" +
                    "        </ul><p>", Html.FROM_HTML_MODE_COMPACT));
        } else {
            tvNotaires.setText(Html.fromHtml("<p style='float:left'>Les notaires sont des officiers publics qui exercent une juridiction volontaire et amiable. Ils sont institués à vie et reçoivent tous les actes, contrats auxquels les parties doivent ou veulent donner le caractère d’authenticité attaché aux actes de l’autorité publique t pour en assurer la date, en conserver le dépôt, en délivrer des grosses expéditions et extraits.\n" +
                    "        <br />\n" +
                    "        1.2- Les conditions d’accès aux fonctions de notaire L’aspirant à la fonction de notaire doit remplir les conditions suivantes :\n" +
                    "        <br />\n" +
                    "        <ul>\n" +
                    "            <li>Etre Haïtien;</li>\n" +
                    "            <li>Etre âgé de 25 ans au moins ;</li>\n" +
                    "            <li>Jouir de l’exercice des droits civils et politiques ;</li>\n" +
                    "            <li>Etre licencié en droit</li>\n" +
                    "            <li>Etre porteur d’une licence d’aptitude dûment signée du Ministre de la Justice, à la suite d’un examen professionnel subi avec succès au Parquet compétent ;</li>\n" +
                    "            <li>Justifier, au surplus, d’un stage de trois années entières et consécutives en qualité de clerc de notaire, sur la production d’un certificat délivré par le notaire intéressé dûment contresigné par le chef du parquet compétent ;</li>\n" +
                    "            <li>Produire un certificat de bonnes vies et mœurs délivré par le juge de paix compétent.</li>\n" +
                    "        </ul></p>"));
        }

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
