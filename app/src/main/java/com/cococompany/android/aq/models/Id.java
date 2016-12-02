package com.cococompany.android.aq.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alexandrmyagkiy on 01.12.16.
 */

public class Id {

<<<<<<< HEAD
    @SerializedName("user")
=======
    @SerializedName("creationTime")
>>>>>>> 63badb1f60f49c7aeb7fc97546a4078bd4dc9871
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
<<<<<<< HEAD
}
=======
}
>>>>>>> 63badb1f60f49c7aeb7fc97546a4078bd4dc9871
