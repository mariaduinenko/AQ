package com.cococompany.android.aq.services;

import android.content.Context;
import android.os.AsyncTask;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.Speciality;

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

public class SpecialityService {

    private Retrofit retrofit;
    private AQService aqService;

    public SpecialityService(Context context){

        retrofit = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.project_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aqService = retrofit.create(AQService.class);
    }

    public List<Speciality> getSpecialities(){

        List<Speciality> result = null;

        SpecialitiesTask specialitiesTask = new SpecialitiesTask();
        specialitiesTask.execute();
        try {
            result =  specialitiesTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (result == null)
            result = new ArrayList<>();

        return  result;
    }

    public List<Speciality> getSpecialitiesByUniversityId(final Long universityId){

        List<Speciality> result = null;

        SpecialitiesByUniversityIdTask specialitiesByUniversityIdTask = new SpecialitiesByUniversityIdTask();
        specialitiesByUniversityIdTask.execute(universityId);
        try {
            result =  specialitiesByUniversityIdTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (result == null)
            result = new ArrayList<>();

        return  result;
    }

    public List<Speciality> getSpecialitiesByFacultyId(final Long facultyId){

        List<Speciality> result = null;

        SpecialitiesByFacultyIdTask specialitiesByFacultyIdTask = new SpecialitiesByFacultyIdTask();
        specialitiesByFacultyIdTask.execute(facultyId);
        try {
            result =  specialitiesByFacultyIdTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (result == null)
            result = new ArrayList<>();

        return  result;
    }

    class SpecialitiesTask extends AsyncTask<Void,Void,List<Speciality>>{

        @Override
        protected List<Speciality> doInBackground(Void... params) {
            List<Speciality> result = null;
            final Call<List<Speciality>> call = aqService.getSpecialities();
            try {
                Response<List<Speciality>> response = call.execute();
                result =  response.body();
                if(response.isSuccessful())
                    System.out.println("Result of specialities retrieving is successfull: "+result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    class SpecialitiesByUniversityIdTask extends AsyncTask<Long,Void,List<Speciality>>{

        @Override
        protected List<Speciality> doInBackground(Long... universityIds) {
            List<Speciality> result = null;
            final Call<List<Speciality>> call = aqService.getSpecialitiesByUniversityId(universityIds[0]);
            try {
                Response<List<Speciality>> response = call.execute();
                result =  response.body();
                if(response.isSuccessful())
                    System.out.println("Result of specialities by universityId retrieving is successfull: "+result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    class SpecialitiesByFacultyIdTask extends AsyncTask<Long,Void,List<Speciality>>{

        @Override
        protected List<Speciality> doInBackground(Long... facultyIds) {
            List<Speciality> result = null;
            final Call<List<Speciality>> call = aqService.getSpecialitiesByFacultyId(facultyIds[0]);
            try {
                Response<List<Speciality>> response = call.execute();
                result =  response.body();
                if(response.isSuccessful())
                    System.out.println("Result of specialities by facultyId retrieving is successfull: "+result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}