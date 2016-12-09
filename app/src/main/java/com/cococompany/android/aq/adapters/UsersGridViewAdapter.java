package com.cococompany.android.aq.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.Category;
import com.cococompany.android.aq.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UsersGridViewAdapter extends ArrayAdapter<User> {

    private Context context;
    private int layoutResourceId;
    private ArrayList<User> data = new ArrayList<User>();
    public List<Integer> selectedPositions;
    private ViewHolder holder;
    private LayoutInflater inflater;

    public UsersGridViewAdapter(Context context, int layoutResourceId, ArrayList<User> data) {
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
    public void setGridData(ArrayList<User> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {

            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.cg_user_item_text);
            holder.image = (ImageView) row.findViewById(R.id.cg_user_item_image);
            row.setTag(holder);
            row.setSelected(selectedPositions.contains(position));
        } else {
            holder = (ViewHolder) row.getTag();
        }

        User item = data.get(position);
        holder.imageTitle.setText(item.getLastName() + " " + item.getFirstName() + " " + item.getMiddleName());

        Picasso.with(context).load(item.getAvatar()).into(holder.image);
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
    public User getItem(int position) {
        return data.get(position);
    }

    public void addSelected(int position, View v) {
        selectedPositions.add(position);
//        Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.blue_selection, null);
//        holder.image.setForeground(drawable);
//        holder.image.setBackgroundColor(Color.BLUE);
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