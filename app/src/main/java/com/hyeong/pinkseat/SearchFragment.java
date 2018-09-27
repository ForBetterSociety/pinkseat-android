package com.hyeong.pinkseat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SearchFragment extends Fragment {

    String idx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search,container,false);
        Button suwon = (Button)v.findViewById(R.id.suwon);
        Button logout = (Button)v.findViewById(R.id.btn_logout);

        //로그인한 사용자의 정보를 받아옴
        Intent intent = getActivity().getIntent();
        if(intent.getStringExtra("user_idx")==null){
            idx = AutoLoginPreference.getIdx(getActivity()).toString(); //자동 로그인으로 저장된 사용자의 정보를 받음
        } else {idx = intent.getStringExtra("user_idx");}


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutoLoginPreference.clearLogin(getActivity());
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); //액티비티 쌓인 것 제거
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   //액티비티 쌓인 것 제거
                startActivity(intent);
            }
        });

        suwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(),SubwayActivity.class);
                intent1.putExtra("user_idx", idx);
                startActivity(intent1);
            }
        });

        return v;
    }


}
