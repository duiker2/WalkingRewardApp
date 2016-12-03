package edu.illinois.cs465.walkingrewardapp;

/**
 * Created by computerpp on 11/5/2016.
 * The generic list code is from https://www.learn2crack.com/2013/10/android-custom-listview-images-text-example.html
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import edu.illinois.cs465.walkingrewardapp.Data.Challenge;

public class ViewGoalActivity extends AppCompatActivity implements View.OnClickListener {
    private Challenge goal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_goal);

        goal = (Challenge) getIntent().getExtras().getSerializable("goal");

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(goal.getTitle());
        }
        catch(NullPointerException e) {
            e.printStackTrace();
        }

        setTextView();

        FloatingActionButton selectButton = (FloatingActionButton) findViewById(R.id.view_goal_select);
        selectButton.setOnClickListener(this);
    }

    private void setTextView() {
        TextView title = (TextView) findViewById(R.id.view_goal_restaurant);
        title.setText(goal.getRestaurant());

        TextView description = (TextView) findViewById(R.id.view_goal_description);
        description.setText(goal.getDescription());

        TextView steps = (TextView) findViewById(R.id.view_goal_steps);
        steps.setText(goal.getStringTimeLimitMinutes() + ", " + Integer.toString(goal.getStepsRequired()) + " steps");

        ImageView imageView = (ImageView) findViewById(R.id.restaurant_icon);
        imageView.setImageResource(goal.getImage());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                startActivity(new Intent(getApplicationContext(), WalkingActivity.class));
                break;
            case R.id.action_change_goal:
                startActivity(new Intent(getApplicationContext(), ChooseGoalActivity.class));
                break;
            case R.id.action_my_rewards:
                startActivity(new Intent(getApplicationContext(), RewardsActivity.class));
                break;
            case R.id.action_view_statistics:
                startActivity(new Intent(getApplicationContext(), StatisticsActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v){
        if (v.getId() == R.id.view_goal_select) {
            Intent intent = new Intent(this, WalkingActivity.class);
            intent.putExtra("goal_object", goal);
            intent.putExtra("start_message", "Start your goal now");
            startActivity(intent);
            Library.setCurrentGoal(goal);
        }
    }
}
