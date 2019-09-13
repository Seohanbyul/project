package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends Fragment {
    ImageView gift_Btn, profile_Btn1, profile_Btn2, storage, close;
    TextView to_do_Btn, date;
    View profileLayout1, profileLayout2;

    public Main(){}

    @Override
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_main,container,false);

        //인플레이트
        to_do_Btn = layout.findViewById(R.id.to_do_Btn);                            //투두리스트 버튼
        gift_Btn = layout.findViewById(R.id.gift_Btn);                              //선물버튼
        date = layout.findViewById(R.id.date);                                      //메인화면 사귄날짜

        profile_Btn1 = layout.findViewById(R.id.profile_Btn1);                      //프로필사진1(나) 버튼
        profile_Btn2 = layout.findViewById(R.id.profile_Btn2);                      //프로필사진2(상대방) 버튼


        //Date 날짜 계산 함수
        doDateSystem();



        //to_do_list 버튼 눌렀을 때 --> to_do 화면 전환
        to_do_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ToDoList.class);
                startActivity(intent);
            }
        });


        //선물버튼 눌렀을 때 --> 선물화면 전환
        gift_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Gift_Main.class);
                startActivity(intent);
            }
        });


        //왼쪽 프로필을 누를 때 -->  정보 변경 가능한 다이얼로그 창
        profile_Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());                            //Main 화면에 다이얼로그 생성
                final AlertDialog dl = dlg.create();                                                        //빌더를 이용해 AlertDialog 객체 생성
                profileLayout1 = View.inflate(getContext(), R.layout.profile1, null);                 //레이아웃뷰인 profileLayout을 메인에 profile.xml 사용해서 인플레이트
                dl.setView(profileLayout1);                                                                 //porofileLayout을 AlertDialog 객체로 보여줌
                dl.show();                                                                                  //다이얼로그 보여줌

                //메인화면 다이얼로그에 들어가는 profile1의 뷰들 인플레이트
                storage = profileLayout1.findViewById(R.id.storage);

                //저장을 버튼을 클릭했을 때 수정된 내용을 저장한 후 다이얼로그 종료
                storage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //저장 클릭 시 수정된 내용을 저장하고 출력 구현할 부분
                        dl.dismiss();
                    }
                });

            }
        });

        //오른쪽 프로필을 누를 때 -->  정보 변경 불가능한 다이얼로그 창
        profile_Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());                         //Main 화면에 다이얼로그 생성
                final AlertDialog dl2 = dlg.create();                                                    //빌더를 이용해 AlertDialog 객체 생성
                profileLayout2 = View.inflate(getContext(), R.layout.profile2, null);              //레이아웃뷰인 profileLayout2을 메인에 profile2.xml 사용해서 인플레이트
                dl2.setView(profileLayout2);                                                             //porofileLayout2을 AlertDialog 객체로 보여줌
                dl2.show();                                                                              //다이얼로그 보여줌

                //메인화면 다이얼로그에 들어가는 profile2의 뷰들 인플레이트
                close = profileLayout2.findViewById(R.id.close);

                //닫기 버튼 클릭했을 때 다이얼로그 종료
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dl2.dismiss();
                    }
                });
            }
        });

        return layout;
    }

    public void doDateSystem(){
        String start = "2019-03-04";        // 사귄 날짜 입력

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");        //SimpleDataFormat 형태의 변수를 년-월-일로 생성
            Date beginDate = formatter.parse(start);                                        //사귄날짜를 입력받은 문자열을 date 형식으로 변경
            String end = formatter.format(new Date());                                      //현재날짜를 문자열로 받아옴
            Date endDate = formatter.parse(end);                                            //현재날짜를 받아온 문자열을 date 형식으로 변경

            // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
            long diff =  endDate.getTime() - beginDate.getTime();
            long coupleDays = diff / (24 * 60 * 60 * 1000);

            //사귄 날짜가 1000일이 넘으면 textSize 변경
            if(coupleDays > 1000) {
                date.setTextSize(30);
            }
            date.setText (coupleDays+" 일");     //사귄날짜 + 일 출력


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
