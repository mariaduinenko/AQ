package com.cococompany.android.aq.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.adapters.FeedAdapter;
import com.cococompany.android.aq.models.Question;
import com.cococompany.android.aq.utils.OnLoadMoreListener;
import com.cococompany.android.aq.services.QuestionService;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class FeedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView feedRecyclerView;
    private FeedAdapter feedAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Question> questions;
    private QuestionService questionService;
    private ArrayList<Question> newQustions;
    private int testCount;
    private DownloadNewQuestionTask downloadNewQuestionTask;
    private Timer timer;
    //private OnFragmentInteractionListener mListener;

    public FeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedFragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionService = new QuestionService(getContext());
        questions = questionService.getQuestionsInternal();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        downloadNewQuestionTask = new DownloadNewQuestionTask();
        timer = new Timer();
        //timer.schedule(downloadNewQuestionTask, 2000, 2000);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("haint", "Create view");
        View v = inflater.inflate(R.layout.fragment_feed, container, false);
        feedRecyclerView = (RecyclerView) v.findViewById(R.id.feed);
        feedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        feedAdapter = new FeedAdapter(questions, getActivity(), feedRecyclerView);
        feedRecyclerView.setAdapter(feedAdapter);
        feedAdapter.setmRecyclerView(feedRecyclerView);
        feedAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.e("haint", "Load More");
                feedAdapter.getQuestions().add(null);
                feedAdapter.notifyItemInserted(feedAdapter.getQuestions().size() - 1);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("haint", "Load More 2");
                        //Remove loading item
                        feedAdapter.getQuestions().remove(feedAdapter.getQuestions().size() - 1);
                        feedAdapter.notifyItemRemoved(feedAdapter.getQuestions().size());

                        //Load data
                        long index = feedAdapter.getQuestions().get(feedAdapter.getQuestions().size()-1).getId();
                        int lastPosition = feedAdapter.getItemCount()-1;
                        Log.e("haint", "start loading");
                        ArrayList<Question> nextQuestions = questionService.getNextQuestion(index,6);
                        Log.e("haint", "end loading");
                        if (nextQuestions!=null)
                            for (int i = 0; i < nextQuestions.size(); i++) {
                                feedAdapter.getQuestions().add(nextQuestions.get(i));
                            }
                        Log.e("haint", "added to local data");

                        feedAdapter.notifyDataSetChanged();
                        feedAdapter.setLoaded();
                        //feedRecyclerView.getLayoutManager().scrollToPosition(1);
                    }
                } ,2000);


            }

        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                questions = questionService.getQuestionsInternal();
                feedAdapter.setQuestions(questions);
                feedAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    class DownloadNewQuestionTask extends TimerTask{

        @Override
        public void run() {
            if (feedAdapter.getQuestions().size()>0){
            //Long lastId = feedAdapter.getQuestions().get(0).getId();
            if (feedAdapter.getQuestions().get(0)!=null)
            feedAdapter.getQuestions().add(0,null);

            if (feedAdapter.getQuestions().size()>0)
            //feedAdapter.setTestCount(feedAdapter.getTestCount()+5);

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    feedAdapter.notifyDataSetChanged();
                }
            });
            }

        }
    }
}



