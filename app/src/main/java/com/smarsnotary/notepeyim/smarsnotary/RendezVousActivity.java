package com.smarsnotary.notepeyim.smarsnotary;

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
import android.widget.TextView;

import com.smarsnotary.notepeyim.smarsnotary.adapter.MeetingAdapter;
import com.smarsnotary.notepeyim.smarsnotary.model.Meeting;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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

    private SwipeRefreshLayout swipeRefresh;
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

        sharedPreferences = getApplicationContext().getSharedPreferences("LOGIN_USER_INFO", Context.MODE_PRIVATE);


        lvMeeting = findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.lvMeeting);
        lvMeeting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!sharedPreferences.getString("type_User", null).equals("CLIENT")){
                    Meeting objmeeting = (Meeting) lvMeeting.getItemAtPosition(i);
                    alert_valid_meeting("Rendez-vous", "Confirmer ce meeting....", objmeeting);
                }
            }
        });

        get_meeting();

        swipeRefresh=(SwipeRefreshLayout) findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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

        String apiLink= API_LINK+"meeting/meeting.php";
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        RequestParams params = new RequestParams();

        params.put("type_user", sharedPreferences.getString("type_User", null));
        params.put("clientUserID", sharedPreferences.getString("iduser", null));
        client.post(apiLink, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //Object-->(response) --> JSonArray --> Object
                JSONObject json = response;
                try {
                    JSONArray array = json.getJSONArray("response");

                    meetingAdapter.addAll(Meeting.fromJSONArray(json.getJSONArray("response")));

                    Log.d("DEBUG APP: ", Liste_meeting.toString());
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

    private void alert_valid_meeting(String title, String message, Meeting objmeeting) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(RendezVousActivity.this);

        LayoutInflater inflater= getLayoutInflater();
        View layout=inflater.inflate(com.smarsnotary.notepeyim.smarsnotary.R.layout.alert_dialog_validate_meeting, null);
        dialog.setView(layout);

        TextView tvMotifMeet = layout.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvMotifMeet);
        tvMotifMeet.setText(objmeeting.getMotif_description());

        TextView tvSubjMet = layout.findViewById(com.smarsnotary.notepeyim.smarsnotary.R.id.tvSubjMet);
        tvSubjMet.setText(objmeeting.getSujet_motif());


        dialog.setTitle( title )
                .setIcon(android.R.drawable.ic_input_add)
                .setMessage(message)
                .setPositiveButton("Enregistrer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {

                    }
                });

        dialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
