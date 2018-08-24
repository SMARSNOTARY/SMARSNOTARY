package com.example.sandynasandaire.smarsnotary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sandynasandaire.smarsnotary.adapter.CommuneAdapter;
import com.example.sandynasandaire.smarsnotary.model.Commune;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class RegisterNotary extends AppCompatActivity{

    private EditText firstnamenotaires,lastnamenotaires,nifnotaires, adressecabinets, communenotaires, datecommissions;
    private Button btnregisternotaire;
    private ProgressBar loadingN;
    ProgressDialog progressDialogN;
    Spinner spCommune;
    private static String URL_REGISTER="";
    String firstnamenotaire,lastnamenotaire,nifnotaire,datecommission,communenotaire,adressecabinet;
    CommuneAdapter communeAdapter;
    ArrayList<Commune> communeArrayList;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_notary);



        progressDialogN = new ProgressDialog(RegisterNotary.this);
        loadingN=(ProgressBar)findViewById(R.id.loading);
        firstnamenotaires=(EditText)findViewById(R.id.idfirstnamenotaire);
        lastnamenotaires=(EditText)findViewById(R.id.idlastnamenotaire);
        nifnotaires=(EditText)findViewById(R.id.idnifnotaire);
        communenotaires=(EditText)findViewById(R.id.idcomune);
        datecommissions=(EditText)findViewById(R.id.iddatecommission);
        btnregisternotaire=(Button)findViewById(R.id.idbtnRegisternotaire);
        adressecabinets=(EditText)findViewById(R.id.idadressnotaire);
        spCommune=(Spinner)findViewById(R.id.spCommune);
        loadDevise();
        btnregisternotaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
                progressDialogN.setIndeterminate(true);
                progressDialogN.setMessage("connexion en cours...");
                progressDialogN.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialogN.setCancelable(false);
                progressDialogN.show();
                Commune co = (Commune) spCommune.getSelectedItem();
                Toast.makeText(RegisterNotary.this, ""+co.getId_commune(), Toast.LENGTH_SHORT).show();

                /*  if (TextUtils.isEmpty(firstnamenotaires.getText().toString().trim())
                        || TextUtils.isEmpty(lastnamenotaires.getText().toString().trim())
                        || TextUtils.isEmpty(adressecabinets.getText().toString().trim())
                        || TextUtils.isEmpty(communenotaires.getText().toString().trim())
                        || TextUtils.isEmpty(datecommissions.getText().toString().trim())
                        || TextUtils.isEmpty(nifnotaires.getText().toString().trim()))
                {
                    firstnamenotaires.setError(" is empty");
                    lastnamenotaires.setError(" is empty");
                    adressecabinets.setError(" is empty");
                    communenotaires.setError(" is empty");
                    datecommissions.setError(" is empty");
                    nifnotaires.setError(" is empty");
                }
                else{
                    Regist();
                }*/

            }
        });






    }



    private  void  Regist(){
//        loading.setVisibility(View.VISIBLE);
//        btnregisterclients.setVisibility(View.GONE);


        firstnamenotaire=this.firstnamenotaires.getText().toString().trim();
        lastnamenotaire=this.lastnamenotaires.getText().toString().trim();
        nifnotaire=this.nifnotaires.getText().toString().trim();
        datecommission=this.datecommissions.getText().toString().trim();
        communenotaire=this.communenotaires.getText().toString().trim();
        adressecabinet=this.adressecabinets.getText().toString().trim();


        String apiLink= "https://simenonline.com/SMARSNOTARY/register/register.php";
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        RequestParams params = new RequestParams();
        params.put("type_user", "NOTAIRE");
        params.put("desc_commune", communenotaire);
        params.put("nom_notaie", firstnamenotaire );
        params.put("prenom_notaire",lastnamenotaire);
        params.put("nif_notaire",nifnotaire);
        params.put("adresse_cabinet",adressecabinet);
        params.put("date_commision", datecommission);





        client.post(apiLink, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //Object-->(response) --> JSonArray --> Object
                JSONObject json = response;
                try {
                    JSONArray array = json.getJSONArray("response");
//                    Intent intent=new Intent(RegisterNotary.this,Navigation_drawer.class);
//                    Toast.makeText(RegisterClient.this,"Reister succes!", Toast.LENGTH_SHORT).show();

                    Toast.makeText(RegisterNotary.this, "ok ok", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterNotary.this,"Reister Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(RegisterNotary.this, "not ok", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadDevise() {
        progressDialogN.setIndeterminate(true);
        progressDialogN.setMessage("Telechargement commune...");
        progressDialogN.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialogN.setCancelable(false);
        progressDialogN.show();

        String apiLink= "https://simenonline.com/SMARSNOTARY/commune/commune.php";
        communeArrayList = new ArrayList<>();
        communeArrayList.clear();
        Resources res= getResources();
        communeAdapter = new CommuneAdapter(getApplicationContext(), R.layout.item_commune, communeArrayList, res);
        spCommune.setAdapter(communeAdapter);

        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.get(apiLink, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //Object-->(response) --> JSonArray --> Object

                Log.d("RegisterNotary", response.toString());
                communeArrayList.clear();
                JSONObject json = response;
                try {
                    JSONArray array = json.getJSONArray("response");
                    communeArrayList.add(new Commune("0", "0","0","Choisir Commune","0"));
                    for(int i=0; i<array.length(); i++){
                        String key = array.getJSONObject(i).get("key").toString();
                        String id_commune = array.getJSONObject(i).get("id_commune").toString();
                        String desc_department = array.getJSONObject(i).get("desc_department").toString();
                        String desc_commune = array.getJSONObject(i).get("desc_commune").toString();
                        String deptID = array.getJSONObject(i).get("deptID").toString();
                        communeArrayList.add(new Commune(key, id_commune,desc_department, desc_commune, deptID));
                    }
                    Log.d("RegisterNotary", communeArrayList.toString());

                    communeAdapter.notifyDataSetChanged();
                    progressDialogN.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialogN.dismiss();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialogN.dismiss();
                Log.d("RegisterNotary", throwable.getMessage());

            }


        });

    }
}

