package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

public class DateImageClick extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dateimage_click);

        ListView listView;
        Datecourse_ListViewAdapter adapter;

        ImageButton btnHome=findViewById(R.id.btnHome);
        adapter = new Datecourse_ListViewAdapter();

        listView = findViewById(R.id.imagelist);
        listView.setAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.dateimage1), "석촌 호수");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.dateimage2), "수원 경기도청");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.dateimage3), "서울 어린이 대공원");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.dateimage4), "여의도");
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.dateimage5), "인천 대공원");


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DateImageClick.this, Datecourse_Fragment.class);
                startActivity(intent);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DateImageClick.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
