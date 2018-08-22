package com.example.sandynasandaire.smarsnotary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sandynasandaire.smarsnotary.R;
import com.example.sandynasandaire.smarsnotary.model.Commune;
import com.example.sandynasandaire.smarsnotary.model.Departement;
import com.example.sandynasandaire.smarsnotary.model.Notaire;

import java.util.ArrayList;

/**
 * Created by Sandyna Sandaire on 20/08/2018.
 */

public class DepartementAdapter extends ArrayAdapter<Departement> {

    public DepartementAdapter(Context context, ArrayList<Departement> Departements) {

        super(context, android.R.layout.simple_list_item_1, Departements);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the Showbiz
        final Departement Departement = getItem(position);

        // find or inflate the template
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_departement, parent, false);
        }

        TextView tvDepartement = convertView.findViewById(R.id.tvNomDepartement);
        tvDepartement.setText(Departement.getDesc_departement());

        /*ImageView ivDepartement_commune = convertView.findViewById(R.id.ivDepartement_commune);
        Glide.with(getContext())
                .load("https://cdn.pixabay.com/photo/2017/02/23/13/05/profile-2092113_960_720.png")
                .into(ivDepartement_commune);*/

        return convertView;
    }
}




