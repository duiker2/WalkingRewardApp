package edu.illinois.cs465.walkingrewardapp;

import android.content.Intent;

import java.util.ArrayList;

import edu.illinois.cs465.walkingrewardapp.Data.Challenge;

/**
 * Created by computerpp on 11/8/2016.
 */

public class Library {
    public static ArrayList<Challenge> getGoals() {
        ArrayList<Challenge> list = new ArrayList<Challenge>();
        list.add(new Challenge("Chipotle BOGO", "Chipotle", "Walk really far to earn your b" +
                        "uy-one-get-one-free burrito!", 12000, R.drawable.chipotle));
        list.add(new Challenge("Cheese!", "McDonald's", "Craving a cheeseburger?  Walk 8000 steps to " +
                        "get a free cheeseburger with any meal.", 8000, R.drawable.mcdonalds));
        return list;
    }
}
