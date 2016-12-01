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

    private List<Speciality> specialitiesData = new ArrayList<>();
    private List<Faculty> facultiesData = new ArrayList<>();
    private List<University> universitiesData = new ArrayList<>();

    private Spinner spSpecialities = null;
    private Spinner spFaculties = null;
    private Spinner spUniversities = null;

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
                userUniversityInfos.add(new UserUniversityInfo());

                ProfileFragment.userUniversityInfos = new UserUniversityInfo[userUniversityInfos.size()];
                userUniversityInfos.toArray(ProfileFragment.userUniversityInfos);

                titleTotal.setText(String.valueOf(ProfileFragment.userUniversityInfos.length));

                notifyDataSetChanged();
            }
        });

        titlePosition = (TextView) item_view.findViewById(R.id.titleContentProfileUui);
        titlePosition.setText(String.valueOf(position+1));
        titleTotal = (TextView) item_view.findViewById(R.id.titleEndProfileUui);
        titleTotal.setText(String.valueOf(ProfileFragment.userUniversityInfos.length));

        spSpecialities = (Spinner) item_view.findViewById(R.id.speciality_sp);
        spFaculties = (Spinner) item_view.findViewById(R.id.faculty_sp);
        spUniversities = (Spinner) item_view.findViewById(R.id.university_sp);

        //university configuration
        initialUniversityId[position] = 0L;
        University university = ProfileFragment.userUniversityInfos[position].getUniversity();
        if (university != null) {
            startTime = System.currentTimeMillis();
            initialUniversityId[position] = university.getId();
            universitiesData.clear();
            universitiesData.add(null);
            universitiesData.addAll(universityService.getUniversities());

            configureUniversitySpinner(universityIndex(university.getId()), position);
            finishTime = System.currentTimeMillis();
            System.out.println("("+position+")-(instantiateItem)" + "-(university is not null, universitiesData:"+universitiesData+")|execution time:" + (finishTime - startTime) + " msec");
        } else {
            configureUniversitiesList(position);

            configureUniversitySpinner(0, position);
            System.out.println("("+position+")-(instantiateItem)" + "-(university is null, universitiesData:"+universitiesData+", selected none of universities)");
        }

        //faculty configuration
        initialFacultyId[position] = 0L;
        Faculty faculty = ProfileFragment.userUniversityInfos[position].getFaculty();
        if (faculty != null) {
            startTime = System.currentTimeMillis();
            initialFacultyId[position] = faculty.getId();
            facultiesData.clear();
            facultiesData.add(null);
            if (university != null)
                facultiesData.addAll(facultyService.getFacultiesByUniversityId(university.getId()));

            configureFacultySpinner(facultyIndex(faculty.getId()), position);
            finishTime = System.currentTimeMillis();
            System.out.println("("+position+")-(instantiateItem)" + "-(faculty is not null, facultiesData:"+facultiesData+")|execution time:" + (finishTime - startTime) + " msec");
        } else {
            facultiesData = new ArrayList<>();
            facultiesData.add(null);

            configureFacultySpinner(0, position);
            System.out.println("("+position+")-(instantiateItem)" + "-(faculty is null, facultiesData:"+facultiesData+", selected none of faculties)");
        }

        //speciality configuration
        initialSpecialityId[position] = 0L;
        Speciality speciality = ProfileFragment.userUniversityInfos[position].getSpeciality();
        if (speciality != null) {
            startTime = System.currentTimeMillis();
            initialSpecialityId[position] = speciality.getId();
            specialitiesData.clear();
            specialitiesData.add(null);
            if (university != null) {
                if (faculty != null) {
                    List<Speciality> specialities = specialityService.getSpecialitiesByFacultyId(faculty.getId());
                    specialitiesData.addAll(specialities);
                    System.out.println("("+position+")-(instantiateItem)" + "-(speciality is not null and faculty too, specialitiesData:"+specialitiesData+")|execution time:" + (finishTime - startTime) + " msec");
                }
                else {
                    List<Speciality> specialities = specialityService.getSpecialitiesByUniversityId(university.getId());
                    specialitiesData.addAll(specialities);
                    System.out.println("("+position+")-(instantiateItem)" + "-(speciality is not null and faculty is null, specialitiesData:"+specialitiesData+")|execution time:" + (finishTime - startTime) + " msec");
                }
            }

            System.out.println("("+position+")-(instantiateItem)" + "-(speciality index=" + specialityIndex(speciality.getId()) + ")");
            configureSpecialitySpinner(specialityIndex(speciality.getId()), position);
            finishTime = System.currentTimeMillis();
            System.out.println("("+position+")-(instantiateItem)" + "-(specialities spinner configured)|execution time:" + (finishTime - startTime) + " msec");
        } else {
            specialitiesData.clear();
            specialitiesData.add(null);

            configureSpecialitySpinner(0, position);
            System.out.println("("+position+")-(instantiateItem)" + "-(speciality is null, selected none of specialities)");
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
        facultiesData.clear();
        facultiesData.add(null);
        facultiesData.addAll(facultyService.getFaculties());
        finishTime = System.currentTimeMillis();
        System.out.println("("+position+")-(configureFacultiesList)" + "-(configured faculties list with all faculties)|execution time:" + (finishTime - startTime) + " msec");
    }

    private void configureUniversitiesList(int position) {
        startTime = System.currentTimeMillis();
        universitiesData.clear();
        universitiesData.add(null);
        universitiesData.addAll(universityService.getUniversities());
        finishTime = System.currentTimeMillis();
        System.out.println("("+position+")-(configureUniversitiesList)" + "-(configured universities list with all universities)|execution time:" + (finishTime - startTime) + " msec");
    }

    private void configureSpecialitySpinner(final int selectPosition, final int currentPos) {
        if (spSpecialities == null)
            return;

        specialityAdapter = new CustomSpecialitySpinnerAdapter(ctx, R.layout.spinner_specialities_rows, specialitiesData);

        spSpecialities.setAdapter(specialityAdapter);
        spSpecialities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (initialSpecialityId[currentPos] > 0) {
                    ProfileFragment.userUniversityInfos[currentPos].setSpeciality(specialitiesData.get(selectPosition));
                    initialSpecialityId[currentPos] = -2L;
                    System.out.println("("+currentPos+")-(configureSpecialitySpinner, onItemSelected)" + "-(selected speciality first time, i="+i+")");
                } else {
                    System.out.println("("+currentPos+")-(configureSpecialitySpinner, onItemSelected)" + "-(selected speciality not first time, i="+i+")");
                    ProfileFragment.userUniversityInfos[currentPos].setSpeciality(specialitiesData.get(i));
//                    ProfileFragment.userUniversityInfos[currentPos] = uuiService.updateUui(ProfileFragment.userUniversityInfos[currentPos]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spSpecialities.setSelection(selectPosition);
        System.out.println("("+currentPos+")-(configureSpecialitySpinner)" + "-(selected speciality index = "+selectPosition+")");
    }

    private void configureFacultySpinner(final int selectPosition, final int currentPos) {
        if (spFaculties == null)
            return;

        facultyAdapter = new CustomFacultySpinnerAdapter(ctx, R.layout.spinner_faculties_rows, facultiesData);

        spFaculties.setAdapter(facultyAdapter);
        spFaculties.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (initialFacultyId[currentPos] > 0) {
                    ProfileFragment.userUniversityInfos[currentPos].setFaculty(facultiesData.get(selectPosition));
                    System.out.println("("+currentPos+")-(configureFacultySpinner, onItemSelected)" + "-(selected faculty first time, i="+i+")");
                } else {
                    System.out.println("("+currentPos+")-(configureFacultySpinner, onItemSelected)" + "-(selected faculty not first time, i="+i+")");
                    ProfileFragment.userUniversityInfos[currentPos].setFaculty(facultiesData.get(i));
//                    ProfileFragment.userUniversityInfos[currentPos] = uuiService.updateUui(ProfileFragment.userUniversityInfos[currentPos]);
                }

                if (i > 0 && facultiesData != null && facultiesData.get(i) != null && initialFacultyId[currentPos] < 0) {
                    //load all specialities related with specified faculty
                    System.out.println("("+currentPos+")-(configureFacultySpinner, onItemSelected)" + "-(configuring related specialities)");
                    configureSpecialitiesListByFaculty(facultiesData.get(i).getId(), currentPos);
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
        spFaculties.setSelection(selectPosition);
        System.out.println("("+currentPos+")-(configureFacultySpinner)" + "-(selected faculty index = "+selectPosition+")");
    }

    private void configureUniversitySpinner(final int selectPosition, final int currentPos) {
        if (spUniversities == null)
            return;

        universityAdapter = new CustomUniversitySpinnerAdapter(ctx, R.layout.spinner_universities_rows, universitiesData);

        spUniversities.setAdapter(universityAdapter);
        spUniversities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (initialUniversityId[currentPos] > 0) {
                    //saving specified university
                    if (ProfileFragment.userUniversityInfos[currentPos] == null || ProfileFragment.userUniversityInfos[currentPos].getId() == null) {
                        ProfileFragment.userUniversityInfos[currentPos].setUniversity(universitiesData.get(i));
                        ProfileFragment.userUniversityInfos[currentPos].setUser(ProfileFragment.user);
                        System.out.println("("+currentPos+")-(configureUniversitySpinner, onItemSelected)" + "-(selected university first time, i="+i+", saving university and user)");
                    }
                    else {
                        ProfileFragment.userUniversityInfos[currentPos].setUniversity(universitiesData.get(i));
                        System.out.println("("+currentPos+")-(configureUniversitySpinner, onItemSelected)" + "-(selected university first time, i="+i+", saving university)");
                    }
                } else {
                    //saving specified university
                    if (ProfileFragment.userUniversityInfos[currentPos] == null || ProfileFragment.userUniversityInfos[currentPos].getId() == null) {
                        ProfileFragment.userUniversityInfos[currentPos].setUniversity(universitiesData.get(i));
                        ProfileFragment.userUniversityInfos[currentPos].setUser(ProfileFragment.user);
                        System.out.println("("+currentPos+")-(configureUniversitySpinner, onItemSelected)" + "-(selected university not first time, i="+i+", saving university and user)");
//                        ProfileFragment.userUniversityInfos[currentPos] = uuiService.createUui(ProfileFragment.userUniversityInfos[currentPos]);
                    }
                    else {
                        ProfileFragment.userUniversityInfos[currentPos].setUniversity(universitiesData.get(i));
                        System.out.println("("+currentPos+")-(configureUniversitySpinner, onItemSelected)" + "-(selected university not first time, i="+i+", saving university)");
//                        ProfileFragment.userUniversityInfos[currentPos] = uuiService.updateUui(ProfileFragment.userUniversityInfos[currentPos]);
                    }
                }


                if (i > 0 && universitiesData != null && universitiesData.get(i) != null && initialUniversityId[currentPos] < 0) {
                    //load all faculties and specialities related with specified university
                    System.out.println("("+currentPos+")-(configureUniversitySpinner, onItemSelected)" + "-(configuring faculties and specialities related with university)");
                    configureFacultiesList(universitiesData.get(i).getId(), currentPos);
                    configureFacultySpinner(0, currentPos);

                    configureSpecialitiesListByUniversity(universitiesData.get(i).getId(), currentPos);
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
        spUniversities.setSelection(selectPosition);
    }

    private void configureSpecialitiesList(int position) {
        startTime = System.currentTimeMillis();
        specialitiesData.clear();
        specialitiesData.add(null);
        specialitiesData.addAll(specialityService.getSpecialities());
        finishTime = System.currentTimeMillis();
        System.out.println("("+position+")-(configureSpecialitiesList)" + "-(configured specialities list with all specialities)|execution time:" + (finishTime - startTime) + " msec");
    }

    private void configureSpecialitiesListByUniversity(Long universityId, int pos) {
        specialitiesData.clear();
        specialitiesData.add(null);
        List<Speciality> specialitiesByUniversity = specialityService.getSpecialitiesByUniversityId(universityId);
        specialitiesData.addAll(specialitiesByUniversity);
        System.out.println("("+pos+")-(configureSpecialitiesListByUniversity)" + "-(configured specialities list by university)-(universityId="+universityId+", specialitiesData="+specialitiesData+")");
    }

    private void configureSpecialitiesListByFaculty(Long facultyId, int pos) {
        specialitiesData.clear();
        specialitiesData.add(null);
        List<Speciality> specialitiesByFaculty = specialityService.getSpecialitiesByFacultyId(facultyId);
        specialitiesData.addAll(specialitiesByFaculty);
        System.out.println("("+pos+")-(configureSpecialitiesListByFaculty)" + "-(configured specialities list by faculty)-(facultyId="+facultyId+", specialitiesData="+specialitiesData+")");
    }

    private void configureFacultiesList(Long universityId, int pos) {
        facultiesData.clear();
        facultiesData.add(null);
        List<Faculty> facultiesByUniversity = facultyService.getFacultiesByUniversityId(universityId);
        facultiesData.addAll(facultiesByUniversity);
        System.out.println("("+pos+")-(configureFacultiesList)" + "-(configured faculties list by university)-(universityId="+universityId+", facultiesData="+facultiesData+")");
    }

    private int specialityIndex(Long id) {
        int index = 0;

        if (specialitiesData == null || specialitiesData.size() == 1)
            return index;

        for (int i = 1; i < specialitiesData.size(); i++)
            if (specialitiesData.get(i).getId().equals(id)) {
                return (i);
            }
        return index;
    }

    private int facultyIndex(Long id) {
        int index = 0;

        if (facultiesData == null || facultiesData.size() == 1)
            return index;

        for (int i = 1; i < facultiesData.size(); i++)
            if (facultiesData.get(i).getId().equals(id)) {
                return (i);
            }
        return index;
    }

    private int universityIndex(Long id) {
        int index = 0;

        if (universitiesData == null || universitiesData.size() == 1)
            return index;

        for (int i = 1; i < universitiesData.size(); i++)
            if (universitiesData.get(i).getId().equals(id)) {
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
