package com.example.sandynasandaire.smarsnotary.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sandynasandaire.smarsnotary.R;
import com.example.sandynasandaire.smarsnotary.model.Commune;
import java.util.ArrayList;

/**
 * Created by Sandyna Sandaire on 15/08/2018.
 */


public class CommuneAdapter extends ArrayAdapter<Commune> {
    public CommuneAdapter(Context context, int item_commune, ArrayList<Commune> Communes, Resources res) {
        super(context, android.R.layout.simple_list_item_1, Communes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the Showbiz
        final Commune Commune = getItem(position);

        // find or inflate the template
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_commune, parent, false);
        }

       TextView tvCommune = convertView.findViewById(R.id.tvNomCommune);
       tvCommune.setText(Commune.getDesc_Commune());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}