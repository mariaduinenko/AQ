package com.cococompany.android.aq.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alexandrmyagkiy on 11.12.16.
 */

public class QuestionNotification {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("notification_id")
    @Expose
    private Notification notification;

    @SerializedName("user_id")
    @Expose
    private User user;

    @SerializedName("creationTime")
    @Expose
    private String creationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "QuestionNotification{" +
                "id=" + id +
                ", notification=" + notification +
                ", user=" + user +
                ", creationTime='" + creationTime + '\'' +
                '}';
    }
}

