package com.smarsnotary.notepeyim.smarsnotary.model;

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
    private  String img_dept;

    public String getKey() {
        return key;
    }

    public String getDesc_departement() {
        return desc_departement;
    }

    public String getId_departement() {
        return id_departement;
    }

    public String getImg_dept() {
        return img_dept;
    }

    public Departement(JSONObject jsonObject) throws JSONException {
        this.key = jsonObject.getString("key");
        this.desc_departement = jsonObject.getString("desc_department");
        this.id_departement = jsonObject.getString("id_department");
        this.img_dept = jsonObject.getString("img_dept");
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

