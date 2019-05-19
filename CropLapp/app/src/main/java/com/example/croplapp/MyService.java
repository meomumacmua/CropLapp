package com.example.croplapp;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public class MyService extends IntentService {

    public MyService() {
        super("MyService");
    }

    // Invoked when this intent service is started.
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            // Log current thread info.
            Log.d("print", "IntentService");
//            for (int i = 0; i < 3000000; i++);
//            showNotification();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        for (int i = 0; i < 3000000; i++);
        showNotification();

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

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
