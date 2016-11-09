package edu.illinois.cs465.walkingrewardapp;

/**
 * Created by computerpp on 11/5/2016.
 * The generic list code is from https://www.learn2crack.com/2013/10/android-custom-listview-images-text-example.html
 */

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ViewGoalActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_goal);
        try {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        } catch(NullPointerException ex) {
            int x = 1;
        }
    }
}
