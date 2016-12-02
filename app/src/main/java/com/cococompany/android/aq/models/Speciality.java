package com.cococompany.android.aq.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandr on 24.11.16.
 */

public class Speciality {

    @SerializedName("creationTime")
    @Expose
    private String creationTime;
    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("faculty")
    @Expose
    private Faculty faculty;

    public Speciality() {
    }

    public Speciality(String creationTime, Long id, String name) {
        this.creationTime = creationTime;
        this.id = id;
        this.name = name;
    }

    public Speciality(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Speciality(String creationTime, Long id, String name, Faculty faculty) {
        this.creationTime = creationTime;
        this.id = id;
        this.name = name;
        this.faculty = faculty;
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
     * The faculty
     */
    public Faculty getFaculty() {
        return faculty;
    }

    /**
     *
     * @param faculty
     * The faculty
     */
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    @Override
    public String toString() {
        return "Speciality{" +
                "creationTime='" + creationTime + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +

                ", faculty=" + faculty +

                '}';
    }
}