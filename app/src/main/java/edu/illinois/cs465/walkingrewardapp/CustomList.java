package edu.illinois.cs465.walkingrewardapp;

/**
 * Created by computerpp on 11/5/2016.
 * Code is from https://www.learn2crack.com/2013/10/android-custom-listview-images-text-example.html
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.illinois.cs465.walkingrewardapp.Data.Challenge;

public class CustomList extends ArrayAdapter<Challenge>{

    private final Activity context;
    private final List<Challenge> challenges;

    public CustomList(Activity context, List<Challenge> challenges) {
        super(context, R.layout.list_item_image_text, challenges);
        this.context = context;
        this.challenges = challenges;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_item_image_text, null, true);
        TextView txtReward = (TextView) rowView.findViewById(R.id.rewards);
        txtReward.setText(challenges.get(position).getDescription());

        TextView txtGoal = (TextView) rowView.findViewById(R.id.goals);
        txtGoal.setText(Integer.toString(challenges.get(position).getStepsRequired()));

        ImageView imageView = (ImageView) rowView.findViewById(R.id.restaurant_icon);
        imageView.setImageResource(challenges.get(position).getImage());
        return rowView;
    }
}