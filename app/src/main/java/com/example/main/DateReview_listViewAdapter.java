package com.example.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class DateReview_listViewAdapter extends BaseAdapter {

    public static Object context;
    public RatingBar ratingbar;
    ArrayList<DateReview_listViewItem> review_listItem = new ArrayList<DateReview_listViewItem>();

    public DateReview_listViewAdapter(){
        context=this;
    }
    @Override
    public int getCount() {
        return review_listItem.size();
    }

    @Override
    public Object getItem(int position) {
        return review_listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context _context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.datereview_listview, parent, false);
        }

        TextView context = convertView.findViewById(R.id.context);
        ratingbar = convertView.findViewById(R.id.ratingBar);

        DateReview_listViewItem listItem = review_listItem.get(position);

        context.setText(listItem.getContext());
        ratingbar.setRating(listItem.getRating());

        return convertView;
    }

    public void addItem(float rating, String context) {
        DateReview_listViewItem item = new DateReview_listViewItem();

        item.setContext(context);
        item.setRating(rating);

        review_listItem.add(item);
    }
}
