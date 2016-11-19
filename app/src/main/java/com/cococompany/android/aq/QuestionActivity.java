package com.cococompany.android.aq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.cococompany.android.aq.models.Question1;
import com.cococompany.android.aq.services.QuestionService;
import com.cococompany.android.aq.utils.UIutils;

public class QuestionActivity extends AppCompatActivity {

    private static Question1 question = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        UIutils.setToolbarWithBackButton(R.id.toolbar,this);

        TextView title = (TextView) findViewById(R.id.question_title);
        if (question != null)
            title.setText(question.getTitle());
    }

    public static void showQuestion(Activity from, Class<?> to, Long questionId, View view) {
        Intent intent = new Intent(from, to);
        from.startActivity(intent);

        QuestionService service = new QuestionService(view);
        question = service.getQuestionById(questionId);
    }

}
