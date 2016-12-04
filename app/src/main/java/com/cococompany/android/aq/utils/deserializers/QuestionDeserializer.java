package com.cococompany.android.aq.utils.deserializers;

import com.cococompany.android.aq.models.Answer;
import com.cococompany.android.aq.models.Like;
import com.cococompany.android.aq.models.Question;
import com.cococompany.android.aq.models.User;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandrmyagkiy on 04.12.16.
 */

public class QuestionDeserializer implements JsonDeserializer<Question> {
    private ArrayList<User> users = new ArrayList<>();

    @Override
    public Question deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Question question = new Question();
        JsonObject jsonObject = json.getAsJsonObject();

        question.setId(jsonObject.get("id").getAsLong());
        question.setTitle(jsonObject.get("title").getAsString());
        question.setComment(jsonObject.get("comment").getAsString());
        User user = new User();
        if (jsonObject.get("user").isJsonObject()) {
            JsonObject jsonUser = jsonObject.get("user").getAsJsonObject();
            user.setId(jsonUser.get("id").getAsLong());
            user.setEmail(jsonUser.get("email").getAsString());
            user.setFirstName(jsonUser.get("firstName").getAsString());
            user.setLastName(jsonUser.get("lastName").getAsString());
            user.setMiddleName(jsonUser.get("middleName").getAsString());
            user.setNickname(jsonUser.get("nickname").getAsString());
            user.setActive(jsonUser.get("active").getAsBoolean());
            users.add(user);
        } else {
            user = findUserById(jsonObject.get("user").getAsLong());
        }

        question.setUser(user);

        //retrieving likes and answers
        List<Like> likes = null;
        if (jsonObject.get("likes").isJsonArray()) {
            likes = new ArrayList<>();
            JsonArray jsonLikes = jsonObject.get("likes").getAsJsonArray();
            for (int j = 0; j < jsonLikes.size(); j++) {
                JsonObject jsonLike = jsonLikes.get(j).getAsJsonObject();
                Like like = new Like();
                like.setCreationTime(jsonLike.get("creationTime").getAsString());
                likes.add(like);
            }
        }
        if (likes != null)
            question.setLikes(likes);

        List<Answer> answers = null;
        if (jsonObject.get("answers").isJsonArray()) {
            answers = new ArrayList<>();
            JsonArray jsonAnswers = jsonObject.get("answers").getAsJsonArray();
            for (int j = 0; j < jsonAnswers.size(); j++) {
                JsonObject jsonAnswer = jsonAnswers.get(j).getAsJsonObject();
                Answer answer = new Answer();
                answer.setId(jsonAnswer.get("id").getAsLong());
                answer.setContent(jsonAnswer.get("content").getAsString());

                //retrieving likes for answers
                List<Like> answerLikes = null;
                if (jsonAnswer.get("answerLikes").isJsonArray()) {
                    answerLikes = new ArrayList<>();
                    JsonArray jsonAnswerLikes = jsonAnswer.get("answerLikes").getAsJsonArray();
                    for (int k = 0; k < jsonAnswerLikes.size(); k++) {
                        JsonObject jsonAnswerLike = jsonAnswerLikes.get(k).getAsJsonObject();
                        if (!jsonAnswerLike.isJsonNull()) {
                            if (jsonAnswerLike.has("creationTime")) {
                                Like like = new Like();
                                like.setCreationTime(jsonAnswerLike.get("creationTime").getAsString());
                                answerLikes.add(like);
                            }
                        } else if (jsonAnswerLike.isJsonNull() && k==0) {
                            answerLikes = null;
                            break;
                        }
                    }
                }
                if (answerLikes != null)
                    answer.setLikes(answerLikes);

                User answerUser = new User();
                if (jsonAnswer.get("user").isJsonObject()) {
                    JsonObject jsonUser = jsonAnswer.get("user").getAsJsonObject();
                    answerUser.setId(jsonUser.get("id").getAsLong());
                    answerUser.setEmail(jsonUser.get("email").getAsString());
                    answerUser.setFirstName(jsonUser.get("firstName").getAsString());
                    answerUser.setLastName(jsonUser.get("lastName").getAsString());
                    answerUser.setMiddleName(jsonUser.get("middleName").getAsString());
                    answerUser.setNickname(jsonUser.get("nickname").getAsString());
                    answerUser.setActive(jsonUser.get("active").getAsBoolean());
                    users.add(answerUser);
                } else {
                    answerUser = findUserById(jsonAnswer.get("user").getAsLong());
                }

                answer.setUser(answerUser);

                answers.add(answer);
            }
        }
        if (answers != null)
            question.setAnswers(answers);


        return question;
    }

    private User findUserById(Long id) {
        for (User sUser: users) {
            if (id == sUser.getId())
                return sUser;
        }
        return null;
    }
}
