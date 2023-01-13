package com.example.calendarapplication.TimeFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment{

    public boolean check = false;
    public int setYear;
    public int setMonth;
    public int setDay;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        if (check){
            year = setYear;
            month = setMonth-1;
            day = setDay;
            check = false;
        }

        Log.d("debug",""+year+"："+month+"："+day+"：");

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener)getActivity(), year, month, day); //this はonDateSetListener
        return datePickerDialog;
    }

    public void selected(int year,int month,int day){
        setYear = year;
        setMonth = month;
        setDay = day;
        check = true;
    }
}
