/*
* Name: TrackingActivity.java
* Author: Nguyen Duc Tien 16020175
* Purpose: Seach bill code
* Include: firebase, alert dialog, Intent bundle, keyboard hide
*/

package com.example.croplapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class TrackingActivity extends AppCompatActivity {
    MyLib myLib = new MyLib(this);
    /* */
    boolean DEBUG = true;
    /* key specifying the search area: "hanoi" or "saigon" */
//    String currentareaCode;
    /* String specifying the search area: "Hà Nội" or "Sài Gòn" */
    String currentareaCode;
    boolean onlineStatus;
    /* */
    public ArrayList<String> a0 = new ArrayList<>();
    String lastTimeAccessDB;

    EditText clickedEditText;

    /**/
    MyLib alertDialog = new MyLib(this);



    /* */
    public static final String TRACK_EXTRA_DATA = "TRACK_EXTRA_DATA";
    public static final String TRACK_EXTRA_DATE = "TRACK_EXTRA_DATE";
    /*
    * Feedback when searching data on firebase 
    * 0 = Init
    * 1 = Invalid Code
    * 2 = No code found
    * 3 = Received
    * 4 = Processing
    * 5 = Finished
    * 6 = Database error
    */
    
    /* onCreat()
    * Get intent bundle
    * Init database
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking_layout);

        if (DEBUG) {
            Log.d("print", "onCreat - track");
        }
        
        /* Get intent bundle */
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            currentareaCode = bundle.getString(getString(R.string.keyArea), getString(R.string.areaHanoiCode));
            onlineStatus = bundle.getBoolean(getString(R.string.keyState), true);

            if (DEBUG) {
                Log.d("print", "onCreat - bundle - currentareaCode: "   + currentareaCode);
                Log.d("print", "onCreat - bundle - state: "             + onlineStatus);
            }

            if(!onlineStatus) {
                a0 = bundle.getStringArrayList(getString(R.string.keydata));
                lastTimeAccessDB = bundle.getString(getString(R.string.keyLastTime));

                if (DEBUG) {
                    Log.d("print", "onCreat - bundle - time: " + lastTimeAccessDB);
                    Log.d("print", "onCreat - bundle - data: " + a0.get(0));
                }

            } else {
                /* Init Database */
                if(myLib.initDatabase(currentareaCode, a0) == 6){
                    alertDialog.showAlertDialog2(6,"Error");
                }
            }
        }

    }
    
    /* onStart()
    * Show seach area
    * Seach in database if match input code
    */
    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.tracking_layout);

        /* Show seach area */
        TextView showArea = findViewById(R.id.textViewArea);
        if (currentareaCode.contains(getString(R.string.areaHcmCode))) {
            showArea.setText(getString(R.string.areaHcm));
        }
        if (currentareaCode.contains(getString(R.string.areaHanoiCode))) {
            showArea.setText(getString(R.string.areaHanoi));
        }

        /* */
        clickedEditText = findViewById(R.id.editText);
        clickedEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedEditText.getText().clear();
            }
        });

        /* Seach button */
        ImageButton button1 = findViewById(R.id.seachButton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call to hide keyboard when clicked
                hideKeyboard(view);
                // Get string input
                EditText inputText = findViewById(R.id.editText);
                inputText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                final String text = inputText.getText().toString();
                /*
                * If frist letter is digit or string length < 4, notice invalid input 
                * If not, seach in database
                */
                char c = text.charAt(0);
                if ((text.trim().length() <= 4)  || (Character.isDigit(c))) {   // Invalid code (1)
                    alertDialog.showAlertDialog2(1, text);
                } else {                                                        // Seach in database
                    getDatabase(text);
                }
            }
        });

        if (!onlineStatus) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            TestFragment frgA = new TestFragment();
            Bundle frgBundle = new Bundle();
            frgBundle.putString(getString(R.string.keyLastTime), lastTimeAccessDB);
            frgA.setArguments(frgBundle);

            fragmentTransaction.add(R.id.frg, frgA);
            fragmentTransaction.commit();
//
//            TextView frgtxt = findViewById(R.id.frg_txt);
//            frgtxt.setText(lastTimeAccessDB);
        }
    }
    
    /* onBackPressed()
    * Finish current activity and back to MainActivity
    */
    @Override
    public void onBackPressed(){
        final Intent data = new Intent();
        // Add data to the intent
        if (onlineStatus) {
            data.putExtra(TRACK_EXTRA_DATA, a0);

            Date date = new Date();
            final String DATE_FORMAT = "dd/MM/yyyy";
//            final String TIME_FORMAT_12 = "hh:mm:ss a";
            final String TIME_FORMAT_24 = "HH:mm:ss";
            SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT_24 + " " + DATE_FORMAT  );
            data.putExtra(TRACK_EXTRA_DATE, format.format(date));
        } else {

            data.putExtra(TRACK_EXTRA_DATA, 1);
        }

        /*
         * Set the resultCode to AppCompatActivity.RESULT_OK to show
         * instance was successful and contains returned results
         */
        setResult(AppCompatActivity.RESULT_OK, data);
        // Call the finish() function to close the current Activity and return to MainActivity
        finish();
    }

    /* Hide keyboard function */
    public void hideKeyboard(View view) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch(Exception ignored) {
        }
    }

    public void getDatabase(final String compareText) {

        if (DEBUG) {
            Log.d("print", "onCreat - getdatabase2 - data: " + a0.get(0));
        }

        int temp = 2;
        for (int i = 0; i < a0.size(); i++) {
            String value = a0.get(i);
            if (value.contains(compareText)) {      // Check if the code exists
                temp = 3;                           // Feedback curent is Received (3)
                if (value.contains("...")) {        // Continue to check whether the status is processing
                    temp = 4;                       // Feedback curent is Processing (4)
                    if (value.contains("OK")) {     // Keep checking if the code is complete
                        temp = 5;                   // Feedback curent is Finished (5)
                    }
                }
            }
        }
        alertDialog.showAlertDialog2(temp, compareText);
    }
}
