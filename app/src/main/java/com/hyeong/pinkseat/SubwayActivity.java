package com.hyeong.pinkseat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TabHost;

// 2018.07.31 한 것 : 탭 메뉴 생성 & 스피너 값 지정 & 버튼 클릭 이벤트 선언
//            남은 것 : 탭 메뉴 구동 확인 & 탭 메뉴에 리스트뷰 추가 & '조회'버튼 클릭 시, SeatActivity 로 인텐트 (전송할 정보는 x)

// 2018.08.01 한 것 : 탭 메뉴 구동 확인 & '조회'버튼 클릭 시, 7번 화면으로 인텐트 & 7번 화면으로 인텐트 시, 문자열 전달 성공 & '취소'버튼 클릭 시, 현재 액티비티 종료 (2번 화면이 보여짐)
//          남은 것 : 탭 메뉴에 리스트뷰 추가 & 7번으로 intent 시, 입력받은 열차정보를 담은 변수를 전달

// <6번 열차 조회 화면>
public class SubwayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subway);

        /*** [탭 메뉴 설정] ***/
        TabHost tabHost1 = (TabHost) findViewById(R.id.tabhost1);
        tabHost1.setup(); //탭 호출

        // 첫 번째 Tab (소요산 행)
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1").setContent(R.id.content1).setIndicator("소요산 행");
        tabHost1.addTab(ts1);

        // 두 번째 Tab (인천/신창 행)
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2").setContent(R.id.content2).setIndicator("인천/신창 행");
        tabHost1.addTab(ts2);


        /*** [스피너 설정] ***/
        // 승강장 번호 스피너
        Spinner Spi_num = (Spinner)findViewById(R.id.spin_plat_num);
        ArrayAdapter numAdapter = ArrayAdapter.createFromResource(this, R.array.platform_num, android.R.layout.simple_spinner_item);
        numAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spi_num.setAdapter(numAdapter);

        // 구간 번호 스피너
        Spinner Spi_section = (Spinner)findViewById(R.id.spin_plat_section);
        ArrayAdapter sectionAdapter = ArrayAdapter.createFromResource(this, R.array.platform_section, android.R.layout.simple_spinner_item);
        sectionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spi_section.setAdapter(sectionAdapter);


        /*** [버튼 이벤트 설정] ***/
        Button btn_ok = (Button) findViewById(R.id.btn_Ok);
        Button btn_no = (Button) findViewById(R.id.btn_No);

        // 조회 Button 클릭 이벤트
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5 = new Intent(getApplicationContext(),SeatActivity.class);
                intent5.putExtra("train_info1","시간"); //#####train_info1를 열차 정보를 담은 변수로 대체필요#####
                startActivity(intent5); //7번 화면으로 이동
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
}
