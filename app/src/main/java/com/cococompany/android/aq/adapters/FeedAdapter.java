package com.cococompany.android.aq.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cococompany.android.aq.QuestionActivity;
import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.Question;

import java.util.ArrayList;

/**
 * Created by Valentin on 05.11.2016.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.QuestionHolder> {

    private ArrayList<Question> questions;
    private Activity activity;

    public FeedAdapter(ArrayList<Question> questions, Activity activity) {
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
    public void onBindViewHolder(QuestionHolder holder, final int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, QuestionActivity.class);
                    //intent.putExtra("question_id",questions.get(position).getId());
                    activity.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    public static class QuestionHolder extends RecyclerView.ViewHolder{
        QuestionHolder(View itemView) {
            super(itemView);
        }
    }
}
