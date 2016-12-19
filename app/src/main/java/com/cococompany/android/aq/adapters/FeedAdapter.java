package com.cococompany.android.aq.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cococompany.android.aq.QuestionActivity;
import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.Question;
import com.cococompany.android.aq.utils.OnLoadMoreListener;

import java.util.ArrayList;

/**
 * Created by Valentin on 05.11.2016.
 */
public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private final int VIEW_TYPE_NEW_QUESTIONS = 2;
    private ArrayList<Question> questions;
    private ArrayList<Question> newQuestions;
    private Activity activity;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount,firstFullVisibleItem;
    private RecyclerView mRecyclerView;
    private int testCount;

    @Override
    public int getItemViewType(int position) {
        if (questions.get(position) == null){
            if (position==0)
                return VIEW_TYPE_NEW_QUESTIONS;
            else
            return VIEW_TYPE_LOADING;
        }
        else
            return VIEW_TYPE_ITEM;
    }

    public void setmRecyclerView(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

        public FeedAdapter(ArrayList<Question> questions, Activity activity,RecyclerView mRecyclerView) {
        this.questions = questions;
        this.activity = activity;
            newQuestions = new ArrayList<Question>();
            testCount = 0;
            this.mRecyclerView = mRecyclerView;
            this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = recyclerView.getLayoutManager().getItemCount();
                    lastVisibleItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    firstFullVisibleItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                    Log.e("haint",totalItemCount+" <=> "+lastVisibleItem);
                    if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {

                        if (mOnLoadMoreListener != null) {
                            mOnLoadMoreListener.onLoadMore();
                        }
                        isLoading = true;
                    }
                }
            });
    }


    public ArrayList<Question> getNewQuestions() {
        return newQuestions;
    }

    public void setNewQuestions(ArrayList<Question> newQuestions) {
        this.newQuestions = newQuestions;
    }



    public int getTestCount() {
        return testCount;
    }

    public void setTestCount(int testCount) {
        this.testCount = testCount;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(this.activity).inflate(R.layout.question_item, parent, false);
            return new QuestionHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(this.activity).inflate(R.layout.loadind_item, parent, false);
            return new LoadingViewHolder(view);
        }else if (viewType == VIEW_TYPE_NEW_QUESTIONS){
            View view = LayoutInflater.from(this.activity).inflate(R.layout.new_questions_item, parent, false);
            return new NewQuestionsViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof QuestionHolder) {
            Log.e("haint","Q = "+questions.get(position).getId());
            if (questions.get(position).getUser()!=null)
                ((TextView) holder.itemView.findViewById(R.id.question_owner)).setText(questions.get(position).getUser().getFirstName() + " " + questions.get(position).getUser().getLastName());
                ((TextView) holder.itemView.findViewById(R.id.question_date)).setText(questions.get(position).getCreationTime());
                ((TextView) holder.itemView.findViewById(R.id.question_title)).setText(questions.get(position).getTitle());
                ((TextView) holder.itemView.findViewById(R.id.comment_count)).setText(Integer.toString(questions.get(position).getAnswers().size()));
                ((TextView) holder.itemView.findViewById(R.id.count_likes)).setText(Integer.toString(questions.get(position).getLikes().size()));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(activity, QuestionActivity.class);

                        intent.putExtra("question_id", questions.get(position).getId());
                        System.out.println("position " + position + " id  " + questions.get(position).getId());
                        activity.startActivity(intent);
                    }
                });
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        } else if (holder instanceof NewQuestionsViewHolder){
            NewQuestionsViewHolder newQuestionsViewHolder = (NewQuestionsViewHolder) holder;
            newQuestionsViewHolder.countOfNewQuestions.setText("You have "+Integer.toString(newQuestions.size())+" new questions");
            newQuestionsViewHolder.countOfNewQuestions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    questions.remove(0);
                    questions.addAll(0,newQuestions);
                    newQuestions = new ArrayList<Question>();
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return questions == null ? 0 : questions.size();
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
    public void setLoaded() {
        isLoading = false;
    }
    public static class QuestionHolder extends RecyclerView.ViewHolder{
        QuestionHolder(View itemView) {
            super(itemView);
        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

    public static class NewQuestionsViewHolder extends RecyclerView.ViewHolder{
        public TextView countOfNewQuestions;

        public NewQuestionsViewHolder(View itemView) {
            super(itemView);
            countOfNewQuestions = (TextView) itemView.findViewById(R.id.countOfNewQuestions);
        }
    }
}
