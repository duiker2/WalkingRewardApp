package edu.illinois.cs465.walkingrewardapp;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.reward.RewardedVideoAd;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengxiongliu on 08/11/2016.
 */

public class RewardsActivity extends AppCompatActivity {
    private ListView list;
    private List<Challenge> rewards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch(NullPointerException ex) {
            ex.printStackTrace();
        }

        initGoals();

        CustomList adapter = new CustomList(this, rewards);
        list = (ListView)findViewById(R.id.reward_list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(RewardsActivity.this, "You Clicked at " + rewards.get(position).getReward(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initGoals() {
        rewards = new ArrayList<>();
        rewards.add(new Challenge("Chipotle", "2000 steps", R.drawable.chipotle));
        rewards.add(new Challenge("McDonald's", "2000 steps", R.drawable.mcdonalds));
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
