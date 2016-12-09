package com.cococompany.android.aq.utils;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.Question;
import com.cococompany.android.aq.services.QuestionService;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Valentin on 30.10.2016.
 */
public class UIutils {

    private static ArrayList<Question> foundQuestions;

    //Метод налаштування тулбару
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

    //Метод налаштування поля пошуку
    public static void setSearchBar(int id, Menu menu, Activity activity) {
        final QuestionService questionService = new QuestionService(activity);

        SearchManager searchManager = (SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(id).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                foundQuestions = new ArrayList<Question>();
                foundQuestions = questionService.getQuestionsByTitle(query);

                if (foundQuestions != null && foundQuestions.size() > 0)
                    return true;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    //Метод налаштування тулбару кнопкою Назад
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

    //Метод перевірки валідності e-mail адреси
    public  static boolean isValidEmail(String email){
        email = email.trim();
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher m = pattern.matcher(email);
        return m.matches();
    }

    //Метод перевірки валідності повного імені користувача
    public static boolean isValidName(String name){
        name = name.trim();
        Pattern p = Pattern.compile("^\\w+\\s\\w+\\s\\w+");
        Matcher m = p.matcher(name);
        return m.matches();
    }
}
