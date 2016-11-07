package com.cococompany.android.aq.models;

import java.sql.Date;

/**
 * Created by Valentin on 05.11.2016.
 */
public class User {
    private String email;
    private String firstname;
    private String middlename;
    private String lastname;
    private Date birthday;
    private String nickname;
    private String avatar_url;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public User(String email) {

        this.email = email;
    }

    public User(String email, String firstname, String middlename, String lastname, Date birthday, String nickname, String avatar_url) {

        this.email = email;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.birthday = birthday;
        this.nickname = nickname;
        this.avatar_url = avatar_url;
    }
}
