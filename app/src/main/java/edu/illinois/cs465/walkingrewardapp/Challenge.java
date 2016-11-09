package edu.illinois.cs465.walkingrewardapp;

/**
 * Created by mengxiongliu on 08/11/2016.
 */

public class Challenge {
    private String reward;
    private String goal;
    private Integer image;

    public Challenge(String reward, String goal, Integer image) {
        this.reward = reward;
        this.goal = goal;
        this.image = image;
    }

    public String getReward() {
        return reward;
    }

    public String getGoal() {
        return goal;
    }

    public Integer getImage() {
        return image;
    }
}
