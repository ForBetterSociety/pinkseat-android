package com.hyeong.pinkseat;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


//  **Volley http 라이브러리로 네트워크 요청, 수신 하는 방법**
//  ① RequestQueue 생성
//  ② Request Object 생성     (RegisterRequest클래스 필요)
//  ③ 생성된 Object를 RequestQueue로 전달
//  ④ 이후 할당된 Response로 Callback

// <회원가입 화면>
public class JoinActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        getSupportActionBar().setTitle("임산부 인증");

        //init();

        Button join_ok = (Button) findViewById(R.id.join_ok);
        Button join_cancle = (Button) findViewById(R.id.join_cancle);

        final EditText ed_id = (EditText) findViewById(R.id.register_id_et);
        final EditText ed_pw = (EditText) findViewById(R.id.register_pw_et);
        final EditText ed_name = (EditText) findViewById(R.id.register_name_et);
        final EditText ed_jumin = (EditText) findViewById(R.id.register_birth_et1);
        final EditText ed_address = (EditText) findViewById(R.id.register_address_et);
        final EditText ed_date = (EditText) findViewById(R.id.register_date_et);
        final EditText ed_hospital = (EditText) findViewById(R.id.register_hospital_et);

        //[확인 버튼] 클릭 이벤트 (가입)
        join_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = ed_id.getText().toString();
                final String pw = ed_pw.getText().toString();
                final String name = ed_name.getText().toString();
                final int jumin = Integer.parseInt(ed_jumin.getText().toString());
                final String address = ed_address.getText().toString();
                final String date = ed_date.getText().toString();
                final String hospital = ed_hospital.getText().toString();

                //④ Callback 처리부분 (volley 사용을 위한 ResponseListener 구현 부분)
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    //서버로부터 데이터를 받음
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);                //서버로부터 받는 데이터는 JSON타입의 객체
                            boolean success = jsonResponse.getBoolean("success");    //그중 Key값이 "success"인 것을 가져옴

                            //회원 가입 성공 시 로그인 화면으로 인텐트(회원 가입 성공시 success값이 true)
                            if (success) {
                                Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); //액티비티 쌓인 것 제거
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   //액티비티 쌓인 것 제거
                                JoinActivity.this.startActivity(intent);
                                //회원 가입 실패 시 알림창 띄움(회원 가입 실패시 success값이 false)
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                builder.setMessage("Register Failed")
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
                RegisterRequest registerRequest = new RegisterRequest(id, pw, name, jumin, address, date, hospital, responseListener);
                //① RequestQueue 생성
                RequestQueue queue = Volley.newRequestQueue(JoinActivity.this);
                //③ 생성된 Object를 RequestQueue로 전달
                queue.add(registerRequest);

            }
        });


        //[취소 버튼] 클릭 이벤트 (가입취소 후, 로그인 화면으로 돌아가기)
        join_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent2);
                finish();
            }
        });

    }
}


