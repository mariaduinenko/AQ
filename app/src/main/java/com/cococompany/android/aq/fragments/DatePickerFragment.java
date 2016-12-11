package com.cococompany.android.aq.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cococompany.android.aq.R;
import com.cococompany.android.aq.fragments.ProfileFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by alexandrmyagkiy on 05.12.16.
 */

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener, DialogInterface.OnClickListener {

    private View view = null;
    private String type = "";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        view = ((RelativeLayout) ProfileFragment.uuiSwipeAdapter.getView(ProfileFragment.pagePosition));

//        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK);
//        return (builder.setTitle("Диалог выбора даты").setView(view)
//                .setPositiveButton(android.R.string.ok, this)
//                .setNegativeButton(android.R.string.cancel, null)
//                .create());

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

    public void setEntranceType() {
        this.type = "entr";
    }

    public void setGraduationType() {
        this.type = "grad";
    }

    public void setBirthdateType() {
        this.type = "bdate";
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, 0, 0, 0);

        if (type.equals("entr")) {
            EditText etEntr = (EditText) this.view.findViewById(R.id.etEntranceDate);
            etEntr.setText(dateFormatter.format(c.getTime()));
            try {
                Date date = dateFormatter.parse(etEntr.getText().toString());
                ProfileFragment.me.getUuis().get(ProfileFragment.pagePosition).setEntranceDate(dateFormatter.format(date));
                showToast(view, "Date:" + date.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (type.equals("grad")) {
            EditText etGrad = (EditText) this.view.findViewById(R.id.etGraduationDate);
            etGrad.setText(dateFormatter.format(c.getTime()));
            try {
                Date date = dateFormatter.parse(etGrad.getText().toString());
                ProfileFragment.me.getUuis().get(ProfileFragment.pagePosition).setGraduationDate(dateFormatter.format(date));
                showToast(view, "Date:" + date.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (type.equals("bdate")) {
            View parent = ((RelativeLayout)this.view.getParent().getParent()).findViewById(R.id.birthdate);
            EditText etBdate = (EditText) parent.findViewById(R.id.birthdate);
            etBdate.setText(dateFormatter.format(c.getTime()));
            try {
                Date date = dateFormatter.parse(etBdate.getText().toString());
                showToast(view, "Date:" + date.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void showToast(View view, String text) {
        //создаем и отображаем текстовое уведомление
        Toast toast = Toast.makeText(view.getContext(),
                text,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }
}
