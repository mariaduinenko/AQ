package com.cococompany.android.aq.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.Toast;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.adapters.CategoryListItemAdapter;
import com.cococompany.android.aq.adapters.GridViewAdapter;
import com.cococompany.android.aq.models.Category;
import com.cococompany.android.aq.models.ImageItem;
import com.cococompany.android.aq.models.User;
import com.cococompany.android.aq.utils.CategoryService;
import com.cococompany.android.aq.utils.UserService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by alexandrmyagkiy on 06.12.16.
 */

public class CategoriesChooseFragment extends DialogFragment {

    private static final String projectBaseUrl = "https://pure-mesa-13823.herokuapp.com";

    private ArrayList<Category> data;
    private ArrayList<User> users;
    private GridViewAdapter gridAdapter;
    private ProgressBar progressBar;
    private CategoryListItemAdapter listItemAdapter;
    private ListView lvMain;

    private String CATEGORIES_URL = projectBaseUrl + "/rest/categories";
    private String USERS_URL = projectBaseUrl + "/rest/users";

    private ArrayList<Category> selectedCategories;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        long initialTime = System.currentTimeMillis();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.activity_dialog_categories, null);

        TabHost host = (TabHost)view.findViewById(R.id.categories_tab_host);
        host.setup();

        //Вкладка №1
        TabHost.TabSpec spec = host.newTabSpec("Categories");
        spec.setContent(R.id.categories_choose_category);
        spec.setIndicator("Categories");
        host.addTab(spec);

        //Вкладка №2
        spec = host.newTabSpec("Users");
        spec.setContent(R.id.categories_choose_user);
        spec.setIndicator("Users");
        host.addTab(spec);

        data = new ArrayList<>();
        selectedCategories = new ArrayList<>();

        progressBar = (ProgressBar) view.findViewById(R.id.categories_progress_bar);

        //Додавання gridView до першої вкладки
        final GridView gridView = (GridView) view.findViewById(R.id.categories_grid_view);
        gridAdapter = null;
        gridAdapter = new GridViewAdapter(getContext(), R.layout.category_grid_item, data);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                int selectedIndex = gridAdapter.selectedPositions.indexOf(position);
                if (selectedIndex > -1) {
                    gridAdapter.removeSelected(selectedIndex, v);
                    selectedCategories.remove((ImageItem) parent.getItemAtPosition(position));
                } else {
                    for (Category i : selectedCategories) {
                        if (!data.get(position).getId().equals(i.getId()))
                            selectedCategories.remove(i);
                    }
                    gridAdapter.removeOther(position, v);
                    gridAdapter.addSelected(position, v);
                    selectedCategories.add((Category) parent.getItemAtPosition(position));
                }

            }
        });

        // створюємо адаптер для списку користувачів
        users = new ArrayList<>();
