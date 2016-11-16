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
    private static int totalSteps = 0;
    private static int currentSteps = 0;
    private static int nRewardsEarned = 0;
    private static ArrayList<Challenge>  goals = null;
    private static ArrayList<Challenge>  rewards = null;
    private static Challenge currentGoal = null;

    public static void setCurrentGoal(Challenge c){
        currentGoal = c;
    }

    public static Challenge getCurrentGoal(){
        return currentGoal;
    }

    public static ArrayList<Challenge> getGoals() {
        if(goals == null) {
            goals = new ArrayList<Challenge>();
            goals.add(new Challenge("Chipotle BOGO", "Chipotle", "Walk really far to earn your b" +
                    "uy-one-get-one-free burrito!", 12000, R.drawable.chipotle, null));
            goals.add(new Challenge("Cheese!", "McDonald's", "Craving a cheeseburger?  Walk 8000 steps to " +
                    "get a free cheeseburger with any meal.", 8000, R.drawable.mcdonalds, null));
            goals.add(new Challenge("Short Goal!", "McDonald's", "Craving a cheeseburger?  Walk 10 steps to " +
                    "get 10 cents off", 10, R.drawable.mcdonalds, null));
        }
        return goals;
    }

    public static ArrayList<Challenge> getRewards() {
        if(rewards == null) {
            rewards = new ArrayList<Challenge>();
            rewards.add(new Challenge("Chipotle BOGO", "Chipotle", "Walk really far to earn your b" +
                    "uy-one-get-one-free burrito!", 12000, R.drawable.chipotle, null));
            rewards.add(new Challenge("Cheese!", "McDonald's", "Craving a cheeseburger?  Walk 8000 steps to " +
                    "get a free cheeseburger with any meal.", 8000, R.drawable.mcdonalds, null));
        }
        return rewards;
    }

    public static void addReward(Challenge c) {
        rewards = getRewards();
        rewards.add(c);
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

    public static void setnRewardsEarned(int i){ nRewardsEarned = i; }

    public static int getnRewardsEarned() { return nRewardsEarned; }
}
