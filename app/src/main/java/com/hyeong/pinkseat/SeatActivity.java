package com.hyeong.pinkseat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

// 2018.07.31 한 것 : 좌석버튼 생성 & 버튼 이벤트 정의(AlertDialog)
//          남은 것 : 레이아웃에 뒤로가기 버튼 추가 & 뒤로가기 버튼 선택 시 SubwayActivity로 인텐트
//                    & AlertDialog 정상 작동 확인 & P42~44 버튼 선택 후 AlertDialog의 확인 버튼 선택 시, MySeatFragment 로 화면 전환

// 2018.08.01 한 것 : AlertDialog의 '확인' 버튼 클릭 시, 2번 메인화면으로 intent & 2번으로 intent 시, 문자열 전달 성공 & 뒤로가기 버튼 생성 & 뒤로가기 버튼 클릭 시, 액티비티 종료(6번 화면으로 이동)
//          남은 것 : 2번으로 intent 시, 입력받은 좌석정보를 전달할 때 6번에서 받은 정보를 담은 변수를 전달

// 2018.08.05 한 것 : 6번에서 인텐트 될 때, 열차 정보를 받음
//          남은 것 : 2번으로 intent 시, 2번 화면에 인텐트 신호를 보내, 2번에서 그 신호를 받았을 때 "착석 신청이 되었습니다" 토스트 띄우기

// 2018.08.06 한 것 : 승강장 번호 설정한대로 변함 & 2번에서 그 신호를 받았을 때 "착석 신청이 되었습니다" 토스트 띄우기
//          남은 것 : 착석 완료 된 좌석(버튼)의 색을 @color/colorFullSeat로 바꾸기 & 착석 완료된 좌석도 선택 못하게 동적으로 바꾸기

// 2018.09.19 한 것 : reservating=0인 사용자만 예약 가능
//          남은 것 : 예약 완료 시, 완료 시간으로부터 5분동안 타이머 주기


// <7번 좌석 선택 화면>
public class SeatActivity extends AppCompatActivity {

    String train_info1, train_info2;
    String plat_num, plat_dir;

    String idx="";

    int[][] seats = new int[4][2];

    static boolean user_reservating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        Intent intent_5 = getIntent(); //6번의 인텐트 수신
        train_info1 = intent_5.getStringExtra("train_info1");
        train_info2 = intent_5.getStringExtra("train_info2");
        plat_num = intent_5.getStringExtra("plat_num1");     //승강장 번호
        plat_dir = intent_5.getStringExtra("plat_num2");     //구간 번호

        idx = intent_5.getStringExtra("user_idx"); //로그인 한 사용자의 idx

        /*** [승강장 번호 설정] ***/
        TextView plat1 = (TextView) findViewById(R.id.plat_num1_left);
        TextView plat2 = (TextView) findViewById(R.id.plat_num1_rignt);
        TextView plat3 = (TextView) findViewById(R.id.plat_num2_left);
        TextView plat4 = (TextView) findViewById(R.id.plat_num2_rignt);

        plat1.setText(plat_num + "-" + plat_dir.charAt(0));
        plat2.setText(plat_num + "-" + plat_dir.charAt(0));
        plat3.setText(plat_num + "-" + plat_dir.charAt(2));
        plat4.setText(plat_num + "-" + plat_dir.charAt(2));


        /*** [뒤로가기 버튼 이벤트 설정] ***/
        Button btn_Back = (Button) findViewById(R.id.btn_back);
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //착석 불가 자리
        final Button btn_p41 = (Button) findViewById(R.id.btn_P41);
        //착석 가능 자리
        final Button btn_p42 = (Button) findViewById(R.id.btn_P42);
        final Button btn_p43 = (Button) findViewById(R.id.btn_P43);
        final Button btn_p44 = (Button) findViewById(R.id.btn_P44);

        /*** [좌석 occupied, reservated 정보 받음] ***/
        int p41_oc = intent_5.getIntExtra("p41_oc", 0);
        int p41_re = intent_5.getIntExtra("p41_re", 0);
        int p42_oc = intent_5.getIntExtra("p42_oc", 0);
        int p42_re = intent_5.getIntExtra("p42_re", 0);
        int p43_oc = intent_5.getIntExtra("p43_oc", 0);
        int p43_re = intent_5.getIntExtra("p43_re", 0);
        int p44_oc = intent_5.getIntExtra("p44_oc", 0);
        int p44_re = intent_5.getIntExtra("p44_re", 0);

