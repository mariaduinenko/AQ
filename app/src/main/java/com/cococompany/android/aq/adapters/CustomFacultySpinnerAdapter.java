package com.cococompany.android.aq.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.Faculty;
import com.cococompany.android.aq.models.University;

import java.util.List;

/**
 * Created by alexandr on 24.11.16.
 */

public class CustomFacultySpinnerAdapter extends ArrayAdapter<Faculty> {

    private List<Faculty> itemList;

    LayoutInflater inflator;

    public CustomFacultySpinnerAdapter(Context context, int textViewResourceId, List<Faculty> itemList)
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
        View row = inflator.inflate(R.layout.spinner_faculties_rows, parent, false);

        Faculty fac = itemList.get(position);
        TextView label = (TextView) row.findViewById(R.id.tvSpinnerFacultyItem);

        if (position == 0) {
            label.setText("Please select faculty");
        }
        else {
            label.setText(fac.getName());
        }

        return row;
    }



}
