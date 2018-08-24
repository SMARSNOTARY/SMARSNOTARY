package com.example.sandynasandaire.smarsnotary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.sandynasandaire.smarsnotary.adapter.DepartementCommuneAdapter;
import com.example.sandynasandaire.smarsnotary.model.DepartementCommune;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class DepartementCommuneActivity extends AppCompatActivity {

    private ArrayList<DepartementCommune> Liste_departement_commune;
    private com.example.sandynasandaire.smarsnotary.adapter.DepartementCommuneAdapter DepartementCommuneAdapter;
    GridView gvDepartementCommune;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departement_commune);


        gvDepartementCommune = findViewById(R.id.gvDepartementCommune);
        gvDepartementCommune.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DepartementCommune objDepartementCommune = (DepartementCommune) gvDepartementCommune.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(), DepartementCommuneActivity.class);
                intent.putExtra("selected_departement_commune", objDepartementCommune);
                startActivity(intent);
            }
        });
        get_departement_commune();
    }

    private void get_departement_commune() {
        Liste_departement_commune = new ArrayList<>();
        DepartementCommuneAdapter = new DepartementCommuneAdapter(getApplicationContext(),Liste_departement_commune );
        gvDepartementCommune.setAdapter(DepartementCommuneAdapter);

        String apiLink= "https://simenonline.com/SMARSNOTARY/commune/commune.php?dept=1";
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.get(apiLink, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //Object-->(response) --> JSonArray --> Object
                JSONObject json = response;
                try {
                    JSONArray array = json.getJSONArray("response");
                    DepartementCommuneAdapter.addAll(DepartementCommune.fromJSONArray(json.getJSONArray("response")));
                    Log.d("DEBUG APP: ", Liste_departement_commune.toString());
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
