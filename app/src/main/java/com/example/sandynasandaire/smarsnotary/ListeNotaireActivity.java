package com.example.sandynasandaire.smarsnotary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.sandynasandaire.smarsnotary.model.Commune;

public class ListeNotaireActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_notaire);
        if(getIntent().getSerializableExtra("selected_commune") != null){
            Commune selectedCommune = (Commune) getIntent().getSerializableExtra("selected_commune");
            Toast.makeText(getApplicationContext(), "SL COM : "+selectedCommune.getId_commune(),Toast.LENGTH_LONG).show();
        }
    }
}
