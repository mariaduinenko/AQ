package com.cococompany.android.aq.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cococompany.android.aq.QuestionActivity;
import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.Question;
import com.cococompany.android.aq.models.Question1;
import com.cococompany.android.aq.services.QuestionService;

import java.util.ArrayList;

/**
 * Created by Valentin on 05.11.2016.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.QuestionHolder> {

    private ArrayList<Question1> questions;
    private Activity activity;

    public FeedAdapter(ArrayList<Question1> questions, Activity activity) {
        this.questions = questions;
        this.activity = activity;
    }

    @Override
    public QuestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item,parent,false);
        QuestionHolder questionHolder = new QuestionHolder(view);
        Log.d("Creation of item","TEST");
        return questionHolder;
    }

    @Override
    public void onBindViewHolder(QuestionHolder holder, int i) {
        final Question1 question = questions.get(i);

        ((TextView) holder.itemView.findViewById(R.id.question_title)).setText(question.getTitle());
        ((TextView) holder.itemView.findViewById(R.id.question_owner)).setText((question.getUser().getNickname()==null?question.getUser().getEmail():question.getUser().getNickname()));
        ((TextView) holder.itemView.findViewById(R.id.question_date)).setText(question.getCreationTime());
        ((TextView) holder.itemView.findViewById(R.id.count_likes)).setText(String.valueOf(question.getLikes().size()));
        ((TextView) holder.itemView.findViewById(R.id.comment_count)).setText(String.valueOf(question.getAnswers().size()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(activity, QuestionActivity.class);
//                activity.startActivity(intent);

                QuestionActivity.showQuestion(activity, QuestionActivity.class, question.getId(), view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class QuestionHolder extends RecyclerView.ViewHolder{
        QuestionHolder(View itemView) {
            super(itemView);
        }
    }
}
