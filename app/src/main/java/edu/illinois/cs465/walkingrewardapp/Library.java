package edu.illinois.cs465.walkingrewardapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.illinois.cs465.walkingrewardapp.Data.Challenge;

/**
 * Created by computerpp on 11/8/2016.
 */

public class Library {
    public static ArrayList<Challenge> getGoals() {
        ArrayList<Challenge> list = new ArrayList<Challenge>();
        list.add(new Challenge("Chipotle BOGO", "Chipotle", "Walk really far to earn your b" +
                        "uy-one-get-one-free burrito!", 12000, R.drawable.chipotle, null));
        list.add(new Challenge("Cheese!", "McDonald's", "Craving a cheeseburger?  Walk 8000 steps to " +
                        "get a free cheeseburger with any meal.", 8000, R.drawable.mcdonalds, null));
        return list;
    }

    public static ArrayList<Challenge> getRewards() {
        ArrayList<Challenge> list = new ArrayList<Challenge>();
        try {
            list.add(new Challenge("Free Entre", "Panera Bread", "Want a free entre the next time you " +
                    "visit your favorite coffee shop/bakery/sandwich shop?  Take 15000 steps in " +
                    "a day and it's yours!", 15000, R.drawable.panera, new SimpleDateFormat("MM/dd/yy").parse("08/01/2016")));
        }
        catch(ParseException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
