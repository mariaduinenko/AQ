//package com.cococompany.android.aq.fragments;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import com.cococompany.android.aq.R;
//import com.cococompany.android.aq.adapters.CustomFacultySpinnerAdapter;
//import com.cococompany.android.aq.adapters.CustomUniversitySpinnerAdapter;
//import com.cococompany.android.aq.models.Faculty;
//import com.cococompany.android.aq.models.Speciality;
//import com.cococompany.android.aq.models.University;
//import com.cococompany.android.aq.models.User;
//import com.cococompany.android.aq.models.UserUniversityInfo;
//import com.cococompany.android.aq.utils.FacultyService;
//import com.cococompany.android.aq.utils.LoginPreferences;
//import com.cococompany.android.aq.utils.UniversityService;
//import com.cococompany.android.aq.utils.UserService;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Set;
//
//public class ProfileFragment_ extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    public static LoginPreferences preferences;
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private Spinner spUniversity = null;
//    private Spinner spFaculty = null;
//
//    private CustomUniversitySpinnerAdapter adapter;
//    private CustomFacultySpinnerAdapter facultyAdapter;
//
//    private List<University> universitiesData = null;
//    private List<Faculty> facultiesData = null;
//
//    public ProfileFragment_() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ProfileFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ProfileFragment_ newInstance(String param1, String param2) {
//        ProfileFragment_ fragment = new ProfileFragment_();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        View view = inflater.inflate(R.layout.fragment_profile, container, false);
//
//        configureUniversitiesData();
//        configureSpinner(view);
//
//        //receiving preferences
//        preferences = new LoginPreferences(getContext());
//        final Long userId = preferences.getUserId();
//        String userPass = preferences.getUserPassword();
//        String userAvatar = preferences.getUserAvatar();
//        String userBirthdate = preferences.getUserBirthdate();
//        String userEmail = preferences.getUserEmail();
//        String userFirstname = preferences.getUserFirstname();
//        String userLastname = preferences.getUserLastname();
//        String userMiddlename = preferences.getUserMiddlename();
//        String userNickname = preferences.getUserNickname();
//        Set<String> categories = preferences.getUserCategories();
//
//        EditText etName = (EditText) view.findViewById(R.id.name);
//        EditText etNickname = (EditText) view.findViewById(R.id.nickname);
//        EditText etEmail = (EditText) view.findViewById(R.id.email);
//        EditText etSpeciality = (EditText) view.findViewById(R.id.speciality_et);
//        Button btnApply = (Button) view.findViewById(R.id.btn_apply);
//
//        String firstPart = "", secondPart = "", thirdPart = "";
//        if (userLastname.length() > 1) {
//            firstPart = userLastname.substring(0, 1).toUpperCase() + userLastname.substring(1) + " ";
//        }
//        if (userFirstname.length() > 1) {
//            secondPart = userFirstname.substring(0, 1).toUpperCase() + userFirstname.substring(1) + " ";
//        }
//        if (userMiddlename.length() > 1) {
//            thirdPart = userMiddlename.substring(0, 1).toUpperCase() + userMiddlename.substring(1);
//        }
//
//        etName.setText(firstPart + secondPart + thirdPart);
//        etNickname.setText(userNickname);
//        etEmail.setText(userEmail);
//
//        int uuisCount = (preferences.getUserUniversityInfos() != null)? preferences.getUserUniversityInfos().size() : 0;
//        if (uuisCount > 0) {
//            UserUniversityInfo uui = preferences.getUserUniversityInfos().get(0);
////            String uuiName = uui.getName();
//
//            //speciality spinner configuration
//            int universityIndex = findUniversityIndex(name);
//            System.out.println("Index of universities = "+universityIndex);
//            spUniversity.setSelection(universityIndex);
//
//            configureFacultiesData(view, (universityIndex > 0) ? universitiesData.get(universityIndex).getId() : -1);
//
//            if (university.getFaculties() != null && university.getFaculties().size() > 0) {
//                Faculty faculty = university.getFaculties().get(0);
//                String facultyName = faculty.getName();
//
//                int facultyIndex = findFacultyIndex(facultyName);
//                configureFacultiesSpinner(view);
//                spFaculty.setSelection(facultyIndex);
//
//                if (faculty.getSpecialities() != null && faculty.getSpecialities().size() > 0) {
//                    Speciality speciality = faculty.getSpecialities().get(0);
//                    String specialityName = speciality.getName();
//                    etSpeciality.setText(specialityName);
//                }
//            }
//        }else {
//            spUniversity.setSelection(0);
//            configureFacultiesData(view, -1L);
//        }
//
//        etNickname.addTextChangedListener(new NicknameTextChangedListener());
//        etName.addTextChangedListener(new NameTextChangedListener());
//        etEmail.addTextChangedListener(new EmailTextChangedListener());
//        btnApply.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                UserService userService = new UserService(getContext());
//
//                User user = new User();
//                user.setId(userId);
//                user.setEmail(preferences.getUserEmail());
//                user.setFirstName(preferences.getUserFirstname());
//                user.setLastName(preferences.getUserLastname());
//                user.setMiddleName(preferences.getUserMiddlename());
//                user.setNickname(preferences.getUserNickname());
//
//                userService.lightUpdate(user);
//
//                String s = "Saved prefs:\nName:"+preferences.getUserLastname()+" "+preferences.getUserFirstname()+" "+preferences.getUserMiddlename()+"\nNickname:"+preferences.getUserNickname()+
//                        "\nEmail:"+preferences.getUserEmail();
//                showToast(view, s);
//            }
//        });
//
//
//        // Inflate the layout for this fragment
//        return view;
//    }
//
//    private void configureUniversitiesData() {
//        UniversityService universityService = new UniversityService(getContext());
//        List<University> universities = universityService.getUniversities();
//        universitiesData = new ArrayList<>();
//        universitiesData.add(null);
//        universitiesData.addAll(universities);
//
//        facultiesData = new ArrayList<>();
//        facultiesData.add(null);
//    }
//
//    private void configureFacultiesData(View view, Long universityId) {
//        if (universityId == -1) {
//            facultiesData = new ArrayList<>();
//            facultiesData.add(null);
//        }else {
//            FacultyService facultyService = new FacultyService(getContext());
//            List<Faculty> faculties = facultyService.getFacultiesByUniversityId(universityId);
//            facultiesData = new ArrayList<>();
//            facultiesData.add(null);
//            if (faculties != null && faculties.size() > 0) {
//                facultiesData.addAll(faculties);
//
//                if (spFaculty == null)
//                    spFaculty = (Spinner) view.findViewById(R.id.faculty_sp);
//
//                facultyAdapter = new CustomFacultySpinnerAdapter(getContext(), R.layout.spinner_faculties_rows, facultiesData);
//                spFaculty.setAdapter(facultyAdapter);
//            }
//        }
//    }
//
//    private int findUniversityIndex(String name) {
//        University found = null;
//        for (University u : universitiesData) {
//            if (u != null && u.getName().equals(name)) {
//                found = u;
//                break;
//            }
//        }
//        if (found != null)
//            return universitiesData.indexOf(found);
//        return 0;
//    }
//
//    private int findFacultyIndex(String name) {
//        Faculty found = null;
//        for (Faculty f : facultiesData) {
//            if (f != null && f.getName().equals(name)) {
//                found = f;
//                break;
//            }
//        }
//        if (found != null)
//            return facultiesData.indexOf(found);
//        return 0;
//    }
//
//    private void configureSpinner(View view) {
//        spUniversity = (Spinner) view.findViewById(R.id.university_sp);
//
//        adapter = new CustomUniversitySpinnerAdapter(getContext(), R.layout.spinner_universities_rows, universitiesData);
//
//        spUniversity.setAdapter(adapter);
//        spUniversity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                System.out.println("Selected: i="+i+" l="+l);
//                if (i == 0)
//                    configureFacultiesData(view, -1L);
//                else
//                    configureFacultiesData(view, universitiesData.get(i).getId());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//    }
//
//    private void configureFacultiesSpinner(View view) {
//        spFaculty = (Spinner) view.findViewById(R.id.faculty_sp);
//
//        facultyAdapter = new CustomFacultySpinnerAdapter(getContext(), R.layout.spinner_faculties_rows, facultiesData);
//
//        spFaculty.setAdapter(facultyAdapter);
//
//    }
//
//    public class NicknameTextChangedListener implements TextWatcher {
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//        @Override
//        public void afterTextChanged(Editable editable) {
//            EditText etNickname = (EditText) getView().findViewById(R.id.nickname);
//            preferences.setUserNickname(etNickname.getText().toString());
//        }
//    }
//
//    public class NameTextChangedListener implements TextWatcher {
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//        @Override
//        public void afterTextChanged(Editable editable) {
//            EditText etName = (EditText) getView().findViewById(R.id.name);
//            String[] nameParts = etName.getText().toString().split(" ");
//            switch (nameParts.length) {
//                case 1:
//                    preferences.setUserLastname(nameParts[0]);
//                    break;
//                case 2:
//                    preferences.setUserFirstname(nameParts[1]);
//                    break;
//                case 3:
//                    preferences.setUserMiddlename(nameParts[2]);
//                    break;
//                default:
//                    break;
//            }
//        }
//    }
//
//    public class EmailTextChangedListener implements TextWatcher {
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//        @Override
//        public void afterTextChanged(Editable editable) {
//            EditText etEmail = (EditText) getView().findViewById(R.id.email);
//            preferences.setUserEmail(etEmail.getText().toString());
//        }
//    }
//
//    public void showToast(View view, String text) {
//        //создаем и отображаем текстовое уведомление
//        Toast toast = Toast.makeText(getContext(),
//                text,
//                Toast.LENGTH_SHORT);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();
//    }
//
//}
