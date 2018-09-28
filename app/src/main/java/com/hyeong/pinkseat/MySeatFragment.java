package com.hyeong.pinkseat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

// 2018.08.01 한 것 : MySeatActivity를 MySeatFragment로 바꿈 (레이아웃도 수정)
//          남은 것 : 2번 화면의 메뉴 '나의 좌석 현황'과 연결 & 2번 화면이 7번 화면의 좌석 선택 정보를 받으면, 각 TextView에 출력 & 타이머 구동 확인 후 수정

// 2018.08.05 한 것 : 2번 화면의 메뉴 '나의 좌석 현황'과 연결 & 2번 화면이 7번 화면의 좌석 선택 정보를 받으면, 각 TextView에 출력
//          남은 것 : '취소'버튼 클릭시 동작 설정 & 타이머 구동 확인 후 수정

// 2018.08.06 한 것 : 나의 좌석 현황을 레이아웃->리스트뷰로 바꿈 ('취소'버튼 클릭 시, 빈 화면 설정을 위함)
//          남은 것 : 타이머 구동 & '확인'버튼 클릭(제한 시간 내로 착석)시, 타이머 멈추기

// 2018.09.18 한 것 : 나의 좌석 현황을 리스트뷰->레이아웃으로 바꿈 (DB값을 이용하므로 리스트뷰가 필요 없어짐) & 착석 취소 버튼 이벤트(DB연동)
//          남은 것 : 타이머 구동 & '확인'버튼 클릭(제한 시간 내로 착석)시, 타이머 멈추기

// 2018.09.18 한 것 : CountDown 타이머 5분동안 구동 & 5분안에 occupied=1로 변경시 타이머 멈추고(DB연동), '착석완료'띄우기 & 5분안에 못앉으면 예약취소됨(DB연동)
//          남은 것 : 예약시간으로부터 5분동안만 타이머 가동 (현재, 나의좌석현황 화면이 뜰 때마다 5분 타이머 가동됨)


// <8번 나의 좌석 현황 화면>
public class MySeatFragment extends Fragment {

    //착석여부를 저장하는 변수
    static int isOccupied;

    //user_idx 담을 변수
    String idx;

    //오늘 날짜 값 담을 변수
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy년MM월dd일");

    //빈 화면용 변수
    TextView emptyview;
    LinearLayout lay_myseat;

    //seat 테이블 값 받아올 변수
    public String today;
    public String train_info1, train_info2, train_time;
    public String plat_num, plat_dir, plat_info;
    public String seat_num;

    //내 좌석 정보 띄울 텍스트뷰
    TextView seatinfo_date, seatinfo_time, seatinfo_platnum, seatinfo_seatnum, seatinfo_occupied;

    //버튼 변수
    Button btn_cansle;
    ImageButton btn_replay;
    View myseat_view;

