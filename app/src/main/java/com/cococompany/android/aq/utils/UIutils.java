package com.cococompany.android.aq.utils;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.graphics.drawable.Drawable;
import android.provider.BaseColumns;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import com.cococompany.android.aq.QuestionActivity;
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
    private static SimpleCursorAdapter mAdapter;

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
    public static void setSearchBar(int id, Menu menu, final Activity activity) {
        final QuestionService questionService = new QuestionService(activity);

        final String[] from = new String[] {"cityName"};
        final int[] to = new int[] {android.R.id.text1};
        mAdapter = new SimpleCursorAdapter(activity,
                android.R.layout.simple_list_item_1,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        SearchManager searchManager = (SearchManager) activity.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(id).getActionView();
        searchView.setSuggestionsAdapter(mAdapter);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {
                // Your code here
                Log.i("Suggestion click", "Current item: " + foundQuestions.get(position));
                Intent intent = new Intent(activity, QuestionActivity.class);
                intent.putExtra("question_id", foundQuestions.get(position).getId());
                activity.startActivity(intent);
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                // Your code here
                return true;
            }
        });
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                foundQuestions = new ArrayList<Question>();
                foundQuestions = questionService.getQuestionsByTitle(query);

                if (foundQuestions != null && foundQuestions.size() > 0) {
                    populateAdapter();
                    return true;
                } else {
                    foundQuestions = new ArrayList<Question>();
                }

                populateAdapter();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private static void populateAdapter() {
        final MatrixCursor c = new MatrixCursor(new String[]{ BaseColumns._ID, "cityName" });
        for (int i = 0; i < foundQuestions.size(); i++) {
            c.addRow(new Object[]{i, foundQuestions.get(i).getTitle()});
        }
        mAdapter.changeCursor(c);
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
