package com.cococompany.android.aq.services;

import android.content.Context;
import android.os.AsyncTask;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.Faculty;

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

public class FacultyService {
    private Retrofit retrofit;
    private AQService aqService;

    public FacultyService(Context context){

        retrofit = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.project_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aqService = retrofit.create(AQService.class);
    }

    public List<Faculty> getFacultiesByUniversityId(final Long universityId){

        List<Faculty> result = null;

        FacultiesByUniversityIdTask facultiesByUniversityIdTask = new FacultiesByUniversityIdTask();
        facultiesByUniversityIdTask.execute(universityId);
        try {
            result =  facultiesByUniversityIdTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (result == null)
            result = new ArrayList<>();

        return  result;
    }

    public List<Faculty> getFacultiesByUserId(final Long userId){

        List<Faculty> result = null;

        FacultiesByUserIdTask facultiesByUserIdTask = new FacultiesByUserIdTask();
        facultiesByUserIdTask.execute(userId);
        try {
            result =  facultiesByUserIdTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (result == null)
            result = new ArrayList<>();

        return  result;
    }

    public List<Faculty> getFaculties(){

        List<Faculty> result = null;

        FacultiesTask facultiesTask = new FacultiesTask();
        facultiesTask.execute();
        try {
            result =  facultiesTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (result == null)
            result = new ArrayList<>();

        return  result;
    }

    class FacultiesByUserIdTask extends AsyncTask<Long,Void,List<Faculty>>{

        @Override
        protected List<Faculty> doInBackground(Long... userIds) {
            List<Faculty> result = null;
            final Call<List<Faculty>> call = aqService.getFacultiesByUserId(userIds[0]);
            try {
                Response<List<Faculty>> response = call.execute();
                result =  response.body();
                if(response.isSuccessful())
                    System.out.println("Result of faculties by userId retrieving is successfull: "+result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    class FacultiesByUniversityIdTask extends AsyncTask<Long,Void,List<Faculty>>{

        @Override
        protected List<Faculty> doInBackground(Long... universityIds) {
            List<Faculty> result = null;
            final Call<List<Faculty>> call = aqService.getFacultiesByUniversityId(universityIds[0]);
            try {
                Response<List<Faculty>> response = call.execute();
                result =  response.body();
                if(response.isSuccessful())
                    System.out.println("Result of faculties by university id retrieving is successfull: "+result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    class FacultiesTask extends AsyncTask<Void,Void,List<Faculty>>{

        @Override
        protected List<Faculty> doInBackground(Void... params) {
            List<Faculty> result = null;
            final Call<List<Faculty>> call = aqService.getFaculties();
            try {
                Response<List<Faculty>> response = call.execute();
                result =  response.body();
                if(response.isSuccessful())
                    System.out.println("Result of faculties retrieving is successfull: "+result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}