//        ArrayList<User> users = fillUsersData();
        listItemAdapter = new CategoryListItemAdapter(getContext(), users);

        // настраиваем список
        lvMain = (ListView) view.findViewById(R.id.categories_list);
        lvMain.setAdapter(listItemAdapter);

        //Start download
        new AsyncHttpTask().execute(CATEGORIES_URL);
        new UserListAsyncTask().execute(USERS_URL);
        progressBar.setVisibility(View.VISIBLE);

        builder.setView(view)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CategoriesChooseFragment.this.getDialog().cancel();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(),"Choosed categories: " + selectedCategories,Toast.LENGTH_LONG).show();
                    }
                });
        long finishTime = System.currentTimeMillis();
        Log.d(this.getClass().getName(), "Whole execution time:" + (finishTime-initialTime));
        return builder.create();
    }

    //Downloading data asynchronously
    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            try {
                // Create Apache HttpClient
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(params[0])
                        .build();

                Response httpResponse = null;

                try {
                    httpResponse = client.newCall(request).execute();
                    int statusCode = httpResponse.code();

                    // 200 represents HTTP OK
                    if (statusCode == 200) {
                        System.out.println("IN AsyncTask. code==200");
                        String response = httpResponse.body().string();
                        parseResult(response);
                        result = 1; // Successful
                    } else {
                        result = 0; //"Failed
                    }

//                    String jsonData = response.body().string();
//                    JSONArray array = new JSONArray(jsonData);
//                    ArrayList<Category> categories = new ArrayList<>();
//
//                    for (int i = 0; i < array.length(); i++) {
//                        JSONObject jsonObject = array.getJSONObject(i);
//                        Category category = new Category();
//                        category.setId(jsonObject.getLong("id"));
//                        if (jsonObject.has("name"))
//                            category.setName(jsonObject.getString("name"));
//                        if (jsonObject.has("image"))
//                            category.setImage(jsonObject.getString("image"));
//                        categories.add(category);
//                    }
//
//                    return categories;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Lets update UI

            if (result == 1) {
                System.out.println("IN POST EXECUTE. RESULT==1");
                gridAdapter.setGridData(data);
            } else {
                Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }

            //Hide progressbar
            progressBar.setVisibility(View.GONE);
        }
    }

    public class UserListAsyncTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            try {
                // Create Apache HttpClient
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(params[0])
                        .build();

                Response httpResponse = null;

                try {
                    httpResponse = client.newCall(request).execute();
                    int statusCode = httpResponse.code();

                    // 200 represents HTTP OK
                    if (statusCode == 200) {
                        System.out.println("IN UserListAsyncTask. code==200");
                        String response = httpResponse.body().string();
                        parseUserListResult(response);
                        result = 1; // Successful
                    } else {
                        result = 0; //"Failed
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Lets update UI

            if (result == 1) {
                System.out.println("IN POST UserListAsyncTask EXECUTE. RESULT==1");
                listItemAdapter.setObjects(users);
            } else {
                Toast.makeText(getActivity(), "Failed to fetch users!", Toast.LENGTH_SHORT).show();
            }

            //Hide progressbar
            progressBar.setVisibility(View.GONE);
        }
    }

    private void parseResult(String result) {
        try {
            JSONArray array = new JSONArray(result);
            Category item;
            for (int i = 0; i < array.length(); i++) {
                item = new Category();

                JSONObject jsonObject = array.getJSONObject(i);
                if (jsonObject.has("id"))
                    item.setId(jsonObject.getLong("id"));
                if (jsonObject.has("name")) {
                    item.setName(jsonObject.getString("name"));
                }
                if (jsonObject.has("image")) {
                    item.setImage(projectBaseUrl + "/rest/images/" + jsonObject.getString("image"));
                }
                data.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseUserListResult(String result) {
        try {
            JSONArray array = new JSONArray(result);
            ArrayList<User> usrs = new ArrayList<>();

            User item;
            for (int i = 0; i < array.length(); i++) {
                item = new User();

                JSONObject jsonObject = array.getJSONObject(i);
                item.setId(jsonObject.getLong("id"));
                if (jsonObject.has("creationTime")) {
                    item.setCreationTime(jsonObject.getString("creationTime"));
                }
                if (jsonObject.has("email")) {
                    item.setEmail(jsonObject.getString("email"));
                }
                if (jsonObject.has("firstName")) {
                    item.setFirstName(jsonObject.getString("firstName"));
                }
                if (jsonObject.has("lastName")) {
                    item.setLastName(jsonObject.getString("lastName"));
                }
                if (jsonObject.has("middleName")) {
                    item.setMiddleName(jsonObject.getString("middleName"));
                }
                if (jsonObject.has("nickname")) {
                    item.setNickname(jsonObject.getString("nickname"));
                }
                if (jsonObject.has("active")) {
                    item.setActive(jsonObject.getBoolean("active"));
                }
                if (jsonObject.has("birthdate")) {
                    item.setBirthdate(jsonObject.getString("birthdate"));
                }
                users.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Підготовка реальних даних для ListView
    private ArrayList<User> fillUsersData() {
        long initialTime = System.currentTimeMillis();
        ArrayList<User> users = new UserService().getActiveUsers();
        long finishTime = System.currentTimeMillis();
        Log.d("fillUsersData()", "Whole execution time:" + (finishTime-initialTime));
        return users;
    }

    //Для отримання зображення за його URL
    public static Bitmap getBitmapFromURL(String src) {
        long initialTime = System.currentTimeMillis();
        try {
            URL url = new URL(projectBaseUrl + "/" + src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            long finishTime = System.currentTimeMillis();
            Log.d("getBitmapFromURL()", "Whole execution time:" + (finishTime-initialTime));
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            long initialTime = System.currentTimeMillis();
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            long finishTime = System.currentTimeMillis();
            Log.d("DownloadImageTask", "Whole execution time:" + (finishTime-initialTime));
            return mIcon11;
        }
    }
}
