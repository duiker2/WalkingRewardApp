package edu.illinois.cs465.walkingrewardapp.Data;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by mengxiongliu on 08/11/2016.
 */

public class Challenge implements Serializable {
    private String title, restaurant, description;
    private int stepsRequired;
    private int image;
    private Date timestampCompleted;

    public Challenge(String title, String name, String description, int stepsRequired, int image, Date timestampCompleted) {
        this.title = title;
        this.restaurant = name;
        this.image = image;
        this.description = description;
        this.stepsRequired = stepsRequired;
        this.timestampCompleted = timestampCompleted;
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

    public Date getTimestampCompleted() {
        return timestampCompleted;
    }

    public String getCompletedTimeString(int dateFormat) {
        if(timestampCompleted == null)
            return null;
        else
            return DateFormat.getDateInstance(dateFormat, Locale.getDefault()).format(timestampCompleted);
    }
}
