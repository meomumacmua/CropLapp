/*
* OMane: OptionList.java
* Author: Nguyen Duc Tien 16020175
* Purpose: Change search area: "Hà Nội" or "Sài Gòn"
* Include: AdapterView, Spinner, Intent bundle
*/
package com.example.croplapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class OptionList extends AppCompatActivity{
    /* */
    boolean DEBUG = false;
    /* String specifying the choosed area: "Hà Nội" or "Sài Gòn" */
    String areaChoosed;
    /*  */
    public static final String EXTRA_DATA = "EXTRA_DATA";

    MyLib test = new MyLib(this);
    
    /* onCreat(); 
    * Get bundle data from MainAcvtivity
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_list);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            areaChoosed = bundle.getString(getString(R.string.keyArea), getString(R.string.areaHanoiCode));

            if (DEBUG) {
                Log.d("print", "onCreat - areaChoosed: " + areaChoosed);
            }
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

        Button button=findViewById(R.id.buttonOption);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OptionList.this, MyService.class);
                startService(intent);  // For the service.
//                showNotification();
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.add(R.id.frg, new TestFragment());
//                fragmentTransaction.commit();
                test.showAlertDialog1( 4, "abc");
                test.OnSeclectListener(new MyLib.OnSeclect() {
                    @Override
                    public void onSeclected() {
                        finish();
                    }
                });

            }
        });
        /*
        Load được ảnh (ko load được do link ko có .jpg)
Thông báo hệ thống (thanh thông báo trên cùng của màn hình)
Chưa chạy được tác vụ nền
         */
        
        /* Show name of search area */
        final TextView showArea = findViewById(R.id.textView2);
        if (areaChoosed.contains(getString(R.string.areaHanoiCode))) {
            showArea.setText(getString(R.string.curentArea) + " " + getString(R.string.areaHanoi));
        }
        if (areaChoosed.contains(getString(R.string.areaHcmCode))) {
            showArea.setText(getString(R.string.curentArea) + " " + getString(R.string.areaHcm));
        }

        /* Get the spinner from the xml. */
        final Spinner dropdown = findViewById(R.id.spinner0);
        // Create a list of items for the spinner.
        String[] items = new String[]{getString(R.string.noneArea), getString(R.string.areaHanoi), getString(R.string.areaHcm)};
        
        /*
        * Create an adapter to describe how the items are displayed, adapters are used in several places in android.
        * There are multiple variations of this, but this is the basic variant.
        */
        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_spinner_dropdown_item, items);
        
        /* Set the spinners adapter */
        dropdown.setAdapter(adapter);
        
        /* Get the text when sellect */
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        if (DEBUG) {
                            Log.d("print", "onDrop - areaChoosed: " + areaChoosed);
                        }

                        break;
                    case 1:
                        areaChoosed = getString(R.string.areaHanoiCode);
                        showArea.setText(getString(R.string.curentArea) + " " + getString(R.string.areaHanoi));

                        if (DEBUG) {
                            Log.d("print", "onDrop - areaChoosed: " + areaChoosed);
                        }

                        break;
                    case 2:
                        areaChoosed = getString(R.string.areaHcmCode);
                        showArea.setText(getString(R.string.curentArea) + " " + getString(R.string.areaHcm));

                        if (DEBUG) {
                            Log.d("print", "onDrop - areaChoosed: " + areaChoosed);
                        }

                        break;

                }
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
        if (DEBUG) {
            Log.d("print", "onBack - areaChoosed: " + areaChoosed);
        }
        data.putExtra(EXTRA_DATA, areaChoosed);

        /*
        * Set the resultCode to AppCompatActivity.RESULT_OK to show
        * instance was successful and contains returned results
        */
        setResult(AppCompatActivity.RESULT_OK, data);
        // Call the finish() function to close the current Activity and return to MainActivity
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
