package com.smarsnotary.notepeyim.smarsnotary;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.smarsnotary.notepeyim.smarsnotary.adapter.MeetingAdapter;
import com.smarsnotary.notepeyim.smarsnotary.model.Meeting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.smarsnotary.notepeyim.smarsnotary.utils.settings.API_LINK;

public class RendezVousActivity extends AppCompatActivity {


    private ArrayList<Meeting> Liste_meeting;
    private MeetingAdapter meetingAdapter;
    ListView lvMeeting;
    SharedPreferences sharedPreferences ;
    ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefresh;
    AlertDialog show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.smarsnotary.notepeyim.smarsnotary.R.layout.activity_rendez_vous);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("Rendez-vous");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Toast.makeText(this, "This is why i do it....", Toast.LENGTH_SHORT).show();
        sharedPreferences = getApplicationContext().getSharedPreferences("LOGIN_USER_INFO", Context.MODE_PRIVATE);

        lvMeeting = findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.lvMeeting);
        lvMeeting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Meeting objmeeting = (Meeting) lvMeeting.getItemAtPosition(position);
                //Toast.makeText(RendezVousActivity.this, "123456788999988"+objmeeting.getMeeting_status(), Toast.LENGTH_SHORT).show();
                alert_valid_meeting("Rendez-vous", "Confirmer ce meeting....", objmeeting);
            }
        });

        progressDialog = new ProgressDialog(RendezVousActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Vérification rendez-vous en cours...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

        get_meeting();

        swipeRefresh=(SwipeRefreshLayout) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                progressDialog.show();
                get_meeting();
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

    private  void get_meeting() {
        Liste_meeting = new ArrayList<>();
        meetingAdapter = new MeetingAdapter(getApplicationContext(),Liste_meeting );
        lvMeeting.setAdapter(meetingAdapter);

        String id = sharedPreferences.getString("iduser", null);
        String type = sharedPreferences.getString("type_User", null);
        //String apiLink= API_LINK+"meeting/meeting.php?meet="+id+"&type="+type+"";
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        //RequestParams params = new RequestParams();

        //params.put("type_user", sharedPreferences.getString("type_User", null));
        //params.put("clientUserID", sharedPreferences.getString("iduser", null));
        client.get(API_LINK+"meeting/meeting.php?meet="+id+"&type="+type+"" , new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //Object-->(response) --> JSonArray --> Object
                JSONObject json = response;
                try {
                    JSONArray array = json.getJSONArray("response");

                    progressDialog.dismiss();
                    if(json.getJSONArray("response").getJSONObject(0).getBoolean("status")){
                        meetingAdapter.addAll(Meeting.fromJSONArray(json.getJSONArray("response")));
                    }else{
                        Toast.makeText(RendezVousActivity.this, "Aucun rendez-vous dans votre agenda.", Toast.LENGTH_SHORT).show();
                    }

                    Log.d("DEBUG APP: ", Liste_meeting.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RendezVousActivity.this, "Erreur pendant la vérification de votre agenda. [1.2]", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(RendezVousActivity.this, "Erreur pendant la vérification de votre agenda. [1.3]", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void alert_valid_meeting(String title, String message, final Meeting objmeeting) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(RendezVousActivity.this);

        LayoutInflater inflater= getLayoutInflater();
        View layout=inflater.inflate(com.smarsnotary.notepeyim.smarsnotary.R.layout.alert_dialog_validate_meeting, null);
        dialog.setView(layout);

        TextView tvMotifMeet = layout.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvMotifMeet);
        tvMotifMeet.setText(objmeeting.getMotif_description());

        TextView tvSubjMet = layout.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvSubjMet);
        tvSubjMet.setText(objmeeting.getSujet_motif());

        //LinearLayout lnChangeStat = layout.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.lnChangeStat);
        final Spinner spChangeStat = layout.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.spChangeStat);
        if(spChangeStat.getSelectedItem().toString().equals("ACCEPTER")){
            spChangeStat.setSelection(1);
        }else if(spChangeStat.getSelectedItem().toString().equals("DECLINER")){
            spChangeStat.setSelection(2);
        }else if(spChangeStat.getSelectedItem().toString().equals("ATTENTE")){
            spChangeStat.setSelection(3);
        }else if(spChangeStat.getSelectedItem().toString().equals("NON-DISPONIBLE")){
            spChangeStat.setSelection(4);
        }else{
            spChangeStat.setSelection(0);
        }


        if(!sharedPreferences.getString("type_User", null).equals("CLIENT")){
            spChangeStat.setVisibility(View.VISIBLE);
        }else{
            spChangeStat.setVisibility(View.GONE);
        }

        dialog.setTitle( title )
                .setIcon(android.R.drawable.ic_input_add)
                .setMessage(message)
                .setPositiveButton("Enregistrer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                            if(!spChangeStat.getSelectedItem().toString().equals("Choisir status") && !spChangeStat.getSelectedItem().toString().equals(objmeeting.getMeeting_status())){
                                progressDialog.show();
                                save_update_meeting(show, objmeeting, spChangeStat.getSelectedItem().toString());
                            }
                    }
                });

        dialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        show = dialog.show();
    }

    private void save_update_meeting(final AlertDialog dialog, Meeting objmeeting, String newstat) {
        String apiLink = API_LINK+"meet/meet.php?meeting="+objmeeting.getIdrendez_vous()+"";
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        RequestParams params = new RequestParams();

        params.put("stat", newstat);
        client.post(apiLink, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject json = response;
                try {
                    JSONArray array = json.getJSONArray("response");
                    if(array.getJSONObject(0).getBoolean("status")){
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Mise à jour agenda réussi...", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }else{
                        Toast.makeText(getApplicationContext(), "Mise à jour agenda annulé, vérifier et éssayer à nouveau...", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Échec lors du mise à jour de votre agenda...[A-2]", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getApplicationContext(), "Échec lors du mise à jour de votre agenda...[A-1]", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }
}
