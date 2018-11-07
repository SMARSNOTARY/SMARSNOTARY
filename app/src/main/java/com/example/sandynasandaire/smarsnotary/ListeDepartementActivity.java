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

import com.example.sandynasandaire.smarsnotary.adapter.DepartementAdapter;
import com.example.sandynasandaire.smarsnotary.model.Departement;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.example.sandynasandaire.smarsnotary.utils.settings.API_LINK;

public class ListeDepartementActivity extends AppCompatActivity {


    private ArrayList<Departement> Liste_departement;
    private DepartementAdapter departementadapter;
    GridView gvDepartement;

    private SwipeRefreshLayout swipeRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_departement);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Département");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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

        swipeRefresh=(SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                get_departement();
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

    private  void get_departement() {
        Liste_departement = new ArrayList<>();
        departementadapter = new DepartementAdapter(getApplicationContext(),Liste_departement );
        gvDepartement.setAdapter(departementadapter);

        String apiLink= API_LINK+"departement/departement.php";
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
