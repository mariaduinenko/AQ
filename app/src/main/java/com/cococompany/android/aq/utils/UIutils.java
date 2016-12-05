package com.cococompany.android.aq.utils;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;

import com.cococompany.android.aq.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Valentin on 30.10.2016.
 */
public class UIutils {
    public static void  setToolbar(int id, AppCompatActivity activity){
        Toolbar toolbar = (Toolbar) activity.findViewById(id);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        Drawable logo = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            logo = activity.getDrawable(R.mipmap.aq_logo);
        }
        toolbar.setLogo(logo);
    }

    public static void setSearchBar(int id, Menu menu, Activity activity){
        SearchManager searchManager =(SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =(SearchView) menu.findItem(id).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.getComponentName()));

    }

    public static void setToolbarWithBackButton(int id, final AppCompatActivity activity){
        final Toolbar toolbar = (Toolbar) activity.findViewById(id);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onBackPressed();
            }
        });
    }

    public  static boolean isValidEmail(String email){
        email = email.trim();
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher m = pattern.matcher(email);
        return m.matches();
    }

    public static boolean isValidName(String name){
        name = name.trim();
        Pattern p = Pattern.compile("^\\w+\\s\\w+\\s\\w+");
        Matcher m = p.matcher(name);
        return m.matches();
    }
}
