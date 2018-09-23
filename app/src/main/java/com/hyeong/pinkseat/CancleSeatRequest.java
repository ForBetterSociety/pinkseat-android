package com.hyeong.pinkseat;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JY on 2018-09-18.
 */

public class CancleSeatRequest extends StringRequest {

    private static final String CANCLESEAT_REQUEST_URL = "http://a0801ji.cafe24.com/android_test/CancleSeat.php"; //서버ip주소에 있는 php파일 실행 url
    private Map<String, String> params;

    //생성자
    public CancleSeatRequest(int user_idx, Response.Listener<String> listener) {
        super(Method.POST, CANCLESEAT_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user_idx", user_idx+"");
    }

    //추후 사용을 위한 부분
    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
