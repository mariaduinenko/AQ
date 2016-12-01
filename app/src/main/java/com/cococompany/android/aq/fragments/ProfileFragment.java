package com.cococompany.android.aq.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.adapters.CustomFacultySpinnerAdapter;
import com.cococompany.android.aq.adapters.CustomSpecialitySpinnerAdapter;
import com.cococompany.android.aq.adapters.CustomUniversitySpinnerAdapter;
import com.cococompany.android.aq.adapters.CustomUuiSwipeAdapter;
import com.cococompany.android.aq.models.Faculty;
import com.cococompany.android.aq.models.Speciality;
import com.cococompany.android.aq.models.University;
import com.cococompany.android.aq.models.User;
import com.cococompany.android.aq.models.UserUniversityInfo;
import com.cococompany.android.aq.utils.FacultyService;
import com.cococompany.android.aq.utils.LoginPreferences;
import com.cococompany.android.aq.utils.UniversityService;
import com.cococompany.android.aq.utils.UserService;
import com.cococompany.android.aq.utils.UserUniversityInfoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static LoginPreferences preferences;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private long startTime = 0L,
                 finishTime = 0L;

    public static UserUniversityInfo[] userUniversityInfos = null;

//    private Long selectedUniversityId = -1L;
    private Long selectedUuiId = -1L;

    private ViewPager viewPager;
    private CustomUuiSwipeAdapter uuiSwipeAdapter;

    private UserUniversityInfoService uuiService = null;

    public static User user = null;

    public static int pagePosition = 0;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pagePosition = position;
                selectedUuiId = (userUniversityInfos[pagePosition] != null)? userUniversityInfos[pagePosition].getId() : -1L;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        uuiService = new UserUniversityInfoService(getContext());

        viewPager = (ViewPager) view.findViewById(R.id.uui_view_pager);
        viewPager.addOnPageChangeListener(onPageChangeListener);

        startTime = System.currentTimeMillis();
        //receiving preferences
        preferences = new LoginPreferences(getContext());
        final Long userId = preferences.getUserId();
//        String userPass = preferences.getUserPassword();
//        String userAvatar = preferences.getUserAvatar();
//        String userBirthdate = preferences.getUserBirthdate();
        String userEmail = preferences.getUserEmail();
        String userFirstname = preferences.getUserFirstname();
        String userLastname = preferences.getUserLastname();
        String userMiddlename = preferences.getUserMiddlename();
        String userNickname = preferences.getUserNickname();
//        Set<String> categories = preferences.getUserCategories();
        finishTime = System.currentTimeMillis();
        System.out.println("%\\_(^_^)_/%" + "load params|execution time:" + (finishTime - startTime));

        EditText etName = (EditText) view.findViewById(R.id.name);
        EditText etNickname = (EditText) view.findViewById(R.id.nickname);
        EditText etEmail = (EditText) view.findViewById(R.id.email);
        Button btnApply = (Button) view.findViewById(R.id.btn_apply);

        user = new User(userId);

        /*Setting up user related information
        * */
        String firstPart = "", secondPart = "", thirdPart = "";
        if (userLastname.length() > 1) {
            firstPart = userLastname.substring(0, 1).toUpperCase() + userLastname.substring(1) + " ";
        }
        if (userFirstname.length() > 1) {
            secondPart = userFirstname.substring(0, 1).toUpperCase() + userFirstname.substring(1) + " ";
        }
        if (userMiddlename.length() > 1) {
            thirdPart = userMiddlename.substring(0, 1).toUpperCase() + userMiddlename.substring(1);
        }

        etName.setText(firstPart + secondPart + thirdPart);
        etNickname.setText(userNickname);
        etEmail.setText(userEmail);

        int uuisCount = (preferences.getUserUniversityInfos() != null)? preferences.getUserUniversityInfos().size() : 0;
        if (uuisCount > 0) {
            startTime = System.currentTimeMillis();
            List<UserUniversityInfo> uuiList = preferences.getUserUniversityInfos();

            userUniversityInfos = new UserUniversityInfo[uuiList.size()];
            uuiList.toArray(userUniversityInfos);

            uuiSwipeAdapter = new CustomUuiSwipeAdapter(getContext());
            viewPager.setAdapter(uuiSwipeAdapter);
            finishTime = System.currentTimeMillis();
        }else {
            startTime = System.currentTimeMillis();
            userUniversityInfos = new UserUniversityInfo[1];
            UserUniversityInfo uui = new UserUniversityInfo();
            uui.setUser(new User(userId));
            userUniversityInfos[0] = uui;

            uuiSwipeAdapter = new CustomUuiSwipeAdapter(getContext());
            viewPager.setAdapter(uuiSwipeAdapter);
            finishTime = System.currentTimeMillis();
        }

        //listeners
        etNickname.addTextChangedListener(new NicknameTextChangedListener());
        etName.addTextChangedListener(new NameTextChangedListener());
        etEmail.addTextChangedListener(new EmailTextChangedListener());

        //Saving user profile
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = System.currentTimeMillis();
                UserService userService = new UserService(getContext());

                User user = new User();
                user.setId(userId);
                user.setEmail(preferences.getUserEmail());
                user.setFirstName(preferences.getUserFirstname());
                user.setLastName(preferences.getUserLastname());
                user.setMiddleName(preferences.getUserMiddlename());
                user.setNickname(preferences.getUserNickname());

                userService.lightUpdate(user);

                for (int i = 0; i < userUniversityInfos.length; i++) {
                    userUniversityInfos[i] = uuiService.updateUui(userUniversityInfos[i]);
                }

                finishTime = System.currentTimeMillis();
                String s = "Saved prefs:\nName:"+preferences.getUserLastname()+" "+preferences.getUserFirstname()+" "+preferences.getUserMiddlename()+"\nNickname:"+preferences.getUserNickname()+
                        "\nEmail:"+preferences.getUserEmail();
                showToast(view, s);
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    public class NicknameTextChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        @Override
        public void afterTextChanged(Editable editable) {
            EditText etNickname = (EditText) getView().findViewById(R.id.nickname);
            preferences.setUserNickname(etNickname.getText().toString());
        }
    }

    public class NameTextChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        @Override
        public void afterTextChanged(Editable editable) {
            EditText etName = (EditText) getView().findViewById(R.id.name);
            String[] nameParts = etName.getText().toString().split(" ");
            switch (nameParts.length) {
                case 1:
                    preferences.setUserLastname(nameParts[0]);
                    break;
                case 2:
                    preferences.setUserFirstname(nameParts[1]);
                    break;
                case 3:
                    preferences.setUserMiddlename(nameParts[2]);
                    break;
                default:
                    break;
            }
        }
    }

    public class EmailTextChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        @Override
        public void afterTextChanged(Editable editable) {
            EditText etEmail = (EditText) getView().findViewById(R.id.email);
            preferences.setUserEmail(etEmail.getText().toString());
        }
    }

    public void showToast(View view, String text) {
        //создаем и отображаем текстовое уведомление
        Toast toast = Toast.makeText(getContext(),
                text,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}
