package edu.illinois.cs465.walkingrewardapp.Data;

import java.io.Serializable;

/**
 * Created by computerpp on 11/8/2016.
 */

public class Goal implements Serializable {
    private String title, restaurant, description;
    private int stepsRequired;
    private int image;

    public Goal(String title, String name, String description, int stepsRequired, int image) {
        this.title = title;
        this.restaurant = name;
        this.image = image;
        this.description = description;
        this.stepsRequired = stepsRequired;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStepsRequired() {
        return stepsRequired;
    }

    public void setStepsRequired(int stepsRequired) {
        this.stepsRequired = stepsRequired;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
