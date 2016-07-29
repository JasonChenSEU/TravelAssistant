package com.jason.listviewtest.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jason.listviewtest.R;

/**
 * Created by Jason on 2016/7/29.
 */
public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private String strDefaultUrl = "http://www.baidu.com";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        String url = getIntent().getStringExtra("URL");
        if(url == null)
            webView.loadUrl(strDefaultUrl);

        webView.loadUrl(getIntent().getStringExtra("URL"));
    }
}
