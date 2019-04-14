/*
* Name: WebViewActivity.java
* Author: Nguyen Duc Tien 16020175
* Purpose: Show store website: https://croplab.vn/
* Include: Webview
*/

package com.example.croplapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {

    private   WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_web);
    }
    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.view_web);

        webView = findViewById(R.id.webView);
        //Setting webview properties
//        WebSettings webSettings = webView.getSettings();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptEnabled (true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setDomStorageEnabled(true);
//        webView.loadUrl("https://croplab.vn/blog");

        webView.loadUrl("https://www.google.com");
        
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

//        public void onPageFinished(WebView view, String url) {
//            view.loadUrl("javascript:alert('hi')");
//        }
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}

