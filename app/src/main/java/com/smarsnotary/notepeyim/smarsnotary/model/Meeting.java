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
    private  String meeting_status;
    private  String adresse_notairecabinet;
    private  String nom_notairecomplet;

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

    public String getAdresse_notairecabinet() {
        return adresse_notairecabinet;
    }

    public void setAdresse_notairecabinet(String adresse_notairecabinet) {
        this.adresse_notairecabinet = adresse_notairecabinet;
    }

    public String getNom_notairecomplet() {
        return nom_notairecomplet;
    }

    public void setNom_notairecomplet(String nom_notairecomplet) {
        this.nom_notairecomplet = nom_notairecomplet;
    }

    public String getMeeting_status() {
        return meeting_status;
    }

    public void setMeeting_status(String meeting_status) {
        this.meeting_status = meeting_status;
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
        this.meeting_status = jsonObject.getString("meeting_status");
        this.adresse_notairecabinet = jsonObject.getString("adresse_notairecabinet");
        this.nom_notairecomplet = jsonObject.getString("nom_notairecomplet");
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

