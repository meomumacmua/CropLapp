package com.example.croplapp;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class MyService extends IntentService {
    MyLib myLib = new MyLib(this);

    public MyService() {
        super("MyService");
    }

    // Invoked when this intent service is started.
    @Override
    protected void onHandleIntent(Intent intent) {
//        if (intent != null) {
            Log.d("print", "IntentService");
            final String topic = intent.getStringExtra("Service");
            Log.d("print","get " + topic);
//            final int[] i = {0};
//            new CountDownTimer(2000, 1000) {
//                public void onTick(long millisUntilFinished) {
////                Log.d("print","seconds remaining: " + millisUntilFinished / 1000);
//                }
//
//                public void onFinish() {
//                    Log.d("print","get" + text + "num" + i[0]);
//                    i[0]++;
//                    if (i[0] < 2) {
//                        start();
//                    } else {
//                        showNotification();
//                    }
//                }
//            }.start();
//
//        }
        int result =  DownloadFile(new String(topic));
        Log.e("print", "done");
        showNotification();
    }

    public int  DownloadFile(final String topic) {
//        boolean[] check = {false};
        try {
            Log.d("print", "start");
            Thread.sleep(5000);
            if(myLib.isNetworkConnected()) {
                myLib.initDatabase(topic);
                myLib.OnRecievedListener(new MyLib.OnRecieved() {
                    boolean check = false;
                    @Override
                    public void onReceived(ArrayList<String> list) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).contains(topic)) {
                                if (list.get(i).contains("OK")) {
                                    Log.d("print", "end");
                                    check = true;
                                    showNotification();
                                    onDestroy();
                                }
                            }
                        }
                        if (!check) {
                            Log.d("print", "repeat 1");
                            DownloadFile(topic);

                        }
                    }
                });
            }
            else {
                Log.d("print", "repeart 2");
                DownloadFile(topic);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("print", "out");
        return 100;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("print", "onDestroy");
    }

    public void showNotification() {
        Log.d("print", "In showNotification();");
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("Hello Service")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("Hello content title")
                .setContentText("hello content text")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        startForeground(0, notification);

        // Play sound
        notification.defaults = Notification.DEFAULT_SOUND;

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
        /* Seach in database */
}
