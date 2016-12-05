package com.cococompany.android.aq.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexandr on 24.11.16.
 */

public class UserUniversityInfo {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("entranceDate")
    @Expose
    private Object entranceDate;
    @SerializedName("graduationDate")
    @Expose
    private Object graduationDate;
    @SerializedName("speciality")
    @Expose
    private Speciality speciality;
    @SerializedName("faculty")
    @Expose
    private Faculty faculty;
    @SerializedName("university")
    @Expose
    private University university;
    @SerializedName("user")
    @Expose
    private User user;

    public UserUniversityInfo() {
    }

    public UserUniversityInfo(Long id, Object entranceDate, Object graduationDate) {
        this.id = id;
        this.entranceDate = entranceDate;
        this.graduationDate = graduationDate;
    }

    public UserUniversityInfo(Long id, Object entranceDate, Object graduationDate, Speciality speciality, Faculty faculty, University university) {
        this.id = id;
        this.entranceDate = entranceDate;
        this.graduationDate = graduationDate;
        this.speciality = speciality;
        this.faculty = faculty;
        this.university = university;
    }

    public UserUniversityInfo(Long id, Object entranceDate, Object graduationDate, Speciality speciality) {
        this.id = id;
        this.entranceDate = entranceDate;
        this.graduationDate = graduationDate;
        this.speciality = speciality;
    }

    public UserUniversityInfo(Long id, Object entranceDate, Faculty faculty, Object graduationDate) {
        this.id = id;
        this.entranceDate = entranceDate;
        this.faculty = faculty;
        this.graduationDate = graduationDate;
    }

    public UserUniversityInfo(Long id, Object entranceDate, Object graduationDate, University university) {
        this.id = id;
        this.entranceDate = entranceDate;
        this.graduationDate = graduationDate;
        this.university = university;
    }

    public UserUniversityInfo(Long id) {
        this.id = id;
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
     * The entranceDate
     */
    public Object getEntranceDate() {
        return entranceDate;
    }

    /**
     *
     * @param entranceDate
     * The entranceDate
     */
    public void setEntranceDate(Object entranceDate) {
        this.entranceDate = entranceDate;
    }

    /**
     *
     * @return
     * The graduationDate
     */
    public Object getGraduationDate() {
        return graduationDate;
    }

    /**
     *
     * @param graduationDate
     * The graduationDate
     */
    public void setGraduationDate(Object graduationDate) {
        this.graduationDate = graduationDate;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserUniversityInfo{" +
                "id=" + id +
                ", entranceDate=" + entranceDate +
                ", graduationDate=" + graduationDate +
                ", speciality=" + speciality +
                ", faculty=" + faculty +
                ", university=" + university +
                '}';
    }
}
