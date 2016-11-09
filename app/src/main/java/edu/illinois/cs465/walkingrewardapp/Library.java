package edu.illinois.cs465.walkingrewardapp;

import android.content.Intent;

import edu.illinois.cs465.walkingrewardapp.Data.Goal;

/**
 * Created by computerpp on 11/8/2016.
 */

public class Library {
    public static Goal[] getGoals() {
        return new Goal[] {
                new Goal("Chipotle BOGO", "Chipotle", "Walk really far to earn your b" +
                        "uy-one-get-one-free burrito!", 12000, R.drawable.chipotle),
                new Goal("Cheese!", "McDonald's", "Craving a cheeseburger?  Walk 8000 steps to " +
                        "get a free cheeseburger with any meal.", 8000, R.drawable.mcdonalds),
        };
    }
}
