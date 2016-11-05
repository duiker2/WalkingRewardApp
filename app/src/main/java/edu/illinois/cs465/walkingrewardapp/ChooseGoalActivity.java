package edu.illinois.cs465.walkingrewardapp;

/**
 * Created by computerpp on 11/5/2016.
 * The generic list code is from https://www.learn2crack.com/2013/10/android-custom-listview-images-text-example.html
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ChooseGoalActivity extends AppCompatActivity {
    private ListView list;
    private String[] web = {
            "Chipotle",
            "McDonald's",
    } ;
    private Integer[] imageId = {
            R.drawable.chipotle,
            R.drawable.mcdonalds,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_goal);
        try {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        } catch(NullPointerException ex) {
            int x = 1;
        }

        CustomList adapter = new
                CustomList(ChooseGoalActivity.this, web, imageId);
        list=(ListView)findViewById(R.id.goal_list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(ChooseGoalActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });
    }

    //code is from https://developer.android.com/training/implementing-navigation/ancestral.html
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
