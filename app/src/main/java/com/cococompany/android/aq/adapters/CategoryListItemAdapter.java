package com.cococompany.android.aq.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.User;

import java.util.ArrayList;

/**
 * Created by alexandrmyagkiy on 08.12.16.
 */

public class CategoryListItemAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<User> objects;

    public CategoryListItemAdapter(Context context, ArrayList<User> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setObjects(ArrayList<User> objects) {
        this.objects = objects;
        notifyDataSetChanged();
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.category_list_item, parent, false);
        }

        User u = getUser(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) view.findViewById(R.id.cli_lbl_name)).setText(u.getFirstName() + " " + u.getMiddleName() + " " + u.getLastName());
        ((ImageView) view.findViewById(R.id.cli_img)).setImageResource(R.drawable.image_9);

        return view;
    }

    // товар по позиции
    User getUser(int position) {
        return ((User) getItem(position));
    }
}