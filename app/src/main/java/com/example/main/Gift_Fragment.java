package com.example.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Gift_Fragment extends AppCompatActivity implements View.OnClickListener {

    TextView giftInfo, giftReview, infoLine, reviewLine;
    Gift_Info gift_info;
    ImageView favorite;
    int i = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gift_imageclick);

        giftInfo = findViewById(R.id.giftInfo);
        giftReview = findViewById(R.id.giftReview);
        infoLine = findViewById(R.id.infoLine);
        reviewLine = findViewById(R.id.reviewLine);
        favorite = findViewById(R.id.favorite);
        gift_info = new Gift_Info();

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if (i % 2 != 0) {
                    favorite.setBackgroundResource(R.drawable.ic_star_black_24dp);
                    Toast.makeText(Gift_Fragment.this, "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    favorite.setBackgroundResource(R.drawable.ic_star_border_black_24dp);
                    Toast.makeText(Gift_Fragment.this, "즐겨찾기에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainFrag, new Gift_Info())
                    .commit();
        }
        giftInfo.setOnClickListener(this);
        giftReview.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.giftInfo:
                giftInfo.setTextColor(Color.rgb(184, 217, 192));
                infoLine.setBackgroundColor(Color.rgb(184, 217, 192));
                giftReview.setTextColor(Color.rgb(140, 140, 140));
                reviewLine.setBackgroundColor(Color.rgb(140, 140, 140));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainFrag, new Gift_Info())
                        .commit();
                break;
            case R.id.giftReview:
                giftInfo.setTextColor(Color.rgb(140, 140, 140));
                infoLine.setBackgroundColor(Color.rgb(140, 140, 140));
                giftReview.setTextColor(Color.rgb(184, 217, 192));
                reviewLine.setBackgroundColor(Color.rgb(184, 217, 192));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainFrag, new Gift_Review())
                        .commit();
                break;
        }
    }
}