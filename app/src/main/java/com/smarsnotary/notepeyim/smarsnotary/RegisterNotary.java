package com.smarsnotary.notepeyim.smarsnotary;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.smarsnotary.notepeyim.smarsnotary.adapter.CommuneAdapter;
import com.smarsnotary.notepeyim.smarsnotary.model.Commune;
import com.smarsnotary.notepeyim.smarsnotary.utils.GPSTracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

import static com.smarsnotary.notepeyim.smarsnotary.utils.settings.API_LINK;


public class RegisterNotary extends AppCompatActivity{

    private EditText firstnamenotaires;
    private EditText lastnamenotaires;
    private EditText nifnotaires;
    private EditText adressecabinets;
    private static EditText datecommissions;
    private EditText idemail;
    private EditText idtelephone;
    private EditText idpassword;
    private EditText idconfirmpassword;
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
        setContentView(com.smarsnotary.notepeyim.smarsnotary.R.layout.activity_register_notary);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        toolbar.setTitle("NotèPeyim");
        setSupportActionBar(toolbar);
        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressDialogN = new ProgressDialog(RegisterNotary.this);
        loadingN=(ProgressBar)findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.loading);
        firstnamenotaires=(EditText)findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.idfirstnamenotaire);
        lastnamenotaires=(EditText)findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.idlastnamenotaire);
        nifnotaires=(EditText)findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.idnifnotaire);
        datecommissions=(EditText)findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.iddatecommission);
        btnregisternotaire=(Button)findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.idbtnRegisternotaire);
        adressecabinets=(EditText)findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.idadressnotaire);
        idemail=(EditText)findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.idemail);
        idtelephone=(EditText)findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.idtelephone);
        idpassword=(EditText)findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.idpassword);
        idconfirmpassword=(EditText)findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.idconfirmpassword);
        spCommune=(Spinner)findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.spCommune);


        datecommissions.setInputType(InputType.TYPE_NULL);
        datecommissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogfragment = new DatePickerDialogTheme1();

                dialogfragment.show(getFragmentManager(), "Theme 1");
            }
        });


        /*try {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }*/

        grantedAccessFineLocation();

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

    private void grantedAccessFineLocation() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                mPermission)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterNotary.this,
                    mPermission)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(RegisterNotary.this,
                        new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    grantedAccessFineLocation();
                    // functionality that depends on this permission.
                }
                return;
            }
        }
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

    public static class DatePickerDialogTheme1 extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_DEVICE_DEFAULT_DARK,this,year,month,day);

            return datepickerdialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){

            datecommissions.setText(year+"-"+(month+1)+ "-" + day);

        }
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


        String apiLink= API_LINK+"register/register.php";
        AsyncHttpClient client = new AsyncHttpClient();
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

        String apiLink= API_LINK+"commune/commune.php";
        communeArrayList = new ArrayList<>();
        communeArrayList.clear();
        Resources res= getResources();
        communeAdapter = new CommuneAdapter(getApplicationContext(), com.smarsnotary.notepeyim.smarsnotary.R.layout.item_commune, communeArrayList, res);
        spCommune.setAdapter(communeAdapter);

        AsyncHttpClient client = new AsyncHttpClient();
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

