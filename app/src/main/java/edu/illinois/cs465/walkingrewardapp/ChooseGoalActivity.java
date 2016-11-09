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

import java.io.Serializable;

import edu.illinois.cs465.walkingrewardapp.Data.Goal;

public class ChooseGoalActivity extends AppCompatActivity {
    private ListView list;

    protected void openActivity(Class<?> activity, Serializable parameter) {
        Intent intent = new Intent(this, activity);
        intent.putExtra("data", parameter);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_goal);
        try {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        } catch(NullPointerException ex) {
            int x = 1;
        }

        //Todo: Make the CustomList constructor accept an array of goals instead of needing to split it up here
        Goal[] goals = Library.getGoals();
        String[] names = new String[goals.length];
        Integer[] images = new Integer[goals.length];
        for(int i = 0 ; i < goals.length ; i++) {
            names[i] = goals[i].getTitle();
            images[i] = goals[i].getImage();
        }
        CustomList adapter = new
                CustomList(ChooseGoalActivity.this, names, images);
        list=(ListView)findViewById(R.id.goal_list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Goal[] goals = Library.getGoals();
                openActivity(ViewGoalActivity.class, goals[position]);

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
