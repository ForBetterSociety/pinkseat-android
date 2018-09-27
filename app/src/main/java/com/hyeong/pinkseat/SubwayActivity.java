package com.hyeong.pinkseat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

// 2018.07.31 한 것 : 탭 메뉴 생성 & 스피너 값 지정 & 버튼 클릭 이벤트 선언
//            남은 것 : 탭 메뉴 구동 확인 & 탭 메뉴에 리스트뷰 추가 & '조회'버튼 클릭 시, SeatActivity 로 인텐트 (전송할 정보는 x)

// 2018.08.01 한 것 : 탭 메뉴 구동 확인 & '조회'버튼 클릭 시, 7번 화면으로 인텐트 & 7번 화면으로 인텐트 시, 문자열 전달 성공 & '취소'버튼 클릭 시, 현재 액티비티 종료 (2번 화면이 보여짐)
//          남은 것 : 탭 메뉴에 리스트뷰 추가 & 7번으로 intent 시, 입력받은 열차정보를 담은 변수를 전달

//2018.08.05 한 것 : 탭 메뉴에 리스트뷰 추가 & 7번으로 intent 시, 입력받은 열차정보를 담은 변수를 전달 (소요산 행 리스트뷰만 정보 전달 가능)

//2018.08.06 한 것 : 서동탄/신창 행 리스트뷰도 정보 전달 가능 & 스피너 값도 전달 가능

// <6번 열차 조회 화면>
public class SubwayActivity extends AppCompatActivity {


    //체크박스 선택 여부 판단 변수
    int checked1 =-1; //소요산 행
    int checked2 =-1; //서동탄/신창 행

    //seat db occupied, reservated 정보 받을 변수
    int p41_oc, p41_re, p42_oc, p42_re, p43_oc, p43_re, p44_oc, p44_re;

