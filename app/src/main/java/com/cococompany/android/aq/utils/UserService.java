package com.cococompany.android.aq.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.University;
import com.cococompany.android.aq.models.User;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Valentin on 19.11.2016.
 */

public class UserService {

    private Retrofit retrofit;
    private AQService aqService;

    private final String projectBaseUrl = "https://pure-mesa-13823.herokuapp.com";

    public UserService() {
    }

    public UserService(Context context){

        retrofit = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.project_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aqService = retrofit.create(AQService.class);
    }

    public User lightUpdate(User user){

        User result = null;

        UserUpdateTask userUpdateTask = new UserUpdateTask();
        userUpdateTask.execute(user);
        try {
            result =  userUpdateTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return  result;
    }

    public ArrayList<User> getActiveUsers() {
        ArrayList<User> result = null;
        UsersTask usersTask = new UsersTask();
        usersTask.execute();

        try {
            result =  usersTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (result == null)
            result = new ArrayList<>();

        return result;
    }

    class UserUpdateTask extends AsyncTask<User,Void,User>{

        @Override
        protected User doInBackground(User... users) {
            User result = null;
            final Call<User> call = aqService.lightUpdateUser(users[0].getId(), users[0]);
            System.out.println("After calling light update: user="+users[0]);
            try {
                Response<User> response = call.execute();
                result =  response.body();
                System.out.println("After calling light update: body="+result);
                if(response.isSuccessful())
                    System.out.println("Result of user light updating is successfull: "+result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    class UsersTask extends AsyncTask<Void, Void, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(projectBaseUrl + "/rest/users")
                    .build();

            okhttp3.Response response = null;

            try {
                response = client.newCall(request).execute();
                String jsonData = response.body().string();
                JSONArray array = new JSONArray(jsonData);
                ArrayList<User> users = new ArrayList<>();

                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    User user = new User();
                    user.setId(jsonObject.getLong("id"));
                    if (jsonObject.has("creationTime"))
                        user.setCreationTime(jsonObject.getString("creationTime"));
                    if (jsonObject.has("email"))
                        user.setEmail(jsonObject.getString("email"));
                    if (jsonObject.has("firstName"))
                        user.setFirstName(jsonObject.getString("firstName"));
                    if (jsonObject.has("lastName"))
                        user.setLastName(jsonObject.getString("lastName"));
                    if (jsonObject.has("middleName"))
                        user.setMiddleName(jsonObject.getString("middleName"));
                    if (jsonObject.has("nickname"))
                        user.setNickname(jsonObject.getString("nickname"));
                    if (jsonObject.has("active"))
                        user.setActive(jsonObject.getBoolean("active"));
                    if (jsonObject.has("avatar"))
                        user.setAvatar(jsonObject.getString("avatar"));
                    users.add(user);
                }

                return users;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}