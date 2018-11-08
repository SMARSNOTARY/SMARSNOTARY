package com.example.sandynasandaire.smarsnotary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sandynasandaire.smarsnotary.R;
import com.example.sandynasandaire.smarsnotary.model.Meeting;

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

        return convertView;
    }
}