    // user_idx 받을 변수
    String idx = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subway);

        //Intent intent_5 = getIntent(); //6번의 인텐트 수신
        //idx = intent_5.getStringExtra("user_idx");

        //로그인한 사용자의 정보를 받아옴
        Intent intent = SubwayActivity.this.getIntent();
        if(intent.getStringExtra("user_idx")==null){
            idx = AutoLoginPreference.getIdx(SubwayActivity.this).toString(); //자동 로그인으로 저장된 사용자의 정보를 받음
        }else {idx = intent.getStringExtra("user_idx");}

        /*** [탭 메뉴 설정] ***/
        TabHost tabHost1 = (TabHost) findViewById(R.id.tabhost1);
        tabHost1.setup(); //탭 호출

        // 첫 번째 Tab (소요산 행)
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1").setContent(R.id.content1).setIndicator("소요산 행");
        tabHost1.addTab(ts1);

        // 두 번째 Tab (서동탄/신창 행)
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2").setContent(R.id.content2).setIndicator("서동탄/신창 행");
        tabHost1.addTab(ts2);


        /*** [탭의 리스트뷰 설정] ***/
        final ListView listview1; //소요산 행 리스트뷰
        final ListView listview2; //인천/신창 행 리스트뷰
        final SubwayLIstViewAdapter adapter1; //소요산 행 어뎁터
        final SubwayLIstViewAdapter adapter2; //인천/신창 행 어뎁터

        // Adapter 생성
        adapter1 = new SubwayLIstViewAdapter();
        adapter2 = new SubwayLIstViewAdapter();

        // 리스트뷰 참조 및 Adapter달기
        listview1 = (ListView) findViewById(R.id.listview1);
        listview2 = (ListView) findViewById(R.id.listview2);
        listview1.setAdapter(adapter1);
        listview2.setAdapter(adapter2);

        // 아이템 추가
        // 소요산 행
        adapter1.addItem("05:10","광운대행");
        adapter1.addItem("05:34","청량리행");
        adapter1.addItem("05:47","광운대행");
        adapter1.addItem("05:57","청량리행");
        adapter1.addItem("06:07","광운대행");
        adapter1.addItem("06:19","광운대행");
        adapter1.addItem("06:29","광운대행");
        adapter1.addItem("06:39","청량리행");

        //서동탄/신창 행
        adapter2.addItem("05:41","신창행");
        adapter2.addItem("05:58","서동탄행");
        adapter2.addItem("06:08","신창행");
        adapter2.addItem("06:22","서동탄행");
        adapter2.addItem("06:32","신창행");
        adapter2.addItem("06:44","서동탄행");
        adapter2.addItem("06:52","천안행(급)");
        adapter2.addItem("06:57","신창행");

        /*** [스피너 설정] ***/
        // 승강장 번호 스피너
        final Spinner Spi_num = (Spinner)findViewById(R.id.spin_plat_num);
        final ArrayAdapter numAdapter = ArrayAdapter.createFromResource(this, R.array.platform_num, android.R.layout.simple_spinner_item);
        numAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spi_num.setAdapter(numAdapter);

        // 구간 번호 스피너
        final Spinner Spi_section = (Spinner)findViewById(R.id.spin_plat_section);
        ArrayAdapter sectionAdapter = ArrayAdapter.createFromResource(this, R.array.platform_section, android.R.layout.simple_spinner_item);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spi_section.setAdapter(sectionAdapter);


        /*** [버튼 이벤트 설정] ***/
        Button btn_ok = (Button) findViewById(R.id.btn_Ok);
        Button btn_no = (Button) findViewById(R.id.btn_No);

        //사용자의 예약 여부 확인 (SeatActivity의 user_reservating을 변화시킴)
        yourReservating(Integer.parseInt(idx.toString()));

        // 조회 Button 클릭 이벤트
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checked1=listview1.getCheckedItemPosition(); //선택된 리스트뷰(소요산행) 아이템의 id를 저장하는 변수 checked 정의
                checked2=listview2.getCheckedItemPosition(); //선택된 리스트뷰(서동탄/신창행) 아이템의 id를 저장하는 변수 checked 정의

                //[체크 여부 판단]
                if(checked1>=0&&checked2>=0){
                    Toast.makeText(getApplicationContext(),"열차 시간은 하나만 선택해주세요",Toast.LENGTH_SHORT).show();
                }
                //소요산 행의 경우
                else if(checked1>=0){
                    //[서버에서 받은 좌석의 occupied, reservated 정보를 SeatActivity로 전달하는 부분]
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        //서버로부터 데이터를 받음
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);              //서버로부터 받는 데이터는 JSON타입의 객체

                                p41_oc = jsonResponse.getJSONArray("seatJson").getJSONObject(0).getInt("occupied");
                                p41_re = jsonResponse.getJSONArray("seatJson").getJSONObject(0).getInt("reservated");

                                p42_oc = jsonResponse.getJSONArray("seatJson").getJSONObject(1).getInt("occupied");
                                p42_re = jsonResponse.getJSONArray("seatJson").getJSONObject(1).getInt("reservated");

                                p43_oc = jsonResponse.getJSONArray("seatJson").getJSONObject(2).getInt("occupied");
                                p43_re = jsonResponse.getJSONArray("seatJson").getJSONObject(2).getInt("reservated");

                                p44_oc = jsonResponse.getJSONArray("seatJson").getJSONObject(3).getInt("occupied");
                                p44_re = jsonResponse.getJSONArray("seatJson").getJSONObject(3).getInt("reservated");

                                Intent intent5 = new Intent(SubwayActivity.this,SeatActivity.class);
                                intent5.putExtra("train_info1",adapter1.getTv1(checked1)); //열차 시간 정보
                                intent5.putExtra("train_info2",adapter1.getTv2(checked1)); //열차 방향 정보
                                intent5.putExtra("plat_num1",Spi_num.getSelectedItem().toString());     //승강장 번호
                                intent5.putExtra("plat_num2",Spi_section.getSelectedItem().toString()); //구간 번호
                                intent5.putExtra("user_idx",idx); //로그인 사용자 idx 번호
                                intent5.putExtra("p41_oc", p41_oc); intent5.putExtra("p41_re", p41_re);
                                intent5.putExtra("p42_oc", p42_oc); intent5.putExtra("p42_re", p42_re);
                                intent5.putExtra("p43_oc", p43_oc); intent5.putExtra("p43_re", p43_re);
                                intent5.putExtra("p44_oc", p44_oc); intent5.putExtra("p44_re", p44_re);
                                SubwayActivity.this.startActivity(intent5); //7번 화면으로 이동 (열차 시간, 방향 정보 전송)

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    CurrentSeatRequest currentSeatRequest = new CurrentSeatRequest(responseListener);
                    RequestQueue queue = Volley.newRequestQueue(SubwayActivity.this);
                    queue.add(currentSeatRequest);

                }
                //서동탄/신창 행의 경우
                else if(checked2>=0){
                    //[서버에서 받은 좌석의 occupied, reservated 정보를 SeatActivity로 전달하는 부분]
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        //서버로부터 데이터를 받음
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);              //서버로부터 받는 데이터는 JSON타입의 객체

                                p41_oc = jsonResponse.getJSONArray("seatJson").getJSONObject(0).getInt("occupied");
                                p41_re = jsonResponse.getJSONArray("seatJson").getJSONObject(0).getInt("reservated");

                                p42_oc = jsonResponse.getJSONArray("seatJson").getJSONObject(1).getInt("occupied");
                                p42_re = jsonResponse.getJSONArray("seatJson").getJSONObject(1).getInt("reservated");

                                p43_oc = jsonResponse.getJSONArray("seatJson").getJSONObject(2).getInt("occupied");
                                p43_re = jsonResponse.getJSONArray("seatJson").getJSONObject(2).getInt("reservated");

                                p44_oc = jsonResponse.getJSONArray("seatJson").getJSONObject(3).getInt("occupied");
                                p44_re = jsonResponse.getJSONArray("seatJson").getJSONObject(3).getInt("reservated");

                                Intent intent5 = new Intent(SubwayActivity.this,SeatActivity.class);
                                intent5.putExtra("train_info1",adapter2.getTv1(checked2)); //열차 시간 정보
                                intent5.putExtra("train_info2",adapter2.getTv2(checked2)); //열차 방향 정보
                                intent5.putExtra("plat_num1",Spi_num.getSelectedItem().toString());     //승강장 번호
                                intent5.putExtra("plat_num2",Spi_section.getSelectedItem().toString()); //구간 번호
                                intent5.putExtra("user_idx",idx); //로그인 사용자 idx 번호
                                intent5.putExtra("p41_oc", p41_oc); intent5.putExtra("p41_re", p41_re);
                                intent5.putExtra("p42_oc", p42_oc); intent5.putExtra("p42_re", p42_re);
                                intent5.putExtra("p43_oc", p43_oc); intent5.putExtra("p43_re", p43_re);
                                intent5.putExtra("p44_oc", p44_oc); intent5.putExtra("p44_re", p44_re);
                                SubwayActivity.this.startActivity(intent5); //7번 화면으로 이동 (열차 시간, 방향 정보 전송)

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    CurrentSeatRequest currentSeatRequest = new CurrentSeatRequest(responseListener);
                    RequestQueue queue = Volley.newRequestQueue(SubwayActivity.this);
                    queue.add(currentSeatRequest);
                }
                else Toast.makeText(getApplicationContext(),"열차 시간을 선택해주세요",Toast.LENGTH_SHORT).show();

            }
        });

        //취소 Button 클릭 이벤트
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); //현재 액티비티 종료
            }
        });


    }


    public void yourReservating(int user_idx){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            //서버로부터 데이터를 받음
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);                //서버로부터 받는 데이터는 JSON타입의 객체
                    boolean success = jsonResponse.getBoolean("success");    //그중 Key값이 "success"인 것을 가져옴

                    if (success) {

                        final int reservating = jsonResponse.getInt("reservating");  // 사용자의 예약 유무
                        if(reservating==1){
                            SeatActivity.user_reservating = true;
                        }else {
                            SeatActivity.user_reservating = false;
                        }

                    } else {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SubwayActivity.this);
                        builder.setMessage("서버 통신 오류")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        //② RequestObject 생성 *이때 서버로부터 데이터를 받을 responseListener를 반드시 넘겨준다.
        MyInfoRequest myInfoRequest = new MyInfoRequest(user_idx, responseListener);
        //① RequestQueue 생성
        RequestQueue queue = Volley.newRequestQueue(SubwayActivity.this);
        //③ 생성된 Object를 RequestQueue로 전달
        queue.add(myInfoRequest);
    }



}
