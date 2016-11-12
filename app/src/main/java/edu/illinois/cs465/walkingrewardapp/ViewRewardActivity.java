package edu.illinois.cs465.walkingrewardapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.illinois.cs465.walkingrewardapp.Data.Challenge;

/**
 * Created by mengxiongliu on 12/11/2016.
 */

public class ViewRewardActivity extends AppCompatActivity {
    private Button cancelButton;
    private Challenge reward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reward);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch(NullPointerException e) {
            e.printStackTrace();
        }

        // replace text in text view with information in the reward object
        reward = (Challenge) getIntent().getExtras().getSerializable("reward");
        setTextView();

        cancelButton = (Button) findViewById(R.id.view_reward_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.view_reward_cancel) {
                    Intent intent = new Intent(ViewRewardActivity.this, RewardsActivity.class);
                    startActivity(intent);
                    return;
                }
            }
        });
    }

    private void setTextView() {
        TextView title = (TextView) findViewById(R.id.view_reward_title);
        title.setText(reward.getTitle());
        TextView description = (TextView) findViewById(R.id.view_reward_description);
        description.setText(reward.getDescription());
    }
}
