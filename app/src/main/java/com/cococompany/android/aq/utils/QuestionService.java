package com.cococompany.android.aq.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.cococompany.android.aq.models.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import okhttp3.internal.Internal;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Valentin on 23.11.2016.
 */

public class QuestionService extends AbsrtactService {
    public QuestionService(Context context) {
        super(context);
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

    class QuestionsTask extends AsyncTask<Void,Void,ArrayList<Question>>{

        @Override
        protected ArrayList<Question> doInBackground(Void... voids) {
            ArrayList<Question> result = null;
            Call<ArrayList<Question>> call = getAqService().getQuestions();

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
    }

