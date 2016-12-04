package com.cococompany.android.aq.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.fragments.ProfileFragment;
import com.cococompany.android.aq.models.Faculty;
import com.cococompany.android.aq.models.Speciality;
import com.cococompany.android.aq.models.University;
import com.cococompany.android.aq.models.UserUniversityInfo;
import com.cococompany.android.aq.utils.FacultyService;
import com.cococompany.android.aq.utils.SpecialityService;
import com.cococompany.android.aq.utils.UniversityService;
import com.cococompany.android.aq.utils.UserUniversityInfoService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.cococompany.android.aq.R.string.speciality;

/**
 * Created by alexandrmyagkiy on 26.11.16.
 */

public class CustomUuiSwipeAdapter extends PagerAdapter {

    private List<Speciality>[] specialitiesData = new ArrayList[ProfileFragment.userUniversityInfos.length];
    private List<Faculty>[] facultiesData = new ArrayList[ProfileFragment.userUniversityInfos.length];
    private List<University>[] universitiesData = new ArrayList[ProfileFragment.userUniversityInfos.length];

    private Spinner[] spSpecialities = new Spinner[ProfileFragment.userUniversityInfos.length];
    private Spinner[] spFaculties = new Spinner[ProfileFragment.userUniversityInfos.length];
    private Spinner[] spUniversities = new Spinner[ProfileFragment.userUniversityInfos.length];

    private CustomUniversitySpinnerAdapter universityAdapter;
    private CustomFacultySpinnerAdapter facultyAdapter;
    private CustomSpecialitySpinnerAdapter specialityAdapter;

    private Context ctx;
    private LayoutInflater layoutInflater;

    private UserUniversityInfoService uuiService = null;
    private SpecialityService specialityService = null;
    private FacultyService facultyService = null;
    private UniversityService universityService = null;

    private Long[] initialUniversityId = new Long[ProfileFragment.userUniversityInfos.length],
            initialSpecialityId = new Long[ProfileFragment.userUniversityInfos.length],
            initialFacultyId = new Long[ProfileFragment.userUniversityInfos.length];

    TextView titlePosition = null;
    TextView titleTotal = null;
    Button btnAddUui = null;

    private long startTime = 0L,
            finishTime = 0L;

    public CustomUuiSwipeAdapter(Context ctx) {
        this.ctx = ctx;

        startTime = System.currentTimeMillis();
        uuiService = new UserUniversityInfoService(ctx);
        specialityService = new SpecialityService(ctx);
        facultyService = new FacultyService(ctx);
        universityService = new UniversityService(ctx);

        //configure initial Ids
        for (int i = 0; i < ProfileFragment.userUniversityInfos.length; i++) {
            initialUniversityId[i] = -1L;
            initialSpecialityId[i] = -1L;
            initialFacultyId[i] = -1L;
        }

        //fulfilling datas
        for (int i = 0; i < universitiesData.length; i++)
            universitiesData[i] = new ArrayList<University>();
        for (int i = 0; i < facultiesData.length; i++)
            facultiesData[i] = new ArrayList<Faculty>();
        for (int i = 0; i < specialitiesData.length; i++)
            specialitiesData[i] = new ArrayList<Speciality>();
        finishTime = System.currentTimeMillis();
        System.out.println("%\\_(^_^)_/%" + "load services|execution time:" + (finishTime - startTime));
    }

    @Override
    public int getCount() {
        return ProfileFragment.userUniversityInfos.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (LinearLayout) object;
    }

