package com.cococompany.android.aq.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cococompany.android.aq.ContentActivity;
import com.cococompany.android.aq.QuestionActivity;
import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.Like;
import com.cococompany.android.aq.models.Question;
import com.cococompany.android.aq.models.User;
import com.cococompany.android.aq.utils.deserializers.FeedDeserializer;
import com.cococompany.android.aq.utils.deserializers.QuestionDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.internal.Internal;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Valentin on 23.11.2016.
 */

public class QuestionService {
    private int length = 8;

    private Retrofit retrofit;
    private AQService aqService;

    private final String projectBaseUrl = "https://pure-mesa-13823.herokuapp.com";

    public AQService getAqService() {
        return aqService;
    }

    public QuestionService(Context context) {
        Gson gson = null;

        if (context.getClass().equals(ContentActivity.class)) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(ArrayList.class, new FeedDeserializer());
            gson = gsonBuilder.create();
        }

        if (context.getClass().equals(QuestionActivity.class)) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Question.class, new QuestionDeserializer());
            gson = gsonBuilder.create();
        }

        if (gson == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(context.getResources().getString(R.string.project_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } else {
            retrofit = new Retrofit.Builder()
                    .baseUrl(context.getResources().getString(R.string.project_url))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }


        aqService = retrofit.create(AQService.class);
    }

    public ArrayList<Question> getQuestions(){
        ArrayList<Question> result = null;
        QuestionsTask questionsTask = new QuestionsTask();
        questionsTask.execute();

        try {
            result =  questionsTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (result == null)
            result = new ArrayList<>();

        return result;
    }

    public ArrayList<Question> getQuestionsInternal(){
        ArrayList<Question> result = null;
        QuestionsInternalTask questionsTask = new QuestionsInternalTask();
        questionsTask.execute();

        try {
            result =  questionsTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (result == null)
            result = new ArrayList<>();

        return result;
    }

    public ArrayList<Question> getNextQuestion(long id, int length){
        ArrayList<Question> result = null;
        NextQuestionTask nextQuestionTask = new NextQuestionTask();
        nextQuestionTask.execute((int)id,length);
        try {
            result =  nextQuestionTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Question getQuestionById(long id){
        Question result = null;
        SingleQuestionTask singleQuestionTask = new SingleQuestionTask();
        singleQuestionTask.execute(id);

        try {
            result = singleQuestionTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Question getQuestionInternalById(long id){
        Question result = null;
        SingleQuestionInternalTask singleQuestionTask = new SingleQuestionInternalTask();
        singleQuestionTask.execute(id);

        try {
            result = singleQuestionTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Question getQuestionLikingById(long id){
        Question result = null;
        SingleQuestionLikingTask singleQuestionTask = new SingleQuestionLikingTask();
        singleQuestionTask.execute(id);

        try {
            result = singleQuestionTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    public Question createQuestion(Question question){
        Question result = null;
        QuestionService.CreateQuestionTask createQuestionTask = new QuestionService.CreateQuestionTask();
        createQuestionTask.execute(question);
        try {
            result =  createQuestionTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return  result;
    }

    public void putLikeOnQuestion(long userId, long questionId){
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(projectBaseUrl + "/rest/questions/like/"+userId+"/"+questionId+"")
                .put(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                Log.d("Response", response.toString());
            }
        });
    }

    class QuestionsTask extends AsyncTask<Void,Void,ArrayList<Question>>{
        @Override
        protected ArrayList<Question> doInBackground(Void... voids) {
            ArrayList<Question> result = null;
            Call<ArrayList<Question>> call = getAqService().getQuestions(length);

            Response<ArrayList<Question>> response = null;

            try {
                response = call.execute();
                for (Question q: response.body()) {
                    System.out.println(q.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            result = response.body();

            return result;
        }
    }

    class QuestionsInternalTask extends AsyncTask<Void,Void,ArrayList<Question>>{

        @Override
        protected ArrayList<Question> doInBackground(Void... voids) {
            ArrayList<Question> result = null;
            Call<ArrayList<Question>> call = getAqService().getQuestionsInternal(length);

            Response<ArrayList<Question>> response = null;

            try {
                response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (response != null)
                result = response.body();

            return result;
        }
    }

    class SingleQuestionTask extends AsyncTask<Long,Void,Question>{

        @Override
        protected Question doInBackground(Long... integers) {
            Question result = null;
            Call<Question> call = getAqService().getQuestionById(integers[0]);

            Response<Question> response = null;

            try {
                response = call.execute();
                if (response.isSuccessful())
                    System.out.println("One QUESTION: "+response.body().toString());
                else
                    System.out.println("ERROR: "+response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }

            result = response.body();

            return result;
        }
    }

    class SingleQuestionInternalTask extends AsyncTask<Long,Void,Question>{
        @Override
        protected Question doInBackground(Long... integers) {
            Question result = null;
            Call<Question> call = getAqService().getQuestionInternalById(integers[0]);

            Response<Question> response = null;

            try {
                response = call.execute();
                if (response.isSuccessful())
                    System.out.println("One QUESTION: "+response.body().toString());
                else
                    System.out.println("ERROR: "+response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }

            result = response.body();

            return result;
        }
    }

    class SingleQuestionLikingTask extends AsyncTask<Long,Void,Question>{
        @Override
        protected Question doInBackground(Long... integers) {
            Question result = null;
            Call<Question> call = getAqService().getQuestionLikingById(integers[0]);

            Response<Question> response = null;

            try {
                response = call.execute();
                if (response.isSuccessful())
                    System.out.println("One QUESTION: "+response.body().toString());
                else
                    System.out.println("ERROR: "+response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }

            result = response.body();

            return result;
        }
    }

    class CreateQuestionTask extends AsyncTask<Question,Void,Question>{


        @Override
        protected Question doInBackground(Question... questions) {
            Question result = null;
            Call<Question> call = getAqService().createQuestion(questions[0]);

            Response<Question> response = null;

            try {
                response = call.execute();
                if (response.isSuccessful())
                    System.out.println("One Like: "+response.body().toString());
                else
                    System.out.println("ERROR: "+response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }

            result = response.body();

            return result;
        }
    }

    class NextQuestionTask extends  AsyncTask<Integer,Void,ArrayList<Question>>{

        @Override
        protected ArrayList<Question> doInBackground(Integer... integers) {
            ArrayList<Question> result = null;
            Call<ArrayList<Question>> call = getAqService().getNextQuestionsInFeedInternal((long)integers[0],integers[1]);

            Response<ArrayList<Question>> response = null;

            try {
                response = call.execute();
                result = response.body();
                if (response.isSuccessful())
                    System.out.println("One QUESTION: "+response.body().toString());
                else
                    System.out.println("ERROR: "+response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }
    }

}

