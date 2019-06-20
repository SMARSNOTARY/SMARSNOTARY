package com.smarsnotary.notepeyim.smarsnotary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.smarsnotary.notepeyim.smarsnotary.R;
import com.smarsnotary.notepeyim.smarsnotary.model.Meeting;

import java.util.ArrayList;

/**
 * Created by Sandyna Sandaire on 20/08/2018.
 */

public class MeetingAdapter extends ArrayAdapter<Meeting> {

    public MeetingAdapter(Context context, ArrayList<Meeting> Departements) {

        super(context, android.R.layout.simple_list_item_1, Departements);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the Showbiz
        final Meeting meeting = getItem(position);

        // find or inflate the template
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_meetings, parent, false);
        }

        TextView tvSubject = convertView.findViewById(R.id.tvSubject);
        tvSubject.setText(meeting.getSujet_motif());

        TextView tvMotif = convertView.findViewById(R.id.tvMotif);
        tvMotif.setText(meeting.getMotif_description());

        TextView tvNomNotaire = convertView.findViewById(R.id.tvNomNotaire);
        tvNomNotaire.setText(meeting.getNom_notairecomplet());

        TextView tvDate = convertView.findViewById(R.id.tvDate);
        tvDate.setText(meeting.getDate_rendez_vous());

        ImageButton ibStatus = convertView.findViewById(R.id.ibStatus);
        //ACCEPTER //DECLINER //NON-DISPONIBLE //ATTENTE
        if(meeting.getMeeting_status().equals("ATTENTE")){
            ibStatus.setImageResource(R.drawable.ic_wait_event);
        }else if(meeting.getMeeting_status().equals("ACCEPTER")){
            ibStatus.setImageResource(R.drawable.ic_event_accept);
        }else if(meeting.getMeeting_status().equals("DECLINER")){
            ibStatus.setImageResource(R.drawable.ic_cancel_event);
        }else{
            ibStatus.setImageResource(R.drawable.ic_edit_calendar);
        }
        //ic_cancel_event ic_event_accept ic_wait_event

        return convertView;
    }
}




