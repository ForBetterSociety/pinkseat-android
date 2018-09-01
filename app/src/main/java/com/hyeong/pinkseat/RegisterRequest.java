package com.hyeong.pinkseat;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JY on 2018-08-30.
 */

// <'가입하기' 기능에 필요한 Request클래스>
public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://a0801ji.cafe24.com/android_test/Register.php"; //서버ip주소에 있는 php파일 실행 url
    private Map<String, String> params;

    //생성자
    public RegisterRequest(String id, String pw, String name, int jumin, String address, String date, String hospital, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", id);
        params.put("pw", pw);
        params.put("name", name);
        params.put("jumin", jumin+"");
        params.put("address", address);
        params.put("date", date);
        params.put("hospital", hospital);
    }

    //추후 사용을 위한 부분
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
