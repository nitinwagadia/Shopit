package com.cloudcomputing.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cloudcomputing.models.GenericModel;
import com.cloudcomputing.shopit.R;

import java.util.ArrayList;

/**
 * Created by Nitin on 12/11/2015.
 */
public class SpinnerAdapter extends ArrayAdapter<GenericModel> {

    LayoutInflater inflater;

    public SpinnerAdapter(Context context, int resource, ArrayList<GenericModel> data) {
        super(context, resource, data);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.spinner_item, parent, false);
        TextView textview = (TextView) view.findViewById(R.id.spinner_name);
        textview.setText(getItem(position).getmName());

        ImageView imageView = (ImageView) view.findViewById(R.id.spinner_image);
        imageView.setImageResource(getItem(position).getmId());

        return view;
    }


}
