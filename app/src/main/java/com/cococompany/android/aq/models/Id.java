package com.cococompany.android.aq.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alexandrmyagkiy on 01.12.16.
 */

public class Id {


    @SerializedName("user")
    @Expose
    private User user;

    public Id() {
    }

    public Id(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Id{" +
                "user=" + user +
                '}';
    }

}

