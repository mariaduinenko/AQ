package com.cococompany.android.aq.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.adapters.CustomUuiSwipeAdapter;
import com.cococompany.android.aq.models.User;
import com.cococompany.android.aq.models.UserUniversityInfo;
import com.cococompany.android.aq.utils.LoginPreferences;
import com.cococompany.android.aq.utils.UserService;
import com.cococompany.android.aq.utils.UserUniversityInfoService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

    //    private Long selectedUniversityId = -1L;

    private SimpleDateFormat dateFormatter;

    public static List<UserUniversityInfo> userUniversityInfos = new ArrayList<>();
//    public static UserUniversityInfo[] userUniversityInfos = null;

//    private Long selectedUniversityId = -1L;

    private Long selectedUuiId = -1L;

    public static ViewPager viewPager;
    public static CustomUuiSwipeAdapter uuiSwipeAdapter;

    private UserUniversityInfoService uuiService = null;

    public static User user = null;

    public static int pagePosition = 0;

    private EditText etBirthdate;

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
                selectedUuiId = (userUniversityInfos.get(pagePosition) != null)? userUniversityInfos.get(pagePosition).getId() : -1L;
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
        String userBdate = preferences.getUserBirthdate();
//        Set<String> categories = preferences.getUserCategories();
        finishTime = System.currentTimeMillis();
        System.out.println("%\\_(^_^)_/%" + "load params|execution time:" + (finishTime - startTime));

        EditText etName = (EditText) view.findViewById(R.id.name);
        EditText etNickname = (EditText) view.findViewById(R.id.nickname);
        EditText etEmail = (EditText) view.findViewById(R.id.email);
        etBirthdate = (EditText) view.findViewById(R.id.birthdate);
        Button btnApply = (Button) view.findViewById(R.id.btn_apply);
        Button btnChangePassword = (Button) view.findViewById(R.id.btn_change_password);

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
        etBirthdate.setText(userBdate);

        etBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fragment = new DatePickerFragment();
                ((DatePickerFragment) fragment).setBirthdateType();
                fragment.show(getActivity().getFragmentManager(), "Date Picker");
            }
        });

        final int uuisCount = (preferences.getUserUniversityInfos() != null)? preferences.getUserUniversityInfos().size() : 0;
        if (uuisCount > 0) {
            startTime = System.currentTimeMillis();
            userUniversityInfos = new ArrayList<>(preferences.getUserUniversityInfos());

            uuiSwipeAdapter = new CustomUuiSwipeAdapter(getContext());
//            uuiSwipeAdapter = new CustomUuiSwipeAdapter(getContext(), this);
            viewPager.setAdapter(uuiSwipeAdapter);

            for (int i = 0; i < userUniversityInfos.size(); i++) {
                final int pos = i;

                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final RelativeLayout item_view = (RelativeLayout) layoutInflater.inflate(R.layout.profile_uui_swipe_layout, null);
                uuiSwipeAdapter.addView(item_view, i);

                EditText etEntranceDate = (EditText) item_view.findViewById(R.id.etEntranceDate);
                etEntranceDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogFragment fragment = new DatePickerFragment();
                        ((DatePickerFragment) fragment).setEntranceType();
                        fragment.show(getActivity().getFragmentManager(), "Date Picker");
                    }
                });
                String entranceDate = userUniversityInfos.get(i).getEntranceDate();
                if (entranceDate != null && !entranceDate.isEmpty()) {
                    etEntranceDate.setText(entranceDate);
                }

                EditText etGraduationDate = (EditText) item_view.findViewById(R.id.etGraduationDate);
                etGraduationDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DialogFragment fragment = new DatePickerFragment();
                        ((DatePickerFragment) fragment).setGraduationType();
                        fragment.show(getActivity().getFragmentManager(), "Date Picker");
                    }
                });
                String graduationDate = userUniversityInfos.get(i).getGraduationDate();
                if (graduationDate != null && !graduationDate.isEmpty()) {
                    etGraduationDate.setText(graduationDate);
                }

                registerForContextMenu(item_view);
                Button btnAddUui = (Button) item_view.findViewById(R.id.btnAddUui);
                btnAddUui.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UserUniversityInfo uui = new UserUniversityInfo();
                        uui.setUser(user);
                        uui = uuiService.createUui(uui);
                        userUniversityInfos.add(uui);

                        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        LinearLayout item_view = (LinearLayout) layoutInflater.inflate(R.layout.profile_uui_swipe_layout, null);

                        uuiSwipeAdapter.addView(item_view, userUniversityInfos.size()-1);
                        registerForContextMenu(item_view);
                        Button btnAddUui = (Button) item_view.findViewById(R.id.btnAddUui);
                        btnAddUui.setOnClickListener(this);
                        uuiSwipeAdapter.changeTotalIcon();
                        uuiSwipeAdapter.notifyDataSetChanged();
                        viewPager.setCurrentItem(userUniversityInfos.size()-1);
                    }
                });

                uuiSwipeAdapter.notifyDataSetChanged();
            }
            finishTime = System.currentTimeMillis();
        }else {
            startTime = System.currentTimeMillis();
            userUniversityInfos = new ArrayList<>();
            UserUniversityInfo uui = new UserUniversityInfo();
            uui.setUser(new User(userId));
            userUniversityInfos.add(uuiService.createUui(uui));

            uuiSwipeAdapter = new CustomUuiSwipeAdapter(getContext());
//            uuiSwipeAdapter = new CustomUuiSwipeAdapter(getContext(), this);
            viewPager.setAdapter(uuiSwipeAdapter);
            finishTime = System.currentTimeMillis();
        }

        //listeners
        etNickname.addTextChangedListener(new NicknameTextChangedListener());
        etName.addTextChangedListener(new NameTextChangedListener());
        etEmail.addTextChangedListener(new EmailTextChangedListener());

        //change password - open Dialog
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fragment = new PasswordChangeFragment();
                fragment.show(getActivity().getFragmentManager(), "Password change");
            }
        });

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

                user.setBirthdate(etBirthdate.getText().toString());

                userService.lightUpdate(user);

                for (int i = 0; i < userUniversityInfos.size(); i++) {
                    userUniversityInfos.get(i).setUser(user);
                    userUniversityInfos.set(i, uuiService.updateUui(userUniversityInfos.get(i)));
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

    public void addView(View newPage)
    {
        int pageIndex = uuiSwipeAdapter.addView (newPage);
        viewPager.setCurrentItem(pageIndex, true);
    }

    public void removeView(View defunctPage)
    {
        int pageIndex = uuiSwipeAdapter.removeView(viewPager, defunctPage);
        if (pageIndex == uuiSwipeAdapter.getCount())
            pageIndex--;
        viewPager.setCurrentItem(pageIndex);
    }

    public View getCurrentPage()
    {
        return uuiSwipeAdapter.getView(viewPager.getCurrentItem());
    }

    public void setCurrentPage(View pageToShow)
    {
        viewPager.setCurrentItem(uuiSwipeAdapter.getItemPosition (pageToShow), true);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Context Menu");
        menu.add(0, v.getId(), 0, "Remove University Info");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Remove University Info") {
            uuiService.removeUui(userUniversityInfos.get(pagePosition).getId());
            //remove ui components too
            uuiSwipeAdapter.removeView(viewPager, pagePosition);
            userUniversityInfos.remove(pagePosition);
            uuiSwipeAdapter.changeTotalIcon();
//            Toast.makeText(getContext(), "Remove University Info invoked. Current page = " + pagePosition, Toast.LENGTH_SHORT).show();
            uuiSwipeAdapter.notifyDataSetChanged();
        } else {
            return false;
        }
        return true;
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