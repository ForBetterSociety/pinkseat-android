package com.hyeong.pinkseat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

// 2018.07.31 한 것 : 좌석버튼 생성 & 버튼 이벤트 정의(AlertDialog)
//          남은 것 : 레이아웃에 뒤로가기 버튼 추가 & 뒤로가기 버튼 선택 시 SubwayActivity로 인텐트
//                    & AlertDialog 정상 작동 확인 & P42~44 버튼 선택 후 AlertDialog의 확인 버튼 선택 시, MySeatFragment 로 화면 전환

// 2018.08.01 한 것 : AlertDialog의 '확인' 버튼 클릭 시, 2번 메인화면으로 intent & 2번으로 intent 시, 문자열 전달 성공 & 뒤로가기 버튼 생성 & 뒤로가기 버튼 클릭 시, 액티비티 종료(6번 화면으로 이동)
//          남은 것 : 2번으로 intent 시, 입력받은 좌석정보를 전달할 때 6번에서 받은 정보를 담은 변수를 전달

// <7번 좌석 선택 화면>
public class SeatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        /*** [뒤로가기 버튼 이벤트 설정] ***/
        Button btn_Back = (Button)findViewById(R.id.btn_back);
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


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
                Toast.makeText(getApplicationContext(),"이미 착석된 좌석입니다.",Toast.LENGTH_SHORT).show();
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
                                Intent intent4 = new Intent(getApplicationContext(),MainActivity.class);
                                intent4.putExtra("seat_num","P42"); //#####P42를 좌석번호 변수로 대체필요#####
                                intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent4);
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
                                Intent intent4 = new Intent(getApplicationContext(),MainActivity.class);
                                intent4.putExtra("seat_num","P43"); //#####P43를 좌석번호 변수로 대체필요#####
                                intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent4);
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
                                Intent intent4 = new Intent(getApplicationContext(),MainActivity.class);
                                intent4.putExtra("seat_num","P44"); //#####P44를 좌석번호 변수로 대체필요#####
                                intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent4);
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

        Intent intent_5 = getIntent(); //6번의 인텐트 수신
        String train_info1 = intent_5.getStringExtra("train_info1");
        String train_info2 = intent_5.getStringExtra("train_info2");
        Toast.makeText(getApplicationContext(),"인텐트로 받은 열차 정보 : "+train_info1+", "+train_info2,Toast.LENGTH_SHORT).show();

    }
}
