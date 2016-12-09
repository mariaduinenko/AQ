package com.cococompany.android.aq.services;

import com.cococompany.android.aq.models.Faculty;
import com.cococompany.android.aq.models.Like;
import com.cococompany.android.aq.models.Question;
import com.cococompany.android.aq.models.Speciality;
import com.cococompany.android.aq.models.University;
import com.cococompany.android.aq.models.User;
import com.cococompany.android.aq.models.UserUniversityInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @POST("/rest/register/profile")
    Call<User> registerUserProfile(@Body User user);

    @POST("/rest/profile")
    Call<User> loginUser(@Body User user);

    @POST("/rest/login/profile")
    Call<User> loginUserProfile(@Body User user);

    //questions and likes
    @GET("/rest/feed/{length}")
    Call<ArrayList<Question>> getQuestions(@Path("length") int length);
    @GET("/rest/feed/internal/{length}")
    Call<ArrayList<Question>> getQuestionsInternal(@Path("length") int length);
    @GET("/rest/feed/{id}/{length}")
    Call<ArrayList<Question>> getNextQuestionsInFeed(@Path("id") long id,@Path("length") int length);
    @GET("/rest/feed/internal/{id}/{length}")
    Call<ArrayList<Question>> getNextQuestionsInFeedInternal(@Path("id") long id,@Path("length") int length);
    @POST("/rest/questions")
    Call<Question> createQuestion(@Body Question question);

    @GET("/rest/questions/{id}")
    Call<Question> getQuestionById(@Path("id") Long id);
    @GET("/rest/questions/internal/{id}")
    Call<Question> getQuestionInternalById(@Path("id") Long id);
    @GET("/rest/questions/liking/{id}")
    Call<Question> getQuestionLikingById(@Path("id") Long id);

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

    //useruniversityinfos
    @GET("/rest/useruniversityinfos")
    Call<List<UserUniversityInfo>> getUserUniversityInfos();
    @GET("/rest/useruniversityinfos/users/{userId}")
    Call<List<UserUniversityInfo>> getUserUniversityInfosByUserId(@Path("userId") Long userId);
    @PUT("/rest/useruniversityinfos/{id}")
    Call<UserUniversityInfo> updateUui(@Path("id") Long id, @Body UserUniversityInfo uui);
    @POST("/rest/useruniversityinfos")
    Call<UserUniversityInfo> createUui(@Body UserUniversityInfo uui);
    @DELETE("/rest/useruniversityinfos/{id}")
    Call<UserUniversityInfo> removeUui(@Path("id") Long id);

    //specialities
    @GET("/rest/specialities")
    Call<List<Speciality>> getSpecialities();
    @GET("/rest/specialities/universities/{universityId}")
    Call<List<Speciality>> getSpecialitiesByUniversityId(@Path("universityId") Long universityId);
    @GET("/rest/specialities/faculties/{id}")
    Call<List<Speciality>> getSpecialitiesByFacultyId(@Path("id") Long id);

}
