package com.cococompany.android.aq.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandr on 24.11.16.
 */

public class Faculty {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("creationTime")
    @Expose
    private String creationTime;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("university")
    @Expose
    private University university;

    public Faculty() {
    }

    public Faculty(Long id, String creationTime, String name) {
        this.id = id;
        this.creationTime = creationTime;
        this.name = name;
    }

    public Faculty(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Faculty(Long id, String creationTime, String name, University university) {
        this.id = id;
        this.creationTime = creationTime;
        this.name = name;
        this.university = university;
    }

    /**
     *
     * @return
     * The id
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The creationTime
     */
    public String getCreationTime() {
        return creationTime;
    }

    /**
     *
     * @param creationTime
     * The creationTime
     */
    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The university
     */
    public University getUniversity() {
        return university;
    }

    /**
     *
     * @param university
     * The university
     */
    public void setUniversity(University university) {
        this.university = university;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", creationTime='" + creationTime + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
