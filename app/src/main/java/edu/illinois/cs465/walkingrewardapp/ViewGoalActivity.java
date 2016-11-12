package edu.illinois.cs465.walkingrewardapp;

/**
 * Created by computerpp on 11/5/2016.
 * The generic list code is from https://www.learn2crack.com/2013/10/android-custom-listview-images-text-example.html
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ViewGoalActivity extends AppCompatActivity implements View.OnClickListener {
    private Button selectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_goal);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch(NullPointerException e) {
            e.printStackTrace();
        }

        selectButton = (Button) findViewById(R.id.view_goal_select);
        selectButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if (v.getId() == R.id.view_goal_select) {
            Intent intent = new Intent(this, WalkingActivity.class);
            intent.putExtra("start_message", "Start your goal now");
            startActivity(intent);
        }
    }
}
