package edu.illinois.cs465.walkingrewardapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import java.util.GregorianCalendar;
import java.util.Calendar;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by mengxiongliu on 05/11/2016.
 */

public class StatisticsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button chooseDateButton;
    private DatePickerFragment dialogFragment;
    private TableLayout statisticsTable;

    private HashMap<String, String> staticStatistics = new HashMap<>();

    @Override
    protected void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_statistics);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch(NullPointerException ex) {
            ex.printStackTrace();
        }

        chooseDateButton = (Button) findViewById(R.id.choose_date_button);
        chooseDateButton.setOnClickListener(this);

        statisticsTable = (TableLayout) findViewById(R.id.statistics_table);
        initStatistics();
    }

    private void initStatistics() {
        staticStatistics.put("Traveled", "4.5 miles");
        staticStatistics.put("Earned", "2 coupons");
        staticStatistics.put("Used", "1 coupon");
        staticStatistics.put("Saved", "$2.24");
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        private StatisticsActivity activity;

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

        public void setActivity(StatisticsActivity activity) {
            this.activity = activity;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            activity.onDateSelected(year, month, day);
        }
    }

    public void showDatePickerDialog(View v) {
        dialogFragment = new DatePickerFragment();
        dialogFragment.setActivity(this);
        dialogFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onClick(View v){
        if (v.getId() == R.id.choose_date_button)
            showDatePickerDialog(v);
    }

    public void onDateSelected(int year, int month, int day) {
        dialogFragment.dismiss();
        Toast.makeText(this, "Select date success", Toast.LENGTH_SHORT).show();

        TextView traveled = (TextView) findViewById(R.id.traveled);
        traveled.setText(staticStatistics.get("Traveled"));
        TextView earned = (TextView) findViewById(R.id.earned);
        earned.setText(staticStatistics.get("Earned"));
        TextView used = (TextView) findViewById(R.id.used);
        used.setText(staticStatistics.get("Used"));
        TextView saved = (TextView) findViewById(R.id.saved);
        saved.setText(staticStatistics.get("Saved"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
