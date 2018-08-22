package com.example.sandynasandaire.smarsnotary.model;

import com.example.sandynasandaire.smarsnotary.ListeDepartementActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sandyna Sandaire on 20/08/2018.
 */

public class Departement implements Serializable {

    private String key;
    private String id_departement;
    private String desc_departement;


    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc_departement() {
        return desc_departement;
    }
    public void setDesc_departement(String desc_department) {this.desc_departement = desc_departement;
    }

    public String getId_departement() {
        return id_departement;
    }

    public void setId_departement(String id_departement) {
        this.id_departement = id_departement;
    }

    public Departement(JSONObject jsonObject) throws JSONException {
        this.key = jsonObject.getString("key");
        this.desc_departement = jsonObject.getString("desc_department");
        this.id_departement = jsonObject.getString("id_department");
    }

    public static ArrayList<Departement> fromJSONArray(JSONArray array) {
        ArrayList<Departement> departements = new ArrayList<>();
        for (int x = 0; x < array.length(); x++){
            try {
                departements.add (new Departement(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return departements;
    }
}

