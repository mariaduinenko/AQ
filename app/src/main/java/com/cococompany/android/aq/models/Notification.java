package com.cococompany.android.aq.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alexandrmyagkiy on 11.12.16.
 */

public class Notification {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("notificationType")
    @Expose
    private String notificationType;

    @SerializedName("subjectId")
    @Expose
    private Long subjectId;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("qNotifications")
    @Expose
    private List<QuestionNotification> qNotifications;

    @SerializedName("publicationTime")
    @Expose
    private String creationTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<QuestionNotification> getqNotifications() {
        return qNotifications;
    }

    public void setqNotifications(List<QuestionNotification> qNotifications) {
        this.qNotifications = qNotifications;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", notificationType=" + notificationType +
                ", subjectId=" + subjectId +
                ", content='" + content + '\'' +
                '}';
    }
}
