package com.example.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class Datecourse_Search extends AppCompatActivity {

    View background;
    AutoCompleteTextView editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datecourse_search);

        //String[] items={"벚꽃 명소","홍대 맛집","루프탑","벚꽃 명소2","홍대 놀거리","벚꽃 보러간당"};

        String[] items = {"a", "hong", "loop", "ab", "hongplay", "abc"};
        ImageButton btnHome = findViewById(R.id.btnHome);
        background = findViewById(R.id.background);
        final boolean[] color = {false};

        editText = findViewById(R.id.editText);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, items);
        editText.setAdapter(adapter);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color[0] = true;
                if (color[0] == true) {
                    background.setBackgroundColor(Color.rgb(189, 189, 189));
                }
                background.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        background.setBackgroundColor(Color.rgb(255, 255, 255));
                    }
                });
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Datecourse_Search.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
