package edu.illinois.cs465.walkingrewardapp;

import android.content.Intent;

import java.util.ArrayList;

import edu.illinois.cs465.walkingrewardapp.Data.Challenge;

/**
 * Created by computerpp on 11/8/2016.
 */

public class Library {
    private static int totalSteps = 0;
    private static int currentSteps = 0;
    private static ArrayList<Challenge>  goals;
    private static ArrayList<Challenge>  rewards;

    public static ArrayList<Challenge> getGoals() {
        if(goals == null) {
            goals = new ArrayList<Challenge>();
            goals.add(new Challenge("Chipotle BOGO", "Chipotle", "Walk really far to earn your b" +
                    "uy-one-get-one-free burrito!", 12000, R.drawable.chipotle));
            goals.add(new Challenge("Cheese!", "McDonald's", "Craving a cheeseburger?  Walk 8000 steps to " +
                    "get a free cheeseburger with any meal.", 8000, R.drawable.mcdonalds));
        }
        return goals;
    }

    public static ArrayList<Challenge> getRewards() {
        if(rewards == null) {
            rewards = new ArrayList<Challenge>();
            rewards.add(new Challenge("Chipotle BOGO", "Chipotle", "Walk really far to earn your b" +
                    "uy-one-get-one-free burrito!", 12000, R.drawable.chipotle));
            rewards.add(new Challenge("Cheese!", "McDonald's", "Craving a cheeseburger?  Walk 8000 steps to " +
                    "get a free cheeseburger with any meal.", 8000, R.drawable.mcdonalds));
        }
        return rewards;
    }

    public static void setTotalSteps(int i){
        totalSteps = i;
    }

    public static int getTotalSteps(){
        return totalSteps;
    }

    public static void setCurrentSteps(int i){
        currentSteps = i;
    }

    public static int getCurrentSteps(){
        return currentSteps;
    }
}
