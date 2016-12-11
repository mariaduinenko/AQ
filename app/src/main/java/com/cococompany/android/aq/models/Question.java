package com.cococompany.android.aq.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Valentin on 05.11.2016.
 */
public class Question {

    @SerializedName("creationTime")
    @Expose
    private String creationTime;

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("category")
    @Expose
    private Category category;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("answers")
    @Expose
    private List<Answer> answers = new ArrayList<Answer>();


    @SerializedName("likes")
    @Expose
    private List<Like> likes = new ArrayList<>();

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Question{" +
                "creationTime='" + creationTime + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", category=" + category +
                ", user=" + user +
                '}';
    }
}