    //Main code part
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.profile_uui_swipe_layout, container, false);

        //filling the fields
        btnAddUui = (Button) item_view.findViewById(R.id.btnAddUui);
        //add onclick listener to button (add new uui)
        btnAddUui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastUuiEmpty())
                    return;

                List<UserUniversityInfo> userUniversityInfos = new ArrayList<UserUniversityInfo>();
                userUniversityInfos.addAll(Arrays.asList(ProfileFragment.userUniversityInfos));

                UserUniversityInfo addedUui = new UserUniversityInfo();
                addedUui.setUser(ProfileFragment.user);
                addedUui = uuiService.createUui(addedUui);

                userUniversityInfos.add(addedUui);

                ProfileFragment.userUniversityInfos = new UserUniversityInfo[userUniversityInfos.size()];
                userUniversityInfos.toArray(ProfileFragment.userUniversityInfos);

                //initial Ids configuring
                Long[] extendedUniversityId = new Long[initialUniversityId.length+1];
                Long[] extendedFacultyId = new Long[initialFacultyId.length+1];
                Long[] extendedSpecialityId = new Long[initialSpecialityId.length+1];

                for (int i = 0; i < extendedUniversityId.length; i++) {
                    if (i == (extendedUniversityId.length-1)) {
                        extendedUniversityId[i] = null;
                        break;
                    }
                    extendedUniversityId[i] = initialUniversityId[i];
                }

                for (int i = 0; i < extendedFacultyId.length; i++) {
                    if (i == (extendedFacultyId.length-1)) {
                        extendedFacultyId[i] = null;
                        break;
                    }
                    extendedFacultyId[i] = initialFacultyId[i];
                }

                for (int i = 0; i < extendedSpecialityId.length; i++) {
                    if (i == (extendedSpecialityId.length-1)) {
                        extendedSpecialityId[i] = null;
                        break;
                    }
                    extendedSpecialityId[i] = initialSpecialityId[i];
                }

                initialUniversityId = extendedUniversityId.clone();
                initialFacultyId = extendedFacultyId.clone();
                initialSpecialityId = extendedSpecialityId.clone();

                extendedUniversityId = null;
                extendedFacultyId = null;
                extendedSpecialityId = null;

                //spinners configuring
                Spinner[] extendedSpUniversities = new Spinner[spUniversities.length+1];
                Spinner[] extendedSpFaculties = new Spinner[spFaculties.length+1];
                Spinner[] extendedSpSpecialities = new Spinner[spSpecialities.length+1];

                for (int i = 0; i < extendedSpUniversities.length; i++) {
                    if (i == (extendedSpUniversities.length-1)) {
                        extendedSpUniversities[i] = null;
                        break;
                    }
                    extendedSpUniversities[i] = spUniversities[i];
                }

                for (int i = 0; i < extendedSpFaculties.length; i++) {
                    if (i == (extendedSpFaculties.length-1)) {
                        extendedSpFaculties[i] = null;
                        break;
                    }
                    extendedSpFaculties[i] = spFaculties[i];
                }

                for (int i = 0; i < extendedSpSpecialities.length; i++) {
                    if (i == (extendedSpSpecialities.length-1)) {
                        extendedSpSpecialities[i] = null;
                        break;
                    }
                    extendedSpSpecialities[i] = spSpecialities[i];
                }

                //datas configuring
                List[] extendedUniversitiesData = new List[universitiesData.length+1];
                List[] extendedFacultiesData = new List[facultiesData.length+1];
                List[] extendedSpecialitiesData = new List[specialitiesData.length+1];

                for (int i = 0; i < extendedUniversitiesData.length; i++) {
                    if (i == (extendedUniversitiesData.length-1)) {
                        extendedUniversitiesData[i] = new ArrayList<University>();
                        break;
                    }
                    extendedUniversitiesData[i] = universitiesData[i];
                }

                for (int i = 0; i < extendedFacultiesData.length; i++) {
                    if (i == (extendedFacultiesData.length-1)) {
                        extendedFacultiesData[i] = new ArrayList<Faculty>();
                        break;
                    }
                    extendedFacultiesData[i] = facultiesData[i];
                }

                for (int i = 0; i < extendedSpecialitiesData.length; i++) {
                    if (i == (extendedSpecialitiesData.length-1)) {
                        extendedSpecialitiesData[i] = new ArrayList<Speciality>();
                        break;
                    }
                    extendedSpecialitiesData[i] = specialitiesData[i];
                }

                universitiesData = extendedUniversitiesData.clone();
                facultiesData = extendedFacultiesData.clone();
                specialitiesData = extendedSpecialitiesData.clone();

                extendedUniversitiesData = null;
                extendedFacultiesData = null;
                extendedSpecialitiesData = null;

                titleTotal.setText(String.valueOf(ProfileFragment.userUniversityInfos.length));

                notifyDataSetChanged();
            }
        });

        titlePosition = (TextView) item_view.findViewById(R.id.titleContentProfileUui);
        titlePosition.setText(String.valueOf(position+1));
        titleTotal = (TextView) item_view.findViewById(R.id.titleEndProfileUui);
        titleTotal.setText(String.valueOf(ProfileFragment.userUniversityInfos.length));

        spSpecialities[position] = (Spinner) item_view.findViewById(R.id.speciality_sp);
        spFaculties[position] = (Spinner) item_view.findViewById(R.id.faculty_sp);
        spUniversities[position] = (Spinner) item_view.findViewById(R.id.university_sp);

        //university configuration
        initialUniversityId[position] = 0L;
        University university = ProfileFragment.userUniversityInfos[position].getUniversity();
        if (university != null) {
            startTime = System.currentTimeMillis();
            initialUniversityId[position] = university.getId();
            universitiesData[position].clear();
            universitiesData[position].add(null);
            universitiesData[position].addAll(universityService.getUniversities());

            configureUniversitySpinner(universityIndex(university.getId(), position), position);
            finishTime = System.currentTimeMillis();
            System.out.println("("+position+")-(instantiateItem)" + "-(university is not null, universitiesData:"+universitiesData[position]+")|execution time:" + (finishTime - startTime) + " msec");
        } else {
            configureUniversitiesList(position);

            configureUniversitySpinner(0, position);
            System.out.println("("+position+")-(instantiateItem)" + "-(university is null, universitiesData:"+universitiesData[position]+", selected none of universities)");
        }

        //faculty configuration
        initialFacultyId[position] = 0L;
        Faculty faculty = ProfileFragment.userUniversityInfos[position].getFaculty();
        if (faculty != null) {
            startTime = System.currentTimeMillis();
            initialFacultyId[position] = faculty.getId();
            facultiesData[position].clear();
            facultiesData[position].add(null);
            if (university != null)
                facultiesData[position].addAll(facultyService.getFacultiesByUniversityId(university.getId()));

            configureFacultySpinner(facultyIndex(faculty.getId(), position), position);
            finishTime = System.currentTimeMillis();
            System.out.println("("+position+")-(instantiateItem)" + "-(faculty is not null, facultiesData:"+facultiesData[position]+")|execution time:" + (finishTime - startTime) + " msec");
        } else {
            if (university == null) {
                facultiesData[position] = new ArrayList<>();
                facultiesData[position].add(null);

                configureFacultySpinner(0, position);
                System.out.println("("+position+")-(instantiateItem)" + "-(faculty is null and university too, facultiesData:"+facultiesData[position]+", selected none of faculties)");
            } else {
                configureFacultiesList(university.getId(), position);
                configureFacultySpinner(0, position);
                System.out.println("("+position+")-(instantiateItem)" + "-(faculty is null and university is not, facultiesData:"+facultiesData[position]+", selected none of faculties)");
            }
        }

        //speciality configuration
        initialSpecialityId[position] = 0L;
        Speciality speciality = ProfileFragment.userUniversityInfos[position].getSpeciality();
        if (speciality != null) {
            startTime = System.currentTimeMillis();
            initialSpecialityId[position] = speciality.getId();
            specialitiesData[position].clear();
            specialitiesData[position].add(null);
            if (university != null) {
                if (faculty != null) {
                    List<Speciality> specialities = specialityService.getSpecialitiesByFacultyId(faculty.getId());
                    specialitiesData[position].addAll(specialities);
                    System.out.println("("+position+")-(instantiateItem)" + "-(speciality is not null and faculty too, specialitiesData:"+specialitiesData[position]+")|execution time:" + (finishTime - startTime) + " msec");
                }
                else {
                    List<Speciality> specialities = specialityService.getSpecialitiesByUniversityId(university.getId());
                    specialitiesData[position].addAll(specialities);
                    System.out.println("("+position+")-(instantiateItem)" + "-(speciality is not null and faculty is null, specialitiesData:"+specialitiesData[position]+")|execution time:" + (finishTime - startTime) + " msec");
                }
            }

            System.out.println("("+position+")-(instantiateItem)" + "-(speciality index=" + specialityIndex(speciality.getId(), position) + ")");
            configureSpecialitySpinner(specialityIndex(speciality.getId(), position), position);
            finishTime = System.currentTimeMillis();
            System.out.println("("+position+")-(instantiateItem)" + "-(specialities spinner configured)|execution time:" + (finishTime - startTime) + " msec");
        } else {
            if (university == null) {
                specialitiesData[position].clear();
                specialitiesData[position].add(null);

                configureSpecialitySpinner(0, position);
                System.out.println("(" + position + ")-(instantiateItem)" + "-(speciality is null and university too, selected none of specialities)");
            } else {
                if (faculty == null) {
                    configureSpecialitiesListByUniversity(university.getId(), position);
                    configureSpecialitySpinner(0, position);
                } else {
                    configureSpecialitiesListByFaculty(faculty.getId(), position);
                    configureSpecialitySpinner(0, position);
                }
            }
        }

        container.addView(item_view);

        return item_view;
    }

    private boolean lastUuiEmpty() {
        UserUniversityInfo uui = ProfileFragment.userUniversityInfos[ProfileFragment.userUniversityInfos.length-1];

        if (uui == null || (uui.getId() == null && uui.getSpeciality() == null && uui.getFaculty() == null))
            return true;

        return false;
    }

    private void configureFacultiesList(int position) {
        startTime = System.currentTimeMillis();
        facultiesData[position].clear();
        facultiesData[position].add(null);
        facultiesData[position].addAll(facultyService.getFaculties());
        finishTime = System.currentTimeMillis();
        System.out.println("("+position+")-(configureFacultiesList)" + "-(configured faculties list with all faculties)|execution time:" + (finishTime - startTime) + " msec");
    }

    private void configureUniversitiesList(int position) {
        startTime = System.currentTimeMillis();
        universitiesData[position].clear();
        universitiesData[position].add(null);
        universitiesData[position].addAll(universityService.getUniversities());
        finishTime = System.currentTimeMillis();
        System.out.println("("+position+")-(configureUniversitiesList)" + "-(configured universities list with all universities)|execution time:" + (finishTime - startTime) + " msec");
    }

    private void configureSpecialitySpinner(final int selectPosition, final int currentPos) {
        if (spSpecialities == null || spSpecialities[currentPos] == null)
            return;

        specialityAdapter = new CustomSpecialitySpinnerAdapter(ctx, R.layout.spinner_specialities_rows, specialitiesData[currentPos]);

        spSpecialities[currentPos].setAdapter(specialityAdapter);
        spSpecialities[currentPos].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (initialSpecialityId[currentPos] > 0) {
                    ProfileFragment.userUniversityInfos[currentPos].setSpeciality(specialitiesData[currentPos].get(selectPosition));
                    initialSpecialityId[currentPos] = -2L;
                    System.out.println("("+currentPos+")-(configureSpecialitySpinner, onItemSelected)" + "-(selected speciality first time, i="+i+")");
                } else {
                    System.out.println("("+currentPos+")-(configureSpecialitySpinner, onItemSelected)" + "-(selected speciality not first time, i="+i+")");
                    ProfileFragment.userUniversityInfos[currentPos].setSpeciality(specialitiesData[currentPos].get(i));
//                    ProfileFragment.userUniversityInfos[currentPos] = uuiService.updateUui(ProfileFragment.userUniversityInfos[currentPos]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spSpecialities[currentPos].setSelection(selectPosition);
        System.out.println("("+currentPos+")-(configureSpecialitySpinner)" + "-(selected speciality index = "+selectPosition+")");
    }

    private void configureFacultySpinner(final int selectPosition, final int currentPos) {
        if (spFaculties == null || spFaculties[currentPos] == null)
            return;

        facultyAdapter = new CustomFacultySpinnerAdapter(ctx, R.layout.spinner_faculties_rows, facultiesData[currentPos]);

        spFaculties[currentPos].setAdapter(facultyAdapter);
        spFaculties[currentPos].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (initialFacultyId[currentPos] > 0) {
                    ProfileFragment.userUniversityInfos[currentPos].setFaculty(facultiesData[currentPos].get(selectPosition));
                    System.out.println("("+currentPos+")-(configureFacultySpinner, onItemSelected)" + "-(selected faculty first time, i="+i+")");
                } else {
                    System.out.println("("+currentPos+")-(configureFacultySpinner, onItemSelected)" + "-(selected faculty not first time, i="+i+")");
                    ProfileFragment.userUniversityInfos[currentPos].setFaculty(facultiesData[currentPos].get(i));
//                    ProfileFragment.userUniversityInfos[currentPos] = uuiService.updateUui(ProfileFragment.userUniversityInfos[currentPos]);
                }

                if (i > 0 && facultiesData[currentPos] != null && facultiesData[currentPos].get(i) != null && initialFacultyId[currentPos] < 0) {
                    //load all specialities related with specified faculty
                    System.out.println("("+currentPos+")-(configureFacultySpinner, onItemSelected)" + "-(configuring related specialities)");
                    configureSpecialitiesListByFaculty(facultiesData[currentPos].get(i).getId(), currentPos);
                    configureSpecialitySpinner(0, currentPos);
                }

                if (initialFacultyId[currentPos] > 0) {
                    initialFacultyId[currentPos] = -2L;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spFaculties[currentPos].setSelection(selectPosition);
        System.out.println("("+currentPos+")-(configureFacultySpinner)" + "-(selected faculty index = "+selectPosition+")");
    }

    private void configureUniversitySpinner(final int selectPosition, final int currentPos) {
        if (spUniversities == null || spUniversities[currentPos] == null)
            return;

        universityAdapter = new CustomUniversitySpinnerAdapter(ctx, R.layout.spinner_universities_rows, universitiesData[currentPos]);

        spUniversities[currentPos].setAdapter(universityAdapter);
        spUniversities[currentPos].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (initialUniversityId[currentPos] > 0) {
                    //saving specified university
                    if (ProfileFragment.userUniversityInfos[currentPos] == null || ProfileFragment.userUniversityInfos[currentPos].getId() == null) {
                        ProfileFragment.userUniversityInfos[currentPos].setUniversity(universitiesData[currentPos].get(i));
                        ProfileFragment.userUniversityInfos[currentPos].setUser(ProfileFragment.user);
                        System.out.println("("+currentPos+")-(configureUniversitySpinner, onItemSelected)" + "-(selected university first time, i="+i+", saving university and user)");
                    }
                    else {
                        ProfileFragment.userUniversityInfos[currentPos].setUniversity(universitiesData[currentPos].get(i));
                        System.out.println("("+currentPos+")-(configureUniversitySpinner, onItemSelected)" + "-(selected university first time, i="+i+", saving university)");
                    }

                    if (i > 0 && universitiesData[currentPos] != null && universitiesData[currentPos].get(i) != null) {
                        //load all faculties and specialities related with specified university
                        System.out.println("("+currentPos+")-(configureUniversitySpinner, onItemSelected)" + "-(configuring faculties and specialities related with university)");
                        configureFacultiesList(universitiesData[currentPos].get(i).getId(), currentPos);
                        configureFacultySpinner(0, currentPos);

                        configureSpecialitiesListByUniversity(universitiesData[currentPos].get(i).getId(), currentPos);
                        configureSpecialitySpinner(0, currentPos);
                    }
                } else {
                    //saving specified university
                    if (ProfileFragment.userUniversityInfos[currentPos] == null || ProfileFragment.userUniversityInfos[currentPos].getId() == null) {
                        ProfileFragment.userUniversityInfos[currentPos].setUniversity(universitiesData[currentPos].get(i));
                        ProfileFragment.userUniversityInfos[currentPos].setUser(ProfileFragment.user);
                        System.out.println("("+currentPos+")-(configureUniversitySpinner, onItemSelected)" + "-(selected university not first time, i="+i+", saving university and user)");
//                        ProfileFragment.userUniversityInfos[currentPos] = uuiService.createUui(ProfileFragment.userUniversityInfos[currentPos]);
                    }
                    else {
                        ProfileFragment.userUniversityInfos[currentPos].setUniversity(universitiesData[currentPos].get(i));
                        System.out.println("("+currentPos+")-(configureUniversitySpinner, onItemSelected)" + "-(selected university not first time, i="+i+", saving university)");
//                        ProfileFragment.userUniversityInfos[currentPos] = uuiService.updateUui(ProfileFragment.userUniversityInfos[currentPos]);
                    }

                    if (i > 0 && universitiesData[currentPos] != null && universitiesData[currentPos].get(i) != null) {
                        //load all faculties and specialities related with specified university
                        System.out.println("("+currentPos+")-(configureUniversitySpinner, onItemSelected)" + "-(configuring faculties and specialities related with university)");
                        configureFacultiesList(universitiesData[currentPos].get(i).getId(), currentPos);
                        configureFacultySpinner(0, currentPos);

                        configureSpecialitiesListByUniversity(universitiesData[currentPos].get(i).getId(), currentPos);
                        configureSpecialitySpinner(0, currentPos);
                    }
                }

                if (i > 0 && universitiesData[currentPos] != null && universitiesData[currentPos].get(i) != null && initialUniversityId[currentPos] < 0) {
                    //load all faculties and specialities related with specified university
                    System.out.println("("+currentPos+")-(configureUniversitySpinner, onItemSelected)" + "-(configuring faculties and specialities related with university)");
                    configureFacultiesList(universitiesData[currentPos].get(i).getId(), currentPos);
                    configureFacultySpinner(0, currentPos);

                    configureSpecialitiesListByUniversity(universitiesData[currentPos].get(i).getId(), currentPos);
                    configureSpecialitySpinner(0, currentPos);
                }

                if (initialUniversityId[currentPos] > 0) {
                    initialUniversityId[currentPos] = -2L;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spUniversities[currentPos].setSelection(selectPosition);
    }

    private void configureSpecialitiesList(int position) {
        startTime = System.currentTimeMillis();
        specialitiesData[position].clear();
        specialitiesData[position].add(null);
        specialitiesData[position].addAll(specialityService.getSpecialities());
        finishTime = System.currentTimeMillis();
        System.out.println("("+position+")-(configureSpecialitiesList)" + "-(configured specialities list with all specialities)|execution time:" + (finishTime - startTime) + " msec");
    }

    private void configureSpecialitiesListByUniversity(Long universityId, int pos) {
        specialitiesData[pos].clear();
        specialitiesData[pos].add(null);
        List<Speciality> specialitiesByUniversity = specialityService.getSpecialitiesByUniversityId(universityId);
        specialitiesData[pos].addAll(specialitiesByUniversity);
        System.out.println("("+pos+")-(configureSpecialitiesListByUniversity)" + "-(configured specialities list by university)-(universityId="+universityId+", specialitiesData="+specialitiesData+")");
    }

    private void configureSpecialitiesListByFaculty(Long facultyId, int pos) {
        specialitiesData[pos].clear();
        specialitiesData[pos].add(null);
        List<Speciality> specialitiesByFaculty = specialityService.getSpecialitiesByFacultyId(facultyId);
        specialitiesData[pos].addAll(specialitiesByFaculty);
        System.out.println("("+pos+")-(configureSpecialitiesListByFaculty)" + "-(configured specialities list by faculty)-(facultyId="+facultyId+", specialitiesData="+specialitiesData+")");
    }

    private void configureFacultiesList(Long universityId, int pos) {
        facultiesData[pos].clear();
        facultiesData[pos].add(null);
        List<Faculty> facultiesByUniversity = facultyService.getFacultiesByUniversityId(universityId);
        facultiesData[pos].addAll(facultiesByUniversity);
        System.out.println("("+pos+")-(configureFacultiesList)" + "-(configured faculties list by university)-(universityId="+universityId+", facultiesData="+facultiesData+")");
    }

    private int specialityIndex(Long id, int pos) {
        int index = 0;

        if (specialitiesData[pos] == null || specialitiesData[pos].size() == 1)
            return index;

        for (int i = 1; i < specialitiesData[pos].size(); i++)
            if (specialitiesData[pos].get(i).getId().equals(id)) {
                return (i);
            }
        return index;
    }

    private int facultyIndex(Long id, int pos) {
        int index = 0;

        if (facultiesData[pos] == null || facultiesData[pos].size() == 1)
            return index;

        for (int i = 1; i < facultiesData[pos].size(); i++)
            if (facultiesData[pos].get(i).getId().equals(id)) {
                return (i);
            }
        return index;
    }

    private int universityIndex(Long id, int pos) {
        int index = 0;

        if (universitiesData == null || universitiesData[pos].size() == 1)
            return index;

        for (int i = 1; i < universitiesData[pos].size(); i++)
            if (universitiesData[pos].get(i).getId().equals(id)) {
                return (i);
            }
        return index;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    public void showToast(View view, String text) {
        //создаем и отображаем текстовое уведомление
        Toast toast = Toast.makeText(ctx,
                text,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
