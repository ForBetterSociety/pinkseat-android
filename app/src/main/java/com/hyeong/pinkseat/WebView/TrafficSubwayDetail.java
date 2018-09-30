package com.hyeong.pinkseat.WebView;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import kr.go.seoul.trafficsubway.Common.BaseActivity;
import kr.go.seoul.trafficsubway.R.id;
import kr.go.seoul.trafficsubway.R.layout;

/**
 * Created by HYEON on 2018-09-11.
 */

public class TrafficSubwayDetail extends BaseActivity {
    private String openAPIKey = "";
    private String subwayLocationAPIKey = "";
    private ImageView btnBackSubway;
    private WebView lineMapWebview;
    private WebViewInterface mWebViewInterface;

    public TrafficSubwayDetail() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layout.traffic_subway_detail);
        if(this.getIntent() != null && this.getIntent().getStringExtra("OpenAPIKey") != null) {
            this.openAPIKey = this.getIntent().getStringExtra("OpenAPIKey");
        }

        if(this.getIntent() != null && this.getIntent().getStringExtra("SubwayLocationAPIKey") != null) {
            this.subwayLocationAPIKey = this.getIntent().getStringExtra("SubwayLocationAPIKey");
        }

        this.initView();
    }

    private void initView() {
        this.btnBackSubway = (ImageView)this.findViewById(id.btn_back_subway);
        this.btnBackSubway.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                TrafficSubwayDetail.this.finish();
            }
        });
        this.lineMapWebview = (WebView)this.findViewById(id.line_map_webview);
        this.lineMapWebview.setWebViewClient(new WebViewClient());
        this.lineMapWebview.getSettings().setJavaScriptEnabled(true);
        this.lineMapWebview.getSettings().setBuiltInZoomControls(true);
        this.lineMapWebview.getSettings().setSupportZoom(true);
        this.lineMapWebview.getSettings().setDisplayZoomControls(false);
        this.lineMapWebview.getSettings().setDefaultTextEncodingName("UTF-8");
        this.mWebViewInterface = new WebViewInterface(this, this.lineMapWebview, this.openAPIKey, this.subwayLocationAPIKey);
        this.lineMapWebview.addJavascriptInterface(this.mWebViewInterface, "Android");
        this.lineMapWebview.loadUrl("file:///android_asset/mSeoul_Subway.html");
    }
}
