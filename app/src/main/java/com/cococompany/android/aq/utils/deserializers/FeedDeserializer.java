package com.cococompany.android.aq.utils.deserializers;

import com.cococompany.android.aq.models.Answer;
import com.cococompany.android.aq.models.Like;
import com.cococompany.android.aq.models.Question;
import com.cococompany.android.aq.models.Role;
import com.cococompany.android.aq.models.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by alexandrmyagkiy on 04.12.16.
 */

public class FeedDeserializer implements JsonDeserializer<ArrayList<Question>> {
    private ArrayList<User> users = new ArrayList<>();

    @Override
    public ArrayList<Question> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        ArrayList<Question> questions = new ArrayList<>();
        JsonArray feedArray = null;

        feedArray = json.getAsJsonArray();

        if (feedArray == null)
            return new ArrayList<>();

        for (int i = 0; i < feedArray.size(); i++) {
            Question question = new Question();
            JsonObject jsonObject = feedArray.get(i).getAsJsonObject();

            if (jsonObject.has("creationTime"))
                question.setCreationTime(jsonObject.get("creationTime").getAsString());
            String modificationTime = null;
            if (jsonObject.has("modificationTime"))
                modificationTime = jsonObject.get("modificationTime").getAsString();
            question.setId(jsonObject.get("id").getAsLong());
            if (jsonObject.has("title"))
                question.setTitle(jsonObject.get("title").getAsString());
            if (jsonObject.has("comment"))
                question.setComment(jsonObject.get("comment").getAsString());
            User user = new User();
            if (jsonObject.has("user")) {
                if (jsonObject.get("user").isJsonObject()) {
                    JsonObject jsonUser = jsonObject.get("user").getAsJsonObject();
                    user.setId(jsonUser.get("id").getAsLong());
                    if (jsonUser.has("email"))
                        user.setEmail(jsonUser.get("email").getAsString());
                    if (jsonUser.has("firstName"))
                        user.setFirstName(jsonUser.get("firstName").getAsString());
                    if (jsonUser.has("lastName"))
                        user.setLastName(jsonUser.get("lastName").getAsString());
                    if (jsonUser.has("middleName"))
                        user.setMiddleName(jsonUser.get("middleName").getAsString());
                    if (jsonUser.has("nickname"))
                        user.setNickname(jsonUser.get("nickname").getAsString());
                    if (jsonUser.has("active"))
                        user.setActive(jsonUser.get("active").getAsBoolean());
                    if (jsonUser.has("avatar"))
                        user.setAvatar(jsonUser.get("avatar").getAsString());
                    if (jsonUser.has("creationTime"))
                        user.setCreationTime(jsonUser.get("creationTime").getAsString());
                    if (jsonUser.has("password"))
                        user.setPassword(jsonUser.get("password").getAsString());
                    //roles retrieving
                    Set<Role> roles = null;
                    if (jsonUser.has("roles"))
                    if (jsonUser.get("roles").isJsonArray()) {
                        roles = new HashSet<>();
                        JsonArray jsonRoles = jsonUser.get("roles").getAsJsonArray();
                        for (int j = 0; j < jsonRoles.size(); j++) {
                            JsonObject jsonRole = jsonRoles.get(j).getAsJsonObject();
                            Role role = new Role();
                            if (jsonRole.has("id"))
                                role.setId(jsonRole.get("id").getAsLong());
                            else break;
                            if (jsonRole.has("name"))
                                role.setName(jsonRole.get("name").getAsString());
                            else break;
                            roles.add(role);
                        }
                    }
                    if (roles != null)
                        user.setRoles(roles);
                    users.add(user);
                } else {
                    user = findUserById(jsonObject.get("user").getAsLong());
                }
            }

            question.setUser(user);

            //retrieving likes and answers
            List<Like> likes = null;
            if (jsonObject.has("likes")) {
                if (jsonObject.get("likes").isJsonArray()) {
                    likes = new ArrayList<>();
                    JsonArray jsonLikes = jsonObject.get("likes").getAsJsonArray();
                    for (int j = 0; j < jsonLikes.size(); j++) {
                        JsonObject jsonLike = jsonLikes.get(j).getAsJsonObject();
                        Like like = new Like();
                        if (jsonLike.has("creationTime"))
                            like.setCreationTime(jsonLike.get("creationTime").getAsString());
                        else
                            break;
                        likes.add(like);
                    }
                }
            }
            if (likes != null)
                question.setLikes(likes);

            List<Answer> answers = null;
            if (jsonObject.has("answers")) {
                if (jsonObject.get("answers").isJsonArray()) {
                    answers = new ArrayList<>();
                    JsonArray jsonAnswers = jsonObject.get("answers").getAsJsonArray();
                    for (int j = 0; j < jsonAnswers.size(); j++) {
                        JsonObject jsonAnswer = jsonAnswers.get(j).getAsJsonObject();
                        Answer answer = new Answer();
                        if (jsonAnswer.has("creationTime"))
                            answer.setCreationTime(jsonAnswer.get("creationTime").getAsString());
                        if (jsonAnswer.has("id"))
                            answer.setId(jsonAnswer.get("id").getAsLong());
                        if (jsonAnswer.has("content"))
                            answer.setContent(jsonAnswer.get("content").getAsString());

                        //retrieving likes for answers
                        List<Like> answerLikes = null;
                        if (jsonAnswer.get("answerLikes").isJsonArray()) {
                            answerLikes = new ArrayList<>();
                            JsonArray jsonAnswerLikes = jsonAnswer.get("answerLikes").getAsJsonArray();
                            for (int k = 0; k < jsonAnswerLikes.size(); k++) {
                                JsonObject jsonAnswerLike = jsonAnswerLikes.get(k).getAsJsonObject();
                                Like like = new Like();
                                if (jsonAnswerLike.has("creationtime"))
                                    like.setCreationTime(jsonAnswerLike.get("creationTime").getAsString());
                                else break;
                                answerLikes.add(like);
                            }
                        }
                        if (answerLikes != null)
                            answer.setLikes(answerLikes);

                        answers.add(answer);
                    }
                }
            }
            if (answers != null)
                question.setAnswers(answers);


            questions.add(question);
        }

        return questions;
    }

    private User findUserById(Long id) {
        for (User sUser: users) {
            if (id.equals(sUser.getId()))
                return sUser;
        }
        return null;
    }
}
