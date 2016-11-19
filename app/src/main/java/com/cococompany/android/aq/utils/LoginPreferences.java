package com.cococompany.android.aq.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

/**
 * Created by Valentin on 19.11.2016.
 */

public class LoginPreferences {
    private SharedPreferences sharedPreferences;
    private final static String LOGIN_PREFERENCES = "acc";
    private final static String USER_ID = "user_id";
    private final static String USER_PASSWORD = "user_pass";
    public LoginPreferences(Context context){
        sharedPreferences = context.getSharedPreferences(LOGIN_PREFERENCES,Context.MODE_PRIVATE);
    }

    public void clear(){
        sharedPreferences.edit().clear().commit();
    }

    public void setUserId(String id){
        sharedPreferences.edit().putString(USER_ID,id).commit();
    }

    public void setUserPassword(String password){
        byte[] encodeValue = Base64.encode(password.getBytes(), Base64.DEFAULT);
          sharedPreferences.edit().putString(USER_PASSWORD,new String(encodeValue)).commit();
    }
}
