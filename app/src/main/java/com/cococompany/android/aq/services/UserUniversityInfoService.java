package com.cococompany.android.aq.services;

import android.content.Context;
import android.os.AsyncTask;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.UserUniversityInfo;

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

public class UserUniversityInfoService {

    private Retrofit retrofit;
    private AQService aqService;

    public UserUniversityInfoService(Context context) {

        retrofit = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.project_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aqService = retrofit.create(AQService.class);
    }

    public List<UserUniversityInfo> getUserUniversityInfosByUserId(final Long userId) {

        List<UserUniversityInfo> result = null;

        UUIByUserIdTask uuiByUserIdTask = new UUIByUserIdTask();
        uuiByUserIdTask.execute(userId);
        try {
            result = uuiByUserIdTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (result == null)
            result = new ArrayList<>();

        return result;
    }

    public List<UserUniversityInfo> getUserUniversityInfos() {

        List<UserUniversityInfo> result = null;

        UUITask uuiTask = new UUITask();
        uuiTask.execute();
        try {
            result = uuiTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    public UserUniversityInfo updateUui(UserUniversityInfo userUniversityInfo) {
        UserUniversityInfo result = null;

        UuiUpdateTask uuiUpdateTask = new UuiUpdateTask();
        uuiUpdateTask.execute(userUniversityInfo);
        try {
            result = uuiUpdateTask.get();
        }

        catch(InterruptedException e) {
            e.printStackTrace();
        }
        catch(ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    public UserUniversityInfo createUui(UserUniversityInfo userUniversityInfo) {
        UserUniversityInfo result = null;

        UuiCreateTask uuiCreateTask = new UuiCreateTask();
        uuiCreateTask.execute(userUniversityInfo);
        try {
            result = uuiCreateTask.get();
        }

        catch(InterruptedException e) {
            e.printStackTrace();
        }
        catch(ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    public UserUniversityInfo removeUui(Long id) {
        UserUniversityInfo result = null;

        UuiRemoveTask uuiCreateTask = new UuiRemoveTask();
        uuiCreateTask.execute(id);
        try {
            result = uuiCreateTask.get();
        }

        catch(InterruptedException e) {
            e.printStackTrace();
        }
        catch(ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    class UUIByUserIdTask extends AsyncTask<Long,Void,List<UserUniversityInfo>>{

        @Override
        protected List<UserUniversityInfo> doInBackground(Long... userIds) {
            List<UserUniversityInfo> result = null;
            final Call<List<UserUniversityInfo>> call = aqService.getUserUniversityInfosByUserId(userIds[0]);
            try {
                Response<List<UserUniversityInfo>> response = call.execute();
                result =  response.body();
                if(response.isSuccessful())
                    System.out.println("Result of UserUniversityInfos by userId retrieving is successfull: "+result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    class UUITask extends AsyncTask<Void,Void,List<UserUniversityInfo>>{

        @Override
        protected List<UserUniversityInfo> doInBackground(Void... params) {
            List<UserUniversityInfo> result = null;
            final Call<List<UserUniversityInfo>> call = aqService.getUserUniversityInfos();
            try {
                Response<List<UserUniversityInfo>> response = call.execute();
                result =  response.body();
                if(response.isSuccessful())
                    System.out.println("Result of UserUniversityInfos retrieving is successfull: "+result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    class UuiUpdateTask extends AsyncTask<UserUniversityInfo, Void, UserUniversityInfo>{
        @Override
        protected UserUniversityInfo doInBackground(UserUniversityInfo... uuis) {
            UserUniversityInfo result = null;
            final Call<UserUniversityInfo> call = aqService.updateUui(uuis[0].getId(), uuis[0]);
            System.out.println("After calling update: uui="+uuis[0]);
            try {
                Response<UserUniversityInfo> response = call.execute();
                result =  response.body();
                System.out.println("After calling uui update: body="+result);
                if(response.isSuccessful())
                    System.out.println("Result of uui updating is successfull: "+result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    class UuiCreateTask extends AsyncTask<UserUniversityInfo, Void, UserUniversityInfo>{
        @Override
        protected UserUniversityInfo doInBackground(UserUniversityInfo... uuis) {
            UserUniversityInfo result = null;
            final Call<UserUniversityInfo> call = aqService.createUui(uuis[0]);
            System.out.println("After calling create: uui="+uuis[0]);
            try {
                Response<UserUniversityInfo> response = call.execute();
                result = response.body();
                System.out.println("After calling uui create: body="+result);
                if(response.isSuccessful())
                    System.out.println("Result of uui creating is successfull: "+result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    class UuiRemoveTask extends AsyncTask<Long, Void, UserUniversityInfo>{
        @Override
        protected UserUniversityInfo doInBackground(Long... ids) {
            UserUniversityInfo result = null;
            final Call<UserUniversityInfo> call = aqService.removeUui(ids[0]);
            System.out.println("After calling remove: uui="+ids[0]);
            try {
                Response<UserUniversityInfo> response = call.execute();
                result = response.body();
                System.out.println("After calling uui remove: body="+result);
                if(response.isSuccessful())
                    System.out.println("Result of uui removing is successfull: "+result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }
}