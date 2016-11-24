package com.cococompany.android.aq.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.University;
import com.cococompany.android.aq.models.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Valentin on 19.11.2016.
 */

public class UniversityService {
    private Retrofit retrofit;
    private AQService aqService;

    public UniversityService(Context context){

        retrofit = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.project_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aqService = retrofit.create(AQService.class);
    }

    public List<University> getUniversitiesByUserId(final Long userId){

        List<University> result = null;

        UniversitiesByUserIdTask universityByUserIdTask = new UniversitiesByUserIdTask();
        universityByUserIdTask.execute(userId);
        try {
            result =  universityByUserIdTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return  result;
    }

    public List<University> getUniversities(){

        List<University> result = null;

        UniversitiesTask universityTask = new UniversitiesTask();
        universityTask.execute();
        try {
            result =  universityTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return  result;
    }

    class UniversitiesByUserIdTask extends AsyncTask<Long,Void,List<University>>{

        @Override
        protected List<University> doInBackground(Long... userIds) {
            List<University> result = null;
            final Call<List<University>> call = aqService.getUniversitiesByUserId(userIds[0]);
            try {
                Response<List<University>> response = call.execute();
                result =  response.body();
                if(response.isSuccessful())
                    System.out.println("Result of university by userId retrieving is successfull: "+result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    class UniversitiesTask extends AsyncTask<Void,Void,List<University>>{

        @Override
        protected List<University> doInBackground(Void... params) {
            List<University> result = null;
            final Call<List<University>> call = aqService.getUniversities();
            try {
                Response<List<University>> response = call.execute();
                result =  response.body();
                if(response.isSuccessful())
                    System.out.println("Result of university retrieving is successfull: "+result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}