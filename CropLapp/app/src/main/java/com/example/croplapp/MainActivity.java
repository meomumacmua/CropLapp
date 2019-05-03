/*
* Name: MainActivity.java
* Author: Nguyen Duc Tien 16020175
* Purpose: Home screen
* Include: alert dialog, Intent bundle, Shared preference, network check
*/

package com.example.croplapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /* */
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    /* String specifying the search area: "Hà Nội" or "Sài Gòn" */
    String loadArea;
    
    /* Request code for startActivityForResult() & onActivityResult() function */
    private static final int REQUEST_CODE = 1998;
    
    /* Internet connection test function */
    private boolean isNetworkConnected() {
        ConnectivityManager checkNetwork = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return checkNetwork.getActiveNetworkInfo() != null;
    }
    
    /* Alert dialog funtiion*/
    public void showAlertDialog(final int feedBack, String code) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("CropLab Thông báo!!!");
        switch (feedBack) {
            case 4:
            {
                // Set the message
                builder.setMessage("Bạn đang ngoại tuyến!!! \nKết nối mạng và thử lại");
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
                finish();
            }
        });
        /* Creat alert on buffer */
        AlertDialog alertDialog = builder.create();
        /* Show alert dialog */
        alertDialog.show();
    }
    
    /* Get saved data from android-shared-preferences */
    private void loadAppSetting()  {
        SharedPreferences sharedPreferences= this.getSharedPreferences("croplabSetting", Context.MODE_PRIVATE);
        if(sharedPreferences!= null) {
            // String specifying the search area
            loadArea = sharedPreferences.getString("area", getString(R.string.areaHanoi));
        }
    }
    
    /* Save data to android-shared-preferences */
    public void saveAppSetting()  {
        SharedPreferences sharedPreferences= this.getSharedPreferences("croplabSetting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("area", loadArea);
        // Save
        editor.apply();
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
            Log.e("print", "You're connected to us");
            // Load saved data
            loadAppSetting();
        } else {
            Log.e("print", "You're alone");
            // Show alert offline and exit
            showAlertDialog(4,"4");
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
        TextView showArea = findViewById(R.id.textView);
        showArea.setText(loadArea);

        /*
        * #1
        * This function will coming soon
        */
        /* Action switch to FilmStoreAdapter.java */
        Button buttonfilmstore = findViewById(R.id.button_film_store);
        buttonfilmstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("FilmstoreActivity", "onClick: ");

//                Toast.makeText(getBaseContext(), "This function will coming soon!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, FilmStoreActivity.class);

                // Start activity
                startActivity(intent);
            }
        });
        /*
        * #1
        */
        
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
                intent1.putExtras(bundle);
                // Start activity
                startActivity(intent1);

            }
        });
        
        /* Action switch to WebViewActivity.java */
        Button buttonnews = findViewById(R.id.button_news);
        buttonnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("WebViewActivity", "onClick: ");
                Intent intent2 = new Intent(MainActivity.this, WebViewActivity.class);
                startActivity(intent2);
            }
        });
        
        /* Action switch to InformationActivity.java */
        Button buttoninfor = findViewById(R.id.button_info);
        buttoninfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("InfoActivity", "onClick: ");
                Intent intent4 = new Intent(MainActivity.this, InformationActivity.class);
                startActivity(intent4);
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
                startActivityForResult(intent3, REQUEST_CODE);



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
        if(requestCode == REQUEST_CODE) {
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
            Toast.makeText(getBaseContext(), "Nhấn phím back lần nữa để thoát", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }







}
