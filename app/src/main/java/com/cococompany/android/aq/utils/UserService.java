package com.cococompany.android.aq.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.University;
import com.cococompany.android.aq.models.User;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
}