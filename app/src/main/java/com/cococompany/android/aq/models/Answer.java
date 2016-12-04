package com.cococompany.android.aq.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valentin on 23.11.2016.
 */

public class Answer {

    @SerializedName("creationTime")
    @Expose
    private String creationTime;

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("question")
    @Expose
    private Question question;

    @SerializedName("answerLikes")
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "creationTime='" + creationTime + '\'' +
                ", id=" + id +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", question=" + question +
                '}';
    }
}
