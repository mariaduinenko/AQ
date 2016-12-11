package com.cococompany.android.aq.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.Toast;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.adapters.CategoriesGridViewAdapter;
import com.cococompany.android.aq.adapters.UsersGridViewAdapter;
import com.cococompany.android.aq.models.Category;
import com.cococompany.android.aq.models.Question;
import com.cococompany.android.aq.models.QuestionWithUsersWrapper;
import com.cococompany.android.aq.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by alexandrmyagkiy on 06.12.16.
 */

public class CategoriesChooseFragment extends DialogFragment {

    private static final String projectBaseUrl = "https://pure-mesa-13823.herokuapp.com";

    private Context ctx;

    private ArrayList<Category> data;
    private ArrayList<User> users;
    private CategoriesGridViewAdapter categoriesGridViewAdapter;
    private UsersGridViewAdapter usersGridViewAdapter;
    private ProgressBar categoriesProgressBar;
    private ProgressBar usersProgressBar;

    private String CATEGORIES_URL = projectBaseUrl + "/rest/categories";
    private String USERS_URL = projectBaseUrl + "/rest/users/profile";

    private ArrayList<Category> selectedCategories;
    private ArrayList<User> selectedUsers;
    private ArrayList<Long> selectedUserIds;

    private String QUESTION_WITH_CATEGORY_URL = projectBaseUrl + "/rest/questions";
    private String QUESTION_WITHOUT_CATEGORY_URL = projectBaseUrl + "/rest/questions/users";

    private Question question = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ctx = getContext();

        //Час початку створення діалогового вікна
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

        //Підготовка початкових даних
        data = new ArrayList<>();
        users = new ArrayList<>();
        selectedCategories = new ArrayList<>();
        selectedUsers = new ArrayList<>();

        categoriesProgressBar = (ProgressBar) view.findViewById(R.id.categories_progress_bar);
        usersProgressBar = (ProgressBar) view.findViewById(R.id.users_progress_bar);

        //Додавання gridView до першої вкладки
        final GridView categoriesGridView = (GridView) view.findViewById(R.id.categories_grid_view);
        categoriesGridViewAdapter = null;
        categoriesGridViewAdapter = new CategoriesGridViewAdapter(getContext(), R.layout.category_grid_item, data);
        categoriesGridView.setAdapter(categoriesGridViewAdapter);

        categoriesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                int selectedIndex = categoriesGridViewAdapter.selectedPositions.indexOf(position);
                if (selectedIndex > -1) {
                    categoriesGridViewAdapter.removeSelected(selectedIndex, v);
                    selectedCategories.remove((Category) parent.getItemAtPosition(position));
                } else {
                    if (selectedUsers.size() > 0) {
                        Toast.makeText(getActivity(),"Unable to select category. Unselect all users first.", Toast.LENGTH_SHORT).show();
                    } else {
                        for (Category i : selectedCategories) {
                            if (!data.get(position).getId().equals(i.getId()))
                                selectedCategories.remove(i);
                        }
                        categoriesGridViewAdapter.removeOther(position, v);
                        categoriesGridViewAdapter.addSelected(position, v);
                        selectedCategories.add((Category) parent.getItemAtPosition(position));
                    }
                }
            }
        });

        //Додавання gridView до другої вкладки
        final GridView usersGridView = (GridView) view.findViewById(R.id.users_grid_view);
        usersGridViewAdapter = null;
        usersGridViewAdapter = new UsersGridViewAdapter(getContext(), R.layout.user_grid_item, users);
        usersGridView.setAdapter(usersGridViewAdapter);

        usersGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                int selectedIndex = usersGridViewAdapter.selectedPositions.indexOf(position);
                if (selectedIndex > -1) {
                    usersGridViewAdapter.removeSelected(selectedIndex, v);
                    selectedUsers.remove((User) parent.getItemAtPosition(position));
                } else {
                    if (selectedCategories.size() > 0) {
                        Toast.makeText(getActivity(),"Unable to select user. Unselect all categories first.", Toast.LENGTH_SHORT).show();
                    } else {
                        usersGridViewAdapter.addSelected(position, v);
                        selectedUsers.add((User) parent.getItemAtPosition(position));
                    }
                }
            }
        });

        //Завантаження категорій
        new CategoriesAsyncTask().execute(CATEGORIES_URL);
        //Завантаження користувачів
        new UserGridAsyncTask().execute(USERS_URL);

        categoriesProgressBar.setVisibility(View.VISIBLE);
        usersProgressBar.setVisibility(View.VISIBLE);

        builder.setView(view)
                //Метод відміни вибору
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CategoriesChooseFragment.this.getDialog().cancel();
                    }
                })
                //Метод підтвердження вибору
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(getActivity(),"Choosed categories: " + selectedCategories + "     Choosed users: " + selectedUsers,Toast.LENGTH_LONG).show();

                        if (selectedCategories.size() > 0) {
                            question.setCategory(selectedCategories.get(0));
                            new CreateQuestionWithCategoryAsyncTask().execute(QUESTION_WITH_CATEGORY_URL);
                        } else if (selectedUsers.size() > 0) {
                            selectedUserIds = new ArrayList<Long>();
                            for (User u: selectedUsers) {
                                selectedUserIds.add(u.getId());
                            }
                            new CreateQuestionWithoutCategoryAsyncTask().execute(QUESTION_WITHOUT_CATEGORY_URL);
                        } else {
                            Toast.makeText(getActivity(), "Not category, nor user choosed. Your question wouldn't be visible to anyone!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        //Час завершення створення діалогового вікна
        long finishTime = System.currentTimeMillis();
        Log.d(this.getClass().getName(), "Whole execution time:" + (finishTime-initialTime));
        return builder.create();
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    //Асинхронне завантаження категорій
    public class CategoriesAsyncTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            try {
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
                        parseCategoriesResult(response);
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
                System.out.println("IN POST EXECUTE. RESULT==1");
                categoriesGridViewAdapter.setGridData(data);
            } else {
                Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }

            //Hide progressbar
            categoriesProgressBar.setVisibility(View.GONE);
        }
    }

    //Асинхронне завантаження користувачів
    public class UserGridAsyncTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            try {
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
                        System.out.println("IN UsersGridAsyncTask. code==200");
                        String response = httpResponse.body().string();
                        parseUsersResult(response);
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
                System.out.println("IN POST USERS EXECUTE. RESULT==1");
                usersGridViewAdapter.setGridData(users);
            } else {
                Toast.makeText(getActivity(), "Failed to fetch users!", Toast.LENGTH_SHORT).show();
            }

            //Hide progressbar
            categoriesProgressBar.setVisibility(View.GONE);
        }
    }

    //Асинхронне створення питання з обраною категорією
    public class CreateQuestionWithCategoryAsyncTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            try {
                OkHttpClient client = new OkHttpClient();

                Gson gson = new GsonBuilder().create();
                String jsonQuestion = gson.toJson(question);// obj is your object
                MediaType MEDIATYPE_JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(MEDIATYPE_JSON, jsonQuestion.toString());

                //{"title":"What is stream?", "comment":"comment", "user":{"id":""+user.getId()+"\"}, \"category\":{\"id\":\""+category.getId()+""}}
                Request request = new Request.Builder()
                        .url(params[0])
                        .post(body)
                        .build();

                Response httpResponse = null;

                try {
                    httpResponse = client.newCall(request).execute();
                    int statusCode = httpResponse.code();

                    // 200 represents HTTP OK
                    if (statusCode == 200) {
                        System.out.println("IN CreateQuestionWithCategoryAsyncTask. code==200");
                        String response = httpResponse.body().string();
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
            if (result == 1) {
                Toast.makeText(ctx, "Question with category successfully created!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ctx, "Failed to create question with category!", Toast.LENGTH_LONG).show();
            }

            //Hide progressbar
            //...
        }
    }

    //Асинхронне створення питання без категорії
    public class CreateQuestionWithoutCategoryAsyncTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            try {
                OkHttpClient client = new OkHttpClient();

                QuestionWithUsersWrapper wrapper = new QuestionWithUsersWrapper();
                wrapper.setQuestion(question);
                wrapper.setUsers(selectedUserIds);
                //{"question":{"title":"What is stream?", "comment":"comment", "user":{"id":""+user.getId()+"\"}}, \"users\":["+user2.getId()+"]}
                Gson gson = new GsonBuilder().create();
                String jsonQuestion = gson.toJson(wrapper);// obj is your object
                MediaType MEDIATYPE_JSON = MediaType.parse("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(MEDIATYPE_JSON, jsonQuestion.toString());

                //{"title":"What is stream?", "comment":"comment", "user":{"id":""+user.getId()+"\"}, \"category\":{\"id\":\""+category.getId()+""}}
                Request request = new Request.Builder()
                        .url(params[0])
                        .post(body)
                        .build();

                Response httpResponse = null;

                try {
                    httpResponse = client.newCall(request).execute();
                    int statusCode = httpResponse.code();

                    // 200 represents HTTP OK
                    if (statusCode == 200) {
                        System.out.println("IN CreateQuestionWithoutCategoryAsyncTask. code==200");
                        String response = httpResponse.body().string();
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
            if (result == 1) {
                Toast.makeText(ctx, "Question without category successfully created!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ctx, "Failed to create question without category!", Toast.LENGTH_LONG).show();
            }

            //Hide progressbar
            //...
        }
    }

    //Парсинг категорій з JSON
    private void parseCategoriesResult(String result) {
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
                    item.setImage(jsonObject.getString("image"));
                } else {
                    item.setImage(projectBaseUrl + "/rest/images/4.jpeg");
                }
                data.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Парсинг користувачів з JSON
    private void parseUsersResult(String result) {
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
                if (jsonObject.has("avatar")) {
                    item.setAvatar(projectBaseUrl + "/rest/images/" + jsonObject.getString("avatar").substring(0, jsonObject.getString("avatar").indexOf(".")));
                } else {
                    item.setAvatar(projectBaseUrl + "/rest/images/4");
                }
                users.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
