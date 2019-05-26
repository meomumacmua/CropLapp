package com.example.croplapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Welcome extends AppCompatActivity {
    boolean DEBUG = true;
    MyLib myLib = new MyLib(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if(!isNetworkConnected()) {
            myLib.showAlertDialog3();
            myLib.OnSeclectListener(new MyLib.OnSeclect() {
                @Override
                public void onSeclected() {
                    finish();
                }
            });
        } else {
            new CountDownTimer(2000, 1000) {
                public void onTick(long millisUntilFinished) {
//                Log.d("print","seconds remaining: " + millisUntilFinished / 1000);
                }

                public void onFinish() {
//          Log.d("print","done!");
                    Intent intent = new Intent(Welcome.this, IntroActivity.class);
                    startActivity(intent);
                    finish();
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
