package com.cococompany.android.aq.utils;

import android.content.Context;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.User;

import java.io.IOException;

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

    public void register(final User newUser){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Call<User> call  = aqService.registerUser(newUser);
                Response<User> response = null;
                try {
                    response = call.execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(response.isSuccessful());
                if(!response.isSuccessful()){
                try {
                    System.out.println(response.code()+response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                }
            }
        });
        thread.start();

    }
}
