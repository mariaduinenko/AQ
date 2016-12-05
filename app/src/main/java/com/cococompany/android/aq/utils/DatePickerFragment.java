package com.cococompany.android.aq.utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.fragments.ProfileFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by alexandrmyagkiy on 05.12.16.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private View view;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        //Create a new DatePickerDialog instance and return it
        /*
            DatePickerDialog Public Constructors which support Theme declaration
            public DatePickerDialog (Context context, int theme, DatePickerDialog.OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth)
            Supported Themes are....
                THEME_DEVICE_DEFAULT_DARK
                THEME_DEVICE_DEFAULT_LIGHT
                THEME_HOLO_DARK
                THEME_HOLO_LIGHT
                THEME_TRADITIONAL

         */
        //*********** Just uncomment any one below line to apply another Theme ************
//        return new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_DARK,this, year, month, day);
        //return new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, this, year, month, day);
        return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_DARK, this, year, month, day);
        //return new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
        //return new DatePickerDialog(getActivity(), AlertDialog.THEME_TRADITIONAL, this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user

        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, 0, 0, 0);

//        EditText etEntr = (EditText) ((LinearLayout) ProfileFragment.viewPager.getChildAt(ProfileFragment.viewPager.getCurrentItem())).findViewById(R.id.etEntranceDate);

//        etEntr.setText(dateFormatter.format(c.getTime()));
        showToast(view, "EditText text:"+c.getTime().toString());
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
