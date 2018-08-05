package com.hyeong.pinkseat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

// 2018.08.01 한 것 : MySeatActivity를 MySeatFragment로 바꿈 (레이아웃도 수정)
//          남은 것 : 2번 화면의 메뉴 '나의 좌석 현황'과 연결 & 2번 화면이 7번 화면의 좌석 선택 정보를 받으면, 각 TextView에 출력 & 타이머 구동 확인 후 수정

// <8번 나의 좌석 현황 화면>
public class MySeatFragment extends Fragment {


    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy년-MM월-dd일");

    protected void Update() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\
        View v = inflater.inflate(R.layout.fragment_my_seat,container,false);
        Button btn_ok = (Button)v.findViewById(R.id.btn_ok2);
        Button btn_cansle = (Button)v.findViewById(R.id.btn_cancle);

        TextView tv_train_time = (TextView)v.findViewById(R.id.seatinfo_time);
        TextView tv_today = (TextView) v.findViewById(R.id.seatinfo_date);
        TextView tv_seet_num = (TextView) v.findViewById(R.id.seatinfo_seatnum);

        Intent intent_5 = getActivity().getIntent(); //7번의 인텐트 수신
        final String train_info1 = intent_5.getStringExtra("train_time");
        final String train_info2 = intent_5.getStringExtra("train_dir");
        final String seat_info = intent_5.getStringExtra("seat_num");

        tv_train_time.setText(train_info1+"\n("+train_info2+")");
        tv_today.setText(getTime().toString());
        tv_seet_num.setText(seat_info);


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"제한시간 내에 앉음",Toast.LENGTH_SHORT).show();
            }
        });

        btn_cansle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return v;
    }

    //오늘 날짜 얻는 함수
    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
    }

