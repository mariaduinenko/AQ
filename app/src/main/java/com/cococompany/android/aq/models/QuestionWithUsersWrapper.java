package com.cococompany.android.aq.models;

import java.util.List;

/**
 * Created by alexandrmyagkiy on 11.12.16.
 */

public class QuestionWithUsersWrapper {

    private Question question;
    private List<Long> users;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Long> getUsers() {
        return users;
    }

    public void setUsers(List<Long> users) {
        this.users = users;
    }
}
