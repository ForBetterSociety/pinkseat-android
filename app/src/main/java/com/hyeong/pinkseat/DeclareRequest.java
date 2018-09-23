package com.hyeong.pinkseat;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JY on 2018-09-09.
 */

// <'신고하기' 기능에 필요한 Request클래스>
public class DeclareRequest extends StringRequest {

    private static final String DECLARE_REQUEST_URL = "http://a0801ji.cafe24.com/android_test/Declare.php"; //서버ip주소에 있는 php파일 실행 url
    private Map<String, String> params;

    //생성자
    public DeclareRequest(String contents, Response.Listener<String> listener) {
        super(Method.POST, DECLARE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("contents", contents);
    }

    //추후 사용을 위한 부분
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
