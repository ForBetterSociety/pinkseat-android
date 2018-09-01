package com.hyeong.pinkseat;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

// <설정 화면>
public class SettingFragment extends Fragment {

    TextView tv_name, tv_date, tv_hospital, tv_code;
    Button btn_delete_account;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // getActivity().setTitle("설정");
        View v = inflater.inflate(R.layout.fragment_setting, container, false);

        tv_name = (TextView) v.findViewById(R.id.set_name);
        tv_date = (TextView) v.findViewById(R.id.set_date);
        tv_hospital = (TextView) v.findViewById(R.id.set_hospital);
        tv_code = (TextView) v.findViewById(R.id.set_code);

        btn_delete_account = (Button) v.findViewById(R.id.btn_delaccount);

        //로그인한 사용자의 정보를 받아옴
        Intent intent = getActivity().getIntent();
        final String idx = intent.getStringExtra("user_idx");
        final String name = intent.getStringExtra("name");
        final String date = intent.getStringExtra("date");
        final String hospital = intent.getStringExtra("hospital");

        tv_name.setText(name+" 산모님");
        tv_date.setText(date);
        tv_hospital.setText(hospital);
        tv_code.setText("P0000"+idx);

        //[인증해제 버튼] 클릭 이벤트 (계정삭제)
        btn_delete_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //계정 삭제 리스너
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    //서버로부터 데이터를 받음
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);              //서버로부터 받는 데이터는 JSON타입의 객체
                            boolean success = jsonResponse.getBoolean("success");  //그중 Key값이 "success"인 것을 가져옴

                            //계정 삭제 성공 시, 로그인 화면으로 인텐트(계정삭제 성공시 success값이 true)
                            if (success){
                                Intent intent = new Intent(getActivity(),LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); //액티비티 쌓인 것 제거
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   //액티비티 쌓인 것 제거
                                startActivity(intent);

                                //계정 삭제 실패 시, 알림창 띄움(계정삭제 실패시 success값이 false)
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("인증해제 실패")
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
                DeleteAccountRequest deleteRequest = new DeleteAccountRequest(idx, responseListener);

                //① RequestQueue 생성
                RequestQueue queue = Volley.newRequestQueue(getActivity());

                //③ 생성된 Object를 RequestQueue로 전달
                queue.add(deleteRequest);
            }
        });

        return v;

        //return inflater.inflate(R.layout.fragment_setting, container, false);
    }


}
