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
    private ArrayList<Question> questions;
    private Activity activity;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount,firstFullVisibleItem;
    private RecyclerView mRecyclerView;

    @Override
    public int getItemViewType(int position) {
        return questions.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(this.activity).inflate(R.layout.question_item, parent, false);
            return new QuestionHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(this.activity).inflate(R.layout.loadind_item, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof QuestionHolder) {
            ((TextView) holder.itemView.findViewById(R.id.question_owner)).setText(questions.get(position).getUser().getFirstName()+" "+questions.get(position).getUser().getLastName());
            ((TextView) holder.itemView.findViewById(R.id.question_date)).setText(questions.get(position).getCreationTime());
            ((TextView) holder.itemView.findViewById(R.id.question_title)).setText(questions.get(position).getTitle());
            ((TextView) holder.itemView.findViewById(R.id.comment_count)).setText(Integer.toString(questions.get(position).getAnswers().size()));
            ((TextView) holder.itemView.findViewById(R.id.count_likes)).setText(Integer.toString(questions.get(position).getLikes().size()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, QuestionActivity.class);

                    intent.putExtra("question_id",questions.get(position).getId());
                    System.out.println("position "+position+" id  "+questions.get(position).getId());
                    activity.startActivity(intent);
                }
            });
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
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
}
