package com.cococompany.android.aq.utils;

import com.cococompany.android.aq.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Valentin on 07.11.2016.
 */
public interface AQService {
    @POST("/rest/register")
    Call<User> registerUser(@Body User user);

    @POST("/rest/profile")
    Call<User> loginUser(@Body User user);


}
