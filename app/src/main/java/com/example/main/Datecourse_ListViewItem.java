package com.example.main;

import android.graphics.drawable.Drawable;
import android.widget.RatingBar;

public class Datecourse_ListViewItem {
    public Drawable image;
    public String title;

    public void setImage(Drawable image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getImage() {
        return this.image;
    }

    public String getTitle() {
        return this.title;
    }
}
