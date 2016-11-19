package com.cococompany.android.aq.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandr on 19.11.16.
 */

public class Answer {

    @SerializedName("creationTime")
    @Expose
    private String creationTime;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("likes")
    @Expose
    private List<Like> likes = new ArrayList<>();

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
