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

    public void showAlertDialog(final int feedBack, String code) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.noticeTitle);
        switch (feedBack) {
            case 4:
            {
                // Set the message
                builder.setMessage(R.string.noticeOffline);
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

        builder.setNegativeButton(R.string.offlineSeach, new DialogInterface.OnClickListener() {
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

    public interface OnSeclect {
        void onSeclected();
    }

    private OnSeclect onSeclect;

    public void OnSeclectListener(OnSeclect onSeclect) {
        this.onSeclect = onSeclect;
    }
}
