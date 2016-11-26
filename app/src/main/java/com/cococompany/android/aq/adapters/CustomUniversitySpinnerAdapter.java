package com.cococompany.android.aq.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.University;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by alexandr on 24.11.16.
 */

public class CustomUniversitySpinnerAdapter extends ArrayAdapter<University> {

    private List<University> itemList;

    LayoutInflater inflator;

    public CustomUniversitySpinnerAdapter(Context context, int textViewResourceId, List<University> itemList)
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
        View row = inflator.inflate(R.layout.spinner_universities_rows, parent, false);

        University univ = itemList.get(position);
        TextView label = (TextView) row.findViewById(R.id.tvSpinnerUniversityItem);

        if (position == 0) {
            label.setText("Please select university");
        }
        else {
            label.setText(univ.getName());
        }

        return row;
    }



}
