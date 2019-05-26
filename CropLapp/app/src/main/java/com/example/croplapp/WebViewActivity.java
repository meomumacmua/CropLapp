package com.example.croplapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class WebViewActivity extends AppCompatActivity {
    ProgressBar superProgressBar;
    ImageView superImageView;
    WebView superWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_web);

        Button buttonCancel =findViewById(R.id.cancelBtn);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });


        superProgressBar = findViewById(R.id.ProgressBar);
        //  superImageView = findViewById(R.id.cancelBtn);
        superWebView = findViewById(R.id.WebView);

        superProgressBar.setMax(100);

        superWebView.loadUrl("https://google.com");
        superWebView.getSettings().setJavaScriptEnabled(true);
        superWebView.setWebViewClient(new WebViewClient());
        superWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                superProgressBar.setProgress(newProgress);
                superProgressBar.setProgress(newProgress);

                if(newProgress == 100){
                    AlphaAnimation fadeOut;
                    fadeOut = new AlphaAnimation(1, 0);
                    fadeOut.setDuration(500);
                    fadeOut.setFillAfter(true);

                    superProgressBar.startAnimation(fadeOut);
                } else {
                    superProgressBar.setVisibility(View.VISIBLE);
                }

            }

        });


    }

    @Override
    public void onBackPressed() {
        if (superWebView.canGoBack()) {
            superWebView.goBack();
        } else {
            finish();
        }
    }
}
