package com.hyeong.pinkseat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

// 2018.08.01 한 것 : MySeatActivity를 MySeatFragment로 바꿈 (레이아웃도 수정)
//          남은 것 : 2번 화면의 메뉴 '나의 좌석 현황'과 연결 & 2번 화면이 7번 화면의 좌석 선택 정보를 받으면, 각 TextView에 출력 & 타이머 구동 확인 후 수정

// 2018.08.05 한 것 : 2번 화면의 메뉴 '나의 좌석 현황'과 연결 & 2번 화면이 7번 화면의 좌석 선택 정보를 받으면, 각 TextView에 출력
//          남은 것 : '취소'버튼 클릭시 동작 설정 & 타이머 구동 확인 후 수정

// 2018.08.06 한 것 : 나의 좌석 현황을 레이아웃->리스트뷰로 바꿈 ('취소'버튼 클릭 시, 빈 화면 설정을 위함)
//          남은 것 : 타이머 구동 & '확인'버튼 클릭(제한 시간 내로 착석)시, 타이머 멈추기

// <8번 나의 좌석 현황 화면>
public class MySeatFragment extends Fragment {

    //오늘 날짜 값 담을 변수
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy년MM월dd일");

    //빈 화면용 변수
    TextView emptyview;
    LinearLayout lay_myseat;
    static boolean cancled =false; //취소 버튼 클릭 여부를 저장하는 변수 -> true면 빈화면만 뜨게 함

    //리스트 아이템용 변수
    String today;
    String train_info1, train_info2, train_time;
    String seat_info;
    String plat_num, plat_dir, plat_info;

    ListView listview; //리스트 뷰
    MySeatListViewAdapter adapter; //어뎁터

    protected void Update() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_seat, container, false);

        /*** [좌석 현황의 리스트뷰 설정] ***/
        // Adapter 생성
        adapter = new MySeatListViewAdapter();
        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) v.findViewById(R.id.listview2);
        listview.setAdapter(adapter);

        if(!cancled){
            Intent intent_5 = getActivity().getIntent(); //7번의 인텐트 수신
            train_info1 = intent_5.getStringExtra("train_time"); //열차 시간
            train_info2 = intent_5.getStringExtra("train_dir");  //열차 방향
            seat_info = intent_5.getStringExtra("seat_num");     //좌석 번호
            plat_num = intent_5.getStringExtra("plat_num");     //승강장 번호
            plat_dir = intent_5.getStringExtra("plat_dir");     //구간 번호

            today = getTime().toString(); //오늘 날짜
            train_time = train_info1 + "\n(" + train_info2;
            plat_info = "["+plat_num+"-"+plat_dir+"칸]";

            // 아이템 추가
            adapter.addItem(today, train_time, plat_info, seat_info, "05:00");
        }else adapter.addItem(null, null, null, null, null);


        //*** [빈 화면 설정] ***//
        emptyview = (TextView) v.findViewById(R.id.empty_view);
        lay_myseat = (LinearLayout)v.findViewById(R.id.lay_myseat);

        if (adapter.getTv4(0)==null||cancled==true) {
            listview.setVisibility(View.GONE);
            lay_myseat.setVisibility(View.GONE);
            emptyview.setVisibility(View.VISIBLE); //선택된 좌석이 없다면, empty_view 보이기
        } else {
            listview.setVisibility(View.VISIBLE);
            lay_myseat.setVisibility(View.VISIBLE);
            emptyview.setVisibility(View.GONE); //좌석이 있다면, empty_view 없애기
        }

        //*** [버튼 이벤트 설정] ***//
        Button btn_ok = (Button) v.findViewById(R.id.btn_ok2);
        Button btn_cansle = (Button) v.findViewById(R.id.btn_cancle);

        //확인 버튼 (착석 완료를 알려주는 임의의 버튼 -> 추후에 아두이노 무게센서로 인식)
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "제한시간 내에 앉음"+ adapter.getTv4(0), Toast.LENGTH_SHORT).show();
                //#####타이머 종료되고, 타이머 대신 "착석완료"라는 TextView를 넣음#####
            }
        });

        btn_cansle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancled=true;
                adapter.removeItem(0);
                listview.setVisibility(View.GONE);
                lay_myseat.setVisibility(View.GONE);
                emptyview.setVisibility(View.VISIBLE); //선택된 좌석을 취소할 시, empty_view 보이기
            } //선택된 좌석이 없다면, empty_view 보이기            }
        });

        return v;
    }

    //오늘 날짜 얻는 함수
    private String getTime() {
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
}

