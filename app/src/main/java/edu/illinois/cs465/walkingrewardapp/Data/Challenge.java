package edu.illinois.cs465.walkingrewardapp.Data;

import java.io.Serializable;

/**
 * Created by mengxiongliu on 08/11/2016.
 */

public class Challenge implements Serializable {
    private String title, restaurant, description;
    private int stepsRequired;
    private int image;

    public Challenge(String title, String name, String description, int stepsRequired, int image) {
        this.title = title;
        this.restaurant = name;
        this.image = image;
        this.description = description;
        this.stepsRequired = stepsRequired;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public String getDescription() {
        return description;
    }

    public int getStepsRequired() {
        return stepsRequired;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }
}
