package com.cococompany.android.aq.services;

import android.os.AsyncTask;
import android.util.Log;

import com.cococompany.android.aq.models.Category;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;

/**
 * Created by alexandrmyagkiy on 08.12.16.
 */

public class CategoryService {

    private final String projectBaseUrl = "https://pure-mesa-13823.herokuapp.com";

    public ArrayList<Category> getCategories(){
        ArrayList<Category> result = null;
        CategoriesTask categoriesTask = new CategoriesTask();
        categoriesTask.execute();

        try {
            result =  categoriesTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (result == null)
            result = new ArrayList<>();

        return result;
    }

    class CategoriesTask extends AsyncTask<Void, Void, ArrayList<Category>> {
        @Override
        protected ArrayList<Category> doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                                        .url(projectBaseUrl + "/rest/categories")
                                        .build();

            Response response = null;

            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                JSONArray array = new JSONArray(jsonData);
                ArrayList<Category> categories = new ArrayList<>();

                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    Category category = new Category();
                    category.setId(jsonObject.getLong("id"));
                    if (jsonObject.has("name"))
                        category.setName(jsonObject.getString("name"));
                    if (jsonObject.has("image"))
                        category.setImage(jsonObject.getString("image"));
                    categories.add(category);
                }

                return categories;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
