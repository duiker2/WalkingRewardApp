package edu.illinois.cs465.walkingrewardapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import java.util.GregorianCalendar;
import java.util.Calendar;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Date;

/**
 * Created by mengxiongliu on 05/11/2016.
 */

public class StatisticsActivity extends Activity implements View.OnClickListener {

    private Button chooseDateButton;

    @Override
    protected void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_statistics);

        chooseDateButton = (Button) findViewById(R.id.choose_date_button);
        chooseDateButton.setOnClickListener(this);
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            GregorianCalendar c = new GregorianCalendar();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DATE);

            // Create a new instance of TimePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int dat) {
            // Do something with the time chosen by the user
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void onClick(View v){
        if (v.getId() == R.id.choose_date_button)
            showDatePickerDialog(v);
    }
}
