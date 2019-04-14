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
import android.widget.Spinner;
import android.widget.TextView;

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
        setContentView(R.layout.activity_option_list);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            areaChoosed = bundle.getString("area", "");
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
        
        /* Show name of search area */
        TextView showArea = findViewById(R.id.textView2);
        showArea.setText("Khu vực hiện tại: " + areaChoosed);

        /* Get the spinner from the xml. */
        final Spinner dropdown = findViewById(R.id.spinner0);
        // Create a list of items for the spinner.
        String[] items = new String[]{getString(R.string.areaHanoi), getString(R.string.areaSaigon)};
        
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
}
