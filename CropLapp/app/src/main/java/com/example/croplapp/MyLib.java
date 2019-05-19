package com.example.croplapp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class MyLib {
    Context context;
    public MyLib(Context context) {
        this.context = context;
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

    public interface OnSeclect {
        void onSeclected();
    }

    private OnSeclect onSeclect;

    public void OnSeclectListener(OnSeclect onSeclect) {
        this.onSeclect = onSeclect;
    }
}
