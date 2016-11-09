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

/**
 * Created by mengxiongliu on 08/11/2016.
 */

public class RewardsActivity extends AppCompatActivity {
    private ListView list;
    private String[] rewards = {
            "Chipotle",
            "McDonald's",
    };
    private Integer[] imageId = {
            R.drawable.chipotle,
            R.drawable.mcdonalds,
    };
    private String[] goals = {
            "2000 steps",
            "2000 steps",
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

//        CustomList adapter = new CustomList(this, rewards, imageId);
//        list=(ListView)findViewById(R.id.goal_list);
//        list.setAdapter(adapter);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                Toast.makeText(RewardsActivity.this, "You Clicked at " + rewards[position], Toast.LENGTH_SHORT).show();
//
//            }
//        });
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
