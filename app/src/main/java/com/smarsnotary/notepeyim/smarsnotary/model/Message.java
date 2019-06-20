package com.smarsnotary.notepeyim.smarsnotary.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {

    private String key;
    private Boolean status;
    private String id_message;
    private String from_user;
    private String to_user;
    private String c_message;
    private String date_message;

    public Message(String key, Boolean status, String id_message, String from_user, String to_user, String c_message, String date_message) {
        this.key = key;
        this.status = status;
        this.id_message = id_message;
        this.from_user = from_user;
        this.to_user = to_user;
        this.c_message = c_message;
        this.date_message = date_message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getId_message() {
        return id_message;
    }

    public void setId_message(String id_message) {
        this.id_message = id_message;
    }

    public String getFrom_user() {
        return from_user;
    }

    public void setFrom_user(String from_user) {
        this.from_user = from_user;
    }

    public String getTo_user() {
        return to_user;
    }

    public void setTo_user(String to_user) {
        this.to_user = to_user;
    }

    public String getC_message() {
        return c_message;
    }

    public void setC_message(String c_message) {
        this.c_message = c_message;
    }

    public String getDate_message() {
        return date_message;
    }

    public void setDate_message(String date_message) {
        this.date_message = date_message;
    }

    public Message(JSONObject jsonObject) throws JSONException {
        this.key = jsonObject.getString("key");
        this.status = jsonObject.getBoolean("status");
        this.id_message = jsonObject.getString("id_message");
        this.from_user = jsonObject.getString("from_user");
        this.to_user = jsonObject.getString("to_user");
        this.c_message = jsonObject.getString("c_message");
        this.date_message = jsonObject.getString("date_message");
    }

    public static ArrayList<Message> fromJSONArray(JSONArray array) {
        ArrayList<Message> messages = new ArrayList<>();
        for (int x = 0; x < array.length(); x++){
            try {
                messages.add (new Message(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return messages;
    }


}