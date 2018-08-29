package com.hyeong.pinkseat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button Login_ok = (Button) findViewById(R.id.login_ok);
        Button Join = (Button) findViewById(R.id.join);

        Login_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent1);

            }
        });
        Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent2);

            }
        });



        getSupportActionBar().setTitle("임산부 인증");


    }


}
