package com.cococompany.android.aq;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cococompany.android.aq.models.Question;
import com.cococompany.android.aq.utils.DownloadAvatarTask;
import com.cococompany.android.aq.utils.LoginPreferences;
import com.cococompany.android.aq.services.QuestionService;
import com.cococompany.android.aq.utils.UIutils;
import com.joooonho.SelectableRoundedImageView;

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
    private LoginPreferences loginPreferences;
    private boolean isMeLikeOwner = false;
    private EditText answerEdit;
    private ProgressBar answerProgress;
    private ImageView answerSend;
    private boolean send = true;
    private Handler h;

    private QuestionService questionService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        UIutils.setToolbarWithBackButton(R.id.toolbar, this);
        question_id = getIntent().getLongExtra("question_id", -1);
        questionService = new QuestionService(this);
        loginPreferences = new LoginPreferences(this);
        current_question = questionService.getQuestionLikingById(question_id);
        name_of_asker = (TextView) findViewById(R.id.name_of_asker_view);
        question_date = (TextView) findViewById(R.id.question_date);
        title_of_question = (TextView) findViewById(R.id.question_title);
        comment_of_question = (TextView) findViewById(R.id.question_commnet);
        count_of_likes = (TextView) findViewById(R.id.question_count_of_likes);
        like = (ImageView) findViewById(R.id.like);
        answerEdit = (EditText) findViewById(R.id.sendAnswer_et);
        answerSend = (ImageView) findViewById(R.id.sendAnswer_button);
        isMeLikeOwner = isMyLike();
        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what ==1){
                    Log.e("haint","Handle msg");
                    refreshCurrentQuestion();
                    answerProgress.setVisibility(View.INVISIBLE);
                }
            };
        };
        answerProgress = (ProgressBar) findViewById(R.id.progressOfAnswer);
        if (isMeLikeOwner)
            like.setImageResource(R.drawable.own_like);
        else
            like.setImageResource(R.drawable.like);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(loginPreferences.getUser().getId());
                if (!isMeLikeOwner){
                    like.setImageResource(R.drawable.own_like);
                    count_of_likes.setText(Integer.toString(Integer.valueOf(count_of_likes.getText().toString())+1));
                    isMeLikeOwner = true;
//                    current_question = questionService.getQuestionLikingById(question_id);
                }
                else{
                    like.setImageResource(R.drawable.like);
                    count_of_likes.setText(Integer.toString(Integer.valueOf(count_of_likes.getText().toString())-1));
                    if (send) {
                        questionService.putLikeOnQuestion(loginPreferences.getUser().getId(), current_question.getId());
                        send = false;
                    }
                    isMeLikeOwner = false;
//                    current_question = questionService.getQuestionLikingById(question_id);
                }
            }
        });

        answerSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //відправка відповіді
                questionService.postAnswerOnQuestion(answerEdit.getText().toString(),loginPreferences.getUser().getId(),current_question.getId(),answerProgress,h);
                answerEdit.setText("");
                answerProgress.setVisibility(View.VISIBLE);



            }
        });

        answer_container = (LinearLayout) findViewById(R.id.answers_container);
        name_of_asker.setText(current_question.getUser().getFirstName() + " " + current_question.getUser().getLastName());
        question_date.setText(current_question.getCreationTime());
        title_of_question.setText(current_question.getTitle());
        comment_of_question.setText(current_question.getComment());
        count_of_likes.setText(Integer.toString(current_question.getLikes().size()));
        showAnswers();

    }


    public void refreshCurrentQuestion(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                current_question = questionService.getQuestionInternalById(question_id);
                System.out.println("Current q answers size "+current_question.getAnswers().size());
                answer_container.removeAllViews();
                showAnswers();
            }
        });
        thread.run();
    }
    public void showAnswers(){
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

    @Override
    public void onBackPressed() {
        if (send) {
            questionService.putLikeOnQuestion(loginPreferences.getUser().getId(), current_question.getId());
            send = false;
        }
        super.onBackPressed();
    }

    public boolean isMyLike(){
        int count = 0;

        for (int i = 0; i < current_question.getLikes().size(); i++) {
            if (current_question.getLikes().get(i).getUser() != null && current_question.getLikes().get(i).getUser().getId().equals(loginPreferences.getUser().getId())) {
                return true;
            }
        }
        return false;
    }
}


