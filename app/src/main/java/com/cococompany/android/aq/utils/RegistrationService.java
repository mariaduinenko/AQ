package com.cococompany.android.aq.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.User;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Valentin on 19.11.2016.
 */

public class RegistrationService {
    private Retrofit retrofit;
    private AQService aqService;

    public RegistrationService(Context context){

        retrofit = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.project_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aqService = retrofit.create(AQService.class);
    }

    public User register(final User newUser){
        User result = null;
        RegisterTask registerTask = new RegisterTask();
        registerTask.execute(newUser);
        try {
            result =  registerTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return  result;
    }

    public User registerProfile(final User newUser){
        User result = null;
        RegisterProfileTask registerTask = new RegisterProfileTask();
        registerTask.execute(newUser);
        try {
            result =  registerTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return  result;
    }

    public User login(String email, String password){
        User user = new User();
        User result = null;
        user.setEmail(email);
        user.setPassword(password);
        LoginTask loginTask = new LoginTask();
        loginTask.execute(user);
        try {
            result =  loginTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    public User loginProfile(String email, String password){
        User user = new User();
        User result = null;
        user.setEmail(email);
        user.setPassword(password);
        LoginProfileTask loginTask = new LoginProfileTask();
        loginTask.execute(user);
        try {
            result =  loginTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    class LoginTask extends AsyncTask<User,Void,User>{
        @Override
        protected User doInBackground(User... users) {
            User result = null;
            final Call<User> call = aqService.loginUser(users[0]);
            try {
                Response<User> response = call.execute();
                result =  response.body();
                if(response.isSuccessful())
                    System.out.println("Result of user retrieving is successfull: "+result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    class LoginProfileTask extends AsyncTask<User,Void,User>{
        @Override
        protected User doInBackground(User... users) {
            User result = null;
            final Call<User> call = aqService.loginUserProfile(users[0]);
            try {
                Response<User> response = call.execute();
                result =  response.body();
                if(response.isSuccessful())
                    System.out.println("Result of user retrieving in profile mode is successfull: "+result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    class RegisterTask extends AsyncTask<User,Void,User>{
        @Override
        protected User doInBackground(User... users) {
            User result = null;
            final Call<User> call = aqService.registerUser(users[0]);
            try {
                Response<User> response = call.execute();
                result =  response.body();
                if(response.isSuccessful())
                    System.out.println("Result of user registration is successfull: "+result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    class RegisterProfileTask extends AsyncTask<User,Void,User>{
        @Override
        protected User doInBackground(User... users) {
            User result = null;
            final Call<User> call = aqService.registerUserProfile(users[0]);
            try {
                Response<User> response = call.execute();
                result =  response.body();
                if(response.isSuccessful())
                    System.out.println("Result of user registration is successfull: "+result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}