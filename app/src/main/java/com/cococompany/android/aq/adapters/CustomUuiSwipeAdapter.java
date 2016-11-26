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

    private UserUniversityInfo[] uuis = null;

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

    private int universityInitialState = 0,
        facultyInitialState = 0,
        specialityInitialState = 0;

    TextView titlePosition = null;
    TextView titleTotal = null;
    Button btnAddUui = null;

    public CustomUuiSwipeAdapter(Context ctx, UserUniversityInfo[] uuis) {
        this.ctx = ctx;
        this.uuis = uuis;

        if (uuis == null)
            uuis = new UserUniversityInfo[0];

        uuiService = new UserUniversityInfoService(ctx);
        specialityService = new SpecialityService(ctx);
        facultyService = new FacultyService(ctx);
        universityService = new UniversityService(ctx);
    }

    @Override
    public int getCount() {
        return uuis.length;
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
        //add onclick listener to button
        btnAddUui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastUuiEmpty())
                    return;

                universityInitialState = 0;
                specialityInitialState = 0;
                facultyInitialState = 0;

                List<UserUniversityInfo> userUniversityInfos = new ArrayList<UserUniversityInfo>();
                userUniversityInfos.addAll(Arrays.asList(uuis));
                userUniversityInfos.add(new UserUniversityInfo());

                uuis = new UserUniversityInfo[userUniversityInfos.size()];
                userUniversityInfos.toArray(uuis);

                titleTotal.setText(String.valueOf(uuis.length));

                notifyDataSetChanged();
            }
        });

        titlePosition = (TextView) item_view.findViewById(R.id.titleContentProfileUui);
        titlePosition.setText(String.valueOf(position+1));
        titleTotal = (TextView) item_view.findViewById(R.id.titleEndProfileUui);
        titleTotal.setText(String.valueOf(uuis.length));

        spSpecialities = (Spinner) item_view.findViewById(R.id.speciality_sp);
        spFaculties = (Spinner) item_view.findViewById(R.id.faculty_sp);
        spUniversities = (Spinner) item_view.findViewById(R.id.university_sp);

        //university configuration
        University university = uuis[position].getUniversity();
        if (university != null) {
            universitiesData = new ArrayList<>();
            universitiesData.add(null);
            universitiesData.addAll(universityService.getUniversities());

            configureUniversitySpinner(universityIndex(university.getId()));
        } else {
            configureUniversitiesList();

            configureUniversitySpinner(0);
        }

        //faculty configuration
        Faculty faculty = uuis[position].getFaculty();
        if (faculty != null) {
            facultiesData = new ArrayList<>();
            facultiesData.add(null);
            if (university != null)
                facultiesData.addAll(facultyService.getFacultiesByUniversityId(university.getId()));

            configureFacultySpinner(facultyIndex(faculty.getId()));
        } else {
//            configureFacultiesList();
            facultiesData = new ArrayList<>();
            facultiesData.add(null);

            configureFacultySpinner(0);
        }

        //speciality configuration
        Speciality speciality = uuis[position].getSpeciality();
        if (speciality != null) {
            specialitiesData = new ArrayList<>();
            specialitiesData.add(null);
            if (university != null) {
                if (faculty != null)
                    specialitiesData.addAll(specialityService.getSpecialitiesByFacultyId(faculty.getId()));
                else
                    specialitiesData.addAll(specialityService.getSpecialitiesByUniversityId(university.getId()));
            }

            configureSpecialitySpinner(specialityIndex(speciality.getId()));
        } else {
//            configureSpecialitiesList();
            specialitiesData = new ArrayList<>();
            specialitiesData.add(null);

            configureSpecialitySpinner(0);
        }

        container.addView(item_view);

        return item_view;
    }

    private boolean lastUuiEmpty() {
        UserUniversityInfo uui = uuis[uuis.length-1];

        if (uui == null || (uui.getId() == null && uui.getSpeciality() == null && uui.getFaculty() == null))
            return true;

        return false;
    }

    private void configureSpecialitiesList() {
        specialitiesData = new ArrayList<>();
        specialitiesData.add(null);
        specialitiesData.addAll(specialityService.getSpecialities());
    }

    private void configureFacultiesList() {
        facultiesData = new ArrayList<>();
        facultiesData.add(null);
        facultiesData.addAll(facultyService.getFaculties());
    }

    private void configureUniversitiesList() {
        universitiesData = new ArrayList<>();
        universitiesData.add(null);
        universitiesData.addAll(universityService.getUniversities());
    }

    private void configureSpecialitySpinner(final int selectPosition) {
        if (spSpecialities == null)
            return;

        specialityAdapter = new CustomSpecialitySpinnerAdapter(ctx, R.layout.spinner_specialities_rows, specialitiesData);

        spSpecialities.setAdapter(specialityAdapter);
        spSpecialities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (specialityInitialState == 0) {
                    specialityInitialState = 1;
                    return;
                }
                System.out.println("Page position before crash:"+ProfileFragment.pagePosition+" i:"+i+" specsData length:"+specialitiesData.size());
                uuis[ProfileFragment.pagePosition].setSpeciality(specialitiesData.get(i));
                uuis[ProfileFragment.pagePosition] = uuiService.updateUui(uuis[ProfileFragment.pagePosition]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spSpecialities.setSelection(selectPosition);
    }

    private void configureFacultySpinner(final int selectPosition) {
        if (spSpecialities == null)
            return;

        facultyAdapter = new CustomFacultySpinnerAdapter(ctx, R.layout.spinner_faculties_rows, facultiesData);

        spFaculties.setAdapter(facultyAdapter);
        spFaculties.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (facultyInitialState == 0) {
                    facultyInitialState = 1;
                    return;
                }

                uuis[ProfileFragment.pagePosition].setFaculty(facultiesData.get(i));
                uuis[ProfileFragment.pagePosition] = uuiService.updateUui(uuis[ProfileFragment.pagePosition]);

                if (i > 0 && facultiesData != null && facultiesData.get(i) != null) {
                    //load all specialities related with specified faculty
                    configureSpecialitiesListByFaculty(facultiesData.get(i).getId());
                    configureSpecialitySpinner(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spFaculties.setSelection(selectPosition);
    }

    private void configureUniversitySpinner(final int selectPosition) {
        universityAdapter = new CustomUniversitySpinnerAdapter(ctx, R.layout.spinner_universities_rows, universitiesData);

        spUniversities.setAdapter(universityAdapter);
        spUniversities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (universityInitialState == 0) {
                    universityInitialState = 1;
                    if (i != 0) {
                        //filling faculties
                        if (spFaculties.getSelectedItemPosition() == 0) {
                            configureFacultiesList(universitiesData.get(i).getId());
                            configureFacultySpinner(0);
                        }
                        //filling specialities
                        if (spSpecialities.getSelectedItemPosition() == 0) {
                            configureSpecialitiesList(universitiesData.get(i).getId());
                            configureSpecialitySpinner(0);
                        }
                    }

                    return;
                }

                //saving specified university
                if (uuis[ProfileFragment.pagePosition] == null || uuis[ProfileFragment.pagePosition].getId() == null) {
                    uuis[ProfileFragment.pagePosition].setUniversity(universitiesData.get(i));
                    uuis[ProfileFragment.pagePosition].setUser(ProfileFragment.user);
                    uuis[ProfileFragment.pagePosition] = uuiService.createUui(uuis[ProfileFragment.pagePosition]);
                }
                else {
                    showToast(view, "Up U: i="+i+" selItem="+adapterView.getSelectedItemPosition());
                    uuis[ProfileFragment.pagePosition].setUniversity(universitiesData.get(i));
                    uuis[ProfileFragment.pagePosition] = uuiService.updateUui(uuis[ProfileFragment.pagePosition]);
                }

                if (i > 0 && universitiesData != null && universitiesData.get(i) != null) {
                    //load all faculties and specialities related with specified university
                    configureFacultiesList(universitiesData.get(i).getId());
                    configureFacultySpinner(0);

                    configureSpecialitiesList(universitiesData.get(i).getId());
                    configureSpecialitySpinner(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spUniversities.setSelection(selectPosition);
    }

    private void configureSpecialitiesList(Long universityId) {
        specialitiesData = new ArrayList<>();
        specialitiesData.add(null);
        List<Speciality> specialitiesByUniversity = specialityService.getSpecialitiesByUniversityId(universityId);
        specialitiesData.addAll(specialitiesByUniversity);
    }

    private void configureSpecialitiesListByFaculty(Long facultyId) {
        specialitiesData = new ArrayList<>();
        specialitiesData.add(null);
        List<Speciality> specialitiesByFaculty = specialityService.getSpecialitiesByFacultyId(facultyId);
        specialitiesData.addAll(specialitiesByFaculty);
    }

    private void configureFacultiesList(Long universityId) {
        facultiesData = new ArrayList<>();
        facultiesData.add(null);
        List<Faculty> facultiesByUniversity = facultyService.getFacultiesByUniversityId(universityId);
        facultiesData.addAll(facultiesByUniversity);
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
