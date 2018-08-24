package com.example.sandynasandaire.smarsnotary;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.test.mock.MockPackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sandynasandaire.smarsnotary.adapter.CommuneAdapter;
import com.example.sandynasandaire.smarsnotary.model.Commune;
import com.example.sandynasandaire.smarsnotary.utils.GPSTracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class RegisterNotary extends AppCompatActivity{

    private EditText firstnamenotaires,lastnamenotaires,nifnotaires, adressecabinets, datecommissions, idemail, idtelephone, idpassword,  idconfirmpassword;
    private Button btnregisternotaire;
    private ProgressBar loadingN;
    ProgressDialog progressDialogN;
    Spinner spCommune;
    private static String URL_REGISTER="";
    String firstnamenotaire,lastnamenotaire,nifnotaire,datecommission,adressecabinet, idemails, idtelephones, idpasswords;
    CommuneAdapter communeAdapter;
    ArrayList<Commune> communeArrayList;
    Double latitude, longitude;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_notary);

        progressDialogN = new ProgressDialog(RegisterNotary.this);
        loadingN=(ProgressBar)findViewById(R.id.loading);
        firstnamenotaires=(EditText)findViewById(R.id.idfirstnamenotaire);
        lastnamenotaires=(EditText)findViewById(R.id.idlastnamenotaire);
        nifnotaires=(EditText)findViewById(R.id.idnifnotaire);
        datecommissions=(EditText)findViewById(R.id.iddatecommission);
        btnregisternotaire=(Button)findViewById(R.id.idbtnRegisternotaire);
        adressecabinets=(EditText)findViewById(R.id.idadressnotaire);
        idemail=(EditText)findViewById(R.id.idemail);
        idtelephone=(EditText)findViewById(R.id.idtelephone);
        idpassword=(EditText)findViewById(R.id.idpassword);
        idconfirmpassword=(EditText)findViewById(R.id.idconfirmpassword);
        spCommune=(Spinner)findViewById(R.id.spCommune);


        try {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(RegisterNotary.this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadDevise();



        btnregisternotaire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,0);
                progressDialogN.setIndeterminate(true);
                progressDialogN.setMessage("connexion en cours...");
                progressDialogN.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialogN.setCancelable(false);
                Commune co = (Commune) spCommune.getSelectedItem();

                if (TextUtils.isEmpty(idpassword.getText().toString().trim()) || TextUtils.isEmpty(idconfirmpassword.getText().toString().trim()) || TextUtils.isEmpty(idemail.getText().toString().trim()) || TextUtils.isEmpty(idtelephone.getText().toString().trim()) || TextUtils.isEmpty(firstnamenotaires.getText().toString().trim()) || TextUtils.isEmpty(lastnamenotaires.getText().toString().trim()) || TextUtils.isEmpty(adressecabinets.getText().toString().trim()) || TextUtils.isEmpty(datecommissions.getText().toString().trim()) || TextUtils.isEmpty(nifnotaires.getText().toString().trim())) {
                    firstnamenotaires.setError(" is empty");
                    lastnamenotaires.setError(" is empty");
                    adressecabinets.setError(" is empty");
                    datecommissions.setError(" is empty");
                    nifnotaires.setError(" is empty");

                    idemail.setError(" is empty");
                    idtelephone.setError(" is empty");
                    idpassword.setError(" is empty");
                    idconfirmpassword.setError(" is empty");

                } else{
                    if(idpassword.getText().toString().equals(idconfirmpassword.getText().toString())){
                        if(co.getKey() != "0"){
                            progressDialogN.show();
                            Regist(co);
                        }else {
                            Toast.makeText(RegisterNotary.this, "Choisir commune avant de valider l'enregistrement...", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(RegisterNotary.this, "Mot de passe différent de confirm password...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });






    }



    private  void  Regist(Commune co){

        firstnamenotaire=this.firstnamenotaires.getText().toString().trim();
        lastnamenotaire=this.lastnamenotaires.getText().toString().trim();
        nifnotaire=this.nifnotaires.getText().toString().trim();
        datecommission=this.datecommissions.getText().toString().trim();
        adressecabinet=this.adressecabinets.getText().toString().trim();

        idemails=this.idemail.getText().toString().trim();
        idtelephones=this.idtelephone.getText().toString().trim();
        idpasswords=this.idpassword.getText().toString().trim();

        progressDialogN.setIndeterminate(true);
        progressDialogN.setMessage("Enregistrement en cours...");
        progressDialogN.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialogN.setCancelable(false);
        progressDialogN.show();


        String apiLink= "https://simenonline.com/SMARSNOTARY/register/register.php";
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        RequestParams params = new RequestParams();
        params.put("type_user", "NOTAIRE");
        params.put("desc_commune", co.getDesc_Commune());
        params.put("nom_notaie", firstnamenotaire );
        params.put("prenom_notaire",lastnamenotaire);
        params.put("nif_notaire",nifnotaire);
        params.put("adresse_cabinet",adressecabinet);
        params.put("communeID",co.getId_commune());
        params.put("date_commision", datecommission);
        params.put("long_adr", longitude);
        params.put("lat_adr", latitude);

        params.put("email", idemails);
        params.put("telephone", idtelephones);
        params.put("password", idpasswords);


        client.post(apiLink, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //Object-->(response) --> JSonArray --> Object
                JSONObject json = response;
                try {
                    JSONArray array = json.getJSONArray("response");
                    if(array.getJSONObject(0).getString("key").equals("SUCCESS")){

                        Toast.makeText(RegisterNotary.this, "Enregistrer avec succès...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finishAffinity();
                    }else{
                        Toast.makeText(RegisterNotary.this,"!"+response.toString(), Toast.LENGTH_SHORT).show();
                    }
                    progressDialogN.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterNotary.this,"Reister Error!", Toast.LENGTH_SHORT).show();
                    progressDialogN.dismiss();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(RegisterNotary.this, "not ok", Toast.LENGTH_SHORT).show();
                progressDialogN.dismiss();
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
                    progressDialogN.setMessage("Localisation en cours...");
                    find_phone_position();
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

    private void find_phone_position() {
        // create class object
        gps = new GPSTracker(getApplicationContext());

        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        progressDialogN.dismiss();
    }
}

