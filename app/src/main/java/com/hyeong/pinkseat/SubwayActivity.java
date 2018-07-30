package com.hyeong.pinkseat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

// 2018.07.31 한 것 : 탭 메뉴 생성 & 스피너 값 지정 & 버튼 클릭 이벤트 선언
//            남은 것 : 탭 메뉴 구동 확인 & 탭 메뉴에 리스트뷰 추가 & '조회'버튼 클릭 시 SeatActivity 로 인텐트 (전송할 정보는 x)


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
        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("소요산 행");
        tabHost1.addTab(ts1);

        // 두 번째 Tab (인천/신창 행)
        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("인천/신창 행");
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
                Toast.makeText(getApplicationContext(),"조회 버튼 클릭",Toast.LENGTH_SHORT).show();
                // #####7번 화면으로 넘어가는 이벤트 추가 필요#####
            }
        });

        //취소 Button 클릭 이벤트
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"취소 버튼 클릭",Toast.LENGTH_SHORT).show();
                // #####2번 화면으로 넘어가는 이벤트 추가 필요#####
            }
        });


    }
}
