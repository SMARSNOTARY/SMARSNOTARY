package com.smarsnotary.notepeyim.smarsnotary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.smarsnotary.notepeyim.smarsnotary.R;
import com.smarsnotary.notepeyim.smarsnotary.model.Departement;

import java.util.ArrayList;

import static com.smarsnotary.notepeyim.smarsnotary.utils.settings.IMAGE_LINK;

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
        final Departement departement = getItem(position);

        // find or inflate the template
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_departement, parent, false);
        }

        TextView tvDepartement = convertView.findViewById(R.id.tvNomDepartement);
        tvDepartement.setText(departement.getDesc_departement());

        System.out.println("LINK IMG :>" +IMAGE_LINK+departement.getImg_dept());
        ImageView ivDepartement_commune = convertView.findViewById(R.id.ivDepartement);

        Glide.with(getContext())
                .load(IMAGE_LINK+departement.getImg_dept())
                .apply(new RequestOptions()
                        .fitCenter()
                        .placeholder(R.drawable.backimage)
                        .error(R.drawable.backimage)
                )
                .into(ivDepartement_commune);

        ivDepartement_commune.setScaleType(ImageView.ScaleType.FIT_XY);
        return convertView;
    }
}




