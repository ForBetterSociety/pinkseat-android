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

// 2018.07.31 한 것 : 좌석버튼 생성 & 버튼 이벤트 정의(AlertDialog)
//          남은 것 : 레이아웃에 뒤로가기 버튼 추가 & 뒤로가기 버튼 선택 시 SubwayActivity로 인텐트
//                    & AlertDialog 정상 작동 확인 & P42~44 버튼 선택 후 AlertDialog의 확인 버튼 선택 시, MySeatFragment 로 화면 전환

// 2018.08.01 한 것 : AlertDialog의 '확인' 버튼 클릭 시, 2번 메인화면으로 intent & 2번으로 intent 시, 문자열 전달 성공 & 뒤로가기 버튼 생성 & 뒤로가기 버튼 클릭 시, 액티비티 종료(6번 화면으로 이동)
//          남은 것 : 2번으로 intent 시, 입력받은 좌석정보를 전달할 때 6번에서 받은 정보를 담은 변수를 전달

// 2018.08.05 한 것 : 6번에서 인텐트 될 때, 열차 정보를 받음
//          남은 것 : 2번으로 intent 시, 2번 화면에 인텐트 신호를 보내, 2번에서 그 신호를 받았을 때 "착석 신청이 되었습니다" 토스트 띄우기

// 2018.08.06 한 것 : 승강장 번호 설정한대로 변함 & 2번에서 그 신호를 받았을 때 "착석 신청이 되었습니다" 토스트 띄우기
//          남은 것 : 착석 완료 된 좌석(버튼)의 색을 @color/colorFullSeat로 바꾸기 & 착석 완료된 좌석도 선택 못하게 동적으로 바꾸기


// <7번 좌석 선택 화면>
public class SeatActivity extends AppCompatActivity {

    String train_info1, train_info2;
    String plat_num, plat_dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        Intent intent_5 = getIntent(); //6번의 인텐트 수신
        train_info1 = intent_5.getStringExtra("train_info1");
        train_info2 = intent_5.getStringExtra("train_info2");
        plat_num = intent_5.getStringExtra("plat_num1");     //승강장 번호
        plat_dir = intent_5.getStringExtra("plat_num2");     //구간 번호

        /*** [승강장 번호 설정] ***/
        TextView plat1 = (TextView)findViewById(R.id.plat_num1_left);
        TextView plat2 = (TextView)findViewById(R.id.plat_num1_rignt);
        TextView plat3 = (TextView)findViewById(R.id.plat_num2_left);
        TextView plat4 = (TextView)findViewById(R.id.plat_num2_rignt);

        plat1.setText(plat_num+"-"+plat_dir.charAt(0));
        plat2.setText(plat_num+"-"+plat_dir.charAt(0));
        plat3.setText(plat_num+"-"+plat_dir.charAt(2));
        plat4.setText(plat_num+"-"+plat_dir.charAt(2));


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

        //착석 불가 자리 클릭 이벤트
        btn_p41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DontSeat();
            }
        });

        //착석 가능 자리 클릭 이벤트
        btn_p42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Seat(btn_p42);
            }
        });
        btn_p43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Seat(btn_p43);
            }
        });
        btn_p44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Seat(btn_p44);
            }
        });

    }

    //착석 가능 자리(버튼) 선택시 이벤트
    public void Seat(final Button btn){
        //다이얼로그 알람
        AlertDialog.Builder builder = new AlertDialog.Builder(SeatActivity.this);

        builder.setTitle("좌석 선택")
                .setMessage("P44 좌석에 앉으시겠습니까?")
                .setCancelable(false) //폰의 뒤로가기로 취소 가능하게 설정
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    // 확인 버튼 시 설정
                    public void onClick(DialogInterface dialog, int whichButton) {
                        btn.setBackground(Drawable.createFromPath("@color/colorFullSeat"));
                        Intent intent4 = new Intent(getApplicationContext(), Main2Activity.class);
                        MySeatFragment.cancled=false;
                        intent4.putExtra("seat_num", "P44");
                        intent4.putExtra("train_time", train_info1);
                        intent4.putExtra("train_dir", train_info2);
                        intent4.putExtra("plat_num",plat_num);
                        intent4.putExtra("plat_dir",plat_dir);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Toast.makeText(getApplicationContext(), btn.getText()+"번 자리에 5분 내로 앉아주세요", Toast.LENGTH_SHORT).show();
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
    }

    //착석 불가능 자리(버튼) 선택시 이벤트
    public void DontSeat(){
        Toast.makeText(getApplicationContext(), "이미 착석된 좌석입니다.", Toast.LENGTH_SHORT).show();
    }

}
