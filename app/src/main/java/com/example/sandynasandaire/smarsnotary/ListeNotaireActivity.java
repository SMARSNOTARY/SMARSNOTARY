package com.example.sandynasandaire.smarsnotary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.sandynasandaire.smarsnotary.adapter.CommuneAdapter;
import com.example.sandynasandaire.smarsnotary.adapter.NotaireAdapter;
import com.example.sandynasandaire.smarsnotary.model.Commune;
import com.example.sandynasandaire.smarsnotary.model.Notaire;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListeNotaireActivity extends AppCompatActivity {

    private ArrayList<Notaire> Liste_notaire;
    private NotaireAdapter notaireadapter;
    GridView gvNotaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_notaire);

        gvNotaire = findViewById(R.id.gvNotaire);
        gvNotaire.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Notaire objNotaire = (Notaire) gvNotaire.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(), ListeNotaireActivity.class);
                intent.putExtra("selected_notaire", objNotaire);
                startActivity(intent);
            }
        });

           // if(getIntent().getSerializableExtra("selected_commune") != null){
             //  Commune selectedCommune = (Commune) getIntent().getSerializableExtra("selected_commune");
             //    Toast.makeText(getApplicationContext(), "SL COM : "+selectedCommune.getId_commune(),Toast.LENGTH_LONG).show();
        // }
        get_notaire();
    }


    private void get_notaire() {
        Liste_notaire = new ArrayList<>();
        notaireadapter = new NotaireAdapter(getApplicationContext(),Liste_notaire );
        gvNotaire.setAdapter(notaireadapter);

        String apiLink= "https://simenonline.com/SMARSNOTARY/notaire/notaire.php";
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);

        client.post(apiLink, new JsonHttpResponseHandler(){

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

//  @Override
//  protected void onCreate(Bundle savedInstanceState) {
  //      super.onCreate(savedInstanceState);
  //      setContentView(R.layout.activity_liste_notaire);
  //      if(getIntent().getSerializableExtra("selected_commune") != null){
   //         Commune selectedCommune = (Commune) getIntent().getSerializableExtra("selected_commune");
  //          Toast.makeText(getApplicationContext(), "SL COM : "+selectedCommune.getId_commune(),Toast.LENGTH_LONG).show();
     //   }
  //  }
//}
