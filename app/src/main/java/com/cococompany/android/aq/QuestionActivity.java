package com.cococompany.android.aq;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cococompany.android.aq.models.Question;
import com.cococompany.android.aq.utils.DownloadAvatarTask;
import com.cococompany.android.aq.utils.QuestionService;
import com.cococompany.android.aq.utils.UIutils;
import com.joooonho.SelectableRoundedImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

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
    private LinearLayout answer_container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        UIutils.setToolbarWithBackButton(R.id.toolbar, this);
        question_id = getIntent().getLongExtra("question_id", -1);
        QuestionService questionService = new QuestionService(this);
        current_question = questionService.getQuestionById(question_id);
        name_of_asker = (TextView) findViewById(R.id.name_of_asker_view);
        question_date = (TextView) findViewById(R.id.question_date);
        title_of_question = (TextView) findViewById(R.id.question_title);
        comment_of_question = (TextView) findViewById(R.id.question_commnet);
        count_of_likes = (TextView) findViewById(R.id.question_count_of_likes);
        like = (ImageView) findViewById(R.id.like);
        answer_container = (LinearLayout) findViewById(R.id.answers_container);
        name_of_asker.setText(current_question.getUser().getFirstName() + " " + current_question.getUser().getLastName());
        question_date.setText(current_question.getCreationTime());
        title_of_question.setText(current_question.getTitle());
        comment_of_question.setText(current_question.getComment());
        count_of_likes.setText(Integer.toString(current_question.getLikes().size()));
        if (current_question.getAnswers().size() != 0) {
            for (int i = 0; i < current_question.getAnswers().size(); i++) {
                View answer = getLayoutInflater().inflate(R.layout.answer_item, null, false);
                SelectableRoundedImageView selectableRoundedImageView = (SelectableRoundedImageView) answer.findViewById(R.id.answer_avatar);
                DownloadAvatarTask downloadAvatarTask = new DownloadAvatarTask();
                downloadAvatarTask.execute(current_question.getAnswers().get(i).getUser().getAvatar());
                if (current_question.getAnswers().get(i).getUser().getAvatar() != null){
                    try {
                        selectableRoundedImageView.setImageBitmap(downloadAvatarTask.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                TextView name = (TextView) answer.findViewById(R.id.answer_user_name);
                name.setText(current_question.getAnswers().get(i).getUser().getFirstName() + " " + current_question.getAnswers().get(i).getUser().getLastName());
                TextView date = (TextView) answer.findViewById(R.id.date_of_answer);
                date.setText(current_question.getAnswers().get(i).getCreationTime());
                TextView content = (TextView) answer.findViewById(R.id.answer_body);
                content.setText(current_question.getAnswers().get(i).getContent());
                answer_container.addView(answer);
            }

        }
    }



}