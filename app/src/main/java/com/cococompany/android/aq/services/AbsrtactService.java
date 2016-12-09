package com.cococompany.android.aq.services;

import android.content.Context;

import com.cococompany.android.aq.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Valentin on 23.11.2016.
 */

public class AbsrtactService {
    private Retrofit retrofit;
    private AQService aqService;

    public AQService getAqService() {
        return aqService;
    }

    public AbsrtactService(Context context){

        retrofit = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.project_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aqService = retrofit.create(AQService.class);
    }
}
