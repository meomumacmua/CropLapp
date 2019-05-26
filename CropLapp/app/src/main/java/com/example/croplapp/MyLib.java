package com.example.croplapp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyLib {
    Context context;
    public MyLib(Context context) {
        this.context = context;
    }

    public int initDatabase(String areaCode, final ArrayList<String> dataR) {
        dataR.clear();

//        final int[] i = {0};
        // Get the FirebaseDatabase object
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Connection to the node named areaCode, this node is defined by the Firebase database ('hanoi' or 'saigon')
        DatabaseReference myRef = database.getReference(areaCode);
        // Access and listen to data changes
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Loop to get data when there is a change on Firebasese
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    dataR.add(data.getValue().toString());
//                    Log.d("print", a0.get(i[0]));
//                    i[0]++;
                }
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

    /* Alert dialog funtiion*/
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

    public interface OnSeclect {
        void onSeclected();
    }

    private OnSeclect onSeclect;

    public void OnSeclectListener(OnSeclect onSeclect) {
        this.onSeclect = onSeclect;
    }
}
