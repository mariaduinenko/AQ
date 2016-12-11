package com.cococompany.android.aq.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v4.content.IntentCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.Category;
import com.cococompany.android.aq.models.ImageItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoriesGridViewAdapter extends ArrayAdapter<Category> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<Category> data = new ArrayList<Category>();
    public List<Integer> selectedPositions;
    private ViewHolder holder;
    private LayoutInflater inflater;

    public CategoriesGridViewAdapter(Context context, int layoutResourceId, ArrayList<Category> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.selectedPositions = new ArrayList<>();

        inflater = ((Activity) context).getLayoutInflater();
    }

    /**
     * Оновляє дані сітки та перезавантажує її елементи.
     * @param data
     */
    public void setGridData(ArrayList<Category> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {

            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.category_item_text);
            holder.image = (ImageView) row.findViewById(R.id.category_item_image);
            row.setTag(holder);
            row.setSelected(selectedPositions.contains(position));
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Category item = data.get(position);
        holder.imageTitle.setText(item.getName());

        Picasso.with(context).load(item.getImage().substring(0, item.getImage().lastIndexOf("."))).into(holder.image);
//        holder.image.setImageBitmap(item.getImage());
        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Nullable
    @Override
    public Category getItem(int position) {
        return data.get(position);
    }

    public void addSelected(int position, View v) {
        selectedPositions.add(position);
        v.setBackgroundColor(Color.LTGRAY);
        notifyDataSetChanged();
    }

    public void removeSelected(int position, View v) {
        selectedPositions.remove(position);
        v.setBackgroundColor(Color.WHITE);
        notifyDataSetChanged();
    }

    public void removeOther(int position, View v) {
        for (int i = 0; i < selectedPositions.size(); i++) {
            if (!selectedPositions.get(i).equals(position)) {
                ((GridView) v.getParent()).getChildAt(selectedPositions.get(i)).setBackgroundColor(Color.WHITE);
                selectedPositions.remove(i);
            }
        }
        notifyDataSetChanged();
    }

}