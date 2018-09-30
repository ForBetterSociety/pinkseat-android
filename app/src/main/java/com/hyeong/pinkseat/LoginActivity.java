package com.hyeong.pinkseat;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

//  **Volley http 라이브러리로 네트워크 요청, 수신 하는 방법**
//  ① RequestQueue 생성
//  ② Request Object 생성     (LoginRequest클래스 필요)
//  ③ 생성된 Object를 RequestQueue로 전달
//  ④ 이후 할당된 Response로 Callback

// <로그인 화면>
public class LoginActivity extends AppCompatActivity {

    EditText et_id, et_pw;
    CheckBox chk_auto;
    Button btn_login;
    SharedPreferences setting;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        et_id = (EditText) findViewById(R.id.login_id);
        et_pw = (EditText) findViewById(R.id.login_pw);
       // chk_auto = (CheckBox) findViewById(R.id.automatic_login);
        btn_login = (Button) findViewById(R.id.login_ok);

        setting = getSharedPreferences("setting", 0); //기록할 파일들 불러오기
        editor= setting.edit();



        if(setting.getBoolean("chk_auto", false)){
            et_id.setText(setting.getString("ID", ""));
            et_pw.setText(setting.getString("PW", ""));
            chk_auto.setChecked(true);

        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chk_auto.isChecked()){
                   // Toast.makeText(this, "로그인", Toast.LENGTH_SHORT).show();
                    String ID = et_id.getText().toString();
                    String PW = et_pw.getText().toString();

                    editor.putString("ID", ID);
                    editor.putString("PW", PW);
                    editor.putBoolean("chk_auto", true);
                    editor.commit();

                }else{
                    editor.clear();
                    editor.commit();
                }

            }
        });
        if(setting.getBoolean("Auto_Login_enabled", false)){
            et_id.setText(setting.getString("ID", ""));
            et_pw.setText(setting.getString("PW", ""));
            chk_auto.setChecked(true);
        }





        getSupportActionBar().setTitle("임산부 인증");

        Button Login_ok = (Button) findViewById(R.id.login_ok);
        Button Join = (Button) findViewById(R.id.join);

        final EditText edit_id = (EditText) findViewById(R.id.login_id);
        final EditText edit_pw = (EditText) findViewById(R.id.login_pw);

        final CheckBox auto_login = (CheckBox) findViewById(R.id.auto_login);  //자동 로그인 체크박스

        //[OK 버튼] 클릭 이벤트 (로그인)
        Login_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = edit_id.getText().toString();
                final String pw = edit_pw.getText().toString();

                //④ Callback 처리부분 (volley 사용을 위한 ResponseListener 구현 부분)
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    //서버로부터 데이터를 받음
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);              //서버로부터 받는 데이터는 JSON타입의 객체
                            boolean success = jsonResponse.getBoolean("success");  //그중 Key값이 "success"인 것을 가져옴

                            //로그인 성공 시, 로그인된 회원 정보를 담아 로그인 후 화면으로 인텐트(로그인 성공시 success값이 true)
                            if (success) {
                                //서버측에서 정보를 받아옴
                                String idx = jsonResponse.getString("user_idx"); //초록글씨 : 서버측에서 user테이블의 값을 담아 전달한 변수(php의 변수)와 이름을 같게해야 함!
                                String name = jsonResponse.getString("name");
                                String date = jsonResponse.getString("date");
                                String hospital = jsonResponse.getString("hospital");
                                String reservating = jsonResponse.getString("reservating"); //예약여부

                                //받은 정보를 담아 로그인 후 화면(Main2Activity)으로 인텐트
                                Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                                intent.putExtra("user_idx", idx);
                                intent.putExtra("name", name);
                                intent.putExtra("date", date);
                                intent.putExtra("hospital", hospital);
                                intent.putExtra("reservating", reservating);

                                //자동 로그인 시, 정보 저장
                                if(auto_login.isChecked()){
                                    AutoLoginPreference.setUser(LoginActivity.this, id, pw, hospital, name, date,idx);
                                }

                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); //액티비티 쌓인 것 제거
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   //액티비티 쌓인 것 제거
                                LoginActivity.this.startActivity(intent);
                                //로그인 실패 시 알림창 띄움(로그인 실패시 success값이 false)
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("로그인 실패")
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
                LoginRequest loginRequest = new LoginRequest(id, pw, responseListener);

                //① RequestQueue 생성
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);

                //③ 생성된 Object를 RequestQueue로 전달
                queue.add(loginRequest);
            }
        });


        //[Join 버튼] 클릭 이벤트 (가입화면 켜기)
        Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(LoginActivity.this, JoinActivity.class);
                LoginActivity.this.startActivity(intent3);
            }
        });


    }

}
