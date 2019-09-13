package com.example.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

public class Date_Review extends Fragment {

    public Date_Review() {
    }

    Button review, OK, cancel;
    EditText addText;
    RatingBar _ratingbar;
    ImageView add;
    ListView listView;
    public DateReview_listViewAdapter adapter;
    public static Date_Review context;

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ConstraintLayout layout = (ConstraintLayout) inflater.inflate(R.layout.date_review, container, false);
        context=this;

        listView = layout.findViewById(R.id.listView);
        adapter = new DateReview_listViewAdapter();
        listView.setAdapter(adapter);

        // adapter.addItem( "밤에 가도 벚꽃이 너무 예뻐요!");
        // adapter.addItem("벚꽃을 보러간건지 사람을 보러 간건지..");

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
                final View digView = View.inflate(getContext(), R.layout.dialog_date_review, null);


                OK = digView.findViewById(R.id.OK);
                cancel = digView.findViewById(R.id.cancel);
                _ratingbar = digView.findViewById(R.id._ratingBar);

               /* _ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        ratingNum = _ratingbar.getRating();

                    }
                });*/
                _dig.setCancelable(false);
                _dig.setView(digView);
                _dig.show();

                OK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addText = digView.findViewById(R.id.addText);
                        String text = addText.getText().toString();
                        adapter.addItem(_ratingbar.getRating(),text);
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
