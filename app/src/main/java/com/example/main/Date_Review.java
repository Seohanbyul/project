package com.example.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Date_Review extends Fragment {
    public Date_Review() {
    }

    Button review, OK, cancel;
    ImageView add;

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.date_review, container, false);

        review = layout.findViewById(R.id.review);
        add = layout.findViewById(R.id.add);

        //전체리뷰 보기 클릭시 발생하는 이벤트 리스러
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AllReview.class);
                startActivity(intent);
            }
        });

        //리뷰를 추가할 수 있는 dialog를 띄운다.
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dig = new AlertDialog.Builder(getContext());
                final AlertDialog _dig = dig.create();
                View digView = View.inflate(getContext(), R.layout.dialog_date_review, null);

                OK = digView.findViewById(R.id.OK);
                cancel = digView.findViewById(R.id.cancel);

                _dig.setCancelable(false);
                _dig.setView(digView);
                _dig.show();

                OK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "리뷰가 등록되었습니다.", Toast.LENGTH_SHORT).show();
                        _dig.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        _dig.dismiss();
                    }
                });
            }
        });
        return layout;
    }
}