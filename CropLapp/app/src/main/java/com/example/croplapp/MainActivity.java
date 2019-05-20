/*
* Name: MainActivity.java
* Author: Nguyen Duc Tien 16020175
* Purpose: Home screen
* Include: alert dialog, Intent bundle, Shared preference, network check
*/

package com.example.croplapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /* String specifying the search area: "Hà Nội" or "Sài Gòn" */
    String loadArea;
    boolean onlineStatus;

    /* */
    MyLib alertDialog = new MyLib(this);
    
    /* Request code for startActivityForResult() & onActivityResult() function */
    private static final int REQUEST_CODE_AREA = 1998;
    private static final int REQUEST_CODE_TRACK = 1997;

    /* */
    ArrayList<String> dataReserve = new ArrayList<String>();
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
            // Load saved data
            onlineStatus = true;
        } else {
            onlineStatus = false;
            // Show alert offline and exit
            alertDialog.showAlertDialog1(4,"4");
            alertDialog.OnSeclectListener(new MyLib.OnSeclect() {
                @Override
                public void onSeclected() {
                    finish();
                }
            });
        }
        loadAppSetting();
        Log.d("print", "oncreat" + " " + loadArea);
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
        TextView showArea = findViewById(R.id.textView);
        showArea.setText(loadArea);

        /* Action switch to FilmStoreAdapter.java */
        Button buttonfilmstore = findViewById(R.id.button_film_store);
        buttonfilmstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!onlineStatus) {
                    Toast.makeText(getBaseContext(), getString(R.string.unavailableWhenOffline), Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("FilmstoreActivity", "onClick: ");
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
                Log.d("TrackingActivity", "onClick: ");

                Intent intent1 = new Intent(MainActivity.this, TrackingActivity.class);
                // Send bundle data (seach area) to destination activity
                Bundle bundle = new Bundle();
                bundle.putString("area",loadArea);

                if (!isNetworkConnected()) {
                    bundle.putBoolean("state", onlineStatus);
                    bundle.putStringArrayList("data", dataReserve);
                    bundle.putString("lastTimeAccessDatabase", lastTimeAccessDB);
                }

                intent1.putExtras(bundle);
                // Start activity
                startActivityForResult(intent1, REQUEST_CODE_TRACK);

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
                    Log.d("WebViewActivity", "onClick: ");
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
                Log.d("InfoActivity", "onClick: ");
//                Intent intent4 = new Intent(MainActivity.this, InformationActivity.class);
//                startActivity(intent4);
                eventClick();
            }
        });

        /* Action switch to OptionList.java */
        ImageButton buttonoption = findViewById(R.id.image_buttonOption);
        buttonoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OptionList", "onClick: ");

                Intent intent3 = new Intent(MainActivity.this, OptionList.class);
                
                // Send bundle data (seach area) to destination activity
                Bundle bundle = new Bundle();
                bundle.putString("area",loadArea);
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
        /* Check if the requestCode matches the REQUESTCODE we just used (REQUEST_CODE = 1998) */
        if(requestCode == REQUEST_CODE_AREA) {
            /*
            * "resultCode" set by DetailActivity
            * "RESULT_OK" indicates that this result was successful
            */
            if(resultCode == AppCompatActivity.RESULT_OK) {
                // Receive data from returned Intent
                loadArea = data.getStringExtra(OptionList.EXTRA_DATA);
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
                    dataReserve = data.getStringArrayListExtra(TrackingActivity.TRACK_EXTRA_DATA);
                    lastTimeAccessDB = data.getStringExtra(TrackingActivity.TRACK_EXTRA_DATE);
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
            return;
        } else {
            // Press back first time
            Toast.makeText(getBaseContext(), R.string.backConfirm, Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }

    /* Get saved data from android-shared-preferences */
    private void loadAppSetting()  {
        dataReserve.clear();
        SharedPreferences sharedPreferences= this.getSharedPreferences("croplabSetting", Context.MODE_PRIVATE);
        if(sharedPreferences!= null) {
            // String specifying the search area
            loadArea = sharedPreferences.getString("area", getString(R.string.areaHanoi));
            //if no connect, load from last save
            if (!isNetworkConnected()) {
                int dataReserveLength = sharedPreferences.getInt("dataReserveLength", 0);
                for (int i = 0; i < dataReserveLength; i++) {
                    dataReserve.add(sharedPreferences.getString("dataReserve" + i, "0"));
                    lastTimeAccessDB = sharedPreferences.getString("lastTimeAccessDatabase","Không có dữ liệu");
                }
            }
        }
    }

    /* Save data a android-shared-preferences */
    public void saveAppSetting()  {
        SharedPreferences sharedPreferences= this.getSharedPreferences("croplabSetting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("area", loadArea);

        int dataReserveLength = dataReserve.size();
        Log.d("print", dataReserve.size() + "");
        editor.putInt("dataReserveLength", dataReserveLength);

        for (int i = 0; i < dataReserveLength; i++) {
            Log.d("print", dataReserve.get(i));
            editor.putString("dataReserve"+i,dataReserve.get(i));
        }
        editor.putString("lastTimeAccessDatabase", lastTimeAccessDB);
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
