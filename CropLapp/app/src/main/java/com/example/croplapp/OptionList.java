/*
 * OMane: OptionList.java
 * Author: Nguyen Duc Tien 16020175, Duong Quoc Anh 16020102
 * Purpose: Change search area: "Hà Nội" or "Sài Gòn", change language, show intro
 * Include: AdapterView, Spinner, setLang, loadLang
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
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;

public class OptionList extends AppCompatActivity {
    /* Specifying the search area: hanoi for "Hà Nội", hcm for "Hồ Chí Minh" */
    String areaChoosedCode;

    /* Language*/
    String language;
    /* onCreat();
     * Get bundle data from MainAcvtivity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_list);
        loadLocale();
        setLocale(language);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        // Get data from MainActivity
        if (bundle != null) {
            areaChoosedCode = bundle.getString("area", "");
        }
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

        Button changeLang = findViewById(R.id.changeLang);
        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showChangeLanguageDialog();
                Log.d("print", "onclick");
                showChangLangDialog();
            }
        });


        Button button=findViewById(R.id.intro);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4 = new Intent(OptionList.this, IntroActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.intro),"1");
                intent4.putExtras(bundle);
                startActivity(intent4);
            }
        });

        /* Show name of search area */
        final TextView showArea = findViewById(R.id.textView2);
        if (areaChoosedCode.contains(getString(R.string.areaHanoiCode))) {
            showArea.setText(getString(R.string.currentArea) + " " + getString(R.string.areaHanoi));
        } else if (areaChoosedCode.contains(getString(R.string.areaHcmCode))) {
            showArea.setText(getString(R.string.currentArea) + " " + getString(R.string.areaHcm));
        }



        /* Get the spinner from the xml. */
        final Spinner dropdownArea = findViewById(R.id.spinner0);
//        final Spinner dropdownLang = findViewById(R.id.spinner1);
        // Create a list of items for the spinner.
        String[] itemsArea = new String[]{getString(R.string.chosseArea) ,getString(R.string.areaHanoi), getString(R.string.areaHcm)};
//        String[] itemsLang = new String[]{getString(R.string.choseLang) ,getString(R.string.vi), getString(R.string.en)};
        /*
         * Create an adapter to describe how the items are displayed, adapters are used in several places in android.
         * There are multiple variations of this, but this is the basic variant.
         */
        ArrayAdapter<String> adapterArea = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsArea);
//        ArrayAdapter<String> adapterLang = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsLang);

        /* Set the spinners adapter */
        dropdownArea.setAdapter(adapterArea);
//        dropdownLang.setAdapter(adapterLang);

        /* Get the text when sellect */
        dropdownArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        areaChoosedCode = getString(R.string.areaHanoiCode);
                        showArea.setText(getString(R.string.currentArea) + " " + getString(R.string.areaHanoi));
                        break;
                    }
                    case 2: {
                        areaChoosedCode = getString(R.string.areaHcmCode);
                        showArea.setText(getString(R.string.currentArea) + " " + getString(R.string.areaHcm));
                        break;
                    }
                }
                
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
//        dropdownLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                switch (position) {
//                    case 0: {
//                        break;
//                    }
//                    case 1: {
//                        setLocale("vi");
//                        recreate();
//                        break;
//                    }
//                    case 2: {
//                        setLocale("en");
//                        recreate();
//                        break;
//                    }
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//            }
//        });
    }

    /*
     * onbackpressed();
     * Press back to return MainActivity and send new name of seach area (if changed)
     */
    /*  */
    public static final String EXTRA_DATA = "EXTRA_DATA";
    public void onBackPressed() {

        final Intent data = new Intent();
        // Add data to the intent
        data.putExtra(EXTRA_DATA, areaChoosedCode);

        /*
         * Set the resultCode to AppCompatActivity.RESULT_OK to show
         * instance was successful and contains returned results
         */
        setResult(AppCompatActivity.RESULT_OK, data);
        // Call the finish() function to close the current Activity and return to MainActivity
        finish();
    }

    /* Alert dialog funtiion*/
    public void showChangLangDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("CropLab !!!");
        builder.setMessage(getString(R.string.choseLang));
        /* Disable click outside the alert to turn off */
        builder.setCancelable(false);
        /* Set "OK" button */
        builder.setPositiveButton(getString(R.string.en), new DialogInterface.OnClickListener() {
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
        builder.setNegativeButton(getString(R.string.vi), new DialogInterface.OnClickListener() {
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

    // Get language from sharedPreferences
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

    // Apply seclected language
    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        language = prefs.getString("My Lang","");
        setLocale(language);
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
