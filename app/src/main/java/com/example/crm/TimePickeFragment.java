package com.example.crm;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.SimpleTimeZone;

public class TimePickeFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private RegistroActividad_Activity.TimeInterface listener;
    Calendar c;

    public TimePickeFragment(RegistroActividad_Activity.TimeInterface listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute, false);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        this.listener.onTimeSet(sdf.format(c.getTime()));
    }
}