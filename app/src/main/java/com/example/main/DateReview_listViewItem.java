package com.example.main;

import android.graphics.drawable.Drawable;
import android.widget.RatingBar;

public class DateReview_listViewItem {
    public String context;
    public static float rating;


    public void setContext(String context) {
        this.context = context;
    }

    public String getContext() {
        return this.context;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getRating() {
        return this.rating;
    }
}
