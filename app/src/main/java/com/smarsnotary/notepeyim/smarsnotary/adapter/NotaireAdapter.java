package com.smarsnotary.notepeyim.smarsnotary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smarsnotary.notepeyim.smarsnotary.R;
import com.smarsnotary.notepeyim.smarsnotary.model.Notaire;

import java.util.ArrayList;

/**
 * Created by Sandyna Sandaire on 16/08/2018.
 */

public class NotaireAdapter extends ArrayAdapter<Notaire> {

    public NotaireAdapter(Context context, ArrayList<Notaire> Notaires) {

        super(context, android.R.layout.simple_list_item_1, Notaires);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the Showbiz
        final Notaire notaire = getItem(position);

        // find or inflate the template
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_notaire, parent, false);
        }

        TextView tvNomNotaire = convertView.findViewById(R.id.tvNomNotaire);
        tvNomNotaire.setText(notaire.getNom_notaie());

        TextView tvNomCommune = convertView.findViewById(R.id.tvNomCommune);
        tvNomCommune.setText(notaire.getDesc_commune());

        ImageView ivNotaire = convertView.findViewById(R.id.ivNotaire);
        Glide.with(getContext())
                .load("https://cdn.pixabay.com/photo/2017/02/23/13/05/profile-2092113_960_720.png")
                .into(ivNotaire);

        return convertView;
    }
}



