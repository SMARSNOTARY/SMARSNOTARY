package com.example.sandynasandaire.smarsnotary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.sandynasandaire.smarsnotary.adapter.CommuneAdapter;
import com.example.sandynasandaire.smarsnotary.model.Commune;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circonscription);

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
        get_commune();
    }

    private void get_commune() {
        Liste_commune = new ArrayList<>();
        communeadapter = new CommuneAdapter(getApplicationContext(),Liste_commune );
        gvCommune.setAdapter(communeadapter);

        String apiLink= "https://simenonline.com/SMARSNOTARY/commune/commune.php";
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.get(apiLink, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //Object-->(response) --> JSonArray --> Object
                JSONObject json = response;
                try {
                    JSONArray array = json.getJSONArray("response");
                    communeadapter.addAll(Commune.fromJSONArray(json.getJSONArray("response")));
                    Log.d("DEBUG APP: ", Liste_commune.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