        seats[0][0] = p41_oc;
        seats[0][1] = p41_re;
        seats[1][0] = p42_oc;
        seats[1][1] = p42_re;
        seats[2][0] = p43_oc;
        seats[2][1] = p43_re;
        seats[3][0] = p44_oc;
        seats[3][1] = p44_re;

        /*** [좌석 색 지정] ***/
        changeButtonColor(btn_p41, seats, 0);
        changeButtonColor(btn_p42, seats, 1);
        changeButtonColor(btn_p43, seats, 2);
        changeButtonColor(btn_p44, seats, 3);


        /*** [좌석 착석 가능 여부에 따른 클릭 이벤트 지정] ***/
            //디폴트 이벤트=DontSeat()
            btn_p41.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DontSeat();
                }
            });
            btn_p42.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DontSeat();
                }
            });
            btn_p43.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DontSeat();
                }
            });
            btn_p44.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DontSeat();
                }
            });
            //occupied=0 && reservated=0 이면 Seat()
            if (seats[0][0] == 0 && seats[0][1] == 0) {
                btn_p41.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Seat(btn_p41);
                    }
                });
            }
            if (seats[1][0] == 0 && seats[1][1] == 0) {
                btn_p42.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Seat(btn_p42);
                    }
                });
            }
            if (seats[2][0] == 0 && seats[2][1] == 0) {
                btn_p43.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Seat(btn_p43);
                    }
                });
            }
            if (seats[3][0] == 0 && seats[3][1] == 0) {
                btn_p44.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Seat(btn_p44);
                    }
                });
            }

    }

    //착석 가능 자리(버튼) 선택시 호출되는 함수
    public void Seat(final Button btn){

        //현재 사용자가 예약 좌석이 없다면 버튼 클릭 가능!
        if (!user_reservating) {
            //다이얼로그 알람
        AlertDialog.Builder builder = new AlertDialog.Builder(SeatActivity.this);
        final String seat_num = btn.getText().toString();

        builder.setTitle("좌석 선택")
                .setMessage(btn.getText()+"좌석에 앉으시겠습니까?")
                .setCancelable(false) //폰의 뒤로가기로 취소 가능하게 설정
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    // 확인 버튼 시 설정
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            //서버로부터 데이터를 받음
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);                //서버로부터 받는 데이터는 JSON타입의 객체
                                    boolean success = jsonResponse.getBoolean("success");    //그중 Key값이 "success"인 것을 가져옴

                                    if (success) {
                                    } else {
                                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SeatActivity.this);
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
                        SelectSeatRequest selectSeatRequest = new SelectSeatRequest(train_info1, train_info2, plat_num, plat_dir, Integer.parseInt(idx.toString()), seat_num, responseListener);

                        //① RequestQueue 생성
                        RequestQueue queue = Volley.newRequestQueue(SeatActivity.this);

                        //③ 생성된 Object를 RequestQueue로 전달
                        queue.add(selectSeatRequest);

                        Intent intent4 = new Intent(getApplicationContext(),Main2Activity.class);

                        intent4.putExtra("user_idx",idx);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Toast.makeText(getApplicationContext(), seat_num+"번 자리에 5분 내로 앉아주세요", Toast.LENGTH_SHORT).show();
                        startActivity(intent4);
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

        } else {
            Toast.makeText(getApplicationContext(), "예약은 한 좌석만 가능합니다.", Toast.LENGTH_SHORT).show();
        }
    }

    //착석 불가능 자리(버튼) 선택시 호출되는 함수
    public void DontSeat(){
        Toast.makeText(getApplicationContext(), "착석 불가능한 자리입니다", Toast.LENGTH_SHORT).show();
    }

    public void changeButtonColor(Button btn, int array[][], int idx){
        if(array[idx][0]==1 && array[idx][1]==1){
            btn.setBackgroundTintList(getResources().getColorStateList(R.color.colorFullSeat));
        }
        else if(array[idx][0]==0 && array[idx][1]==0){
            btn.setBackgroundTintList(getResources().getColorStateList(R.color.colorEmptySeat));
        }
        else if(array[idx][0]==0 && array[idx][1]==1){
            btn.setBackgroundTintList(getResources().getColorStateList(R.color.colorReservatedSeat));
        }
        else{
            btn.setBackgroundTintList(getResources().getColorStateList(R.color.colorFullSeat));
        }
    }

}
