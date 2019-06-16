package com.smarsnotary.notepeyim.smarsnotary;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

import static com.smarsnotary.notepeyim.smarsnotary.utils.settings.API_LINK;

public class RegisterClient extends AppCompatActivity {
    private EditText firstnameclients;
    private EditText lastnameclients;
    private EditText emailclients;
    private EditText passwordclients;
    private EditText confirmpassclients;
    private EditText telclients;
    private static EditText datedenaissance;
    private Button btnregisterclients;
    private ProgressBar loading;
    ProgressDialog progressDialog;
    private static String URL_REGISTER="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.smarsnotary.notepeyim.smarsnotary.R.layout.activity_register_client);

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


        loading=(ProgressBar)findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.loading);
        firstnameclients=(EditText)findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.idfirstnameclients);
        lastnameclients=(EditText)findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.idlastnameclients);
        emailclients=(EditText)findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.idemailclients);
        passwordclients=(EditText)findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.idpasswordclients);
        confirmpassclients=(EditText)findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.idconfirmpasswordclients);
        btnregisterclients=(Button)findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.idbtnRegisterclients);
        telclients=(EditText)findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.etTelephone);
        datedenaissance=(EditText)findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.etDtNaissance);
        progressDialog =new ProgressDialog(RegisterClient.this);

        datedenaissance.setInputType(InputType.TYPE_NULL);
        datedenaissance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogfragment = new DatePickerDialogTheme1();

                dialogfragment.show(getFragmentManager(), "Theme 1");
            }
        });

        btnregisterclients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(firstnameclients.getText().toString().trim()) || TextUtils.isEmpty(telclients.getText().toString().trim()) || TextUtils.isEmpty(datedenaissance.getText().toString().trim()) || TextUtils.isEmpty(confirmpassclients.getText().toString().trim()) || TextUtils.isEmpty(passwordclients.getText().toString().trim()) || TextUtils.isEmpty(emailclients.getText().toString().trim())){
                    firstnameclients.setError(" is empty");
                    lastnameclients.setError(" is empty");
                    telclients.setError(" is empty");
                    datedenaissance.setError(" is empty");
                    confirmpassclients.setError(" is empty");
                    passwordclients.setError(" is empty");
                    emailclients.setError(" is empty");
                }else{
                    if(passwordclients.getText().toString().trim().equals(confirmpassclients.getText().toString().trim())) {
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Enregistrement en cours...");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        Regist();
                    }else{
                        Toast.makeText(getApplicationContext(),"Mot de passe different de confirm password", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    private  void  Regist(){
        final  String emailclients=this.emailclients.getText().toString().trim();
        final String passwordclients=this.passwordclients.getText().toString().trim();
        final String telclients=this.telclients.getText().toString().trim();
        final String  datedenaissance=this.datedenaissance.getText().toString().trim();

        final String  firstnameclients=this.firstnameclients.getText().toString().trim();
        final String  lastnameclients=this.lastnameclients.getText().toString().trim();

            String apiLink = API_LINK+"register/register.php";
           // AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("type_user", "CLIENT");
            params.put("email", emailclients);
            params.put("telephone", telclients);
            params.put("password", passwordclients);
            params.put("date_naissance", datedenaissance);
            params.put("nom_client", firstnameclients);
            params.put("prenom_client", lastnameclients);

        client.post(apiLink, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject json = response;
                try {
                    JSONArray array = json.getJSONArray("response");
                    if(array.getJSONObject(0).getString("key").equals("SUCCESS")){
                        progressDialog.dismiss();
                        Toast.makeText(RegisterClient.this, "Compte enregistrer avec succes", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finishAffinity();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(RegisterClient.this, "Erreur, essayer a nouveau...", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(RegisterClient.this,"Reister Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                progressDialog.dismiss();
                Toast.makeText(RegisterClient.this,"Erreur de connexion....!", Toast.LENGTH_SHORT).show();
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

            datedenaissance.setText(year+"-"+ (month+1)+"-"+day);

        }
    }

}
