package com.cococompany.android.aq;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cococompany.android.aq.models.Question;
import com.cococompany.android.aq.utils.QuestionService;
import com.cococompany.android.aq.utils.UIutils;
import com.joooonho.SelectableRoundedImageView;

public class QuestionActivity extends AppCompatActivity {
    private long question_id;
    private Question current_question;
    private SelectableRoundedImageView avatar;
    private TextView name_of_asker;
    private TextView question_date;
    private TextView title_of_question;
    private TextView comment_of_question;
    private ImageView like;
    private TextView count_of_likes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        UIutils.setToolbarWithBackButton(R.id.toolbar,this);
        question_id = getIntent().getLongExtra("question_id",-1);
        QuestionService questionService = new QuestionService(this);
        current_question = questionService.getQuestionById(question_id);
        name_of_asker = (TextView) findViewById(R.id.name_of_asker_view);
        question_date = (TextView) findViewById(R.id.question_date);
        title_of_question = (TextView) findViewById(R.id.question_title);
        comment_of_question = (TextView) findViewById(R.id.question_commnet);
        count_of_likes = (TextView) findViewById(R.id.question_count_of_likes);
        like =  (ImageView) findViewById(R.id.like);

        name_of_asker.setText(current_question.getUser().getFirstName()+" "+current_question.getUser().getLastName());
        question_date.setText(current_question.getCreationTime());
        title_of_question.setText(current_question.getTitle());
        comment_of_question.setText(current_question.getComment());
        count_of_likes.setText(Integer.toString(current_question.getAnswers().size()));
    }

}
