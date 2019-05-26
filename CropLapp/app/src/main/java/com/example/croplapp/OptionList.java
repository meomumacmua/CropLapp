/*
 * OMane: OptionList.java
 * Author: Nguyen Duc Tien 16020175
 * Purpose: Change search area: "Hà Nội" or "Sài Gòn"
 * Include: AdapterView, Spinner, Intent bundle
 */
package com.example.croplapp;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

public class OptionList extends AppCompatActivity {
    /* String specifying the choosed area: "Hà Nội" or "Sài Gòn" */
    String areaChoosed;
    /*  */
    public static final String EXTRA_DATA = "EXTRA_DATA";


    /* onCreat();
     * Get bundle data from MainAcvtivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_option_list);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle(getResources().getString(R.string.app_name));

//        Button changeLang = findViewById(R.id.changeLang);
        Button cL = findViewById(R.id.changeLang);
        cL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showChangeLanguageDialog();
                Log.d("print", "onclick");
                showAlertDialog(4, "4");
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            areaChoosed = bundle.getString("area", "");
        }

    }

    private void showChangeLanguageDialog(){
        final String[] listItems = {"English", "Vietnamese"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Select Language...");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    setLocale("vi");
                    recreate();
                }
                else if (i == 1){
                    setLocale("en");
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    /* Alert dialog funtiion*/
    public void showAlertDialog(final int feedBack, String code) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("CropLab !!!");
        switch (feedBack) {
            case 4:
            {
                // Set the message
                builder.setMessage("Select Language");
            }
        }
        /* Disable click outside the alert to turn off */
        builder.setCancelable(false);
        /* Set "OK" button */
        builder.setPositiveButton("English", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setLocale("en");
                recreate();
                // Dismiss the alert
                dialogInterface.dismiss();
                // Exit when there is no internet connection
            }
        });

        //////////////////////////////////////////////////
        builder.setNegativeButton("Vietnamese", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setLocale("vi");
                recreate();
                // Dismiss the alert
                dialogInterface.dismiss();
                // Exit when there is no internet connection
            }
        });
        /* Creat alert on buffer */
        AlertDialog alertDialog = builder.create();
        /* Show alert dialog */
        alertDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My Lang", lang);
        editor.apply();
    }

    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My Lang","");
        setLocale(language);
    }

    /* onStart();
     * Show name of search area
     * Show dropdown list
     * Get sellected dropdown list item
     */
    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_option_list);

        Button cL = findViewById(R.id.changeLang);
        cL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showChangeLanguageDialog();
                Log.d("print", "onclick");
                showAlertDialog(4, "4");
            }
        });


        Button button=findViewById(R.id.buttonNotification);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(OptionList.this, MyService.class);
//                startService(i);  // For the service.
//                showNotification();
            }
        });

        /* Show name of search area */
        final TextView showArea = findViewById(R.id.textView2);
        showArea.setText(getString(R.string.currentArea) + areaChoosed);

        /* Get the spinner from the xml. */
        final Spinner dropdown = findViewById(R.id.spinner0);
        // Create a list of items for the spinner.
        String[] items = new String[]{getString(R.string.areaHanoi), getString(R.string.areaHcm)};

        /*
         * Create an adapter to describe how the items are displayed, adapters are used in several places in android.
         * There are multiple variations of this, but this is the basic variant.
         */
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        /* Set the spinners adapter */
        dropdown.setAdapter(adapter);

        /* Get the text when sellect */
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                areaChoosed = dropdown.getSelectedItem().toString();
                showArea.setText(getString(R.string.currentArea) + areaChoosed);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    /*
     * onbackpressed();
     * Press back to return MainActivity and send new name of seach area (if changed)
     */
    public void onBackPressed() {

        final Intent data = new Intent();
        // Add data to the intent
        data.putExtra(EXTRA_DATA, areaChoosed);

        /*
         * Set the resultCode to AppCompatActivity.RESULT_OK to show
         * instance was successful and contains returned results
         */
        setResult(AppCompatActivity.RESULT_OK, data);
        // Call the finish() function to close the current Activity and return to MainActivity
        finish();
    }

    public void showNotification() {
        while (true) {
            try {
                //set time in mili
                Thread.sleep(3000);

            } catch (Exception e) {
                e.printStackTrace();
            }

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

}