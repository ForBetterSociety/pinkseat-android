package com.hyeong.pinkseat.WebView;

import android.app.Activity;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import kr.go.seoul.trafficsubway.TrafficSubwayInfoTypeB;

public class WebViewInterface {
    private WebView mAppView;
    private Activity mContext;
    private String openAPIKey;
    private String subwayLocationAPIKey;

    public WebViewInterface(Activity activity, WebView view, String openAPIKey, String subwayLocationAPIKey) {
        this.mAppView = view;
        this.mContext = activity;
        this.openAPIKey = openAPIKey;
        this.subwayLocationAPIKey = subwayLocationAPIKey;
    }

    @JavascriptInterface
    public void showSubwayInfo(String station) {
        Intent intent = new Intent(this.mContext, TrafficSubwayInfoTypeB.class);
        intent.putExtra("OpenAPIKey", this.openAPIKey);
        intent.putExtra("SubwayLocationAPIKey", this.subwayLocationAPIKey);
        intent.putExtra("StationNM", station);
        this.mContext.startActivity(intent);
    }
}

