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
    //    /* Seach in database */
//    public int getDatabase(String areaCode, final String compareText) {
//        // Get the FirebaseDatabase object
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        // Connection to the node named areaCode, this node is defined by the Firebase database ('hanoi' or 'saigon')
//        DatabaseReference myRef = database.getReference(areaCode);
//        // Access and listen to data changes
//        myRef.addValueEventListener(new ValueEventListener() {
//            /*
//            * Default Feedback is No-code-found (2), until the code is found
//            */
//            int temp = 2;
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                // Loop to get data when there is a change on Firebase
//                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                    // Get the key of the data
//                    // String key = data.getKey();
//
//                    // Transfer data into string then check
//                    String value = data.getValue().toString();
//                    if (value.contains(compareText)) {      // Check if the code exists
//                        temp = 3;                           // Feedback curent is Received (3)
//                        if (value.contains("...")) {        // Continue to check whether the status is processing
//                            temp = 4;                       // Feedback curent is Processing (4)
//                            if (value.contains("OK")) {     // Keep checking if the code is complete
//                                temp = 5;                   // Feedback curent is Finished (5)
//                            }
//                        }
//                    }
//                }
//                alertDialog.showAlertDialog2(temp, compareText);
//            }
//
//            /* Firebase error*/
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w("FIREBASE", "loadPost:onCancelled", databaseError.toException());
//                alertDialog.showAlertDialog2(6,"Error!!!");
//            }
//        });
//        return 0;
//    }
}
