package com.hyeong.pinkseat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //화면 이벤트 확인을 위한 버튼
        Button Btn_1 = (Button) findViewById(R.id.btn_1);
        Button Btn_2 = (Button) findViewById(R.id.btn_2);

        Btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),SubwayActivity.class);
                startActivity(intent1);
            }
        });

        Btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(),SeatActivity.class);
                startActivity(intent2);
            }
        });

    }
}
