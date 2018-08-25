package com.example.sandynasandaire.smarsnotary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.sandynasandaire.smarsnotary.adapter.CommuneAdapter;
import com.example.sandynasandaire.smarsnotary.model.Commune;
import com.example.sandynasandaire.smarsnotary.model.Departement;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class CirconscriptionActivity extends AppCompatActivity {

    private ArrayList<Commune> Liste_commune;
    private CommuneAdapter communeadapter;
    GridView gvCommune;
    ProgressDialog progressDialog;
    Departement objDept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circonscription);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Liste commune");

        //get extra from intent
        objDept = (Departement) getIntent().getSerializableExtra("selected_departement");


        gvCommune = findViewById(R.id.gvCommune);
        gvCommune.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Commune objCommune = (Commune) gvCommune.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(), ListeNotaireActivity.class);
                intent.putExtra("selected_commune", objCommune);
                startActivity(intent);
            }
        });
        progressDialog = new ProgressDialog(CirconscriptionActivity.this);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("connexion en cours...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        get_commune();
    }

    private void get_commune() {
        Liste_commune = new ArrayList<>();
        Resources res= getResources();
        communeadapter = new CommuneAdapter(getApplicationContext(), R.layout.item_commune, Liste_commune, res);
        gvCommune.setAdapter(communeadapter);


        String apiLink= "https://simenonline.com/SMARSNOTARY/commune/commune.php?dept="+objDept.getId_departement();
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.get(apiLink, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //Object-->(response) --> JSonArray --> Object
                JSONObject json = response;
                try {
                    JSONArray array = json.getJSONArray("response");
                    if(array.getJSONObject(0).getString("key").equals("SUCCESS")){
                        communeadapter.addAll(Commune.fromJSONArray(json.getJSONArray("response")));
                        progressDialog.dismiss();
                        Log.d("DEBUG APP: ", Liste_commune.toString());
                    }else{
                        progressDialog.dismiss();
                }

            } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
            }
        });
    }
}
