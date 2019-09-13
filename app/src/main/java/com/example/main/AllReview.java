package com.example.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class AllReview extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_reviewcheck);

        //리뷰 정렬하는 요소를 넣는 ArrayList 선언 및 생성, 어댑터
        ArrayList<String> reviewList;
        ArrayAdapter reviewAdapter;
        String[] review = {"최신순", "별점 높은 순 ", "별점 낮은 순"};

        reviewList = new ArrayList<>();

        for (int i = 0; i < review.length; i++) {
            reviewList.add(review[i]);
        }
        Spinner spinner = findViewById(R.id.spinner);

        reviewAdapter = new ArrayAdapter(AllReview.this, android.R.layout.simple_spinner_dropdown_item, reviewList);
        spinner.setAdapter(reviewAdapter);

        listView = findViewById(R.id.listView);
        listView.setAdapter(((Date_Review)Date_Review.context).adapter);

      /*  spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==1){

                }
            }
        });*/
    }
}
