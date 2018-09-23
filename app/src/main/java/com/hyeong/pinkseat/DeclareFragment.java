package com.hyeong.pinkseat;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

// <신고 화면>
public class DeclareFragment extends Fragment {

    Button btn_declare;
    String content = "";

    RadioButton ra1, ra2, ra3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //getActivity().setTitle("신고");
        View v = inflater.inflate(R.layout.fragment_declare, container, false);

        btn_declare = (Button) v.findViewById(R.id.btn_declare);

        ra1 = (RadioButton) v.findViewById(R.id.ra1);
        ra2 = (RadioButton) v.findViewById(R.id.ra2);
        ra3 = (RadioButton) v.findViewById(R.id.ra3);

        if(ra1.isChecked()){
            content = "임산부 배려석에 비임산부 착석";
        }
        else if(ra2.isChecked()){
            content="임산부 배려석 장치 이상";
        }
        else if(ra3.isChecked()){
            content="어플 작동 이상";
        }

        btn_declare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  DB inquiry 테이블에 선택한 신고내용을 행으로 INSERT
                //신고 리스너
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    //서버로부터 데이터를 받음
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);              //서버로부터 받는 데이터는 JSON타입의 객체
                            boolean success = jsonResponse.getBoolean("success");  //그중 Key값이 "success"인 것을 가져옴

                            //신고 성공 시, 신고 성공 토스트 띄움 (신고 성공시 success값이 true)
                            if (success){
                                Toast.makeText(getActivity(), "신고가 접수되었습니다.", Toast.LENGTH_SHORT).show();

                                //신고 실패 시, 알림창 띄움(신고 실패시 success값이 false)
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage("신고 실패")
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
                DeclareRequest declareRequest = new DeclareRequest(content, responseListener);

                //① RequestQueue 생성
                RequestQueue queue = Volley.newRequestQueue(getActivity());

                //③ 생성된 Object를 RequestQueue로 전달
                queue.add(declareRequest);
            }
        });

        return v;
    }


}
