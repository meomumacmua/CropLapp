/*
* Name: TrackingActivity.java
* Author: Nguyen Duc Tien 16020175
* Purpose: Seach bill code
* Include: firebase, alert dialog, Intent bundle, keyboard hide
*/

package com.example.croplapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    boolean canGoBack = false;
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
    }
    
    /* onStart()
    * Show seach area
    * Seach in database if match input code
    */
    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.tracking_layout);

        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.sharePreName), Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        /* Get intent bundle */
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            currentareaCode = bundle.getString(getString(R.string.keyArea), getString(R.string.areaHanoiCode));
            onlineStatus = bundle.getBoolean(getString(R.string.keyState), true);

            if (DEBUG) {
                Log.d("print", "onStart - bundle - currentareaCode: "   + currentareaCode);
                Log.d("print", "onStart - bundle - state: "             + onlineStatus);
            }

            if(onlineStatus) {
                /* Init Database */
                if(myLib.initDatabaseTrack(currentareaCode) == 6){
                    myLib.showAlertDialog2(6,"Error");
                }
                myLib.OnRecievedTrackListener(new MyLib.OnRecievedTrack() {
                    //Save data to SharedPreferences
                    @Override
                    public void onReceivedTrack(ArrayList<String> list) {
                        a0.clear();
                        a0 = list;
                        if (currentareaCode.contains(getString(R.string.areaHanoiCode))) {
                            editor.putString(getString(R.string.keyLastTimeHn), myLib.getTimer());
                            editor.putInt(getString(R.string.keyDataHnL), a0.size());

                            Log.d("print", "save hn time " + myLib.getTimer());
                            Log.d("print", "save hn L " + list.size());

                            for (int i = 0; i < list.size(); i++) {
                                editor.putString(getString(R.string.keyDataHn) + i, list.get(i));
//                                Log.d("print", "save hanoi " + i + " " + a0.get(i));
                            }
                        } else if (currentareaCode.contains(getString(R.string.areaHcmCode))) {
                            editor.putString(getString(R.string.keyLastTimeHcm), myLib.getTimer());
                            Log.d("print", "save hcm time " + myLib.getTimer());
                            editor.putInt(getString(R.string.keyDataHcmL), a0.size());
                            for (int i = 0; i < a0.size(); i++) {
                                editor.putString(getString(R.string.keyDataHcm) + i, a0.get(i));
//                                Log.d("print", "save hcm " + i + " " + list.get(i));
                            }
                        }
                        editor.apply();
                        canGoBack = true;
                    }
                });

            } else {
                canGoBack = true;
                //Load data from SharedPreferences
//                SharedPreferences sharedPreferences= TrackingActivity.this.getSharedPreferences(getString(R.string.sharePreName), MODE_PRIVATE);

                if (currentareaCode.contains(getString(R.string.areaHanoiCode))) {
                    lastTimeAccessDB = sharedPreferences.getString(getString(R.string.keyLastTimeHn), getString(R.string.keyLastTimeNone));
                    int datasize = sharedPreferences.getInt(getString(R.string.keyDataHnL), 0);
                    Log.d("print", "load hanoi L" + datasize);
                    a0.clear();
                    for (int i = 0; i < datasize; i++) {
                        a0.add(sharedPreferences.getString(getString(R.string.keyDataHn) + i, ""));
                        Log.d("print", "load hanoi " + i + " " + a0.get(i));

                    }
                }

                if (currentareaCode.contains(getString(R.string.areaHcmCode))) {
                    lastTimeAccessDB = sharedPreferences.getString(getString(R.string.keyLastTimeHcm), getString(R.string.keyLastTimeNone));
                    int datasize = sharedPreferences.getInt(getString(R.string.keyDataHcmL), 0);
                    a0.clear();
                    for (int i = 0; i < datasize; i++) {
                        a0.add(sharedPreferences.getString(getString(R.string.keyDataHcm) + i, ""));
                        Log.d("print", "load hcm " + i + " " + a0.get(i));
                    }
                }

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                TestFragment frgA = new TestFragment();
                Bundle frgBundle = new Bundle();
                if (currentareaCode.contains(getString(R.string.areaHanoiCode))) {
                    frgBundle.putString(getString(R.string.keyTime), lastTimeAccessDB);
                }

                if (currentareaCode.contains(getString(R.string.areaHcmCode))) {
                    frgBundle.putString(getString(R.string.keyTime), lastTimeAccessDB);
                }

                frgA.setArguments(frgBundle);
                fragmentTransaction.add(R.id.frg, frgA);
                fragmentTransaction.commit();

                if (DEBUG) {
                    Log.d("print", "onStart - bundle - time: " + lastTimeAccessDB);
//                    Log.d("print", "onCreat - bundle - data: " + a0.get(0));
                }
            }
        }

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
                    myLib.showAlertDialog2(1, text);
                } else {
                    getDatabase(text);
                }
            }
        });
    }
    
    /* onBackPressed()
    * Finish current activity and back to MainActivity
    */
    @Override
    public void onBackPressed(){

        if (canGoBack) {
            finish();
            overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
        }
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
        myLib.showAlertDialog2(temp, compareText);
    }
}
