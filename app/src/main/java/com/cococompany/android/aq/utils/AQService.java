package com.cococompany.android.aq.utils;

import com.cococompany.android.aq.models.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

/**
 * Created by Valentin on 07.11.2016.
 */
public interface AQService {
    @GET("/rest/login")
    Call<User> getRegisteredUser(@Field("email") String email);



}
