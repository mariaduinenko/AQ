package com.cococompany.android.aq.services;

import android.view.View;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.Question1;
import com.cococompany.android.aq.utils.AQService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alexandr on 13.11.16.
 */

public class QuestionService {

    private Retrofit retrofit = null;
    private AQService aqService = null;
    ArrayList<Question1> result = null;

    public QuestionService(View view) {
        retrofit = new Retrofit.Builder()
                .baseUrl(view.getResources().getString(R.string.project_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aqService = retrofit.create(AQService.class);
    }

    public ArrayList<Question1> getAllQuestions() {
        Call<ArrayList<Question1>> call = aqService.getQuestions();

        Response<ArrayList<Question1>> response = null;

        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        result = response.body();

//        call.enqueue(new Callback<ArrayList<Question1>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Question1>> call, Response<ArrayList<Question1>> response) {
//
//                if (response.isSuccess()) {
//                    // request successful (status code 200, 201)
//                    result = response.body();
//                } else {
//                    //request not successful (like 400,401,403 etc)
//                    //Handle errors
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Question1>> call, Throwable t) {
//
//            }
//        });

        return result;
    }

    public ArrayList<Question1> getRecent8() {
        Call<ArrayList<Question1>> call = aqService.getRecent8();

        Response<ArrayList<Question1>> response = null;

        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        result = response.body();

        return result;
    }

}
