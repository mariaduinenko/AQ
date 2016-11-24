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

    public UserUniversityInfo() {
    }

    public UserUniversityInfo(Long id, Object entranceDate, Object graduationDate) {
        this.id = id;
        this.entranceDate = entranceDate;
        this.graduationDate = graduationDate;
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

}
