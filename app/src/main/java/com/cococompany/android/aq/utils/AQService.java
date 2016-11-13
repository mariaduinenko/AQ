package com.cococompany.android.aq.utils;

import com.cococompany.android.aq.models.Greetings;
import com.cococompany.android.aq.models.Question1;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Valentin on 07.11.2016.
 */
public interface AQService {

//    @GET("/rest/login")
//    Call<User> getRegisteredUser(@Field("email") String email);
//
    @GET("rest/questions")
    Call<ArrayList<Question1>> getQuestions();

    @GET("rest/feed/8")
    Call<ArrayList<Question1>> getRecent8();

    @GET("rest/open")
    Call<Greetings> getGreetings();

}
