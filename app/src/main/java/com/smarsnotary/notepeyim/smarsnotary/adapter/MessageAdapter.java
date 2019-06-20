package com.smarsnotary.notepeyim.smarsnotary.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smarsnotary.notepeyim.smarsnotary.R;
import com.smarsnotary.notepeyim.smarsnotary.model.Message;

import java.util.ArrayList;

/**
 * Created by Sandyna Sandaire on 21/08/2018.
 */

public class MessageAdapter extends ArrayAdapter<Message> {
    SharedPreferences sharedPreferences ;
    public MessageAdapter(Context context, ArrayList<Message> messages) {
        super(context, android.R.layout.simple_list_item_1, messages);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // get the Showbiz
        final Message message = getItem(position);

        // find or inflate the template
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_message, parent, false);
        }

        LinearLayout lnSend = convertView.findViewById(R.id.lnSend);
        LinearLayout lnReceive = convertView.findViewById(R.id.lnReceive);

        sharedPreferences = getContext().getSharedPreferences("LOGIN_USER_INFO", Context.MODE_PRIVATE);
        String idME = sharedPreferences.getString("iduser", null);
        if(idME != null){
            if(idME==message.getFrom_user() || idME==message.getTo_user()){
                lnSend.setVisibility(View.VISIBLE);
                TextView tvSendMessage = convertView.findViewById(R.id.tvSendMessage);
                tvSendMessage.setText(message.getC_message());
            }else{
                lnReceive.setVisibility(View.VISIBLE);
                TextView tvReceiveMessage = convertView.findViewById(R.id.tvReceiveMessage);
                tvReceiveMessage.setText(message.getC_message());
            }
        }

        return convertView;
    }
}