
package com.cococompany.android.aq.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question1 {

    @SerializedName("creationTime")
    @Expose
    private String creationTime;

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("comment")
    @Expose
    private String comment;

    @SerializedName("user")
    @Expose
    private User1 user;

    @SerializedName("answers")
    @Expose
    private List<Answer> answers = new ArrayList<Answer>();

    @SerializedName("likes")
    @Expose
    private List<Like> likes = new ArrayList<>();

    /**
     * 
     * @return
     *     The creationTime
     */
    public String getCreationTime() {
        return creationTime;
    }

    /**
     * 
     * @param creationTime
     *     The creationTime
     */
    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * 
     * @return
     *     The id
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * 
     * @param comment
     *     The comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 
     * @return
     *     The user
     */
    public User1 getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    public void setUser(User1 user) {
        this.user = user;
    }

    /**
     * 
     * @return
     *     The answers
     */
    public List<Answer> getAnswers() {
        return answers;
    }

    /**
     * 
     * @param answers
     *     The answers
     */
    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return "Question1{" +
                "creationTime='" + creationTime + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", user=" + user +
                ", answers=" + answers +
                '}';
    }
}
