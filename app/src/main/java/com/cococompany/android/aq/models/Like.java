package com.cococompany.android.aq.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Valentin on 23.11.2016.
 */

public class Like {
    @SerializedName("creationTime")
    @Expose
    private String creationTime;

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("question")
    @Expose
    private Question question;

    @Override
    public String toString() {
        return "Like{" +
                "creationTime='" + creationTime + '\'' +
                ", id=" + id +
                ", user=" + user +
                ", question=" + question +
                '}';
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
