/*
* Name: MainActivity.java
* Author: Nguyen Duc Tien 16020175
* Purpose: Home screen
* Include: alert dialog, Intent bundle, Shared preference, network check
*/

package com.example.croplapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    /* */
    boolean DEBUG = true;
    /* String specifying the search area: "Hà Nội" or "Sài Gòn" */
    String currentAreaCode;
    boolean onlineStatus;

    /* */
    MyLib myLib = new MyLib(this);
    
    /* Request code for startActivityForResult() & onActivityResult() function */
    private static final int REQUEST_CODE_AREA = 1998;
    private static final int REQUEST_CODE_TRACK = 1997;

    /* */
    ArrayList<String> dataReserveHanoi = new ArrayList<String>();
    ArrayList<String> dataReserveHcm = new ArrayList<String>();

    String lastTimeAccessDB;
    
    /* Internet connection test function */
    private boolean isNetworkConnected() {
        ConnectivityManager checkNetwork = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return checkNetwork.getActiveNetworkInfo() != null;
    }

    
    /* onCreat();
    * Check network
    * Load data saved
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /* Check internet */
        if (isNetworkConnected()) {

            if (DEBUG) {
                Log.d("print", "Connect");
            }

            onlineStatus = true;
        } else {

            if (DEBUG) {
                Log.d("print", "No Connect");
            }

            onlineStatus = false;
            // Show alert offline and exit
            myLib.showAlertDialog1(4,"4");
            myLib.OnSeclectListener(new MyLib.OnSeclect() {
                @Override
                public void onSeclected() {
                    finish();
                }
            });
        }
        // Load saved data
        loadAppSetting();

        if (DEBUG) {
            Log.d("print", "onCreat - currentAreaCode: " + currentAreaCode);
        }

    }
    
    /* onstart(); 
    * Show name of search area
    * Switch to FilmStoreAdapter, TrackingActivity, WebViewActivity, OptionList
    */
    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_main);

        /* Show name of search area */

        if (DEBUG) {
            Log.d("print", "onStart - currentAreaCode: " + currentAreaCode);
        }

        TextView showArea = findViewById(R.id.textView);
        if (currentAreaCode.contains(getString(R.string.areaHanoi))) {
            showArea.setText(getString(R.string.areaHanoi));
        }
        if (currentAreaCode.contains(getString(R.string.areaHcm))) {
            showArea.setText(getString(R.string.areaHcm));
        }



        /* Action switch to FilmStoreAdapter.java */
        Button buttonfilmstore = findViewById(R.id.button_film_store);
        buttonfilmstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!onlineStatus) {
                    Toast.makeText(getBaseContext(), getString(R.string.unavailableWhenOffline), Toast.LENGTH_SHORT).show();
                } else {

                    if (DEBUG) {
                        Log.d("print", "onButton Filmstore");
                    }

                    Intent intent = new Intent(MainActivity.this, FilmStoreActivity.class);
                    // Start activity
                    startActivity(intent);
                }
            }
        });

        /* Action switch to TrackingActivity.java */
        Button buttontracking = findViewById(R.id.button_tracking);
        buttontracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(MainActivity.this, TrackingActivity.class);
                // Send bundle data (seach area) to destination activity
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.keyArea),currentAreaCode);

                if (!isNetworkConnected()) {
                    bundle.putBoolean(getString(R.string.keyState), onlineStatus);
                    if (currentAreaCode.contains(getString(R.string.areaHanoiCode))) {
                        bundle.putStringArrayList(getString(R.string.keydata), dataReserveHanoi);
                    }
                    if (currentAreaCode.contains(getString(R.string.areaHcmCode))) {
                        bundle.putStringArrayList(getString(R.string.keydata), dataReserveHcm);
                    }
                    bundle.putString(getString(R.string.keyLastTime), lastTimeAccessDB);

                    if (DEBUG) {
                        Log.d("print", "onButton tracking - hanoi: "            + dataReserveHanoi.get(0));
                        Log.d("print", "onButton tracking - hcm: "              + dataReserveHcm.get(0));
                        Log.d("print", "onButton tracking - time: "             + lastTimeAccessDB);
                    }
                }

                intent1.putExtras(bundle);
                // Start activity
                startActivityForResult(intent1, REQUEST_CODE_TRACK);

                if (DEBUG) {
                    Log.d("print", "onButton tracking - currentAreaCode: "  + currentAreaCode);
                }

            }
        });
        
        /* Action switch to WebViewActivity.java */
        Button buttonnews = findViewById(R.id.button_news);
        buttonnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!onlineStatus) {
                    Toast.makeText(getBaseContext(), getString(R.string.unavailableWhenOffline), Toast.LENGTH_SHORT).show();
                } else {

                    if (DEBUG) {
                        Log.d("print", "onButton Webview");
                    }

                    Intent intent2 = new Intent(MainActivity.this, WebViewActivity.class);
                    startActivity(intent2);
                }
            }
        });
        
        /* Action switch to InformationActivity.java */
        Button buttoninfor = findViewById(R.id.button_info);
        buttoninfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (DEBUG) {
                    Log.d("print", "onButton Info");
                }

                Intent intent4 = new Intent(MainActivity.this, IntroActivity.class);
                startActivity(intent4);
