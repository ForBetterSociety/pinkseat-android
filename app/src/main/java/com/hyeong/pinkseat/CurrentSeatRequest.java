package com.hyeong.pinkseat;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JY on 2018-09-11.
 */

public class CurrentSeatRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "http://a0801ji.cafe24.com/android_test/CurrentSeat.php";  //서버ip주소에 있는 php파일 실행 url
    private Map<String, String> params;

    //생성자
    public CurrentSeatRequest(Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
    }

    //추후 사용을 위한 부분
    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
