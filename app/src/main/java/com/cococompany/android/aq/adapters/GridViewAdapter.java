package com.cococompany.android.aq.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.ImageItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<ImageItem> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<ImageItem> data = new ArrayList<ImageItem>();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<ImageItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    /**
     * Оновляє дані сітки та перезавантажує її елементи.
     * @param data
     */
    public void setGridData(ArrayList<ImageItem> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.category_item_text);
            holder.image = (ImageView) row.findViewById(R.id.category_item_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        ImageItem item = data.get(position);
        holder.imageTitle.setText(item.getTitle());

        Picasso.with(context).load(item.getImageUrl()).into(holder.image);
//        holder.image.setImageBitmap(item.getImage());
        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}