//                eventClick();
            }
        });

        /* Action switch to OptionList.java */
        ImageButton buttonoption = findViewById(R.id.image_buttonOption);
        buttonoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (DEBUG) {
                    Log.d("print", "onButton option - currentAreaCode: " + currentAreaCode);
                }

                Intent intent3 = new Intent(MainActivity.this, OptionList.class);
                
                // Send bundle data (seach area) to destination activity
                Bundle bundle = new Bundle();
                bundle.putString(getString(R.string.keyArea),currentAreaCode);
                intent3.putExtras(bundle);

                // Start OptionList activity and get result when called activity return
                startActivityForResult(intent3, REQUEST_CODE_AREA);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });


    }

    /* onActivityResult();
    * get data from called activity result (OptionList)
    */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /* Get data return */
        super.onActivityResult(requestCode, resultCode, data);
        /* Check if the requestCode matches the REQUESTCODE we just used (REQUEST_CODE_AREA = 1998) */
        if(requestCode == REQUEST_CODE_AREA) {
            /*
            * "resultCode" set by DetailActivity
            * "RESULT_OK" indicates that this result was successful
            */
            if(resultCode == AppCompatActivity.RESULT_OK) {
                // Receive data from returned Intent
                currentAreaCode = data.getStringExtra(OptionList.EXTRA_DATA);

                if (DEBUG) {
                    Log.d("print", "onResult area - currentAreaCode: " + currentAreaCode);
                }

            } else {
                // DetailActivity failed, no data returned.

            }
        }
        if(requestCode == REQUEST_CODE_TRACK) {
            /*
             * "resultCode" set by DetailActivity
             * "RESULT_OK" indicates that this result was successful
             */
            if(resultCode == AppCompatActivity.RESULT_OK) {
                if (onlineStatus) {
                    // Receive data from returned Intent
                    if (currentAreaCode.contains(getString(R.string.areaHanoiCode))) {
                        dataReserveHanoi = data.getStringArrayListExtra(TrackingActivity.TRACK_EXTRA_DATA);

                        if (DEBUG) {
                            Log.d("print", "onResult track - hanoi: " + dataReserveHanoi.get(0));
                        }

                    }
                    if (currentAreaCode.contains(getString(R.string.areaHcmCode))) {
                        dataReserveHcm = data.getStringArrayListExtra(TrackingActivity.TRACK_EXTRA_DATA);

                        if (DEBUG) {
                            Log.d("print", "onResult track - hcm: " + dataReserveHcm.get(0));
                        }

                    }
                    lastTimeAccessDB = data.getStringExtra(TrackingActivity.TRACK_EXTRA_DATE);

                    if (DEBUG) {
                        Log.d("print", "onResult track - time: " + lastTimeAccessDB);
                    }

                } else {
                    int temp = (int) data.getIntExtra(TrackingActivity.TRACK_EXTRA_DATA, 1);
                }
            } else {
                // DetailActivity failed, no data returned.

            }
        }
    }

    /*
    * onbackpressed();
    * Press back 2 times to exit and save data to android-shared-preferences
    */
    private static final int TIME_INTERVAL = 2000;  // milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    @Override
    public void onBackPressed()
    {
        // Press back again in the given time period (<2s)
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            // Save data
            saveAppSetting();

            if (DEBUG) {
                Log.d("print", "exit");
            }

            return;
        } else {
            // Press back first time
            Toast.makeText(getBaseContext(), R.string.backConfirm, Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }

    /* Get saved data from android-shared-preferences */
    private void loadAppSetting()  {
        dataReserveHanoi.clear();
        SharedPreferences sharedPreferences= this.getSharedPreferences(getString(R.string.sharePreName), Context.MODE_PRIVATE);
        if(sharedPreferences!= null) {
            // String specifying the search area
            currentAreaCode = sharedPreferences.getString(getString(R.string.keyArea), getString(R.string.areaHanoiCode));
            if (DEBUG) {
                Log.d("print", "onLoad - currentAreaCode: " + currentAreaCode);
            }
            //if no connect, load from last save
            if (!isNetworkConnected()) {
                int dataReserveHanoiLength = sharedPreferences.getInt(getString(R.string.keyDataHnL), 0);

                if (DEBUG) {
                    Log.d("print", "onLoad - hanoi length: " + dataReserveHanoiLength);
                }

                int dataReserveHcmLength = sharedPreferences.getInt(getString(R.string.keyDataHcmL), 0);

                if (DEBUG) {
                    Log.d("print", "onLoad - hcm length: " + dataReserveHcmLength);
                }

                lastTimeAccessDB = sharedPreferences.getString(getString(R.string.keyLastTime),getString(R.string.keyLastTimeNone));

                if (DEBUG) {
                    Log.d("print", "onLoad - time: " + lastTimeAccessDB);
                }

            }
        }
    }

    /* Save data a android-shared-preferences */
    public void saveAppSetting()  {
        SharedPreferences sharedPreferences= this.getSharedPreferences(getString(R.string.sharePreName), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(getString(R.string.keyArea), currentAreaCode);


        int dataReserveHanoiLength = dataReserveHanoi.size();

        if (DEBUG) {
            Log.d("print", "onSave - hanoi length: " + dataReserveHanoiLength);
        }

        editor.putInt(getString(R.string.keyDataHnL), dataReserveHanoiLength);
        for (int i = 0; i < dataReserveHanoiLength; i++) {
            editor.putString(getString(R.string.keyDataHn) + i, dataReserveHanoi.get(i));
        }

        int dataReserveHcmLength = dataReserveHcm.size();

        if (DEBUG) {
            Log.d("print", "onSave - hcm length: " + dataReserveHcmLength);
        }

        editor.putInt(getString(R.string.keyDataHcmL), dataReserveHcmLength);
        for (int i = 0; i < dataReserveHcmLength; i++) {
            editor.putString(getString(R.string.keyDataHcm) + i, dataReserveHcm.get(i));
        }
        editor.putString(getString(R.string.keyLastTime), lastTimeAccessDB);
        // Save
        editor.apply();
    }

    private void eventClick() {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        TextView txtView1 = (TextView) view.findViewById(R.id.bottom_sheet_textview1);

        final Dialog mBottomSheetDialog = new Dialog(MainActivity.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        txtView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Clicked Backup", Toast.LENGTH_SHORT).show();
                mBottomSheetDialog.dismiss();
            }
        });
    }
}