    protected void Update() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //로그인한 사용자의 정보를 받아옴
        Intent intent = getActivity().getIntent();
        if(intent.getStringExtra("user_idx")==null){
            idx = AutoLoginPreference.getIdx(getActivity()).toString(); //자동 로그인으로 저장된 사용자의 정보를 받음
        }else {idx = intent.getStringExtra("user_idx");}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_my_seat, container, false);

        /*** [좌석현황 텍스트뷰용 객체] ***/
        seatinfo_date = (TextView) v.findViewById(R.id.seatinfo_date);
        seatinfo_time = (TextView) v.findViewById(R.id.seatinfo_time);
        seatinfo_platnum = (TextView) v.findViewById(R.id.seatinfo_platnum);
        seatinfo_seatnum = (TextView) v.findViewById(R.id.seatinfo_seatnum);
        seatinfo_occupied = (TextView) v.findViewById(R.id.seatinfo_occupied);

        /*** [빈 화면 출력용 객체] ***/
        emptyview = (TextView) v.findViewById(R.id.empty_view);
        lay_myseat = (LinearLayout)v.findViewById(R.id.lay_myseat);

        //*** [버튼용 객체] ***//
        btn_cansle = (Button) v.findViewById(R.id.btn_cancle);
        btn_replay = (ImageButton) v.findViewById(R.id.btn_replay);
        myseat_view = (View) v.findViewById(R.id.myseat_view);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            //서버로부터 데이터를 받음
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);                //서버로부터 받는 데이터는 JSON타입의 객체
                    boolean success = jsonResponse.getBoolean("success");    //그중 Key값이 "success"인 것을 가져옴

                    // 예약 정보 있는 경우
                    if (success) {
                        seat_num = jsonResponse.getString("seat_num");  // 예약한 좌석 번호
                        train_info1 = jsonResponse.getString("train_time"); //열차 시간
                        train_info2 = jsonResponse.getString("train_dir");  //열차 방향
                        plat_num = jsonResponse.getString("plat_num");    //승강장 번호
                        plat_dir = jsonResponse.getString("plat_dir");     //구간 번호

                        today = getTime().toString(); //오늘 날짜
                        train_time = train_info1 + "\n(" + train_info2;
                        plat_info = "["+plat_num+" - "+plat_dir+"칸]";

                        seatinfo_time.setText(train_time);
                        seatinfo_date.setText(today);
                        seatinfo_platnum.setText(plat_info);
                        seatinfo_seatnum.setText(seat_num);
                        seatinfo_occupied.setText("미착석");
                        seatinfo_occupied.setTextSize(25);
                        seatinfo_occupied.setTextColor(getResources().getColorStateList(R.color.colorNormalSeat));

                        btn_cansle.setVisibility(View.VISIBLE);
                        myseat_view.setVisibility(View.VISIBLE);

                        isOccupied = jsonResponse.getInt("occupied");  // 착석 여부
                        //착석 여부=1이면, finish();
                        if(isOccupied==1) {
                            seatinfo_occupied.setText("착석완료");
                            seatinfo_occupied.setTextSize(20);
                            seatinfo_occupied.setTextColor(getResources().getColorStateList(R.color.colorPrimary));
                            btn_cansle.setVisibility(View.GONE);
                            myseat_view.setVisibility(View.GONE);
                        }

                        lay_myseat.setVisibility(View.VISIBLE);
                        emptyview.setVisibility(View.GONE); //좌석이 있다면, empty_view 없애기

                    }
                    else { //예약 정보 없는 경우
                        lay_myseat.setVisibility(View.GONE);
                        emptyview.setVisibility(View.VISIBLE); //좌석이 없다면, empty_view 보이기
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };


        //② RequestObject 생성 *이때 서버로부터 데이터를 받을 responseListener를 반드시 넘겨준다.
        MyReservatedSeatRequest myReservatedSeatRequest = new MyReservatedSeatRequest(Integer.parseInt(idx.toString()), responseListener);

        //① RequestQueue 생성
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        //③ 생성된 Object를 RequestQueue로 전달
        queue.add(myReservatedSeatRequest);

        //예약 취소 버튼
        btn_cansle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //다이얼로그 알람
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("예약 취소")
                        .setMessage("예약을 취소하시겠습니까?")
                        .setCancelable(false) //폰의 뒤로가기로 취소 가능하게 설정
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            // 확인 버튼 시 설정
                            public void onClick(DialogInterface dialog, int whichButton) {
                                lay_myseat.setVisibility(View.GONE);
                                emptyview.setVisibility(View.VISIBLE); //예약 취소한 경우, empty_view 보이기

                                //④ Callback 처리부분 (volley 사용을 위한 ResponseListener 구현 부분)
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    //서버로부터 데이터를 받음
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);              //서버로부터 받는 데이터는 JSON타입의 객체
                                            boolean success = jsonResponse.getBoolean("success");  //그중 Key값이 "success"인 것을 가져옴

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };

                                //② RequestObject 생성 *이때 서버로부터 데이터를 받을 responseListener를 반드시 넘겨준다.
                                CancleSeatRequest cancleseatRequest = new CancleSeatRequest(Integer.parseInt(idx.toString()), responseListener);

                                //① RequestQueue 생성
                                RequestQueue queue = Volley.newRequestQueue(getActivity());

                                //③ 생성된 Object를 RequestQueue로 전달
                                queue.add(cancleseatRequest);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            // 취소 버튼 클릭시 설정
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });

                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                dialog.show();    // 알림창 띄우기

            }
        });

        //새로고침 버튼
        btn_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    //서버로부터 데이터를 받음
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);                //서버로부터 받는 데이터는 JSON타입의 객체
                            boolean success = jsonResponse.getBoolean("success");    //그중 Key값이 "success"인 것을 가져옴

                            // 예약 정보 있는 경우
                            if (success) {

                                seat_num = jsonResponse.getString("seat_num");  // 예약한 좌석 번호
                                train_info1 = jsonResponse.getString("train_time"); //열차 시간
                                train_info2 = jsonResponse.getString("train_dir");  //열차 방향
                                plat_num = jsonResponse.getString("plat_num");    //승강장 번호
                                plat_dir = jsonResponse.getString("plat_dir");     //구간 번호

                                today = getTime().toString(); //오늘 날짜
                                train_time = train_info1 + "\n(" + train_info2;
                                plat_info = "["+plat_num+" - "+plat_dir+"칸]";

                                seatinfo_time.setText(train_time);
                                seatinfo_date.setText(today);
                                seatinfo_platnum.setText(plat_info);
                                seatinfo_seatnum.setText(seat_num);
                                seatinfo_occupied.setText("미착석");
                                seatinfo_occupied.setTextSize(25);
                                seatinfo_occupied.setTextColor(getResources().getColorStateList(R.color.colorNormalSeat));

                                btn_cansle.setVisibility(View.VISIBLE);
                                myseat_view.setVisibility(View.VISIBLE);

                                isOccupied = jsonResponse.getInt("occupied");  // 착석 여부
                                //착석 여부=1이면, finish();
                                if(isOccupied==1) {
                                    seatinfo_occupied.setText("착석완료");
                                    seatinfo_occupied.setTextSize(20);
                                    seatinfo_occupied.setTextColor(getResources().getColorStateList(R.color.colorPrimary));
                                    btn_cansle.setVisibility(View.GONE);
                                    myseat_view.setVisibility(View.GONE);
                                }

                                lay_myseat.setVisibility(View.VISIBLE);
                                emptyview.setVisibility(View.GONE); //좌석이 있다면, empty_view 없애기

                            }
                            else { //예약 정보 없는 경우
                                lay_myseat.setVisibility(View.GONE);
                                emptyview.setVisibility(View.VISIBLE); //좌석이 없다면, empty_view 보이기
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                };


                //② RequestObject 생성 *이때 서버로부터 데이터를 받을 responseListener를 반드시 넘겨준다.
                MyReservatedSeatRequest myReservatedSeatRequest = new MyReservatedSeatRequest(Integer.parseInt(idx.toString()), responseListener);

                //① RequestQueue 생성
                RequestQueue queue = Volley.newRequestQueue(getActivity());

                //③ 생성된 Object를 RequestQueue로 전달
                queue.add(myReservatedSeatRequest);
            }
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

