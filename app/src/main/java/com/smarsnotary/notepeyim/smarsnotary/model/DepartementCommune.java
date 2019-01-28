package com.smarsnotary.notepeyim.smarsnotary.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sandyna Sandaire on 21/08/2018.
 */

public class DepartementCommune implements Serializable {

    private String key;
    private String id_commune;
    private String desc_Commune;

    public void setKey(String key) {
        this.key = key;
    }

    public void setId_commune(String id_commune) {
        this.id_commune = id_commune;
    }

    public void setDesc_Commune(String desc_Commune) {
        this.desc_Commune = desc_Commune;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public void setDesc_departement(String desc_departement) {
        this.desc_departement = desc_departement;
    }

    private String deptID;

    public String getKey() {
        return key;
    }

    public String getId_commune() {
        return id_commune;
    }

    public String getDesc_Commune() {
        return desc_Commune;
    }

    public String getDeptID() {
        return deptID;
    }

    public String getDesc_departement() {
        return desc_departement;
    }

    private String desc_departement;


    public DepartementCommune(JSONObject jsonObject) throws JSONException {
        this.key = jsonObject.getString("key");
        this.desc_Commune = jsonObject.getString("desc_Commune");
        this.id_commune = jsonObject.getString("id_commune");
        this.deptID = jsonObject.getString("id_commune");
        this.desc_departement = jsonObject.getString("id_commune");

    }

    public static ArrayList<DepartementCommune> fromJSONArray(JSONArray array) {
        ArrayList<DepartementCommune> departementcommunes = new ArrayList<>();
        for (int x = 0; x < array.length(); x++){
            try {
                departementcommunes.add (new DepartementCommune(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return departementcommunes;
    }

}
