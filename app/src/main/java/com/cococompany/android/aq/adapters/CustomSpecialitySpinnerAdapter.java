package com.cococompany.android.aq.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.Faculty;
import com.cococompany.android.aq.models.Speciality;

import java.util.List;

/**
 * Created by alexandr on 24.11.16.
 */

public class CustomSpecialitySpinnerAdapter extends ArrayAdapter<Speciality> {

    private List<Speciality> itemList;

    LayoutInflater inflator;

    public CustomSpecialitySpinnerAdapter(Context context, int textViewResourceId, List<Speciality> itemList)
    {
        super(context, textViewResourceId, itemList);

        inflator = LayoutInflater.from(context);
        this.itemList=itemList;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflator.inflate(R.layout.spinner_specialities_rows, parent, false);

        Speciality speciality = itemList.get(position);
        TextView label = (TextView) row.findViewById(R.id.tvSpinnerSpecialityItem);

        if (position == 0) {
            label.setText("Please select speciality");
        }
        else {
            label.setText(speciality.getName());
        }

        return row;
    }



}
