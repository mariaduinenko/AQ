package com.cococompany.android.aq.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.cococompany.android.aq.models.Like;
import com.cococompany.android.aq.models.Question;
import com.cococompany.android.aq.models.User;

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
    private int length = 8;
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

    public boolean putLikeOnQuestion(long userId, long questionId){
        boolean result = false;
        QuestionService.PutLikeTask putLikeTask = new PutLikeTask();
        putLikeTask.execute(userId, questionId);
        try {
            result =  putLikeTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return  result;
    }

    public boolean disLikeOnQuestion(long userId, long questionId){
        boolean result = false;
        QuestionService.DisLikeTask disLikeTask = new DisLikeTask();
        disLikeTask.execute(userId, questionId);
        try {
            result =  disLikeTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return  result;
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
            Call<ArrayList<Question>> call = getAqService().getNextQuestionsInFeed((long)integers[0],integers[1]);

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


    class PutLikeTask extends AsyncTask<Long,Void,Boolean>{


        @Override
        protected Boolean doInBackground(Long... id) {
            Boolean result = null;
            Call<Like> call = getAqService().putLikeOnQuestion(id[0], id[1]);
            Response<Like> response = null;

            try {
                response = call.execute();
                result =  call.isExecuted();
                if (response.isSuccessful()){
                    System.out.println("One DisLike: "+response.body().toString());
                }
                else
                    System.out.println("ERROR: "+response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }



            return result;
        }
    }

    class DisLikeTask extends AsyncTask<Long,Void,Boolean>{


        @Override
        protected Boolean doInBackground(Long... id) {
            Boolean result = null;
            Call<Like> call = getAqService().disLikeOnQuestion(id[0], id[1]);

            Response<Like> response = null;

            try {
                response = call.execute();
                result = response.isSuccessful();
                if (response.isSuccessful()){
                    System.out.println("One QUESTION: "+response.body().toString());
                }
                else
                    System.out.println("ERROR: "+response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }



            return result;
        }
    }
    }

