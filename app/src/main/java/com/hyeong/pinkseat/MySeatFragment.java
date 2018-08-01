package com.hyeong.pinkseat;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

// 2018.08.01 한 것 : MySeatActivity를 MySeatFragment로 바꿈 (레이아웃도 수정)
//          남은 것 : 2번 화면의 메뉴 '나의 좌석 현황'과 연결 & 2번 화면이 7번 화면의 좌석 선택 정보를 받으면, 각 TextView에 출력 & 타이머 구동 확인 후 수정

// <8번 나의 좌석 현황 화면>
public class MySeatFragment extends Fragment {

    // 타이머 관련 변수
    private TimerTask second;
    private TextView timer_text;
    private final Handler handler = new Handler();

    int timer_sec;
    int count;

    protected void Update() {
        Runnable updater = new Runnable() {
            public void run() {
                timer_text.setText(timer_sec + "초");
            }
        };
        handler.post(updater);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        testStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_seat, container, false);

    }

    /*** [타이머 설정] ***/
    public void testStart() {
        timer_text = (TextView) getView().findViewById(R.id.seatinfo_timer);
        timer_sec = 0;
        count = 0;

        second = new TimerTask() {

            @Override
            public void run() {
                Log.i("Test", "Timer start");
                Update();
                timer_sec++;
            }
        };
        Timer timer = new Timer();
        timer.schedule(second, 0, 1000);
    }




}
