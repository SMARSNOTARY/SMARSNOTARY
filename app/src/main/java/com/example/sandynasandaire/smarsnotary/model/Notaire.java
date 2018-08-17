package com.example.sandynasandaire.smarsnotary.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sandyna Sandaire on 16/08/2018.
 */

public class Notaire implements Serializable {
    private String key;
    private String id_notaire;
    private String nom_notaie;
    private String prenom_notaire;
    private String nif_notaire;
    private String adresse_cabinet;
    private String date_commision;
    private String long_adr;
    private String lat_adr;
    private String userNoID;
    private String date_creation;
    private String update_notaire;
    private String communeID;
    private String desc_commune;
    private String email;
    private String telephone;
    private String photo;
    private String created_user;
    private String updated_user;

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getId_notaire() {
        return id_notaire;
    }
    public void setId_notaire(String id_notaire) {
        this.id_notaire = id_notaire;
    }

    public String getNom_notaie() {
        return nom_notaie;
    }
    public void setNom_notaie(String nom_notaie) {
        this.nom_notaie = nom_notaie;
    }

    public String getPrenom_notaire() {return prenom_notaire;}
    public void setPrenom_notaire(String prenom_notaire) {this.nom_notaie = prenom_notaire;}


    public String getNif_notaire() {return nif_notaire; }
    public void setnif_notaire(String nif_notaire) {this.prenom_notaire = nif_notaire;}

    public String getAdresse_cabinet() {
        return adresse_cabinet;
    }
    public void setAdresse_cabinet(String adresse_cabinet) {this.adresse_cabinet = adresse_cabinet;}


    public String getDate_commision() {
        return date_commision;
    }
    public void setDate_commision(String date_commision) {this.date_commision = date_commision;}


    public String getLong_adre() {
        return long_adr;
    }
    public void setLong_adr(String long_adr) {this.long_adr = long_adr;}


    public String getLat_adr() {
        return lat_adr;
    }
    public void setLat_adr(String prenom_notaire) {this.lat_adr = lat_adr;}


    public String getUserNoID() {
        return userNoID;
    }
    public void setUserNoID(String userNoID) {this.userNoID = userNoID;}


    public String getDate_creation() {
        return date_creation;
    }
    public void setDate_creation(String date_creation) {this.date_creation = date_creation;}


    public String getUpdate_notaire() {
        return update_notaire;
    }
    public void setUpdate_notaire(String update_notaire) {this.update_notaire = update_notaire;}


    public String getCommuneID() {
        return communeID;
    }
    public void setCommuneID(String communeID) {this.communeID = communeID;}

    public String getDesc_commune() {
        return desc_commune;
    }
    public void setDesc_commune(String desc_commune) {this.desc_commune = desc_commune;}

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {this.email = email;}

    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {this.photo = photo;}

    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {this.telephone = telephone;}

    public String getCreated_user() {return created_user;}
    public void setCreated_user(String created_user) {this.created_user = created_user;}

    public String getUpdated_user() {return updated_user;}
    public void setUpdated_user(String updated_user) {this.updated_user = updated_user;}


    public Notaire(JSONObject jsonObject) throws JSONException {
        this.key = jsonObject.getString("key");
        this.id_notaire = jsonObject.getString("id_notaire");
        this.nom_notaie = jsonObject.getString("nom_notaie");
        this.prenom_notaire = jsonObject.getString("prenom_notaire");
        this.nif_notaire = jsonObject.getString("nif_notaire");
        this.adresse_cabinet = jsonObject.getString("adresse_cabinet");
        this.date_commision = jsonObject.getString("date_commision");
        this.long_adr = jsonObject.getString("long_adr");
        this.lat_adr = jsonObject.getString("lat_adr");
        this.userNoID = jsonObject.getString("userNoID");
        this.date_creation = jsonObject.getString("date_creation");
        this.update_notaire = jsonObject.getString("update_notaire");
        this.communeID = jsonObject.getString("communeID");
        this.desc_commune = jsonObject.getString("desc_commune");
        this.email = jsonObject.getString("email");
        this.telephone = jsonObject.getString("telephone");
        this.photo = jsonObject.getString("photo");
        this.created_user = jsonObject.getString("created_user");
        this.updated_user = jsonObject.getString("updated_user");

    }

    public static ArrayList<Notaire> fromJSONArray(JSONArray array) {
        ArrayList<Notaire> Notaires = new ArrayList<>();
        for (int x = 0; x < array.length(); x++){
            try {
                Notaires.add (new Notaire(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return Notaires;
    }


}

