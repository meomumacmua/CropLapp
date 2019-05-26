package com.example.croplapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    boolean DEBUG = true;
    public PrefManager prefManager;
    MyLib myLib = new MyLib(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!isNetworkConnected()) {
            myLib.showAlertDialog3();
            myLib.OnSeclectListener(new MyLib.OnSeclect() {
                @Override
                public void onSeclected() {
                    finish();
                }
            });
        } else {
            new CountDownTimer(1000, 1000) {
                public void onTick(long millisUntilFinished) {
//                Log.d("print","seconds remaining: " + millisUntilFinished / 1000);
                }

                public void onFinish() {
//          Log.d("print","done!");
                    // Checking for first time launch - before calling setContentView()
                    prefManager = new PrefManager(SplashActivity.this);
                    if (!prefManager.isFirstTimeLaunch()) {
                        Intent intent1 = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent1);
                        finish();
                    } else {
                        Intent intent2 = new Intent(SplashActivity.this, IntroActivity.class);
                        startActivity(intent2);
                        finish();
                    }
                }
            }.start();
        }
    }
    /* Internet connection test function */
    private boolean isNetworkConnected() {
        ConnectivityManager checkNetwork = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return checkNetwork.getActiveNetworkInfo() != null;
    }
}