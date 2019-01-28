package com.smarsnotary.notepeyim.smarsnotary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.smarsnotary.notepeyim.smarsnotary.adapter.CommuneAdapter;
import com.smarsnotary.notepeyim.smarsnotary.model.Commune;
import com.smarsnotary.notepeyim.smarsnotary.model.Departement;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.smarsnotary.notepeyim.smarsnotary.utils.settings.API_LINK;

public class CirconscriptionActivity extends AppCompatActivity {

    private ArrayList<Commune> Liste_commune;
    private CommuneAdapter communeadapter;
    GridView gvCommune;
    ProgressDialog progressDialog;
    Departement objDept;
    private SwipeRefreshLayout swipeRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.smarsnotary.notepeyim.smarsnotary.R.layout.activity_circonscription);

        //get extra from intent
        objDept = (Departement) getIntent().getSerializableExtra("selected_departement");

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Circonsription "+objDept.getDesc_departement());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        gvCommune = findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.gvCommune);
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

        swipeRefresh=(SwipeRefreshLayout) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                get_commune();
                swipeRefresh.setRefreshing(false);
            }
        });
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

    private void get_commune() {
        Liste_commune = new ArrayList<>();
        Resources res= getResources();
        communeadapter = new CommuneAdapter(getApplicationContext(), com.smarsnotary.notepeyim.smarsnotary.R.layout.item_commune, Liste_commune, res);
        gvCommune.setAdapter(communeadapter);


        String apiLink= API_LINK+"commune/commune.php?dept="+objDept.getId_departement();
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
