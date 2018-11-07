package com.example.sandynasandaire.smarsnotary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.sandynasandaire.smarsnotary.adapter.NotaireAdapter;
import com.example.sandynasandaire.smarsnotary.model.Commune;
import com.example.sandynasandaire.smarsnotary.model.Notaire;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.example.sandynasandaire.smarsnotary.utils.settings.API_LINK;

public class ListeNotaireActivity extends AppCompatActivity {

    private ArrayList<Notaire> Liste_notaire;
    private NotaireAdapter notaireadapter;
    GridView gvNotaire;
    Commune objCom;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_notaire);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        objCom = (Commune) getIntent().getSerializableExtra("selected_commune");
        getSupportActionBar().setTitle("Annuaire Notaire "+objCom.getDesc_Commune());
        gvNotaire = findViewById(R.id.gvNotaire);
        gvNotaire.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Notaire objNotaire = (Notaire) gvNotaire.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
                intent.putExtra("selected_notaire", objNotaire);
                startActivity(intent);
            }
        });

        get_notaire();

        swipeRefresh=(SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                get_notaire();
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    private void get_notaire() {
        Liste_notaire = new ArrayList<>();
        notaireadapter = new NotaireAdapter(getApplicationContext(),Liste_notaire );
        gvNotaire.setAdapter(notaireadapter);

        String apiLink= API_LINK+"notaire/notaire.php";
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        RequestParams params = new RequestParams();

        params.put("params", "ok");
        params.put("com", objCom.getId_commune());

        client.post(apiLink, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //Object-->(response) --> JSonArray --> Object
                JSONObject json = response;
                try {
                    JSONArray array = json.getJSONArray("response");

                    notaireadapter.addAll(Notaire.fromJSONArray(json.getJSONArray("response")));
                    Log.d("DEBUG APP: ", Liste_notaire.toString());
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