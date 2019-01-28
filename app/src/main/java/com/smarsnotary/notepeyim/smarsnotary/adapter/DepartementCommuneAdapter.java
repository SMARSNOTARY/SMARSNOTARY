package com.smarsnotary.notepeyim.smarsnotary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smarsnotary.notepeyim.smarsnotary.R;
import com.smarsnotary.notepeyim.smarsnotary.model.DepartementCommune;

import java.util.ArrayList;

/**
 * Created by Sandyna Sandaire on 21/08/2018.
 */

public class DepartementCommuneAdapter extends ArrayAdapter<DepartementCommune> {

    public DepartementCommuneAdapter(Context context, ArrayList<DepartementCommune> DepartementComunes) {
        super(context, android.R.layout.simple_list_item_1, DepartementComunes);
    }



    public View getView(int position, View convertView, ViewGroup parent) {
        // get the Showbiz
        final DepartementCommune departementCommune = getItem(position);

        // find or inflate the template
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_departement_commune, parent, false);
        }


        ImageView ivDepartement_commune = convertView.findViewById(R.id.ivDepartement_commune);
        Glide.with(getContext())
                .load("https://cdn.pixabay.com/photo/2017/02/23/13/05/profile-2092113_960_720.png")
                .into(ivDepartement_commune);

        return convertView;
    }
}
