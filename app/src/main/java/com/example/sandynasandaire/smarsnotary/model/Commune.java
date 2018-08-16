package com.example.sandynasandaire.smarsnotary.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sandyna Sandaire on 15/08/2018.
 */


public class Commune implements Serializable {

    private String key;
    private String id_commune;
    private String desc_department;
    private String desc_Commune;
    private String deptID;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc_Commune() {
        return desc_Commune;
    }

    public void setDesc_Commune(String desc_Commune) {
        this.desc_Commune = desc_Commune;
    }

    public String getDeptID() {
        return deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public String getDesc_department() {
        return desc_department;
    }

    public void setDesc_department(String desc_department) {
        this.desc_department = desc_department;
    }

    public String getId_commune() {
        return id_commune;
    }

    public void setId_commune(String id_commune) {
        this.id_commune = id_commune;
    }

    public Commune(JSONObject jsonObject) throws JSONException {
        this.key = jsonObject.getString("key");
        this.desc_Commune = jsonObject.getString("desc_commune");
        this.desc_department = jsonObject.getString("desc_department");
        this.id_commune = jsonObject.getString("id_commune");
        this.deptID = jsonObject.getString("deptID");
    }

    public static ArrayList<Commune> fromJSONArray(JSONArray array) {
        ArrayList<Commune> Communes = new ArrayList<>();
        for (int x = 0; x < array.length(); x++){
            try {
                Communes.add (new Commune(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return Communes;
    }
}