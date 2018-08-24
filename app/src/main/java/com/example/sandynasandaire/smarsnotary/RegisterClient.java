package com.example.sandynasandaire.smarsnotary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sandynasandaire.smarsnotary.model.Commune;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class RegisterClient extends AppCompatActivity {
    private EditText firstnameclients,lastnameclients,emailclients, passwordclients, confirmpassclients,telclients, datedenaissance;
    private Button btnregisterclients;
    private ProgressBar loading;
    ProgressDialog progressDialog;
    private static String URL_REGISTER="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);

        loading=(ProgressBar)findViewById(R.id.loading);
        firstnameclients=(EditText)findViewById(R.id.idfirstnameclients);
        lastnameclients=(EditText)findViewById(R.id.idlastnameclients);
        emailclients=(EditText)findViewById(R.id.idemailclients);
        passwordclients=(EditText)findViewById(R.id.idpasswordclients);
        confirmpassclients=(EditText)findViewById(R.id.idconfirmpasswordclients);
        btnregisterclients=(Button)findViewById(R.id.idbtnRegisterclients);
        telclients=(EditText)findViewById(R.id.etTelephone);
        datedenaissance=(EditText)findViewById(R.id.etDtNaissance);

        btnregisterclients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(RegisterClient.this, "btn ok", Toast.LENGTH_SHORT).show();

                if (TextUtils.isEmpty(firstnameclients.getText().toString().trim())
                        || TextUtils.isEmpty(telclients.getText().toString().trim())
                        || TextUtils.isEmpty(datedenaissance.getText().toString().trim())
                        || TextUtils.isEmpty(confirmpassclients.getText().toString().trim())
                        || TextUtils.isEmpty(passwordclients.getText().toString().trim())
                        || TextUtils.isEmpty(emailclients.getText().toString().trim()))
                {
                    firstnameclients.setError(" is empty");
                    telclients.setError(" is empty");
                    datedenaissance.setError(" is empty");
                    confirmpassclients.setError(" is empty");
                    passwordclients.setError(" is empty");
                    emailclients.setError(" is empty");
                }
                else if(passwordclients.getText().toString().trim() == confirmpassclients.getText().toString().trim()){
                    progressDialog =new ProgressDialog(RegisterClient.this);
                     
                    Regist();
                }else{
                    Toast.makeText(getApplicationContext(),"Mote de passe different de conifrm password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private  void  Regist(){



        final  String emailclients=this.emailclients.getText().toString().trim();
        final String passwordclients=this.passwordclients.getText().toString().trim();
        final  String confirmpasswordclients=this.confirmpassclients.getText().toString().trim();
        final String telclients=this.telclients.getText().toString().trim();
        final String  datedenaissance=this.datedenaissance.getText().toString().trim();




            String apiLink = "https://simenonline.com/SMARSNOTARY/register/register.php";
            AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
            RequestParams params = new RequestParams();
            params.put("type_user", "CLIENT");
            params.put("email", emailclients);
            params.put("nom_client", firstnameclients);
            params.put("prenom_client", lastnameclients);
            params.put("password", passwordclients);
            params.put("password", confirmpasswordclients);
            params.put("date_naissance", datedenaissance);
            params.put("telephone", telclients);





        client.post(apiLink, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //Object-->(response) --> JSonArray --> Object
                JSONObject json = response;
                try {
                    JSONArray array = json.getJSONArray("response");
//                    Intent intent=new Intent(RegisterClient.this,Navigation_drawer.class);
//                    Toast.makeText(RegisterClient.this,"Reister succes!", Toast.LENGTH_SHORT).show();
                    if( json.getJSONArray("response").getJSONObject(0).getString("key") == "SUCCESS"){
                        progressDialog.dismiss();
                        Toast.makeText(RegisterClient.this, "Compte enregistrer avec succes", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
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

}
