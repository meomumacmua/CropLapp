/*
 * OMane: SplashActivity.java
 * Author: Duong Quoc Anh 16020102
 * Purpose: Welcomescreen
 * Include: check first open
 */
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
    boolean firstStart;
    MyLib myLib = new MyLib(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                // If first start, move to IntroActivity. If not, move to MainActivity
                SharedPreferences setting = getSharedPreferences("PRE", 0);
                firstStart = setting.getBoolean("first_time_start", true);
                if (firstStart) {
                    final SharedPreferences.Editor editor = setting.edit();
                    if(myLib.isNetworkConnected()) {

                        editor.putBoolean("first_time_start", false);
                        editor.commit();

                        Intent intent1 = new Intent(SplashActivity.this, IntroActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString(getString(R.string.intro), getString(R.string.intro));
                        intent1.putExtras(bundle);
                        startActivity(intent1);
                        finish();
                    } else {
                        myLib.showAlertDialog3();
                        myLib.OnSeclectListener(new MyLib.OnSeclect() {
                            @Override
                            public void onSeclected() {
                                editor.putBoolean("first_time_start", true);
                                editor.commit();
                                finish();
                            }
                        });

                    }
                }
                else {
                    Intent intent2 = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent2);
                    finish();
                }
            }
        }.start();
    }
}
