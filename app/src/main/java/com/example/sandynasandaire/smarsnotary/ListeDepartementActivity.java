package com.example.sandynasandaire.smarsnotary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.sandynasandaire.smarsnotary.adapter.DepartementAdapter;
import com.example.sandynasandaire.smarsnotary.model.Departement;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListeDepartementActivity extends AppCompatActivity {


    private ArrayList<Departement> Liste_departement;
    private DepartementAdapter departementadapter;
    GridView gvDepartement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_departement);


        gvDepartement = findViewById(R.id.gvDepartement);
        gvDepartement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Departement objDepartement = (Departement) gvDepartement.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(), CirconscriptionActivity.class);
                intent.putExtra("selected_departement", objDepartement);
                startActivity(intent);
            }
        });

    get_departement();
}


    private  void get_departement() {
        Liste_departement = new ArrayList<>();
        departementadapter = new DepartementAdapter(getApplicationContext(),Liste_departement );
        gvDepartement.setAdapter(departementadapter);

        String apiLink= "https://simenonline.com/SMARSNOTARY/departement/departement.php";
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.post(apiLink, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //Object-->(response) --> JSonArray --> Object
                JSONObject json = response;
                try {
                    JSONArray array = json.getJSONArray("response");

                    departementadapter.addAll(Departement.fromJSONArray(json.getJSONArray("response")));
                    Log.d("DEBUG APP: ", Liste_departement.toString());
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
