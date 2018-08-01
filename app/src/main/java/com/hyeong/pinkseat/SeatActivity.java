package com.hyeong.pinkseat;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

// 2018.07.31 한 것 : 좌석버튼 생성 & 버튼 이벤트 정의(AlertDialog)
//          남은 것 : 레이아웃에 뒤로가기 버튼 추가 & 뒤로가기 버튼 선택 시 SubwayActivity로 인텐트
//                    & AlertDialog 정상 작동 확인 & P42~44 버튼 선택 후 AlertDialog의 확인 버튼 선택 시, MySeatActivity 로 인텐트 (좌석 번호 string 전송)

// <7번 좌석 선택 화면>
public class SeatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        /*** [좌석 선택 & AlertDialog 버튼 선택 이벤트 설정] ***/
        //착석 불가 자리
        Button btn_p41 = (Button)findViewById(R.id.btn_P41);
        //착석 가능 자리
        Button btn_p42 = (Button)findViewById(R.id.btn_P42);
        Button btn_p43 = (Button)findViewById(R.id.btn_P43);
        Button btn_p44 = (Button)findViewById(R.id.btn_P44);

        //착석 불가 자리 클릭 이벤트
        btn_p41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"이미 착석 된 좌석입니다.",Toast.LENGTH_SHORT).show();
            }
        });

        //착석 가능 자리 클릭 이벤트
        btn_p42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //다이얼로그 알람
                AlertDialog.Builder builder = new AlertDialog.Builder(SeatActivity.this);

                builder.setTitle("좌석 선택")
                        .setMessage("P42 좌석에 앉으시겠습니까?")
                        .setCancelable(false) //폰의 뒤로가기로 취소 가능하게 설정
                        .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                            // 확인 버튼 시 설정
                            public void onClick(DialogInterface dialog, int whichButton){
                                // #####8번 화면으로 넘어가는 인텐트 추가 필요 + 좌석정보(P42) 전송#####
                                finish();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener(){
                            // 취소 버튼 클릭시 설정
                            public void onClick(DialogInterface dialog, int whichButton){
                                dialog.cancel();
                            }
                        });

                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                dialog.show();    // 알림창 띄우기
            }
        });
        btn_p43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //다이얼로그 알람
                AlertDialog.Builder builder = new AlertDialog.Builder(SeatActivity.this);

                builder.setTitle("좌석 선택")
                        .setMessage("P43 좌석에 앉으시겠습니까?")
                        .setCancelable(false) //폰의 뒤로가기로 취소 가능하게 설정
                        .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                            // 확인 버튼 시 설정
                            public void onClick(DialogInterface dialog, int whichButton){
                                // #####8번 화면으로 넘어가는 인텐트 추가 필요 + 좌석정보(P43) 전송#####
                                finish();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener(){
                            // 취소 버튼 클릭시 설정
                            public void onClick(DialogInterface dialog, int whichButton){
                                dialog.cancel();
                            }
                        });

                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                dialog.show();    // 알림창 띄우기
            }
        });
        btn_p44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //다이얼로그 알람
                AlertDialog.Builder builder = new AlertDialog.Builder(SeatActivity.this);

                builder.setTitle("좌석 선택")
                        .setMessage("P44 좌석에 앉으시겠습니까?")
                        .setCancelable(false) //폰의 뒤로가기로 취소 가능하게 설정
                        .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                            // 확인 버튼 시 설정
                            public void onClick(DialogInterface dialog, int whichButton){
                                // #####8번 화면으로 넘어가는 인텐트 추가 필요 + 좌석정보(P44) 전송#####
                                finish();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener(){
                            // 취소 버튼 클릭시 설정
                            public void onClick(DialogInterface dialog, int whichButton){
                                dialog.cancel();
                            }
                        });

                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                dialog.show();    // 알림창 띄우기
            }
        });

    }
}
