package com.cococompany.android.aq.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Set;

/**
 * Created by alexandr on 19.11.16.
 */

public class Category {

    @SerializedName("creationTime")
    @Expose
    private String creationTime;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("users")
    @Expose
    private Set<User1> users;

    @SerializedName("questions")
    @Expose
    private List<Question1> questions;

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User1> getUsers() {
        return users;
    }

    public void setUsers(Set<User1> users) {
        this.users = users;
    }

    public List<Question1> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question1> questions) {
        this.questions = questions;
    }
}
