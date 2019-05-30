/*
* Name: MyLib.java
* Author: Nguyen Duc Tien 16020175, Duong Quoc Anh 16020102
* Purpose: custom function to reuse
* Include: initDatabaseTrack, initDatabaseStore, showAlertDialog, getTimer, isNetworkConnected
*/
package com.example.croplapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyLib {
    Context context;
    public MyLib(Context context) {
        this.context = context;
    }

    // Get data from firebase
    public int initDatabaseTrack(final String topic) {
        final ArrayList<String> listReceivedTrack = new ArrayList<>();

        // Get the FirebaseDatabase object
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Connection to the node named areaCode, this node is defined by the Firebase database ('hanoi' or 'saigon')
        DatabaseReference myRef = database.getReference(topic);
        // Access and listen to data changes
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Loop to get data when there is a change on Firebasese
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    listReceivedTrack.add(data.getValue().toString());
                }
                // Done!
                onReceivedTrack.onReceivedTrack(listReceivedTrack);
            }

            /* Firebase error*/
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("FIREBASE", "loadPost:onCancelled", databaseError.toException());
                showAlertDialog2(6, "Error");
            }
        });
        return 0;
    }

    public int initDatabaseStore(final String topic) {
        final ArrayList<String> listReceivedStore = new ArrayList<>();

        // Get the FirebaseDatabase object
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Connection to the node named areaCode, this node is defined by the Firebase database ('hanoi' or 'saigon')
        DatabaseReference myRef = database.getReference(topic);
        // Access and listen to data changes
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Loop to get data when there is a change on Firebasese
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    listReceivedStore.add(data.getKey() + "," + data.getValue());
                }
                // Done!
                onReceivedStore.onReceivedStore(listReceivedStore);
            }

            /* Firebase error*/
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("FIREBASE", "loadPost:onCancelled", databaseError.toException());
                showAlertDialog2(6, "Error");
            }
        });
        return 0;
    }

    public void showAlertDialog1(final int feedBack, String code) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.noticeTitle));
        switch (feedBack) {
            case 4:
            {
                // Set the message
                builder.setMessage(context.getString(R.string.noticeOffline));
            }
        }
        /* Disable click outside the alert to turn off */
        builder.setCancelable(false);
        /* Set "OK" button */
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Dismiss the alert
                dialogInterface.dismiss();
                // Exit when there is no internet connection
//                finish();
                onSeclect.onSeclected();
            }
        });

        builder.setNegativeButton(context.getString(R.string.offlineSeach), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Dismiss the alert
                dialogInterface.dismiss();
            }
        });

//        Toast.makeText(context, R.string.unavailableWhenOffline, Toast.LENGTH_SHORT).show();

        /* Creat alert on buffer */
        AlertDialog alertDialog = builder.create();
        /* Show alert dialog */
        alertDialog.show();
    }

    public void showAlertDialog2(int feedBack ,String code) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.noticeTitle));
        String bill = context.getString(R.string.bill);
        // Set the message
        switch (feedBack) {
            case 1: {   // Invalid Code (1)
                builder.setMessage(bill  + " " + code + " " + context.getString(R.string.codeInvalidd));
                break;
            }
            case 2: {   // No code found (2)
                builder.setMessage(bill + " " + code + " " + context.getString(R.string.codeUnknow));
                break;
            }
            case 3: {   // Received (3)
                builder.setMessage(bill + " " + code + " " + context.getString(R.string.codeInQueue));
                break;
            }
            case 4: {   // Processing (4)
                builder.setMessage(bill + " " + code + " " + context.getString(R.string.codeProcessing));
                break;
            }
            case 5: {   // Finished (5)
                builder.setMessage(bill + " " + code + " " + context.getString(R.string.codeDone));
                break;
            }
            case 6: {   // Database error (6)
                builder.setMessage(context.getString(R.string.firebaseError));
                break;
            }
        }
        /* Disable click outside the alert to turn off */
        builder.setCancelable(false);
        /* Set "OK" button */
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Dismiss the alert
                dialogInterface.dismiss();

            }
        });
        /* Creat alert on buffer */
        AlertDialog alertDialog = builder.create();
        /* Show alert dialog */
        alertDialog.show();

    }

    public void showAlertDialog3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.noticeTitle));
        builder.setMessage(context.getString(R.string.noticeOffline));
        /* Disable click outside the alert to turn off */
        builder.setCancelable(false);
        /* Set "OK" button */
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Dismiss the alert
                dialogInterface.dismiss();
                // Exit when there is no internet connection
//                finish();
                onSeclect.onSeclected();
            }
        });

//        Toast.makeText(context, R.string.unavailableWhenOffline, Toast.LENGTH_SHORT).show();

        /* Creat alert on buffer */
        AlertDialog alertDialog = builder.create();
        /* Show alert dialog */
        alertDialog.show();
    }

    // Get sys time
    public String getTimer() {
        Date date = new Date();
        final String DATE_FORMAT = "dd/MM/yyyy";
        final String TIME_FORMAT_12 = "hh:mm:ss a";
        final String TIME_FORMAT_24 = "HH:mm:ss";
        SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT_24 + " " + DATE_FORMAT  );
        return format.format(date);
    }

    // Check network
    public boolean isNetworkConnected() {
        ConnectivityManager checkNetwork = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return checkNetwork.getActiveNetworkInfo() != null;
    }

    // Interface seclect
    public interface OnSeclect {
        void onSeclected();
    }
    private OnSeclect onSeclect;
    public void OnSeclectListener(OnSeclect onSeclect) {
        this.onSeclect = onSeclect;
    }

    // Interface recieviedTrack
    public interface OnRecievedTrack {
        void onReceivedTrack(ArrayList<String> list);
    }
    private OnRecievedTrack onReceivedTrack;
    public  void OnRecievedTrackListener(OnRecievedTrack onReceivedTrack)
    {
        this.onReceivedTrack = onReceivedTrack;
    }

    // Interface recieviedStore
    public interface OnRecievedStore {
        void onReceivedStore(ArrayList<String> list);
    }
    private OnRecievedStore onReceivedStore;
    public  void OnRecievedStoreListener(OnRecievedStore onReceivedStore)
    {
        this.onReceivedStore = onReceivedStore;
    }
}
