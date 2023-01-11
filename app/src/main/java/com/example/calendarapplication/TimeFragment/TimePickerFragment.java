package com.example.calendarapplication.TimeFragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public  class TimePickerFragment extends DialogFragment{
    public boolean check = false;
    public int setHour;
    public int setMinute;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        if (check){
            hour = setHour;
            minute = setMinute;
            check = false;
        }

        // Create a new instance of TimePickerDialog and return it
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener)getActivity(), hour, minute, DateFormat.is24HourFormat(getActivity()));
        return timePickerDialog;
    }

    public void selected(int hou,int min){
        setHour = hou;
        setMinute = min;
        check = true;
    }
}
