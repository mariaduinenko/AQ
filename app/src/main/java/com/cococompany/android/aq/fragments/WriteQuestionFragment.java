package com.cococompany.android.aq.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.models.Category;
import com.cococompany.android.aq.models.Question;
import com.cococompany.android.aq.models.User;
import com.cococompany.android.aq.utils.LoginPreferences;
import com.cococompany.android.aq.services.QuestionService;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.lines;


public class WriteQuestionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button ask;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LoginPreferences loginPreferences;
    private QuestionService questionServise;
    private EditText bodyOfQuestion;
    private EditText tags;

    public WriteQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WriteQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WriteQuestionFragment newInstance(String param1, String param2) {
        WriteQuestionFragment fragment = new WriteQuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPreferences = new LoginPreferences(getContext());
        questionServise = new QuestionService(getContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Застосовуємо макет до поточного фрагмента
        View v = inflater.inflate(R.layout.fragment_write_question, container, false);
        bodyOfQuestion = (EditText) v.findViewById(R.id.question_body);
        tags = (EditText) v.findViewById(R.id.tags);
        ask = (Button) v.findViewById(R.id.ask_question_button);
        ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CategoriesChooseFragment fragment = new CategoriesChooseFragment();
                fragment.show(getActivity().getSupportFragmentManager(), "Categories choose");

                //Валідація задаваного питання

                Question question = new Question();
                question.setUser(loginPreferences.getUser());
                question.setTitle(tags.getText().toString());
                question.setComment(bodyOfQuestion.getText().toString());

                ((CategoriesChooseFragment) fragment).setQuestion(question);

//                String separator = "\n";
//                String[] lines = bodyOfQuestion.getText().toString().split(separator);
//                Question question = new Question();
//                User user = new User();
//                user.setId(loginPreferences.getUser().getId());
//                List<Category> categories = new ArrayList<Category>();
//                question.setUser(user);
//                question.setCategories(categories);
//                question.setTitle(lines[0]);
//                if (lines.length>1){
//                    StringBuilder builder = new StringBuilder();
//                    for (int i = 1; i < lines.length; i++) {
//                        builder.append(lines[i]);
//                    }
//                    question.setComment(builder.toString());
//                } else {
//                    question.setComment(" ");
//                }
//                questionServise.createQuestion(question);
//
//                //clear fields
//                bodyOfQuestion.getText().clear();
//                tags.getText().clear();
//                showToast(getContext(), WriteQuestionFragment.this.getView(), "Your question is successfully added!");

                //update feed
//                FeedFragment feedFragment = (FeedFragment)
            }
        });
        return v;
    }

    public void showToast(Context ctx, View view, String text) {
        //создаем и отображаем текстовое уведомление
        Toast toast = Toast.makeText(ctx,
                text,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
