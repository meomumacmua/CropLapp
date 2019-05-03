package com.example.croplapp;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class MyService extends IntentService {

    public MyService(){
        super("MyService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        showNotification();
    }

    public void showNotification() {
        Log.d("print", "In showNotification();");
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        Resources r = getResources();
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker("Hello Ticker")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("Hello content title")
                .setContentText("hello content text")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
