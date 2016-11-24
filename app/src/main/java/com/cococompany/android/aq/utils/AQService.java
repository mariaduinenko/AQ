package com.cococompany.android.aq.utils;

import com.cococompany.android.aq.models.Question;
import com.cococompany.android.aq.models.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Valentin on 07.11.2016.
 */
public interface AQService {
    @POST("/rest/register")
    Call<User> registerUser(@Body User user);

    @POST("/rest/profile")
    Call<User> loginUser(@Body User user);

    @GET("/rest/feed/8")
    Call<ArrayList<Question>> getQuestions();

    @GET("/rest/questions/{id}")
    Call<Question> getQuestionById(@Path("id") Long id);

}
