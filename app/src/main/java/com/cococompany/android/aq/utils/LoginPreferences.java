package com.cococompany.android.aq.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Valentin on 19.11.2016.
 */

public class LoginPreferences {

    private SharedPreferences sharedPreferences;

    private final static String LOGIN_PREFERENCES = "acc";
    private final static String USER_ID = "user_id";
    private final static String USER_PASSWORD = "user_pass";
    private final static String USER_EMAIL = "user_email";
    private final static String USER_FIRSTNAME = "user_firstname";
    private final static String USER_LASTNAME = "user_lastname";
    private final static String USER_MIDDLENAME = "user_middlename";
    private final static String USER_NICKNAME = "user_nickname";
    private final static String USER_AVATAR = "user_avatar";
    private final static String USER_BIRTHDATE = "user_birthdate";
    private final static String USER_CATEGORIES = "user_categories";

    public LoginPreferences(Context context){
        sharedPreferences = context.getSharedPreferences(LOGIN_PREFERENCES,Context.MODE_PRIVATE);
    }

    public void clear(){
        sharedPreferences.edit().clear().commit();
    }

    public void setUserId(Long id){
        sharedPreferences.edit().putLong(USER_ID,id).commit();
    }

    public void setUserPassword(String password){
        byte[] encodeValue = Base64.encode(password.getBytes(), Base64.DEFAULT);
        sharedPreferences.edit().putString(USER_PASSWORD,new String(encodeValue)).commit();
    }

    public Long getUserId() {
        return Long.valueOf(sharedPreferences.getAll().get(USER_ID).toString());
    }

    public String getUserPassword() {
        String userPass = "";

        userPass = sharedPreferences.getAll().get(USER_PASSWORD).toString();

        userPass = new String(Base64.decode(userPass.getBytes(), Base64.DEFAULT));

        return userPass;
    }

    public void setUserEmail(String email) {
        sharedPreferences.edit().putString(USER_EMAIL, email).commit();;
    }

    public String getUserEmail() {
        Object email = sharedPreferences.getAll().get(USER_EMAIL);
        return (email != null)? email.toString() : "";
    }

    public void setUserFirstname(String firstname) {
        sharedPreferences.edit().putString(USER_FIRSTNAME, firstname).commit();;
    }

    public String getUserFirstname() {
        Object firstname = sharedPreferences.getAll().get(USER_FIRSTNAME);
        return (firstname != null)? firstname.toString() : "";
    }

    public void setUserLastname(String lastname) {
        sharedPreferences.edit().putString(USER_LASTNAME, lastname).commit();;
    }

    public String getUserLastname() {
        Object lastname = sharedPreferences.getAll().get(USER_LASTNAME);
        return (lastname != null)? lastname.toString() : "";
    }

    public void setUserMiddlename(String middlename) {
        sharedPreferences.edit().putString(USER_MIDDLENAME, middlename).commit();;
    }

    public String getUserMiddlename() {
        Object middlename = sharedPreferences.getAll().get(USER_MIDDLENAME);
        return (middlename != null)? middlename.toString() : "";
    }

    public void setUserNickname(String nickname) {
        sharedPreferences.edit().putString(USER_NICKNAME, nickname).commit();;
    }

    public String getUserNickname() {
        Object nickname = sharedPreferences.getAll().get(USER_NICKNAME);
        return (nickname != null)? nickname.toString() : "";
    }

    public void setUserAvatar(String avatar) {
        sharedPreferences.edit().putString(USER_AVATAR, avatar).commit();;
    }

    public String getUserAvatar() {
        Object avatar = sharedPreferences.getAll().get(USER_AVATAR);
        return (avatar != null)? avatar.toString() : "";
    }

    public void setUserBirtdate(String birtdate) {
        sharedPreferences.edit().putString(USER_BIRTHDATE, birtdate).commit();;
    }

    public String getUserBirthdate() {
        Object birthdate = sharedPreferences.getAll().get(USER_BIRTHDATE);
        return (birthdate != null)? birthdate.toString() : "";
    }

    public void setUserCategories(Set<String> categories) {
        sharedPreferences.edit().putStringSet(USER_CATEGORIES, categories).commit();;
    }

    public Set<String> getUserCategories() {
        Object categories = sharedPreferences.getAll().get(USER_CATEGORIES);
        return (categories != null)? new HashSet<>(Arrays.asList(categories.toString())) : new HashSet<String>();
    }
}