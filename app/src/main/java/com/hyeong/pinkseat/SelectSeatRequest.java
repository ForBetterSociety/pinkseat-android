package com.hyeong.pinkseat;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JY on 2018-09-18.
 */

// <좌석 선택에 필요한 Request 클래스>
public class SelectSeatRequest extends StringRequest {

    private static final String SELECTSEAT_REQUEST_URL = "http://a0801ji.cafe24.com/android_test/SelectSeat.php"; //서버ip주소에 있는 php파일 실행 url
    private Map<String, String> params;

    //생성자
    public SelectSeatRequest(String train_time, String train_dir, String plat_num, String plat_dir, int user_idx, String seat_num, Response.Listener<String> listener) {
        super(Method.POST, SELECTSEAT_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("train_time", train_time);
        params.put("train_dir", train_dir);
        params.put("plat_num", plat_num);
        params.put("plat_dir", plat_dir);
        params.put("user_idx", user_idx+"");
        params.put("seat_num", seat_num);
    }

    //추후 사용을 위한 부분
    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
