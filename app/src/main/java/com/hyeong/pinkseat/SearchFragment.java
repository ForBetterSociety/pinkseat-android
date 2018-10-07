package com.hyeong.pinkseat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class SearchFragment extends Fragment {

    String idx, id, name, date, reservating;

    TextView tv_id, tv_name, tv_reservation, tv_dday;

    static int static_reservating = 0;
    static String static_user_idx = "";
    static String static_name = "";
    static String static_date = "";
    static String static_hospital = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search,container,false);
        //Button suwon = (Button)v.findViewById(R.id.suwon);

        tv_id = (TextView)v.findViewById(R.id.tv_id);
        tv_name = (TextView)v.findViewById(R.id.tv_name);
        tv_reservation = (TextView)v.findViewById(R.id.tv_reservation);
        tv_dday = (TextView)v.findViewById(R.id.tv_dday);

        //로그인한 사용자의 정보를 받아옴
        Intent intent = getActivity().getIntent();
        if(intent.getStringExtra("user_idx")==null){
            idx = AutoLoginPreference.getIdx(getActivity()).toString(); //자동 로그인으로 저장된 사용자의 정보를 받음
        } else {idx = intent.getStringExtra("user_idx");
        static_user_idx=idx;}


        Response.Listener<String> responseListener1 = new Response.Listener<String>() {
            //서버로부터 데이터를 받음
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);                //서버로부터 받는 데이터는 JSON타입의 객체
                    boolean success = jsonResponse.getBoolean("success");    //그중 Key값이 "success"인 것을 가져옴

                    if (success) {
                        id=jsonResponse.getString("id");
                        name = jsonResponse.getString("name");
                        date = jsonResponse.getString("date");
                        reservating = jsonResponse.getString("reservating");

                        static_reservating = Integer.parseInt(reservating);
                        static_name = name;
                        static_date=date;
                        static_hospital=jsonResponse.getString("hospital");

                        tv_id.setText(id);
                        tv_name.setText(name);
                        tv_dday.setText(date);
                        tv_reservation.setText(reservating);

                    } else {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                        builder.setMessage("서버 통신 오류")
                                .setNegativeButton("Retry", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        MyInfoRequest myInfoRequest = new MyInfoRequest(Integer.parseInt(idx), responseListener1);
        RequestQueue queue1 = Volley.newRequestQueue(getActivity());
        queue1.add(myInfoRequest);




//        suwon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1 = new Intent(getActivity(),SubwayActivity.class);
//                intent1.putExtra("user_idx", idx);
//                startActivity(intent1);
//            }
//        });

        return v;
    }


}
