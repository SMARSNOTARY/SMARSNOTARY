package com.smarsnotary.notepeyim.smarsnotary.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sandyna Sandaire on 20/08/2018.
 */

public class Meeting implements Serializable {

    private String key;
    private String idrendez_vous;
    private String clientUserID;
    private  String notaireUserID;


    private  String sujet_motif;
    private  String motif_description;
    private  String date_rendez_vous;
    private  String date_created;

    public String getKey() {
        return key;
    }

    public String getIdrendez_vous() {
        return idrendez_vous;
    }

    public String getClientUserID() {
        return clientUserID;
    }

    public String getNotaireUserID() {
        return notaireUserID;
    }

    public String getSujet_motif() {
        return sujet_motif;
    }

    public String getMotif_description() {
        return motif_description;
    }

    public String getDate_rendez_vous() {
        return date_rendez_vous;
    }

    public String getDate_created() {
        return date_created;
    }

    public Meeting(JSONObject jsonObject) throws JSONException {


        this.key = jsonObject.getString("key");
        this.clientUserID = jsonObject.getString("clientUserID");
        this.notaireUserID = jsonObject.getString("notaireUserID");

        this.sujet_motif = jsonObject.getString("sujet_motif");

        this.motif_description = jsonObject.getString("motif_description");
        this.date_rendez_vous = jsonObject.getString("date_rendez_vous");
        this.date_created = jsonObject.getString("date_created");
        this.idrendez_vous = jsonObject.getString("idrendez_vous");

    }

    public static ArrayList<Meeting> fromJSONArray(JSONArray array) {
        ArrayList<Meeting> meetings = new ArrayList<>();
        for (int x = 0; x < array.length(); x++){
            try {
                meetings.add (new Meeting(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return meetings;
    }
}

