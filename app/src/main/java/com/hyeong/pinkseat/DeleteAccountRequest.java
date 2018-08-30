package com.hyeong.pinkseat;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JY on 2018-08-30.
 */

// <'계정삭제' 기능에 필요한 Request클래스>
public class DeleteAccountRequest extends StringRequest {

    private static final String DELETE_REQUEST_URL = "http://a0801ji.cafe24.com/android_test/DeleteAccount.php"; //서버ip주소에 있는 php파일 실행 url
    private Map<String, String> params;

    //생성자
    public DeleteAccountRequest(String idx, Response.Listener<String> listener) {
        super(Method.POST, DELETE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("user_idx", idx);
    }

    //추후 사용을 위한 부분
    @Override
    public Map<String, String> getParams() {
        return params;
    }


}
