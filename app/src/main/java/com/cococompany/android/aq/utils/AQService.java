package com.cococompany.android.aq.utils;

import com.cococompany.android.aq.models.Faculty;
import com.cococompany.android.aq.models.Question;
import com.cococompany.android.aq.models.University;
import com.cococompany.android.aq.models.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @GET("/rest/universities/users/{userId}")
    Call<List<University>> getUniversitiesByUserId(@Path("userId") Long userId);

    @GET("/rest/universities")
    Call<List<University>> getUniversities();

    @PUT("/rest/users/{userId}")
    Call<User> lightUpdateUser(@Path("userId") Long userId, @Body User user);

    //faculties
    @GET("/rest/faculties")
    Call<List<Faculty>> getFaculties();

    @GET("/rest/faculties/users/{userId}")
    Call<List<Faculty>> getFacultiesByUserId(@Path("userId") Long userId);

    @GET("/rest/faculties/universities/{id}")
    Call<List<Faculty>> getFacultiesByUniversityId(@Path("id") Long id);

}
