package com.cococompany.android.aq.utils;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.cococompany.android.aq.R;

/**
 * Created by Valentin on 30.10.2016.
 */
public class UIutils {
    public static void  setToolbar(int id, AppCompatActivity activity){
        Toolbar toolbar = (Toolbar) activity.findViewById(id);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        Drawable logo = activity.getDrawable(R.mipmap.aq_logo);
        toolbar.setLogo(logo);
    }

    public static void setSearchBar(int id, Menu menu, Activity activity){
        SearchManager searchManager =(SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =(SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.getComponentName()));

    }
}
