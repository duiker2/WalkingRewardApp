package edu.illinois.cs465.walkingrewardapp;

/**
 * Created by computerpp on 11/5/2016.
 * The generic list code is from https://www.learn2crack.com/2013/10/android-custom-listview-images-text-example.html
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import java.io.Serializable;

import edu.illinois.cs465.walkingrewardapp.Data.Challenge;
import edu.illinois.cs465.walkingrewardapp.Data.ChallengeList;

public class ChooseGoalActivity extends AppCompatActivity {
    protected void openActivity(Class<?> activity, Serializable parameter) {
        Intent intent = new Intent(this, activity);
        intent.putExtra("data", parameter);
        startActivity(intent);
    }

    private ChallengeList goals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_goal);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch(NullPointerException ex) {
            ex.printStackTrace();
        }

        goals = Library.getGoals();

        CustomList adapter = new CustomList(this, goals);
        ListView list = (ListView)findViewById(R.id.goal_list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(ChooseGoalActivity.this, ViewGoalActivity.class);
                intent.putExtra("goal", goals.get(position));
                startActivity(intent);
            }
        });
    }

    protected void openActivity(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    //code from http://www.vogella.com/tutorials/AndroidActionBar/article.html
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        Library.initializeData(getApplicationContext());

        return true;
    }

    //code is from https://developer.android.com/training/implementing-navigation/ancestral.html
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_change_goal:
                openActivity(ChooseGoalActivity.class);
                break;
            case R.id.action_my_rewards:
                //Toast.makeText(getApplicationContext(), "Thanks for clicking the Rewards button!", Toast.LENGTH_SHORT).show();
                openActivity(RewardsActivity.class);
                break;
            case R.id.action_view_statistics:
                openActivity(StatisticsActivity.class);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
