/*
* Name: TrackingActivity.java
* Author: Nguyen Duc Tien 16020175
* Purpose: Seach bill code
* Include: firebase, alert dialog, Intent bundle, keyboard hide
*/

package com.example.croplapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TrackingActivity extends AppCompatActivity {
    /* key specifying the search area: "hanoi" or "saigon" */
    String areaToFind;
    /* String specifying the search area: "Hà Nội" or "Sài Gòn" */
    String textReceiver;

    EditText clickedEditText;

    /**/
    MyLib alertDialog = new MyLib(this);

    /* */
    ArrayList<String> a0 = new ArrayList<String>();

    /* */
    public static final String EXTRA_DATA = "EXTRA_DATA";
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
        Log.d("print", "oncreat Track");
        
        /* Get intent bundle */
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            textReceiver = bundle.getString("area", "Hà Nội");

            if (textReceiver.contains(getString(R.string.areaSaigon))){
                areaToFind = getString(R.string.keySaigon);
            } else {
                areaToFind = getString(R.string.keyHanoi);
            }
        }
        /* Init Database */
        if(initDatabase(areaToFind) == 6){
            alertDialog.showAlertDialog2(6,"Error");
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
        showArea.setText(textReceiver);

        /* */
        clickedEditText = findViewById(R.id.editText);
        clickedEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedEditText.getText().clear();
            }
//            public void onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_NAVIGATE_NEXT) {
////                    int temp = getDatabase(areaToFind,clickedEditText.getText().toString());
//                    Log.d("print", keyCode + "");
//                }
//            }
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
                    int temp = getDatabase(areaToFind,text);
                }
            }
        });
    }
    
    /* onBackPressed()
    * Finish current activity and back to MainActivity
    */
    @Override
    public void onBackPressed(){
        final Intent data = new Intent();
        // Add data to the intent
        data.putExtra(EXTRA_DATA, a0);

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
    
    /* Seach in database */
    public int getDatabase(String areaCode, final String compareText) {
        // Get the FirebaseDatabase object 
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Connection to the node named areaCode, this node is defined by the Firebase database ('hanoi' or 'saigon')
        DatabaseReference myRef = database.getReference(areaCode);
        // Access and listen to data changes
        myRef.addValueEventListener(new ValueEventListener() {
            /*
            * Default Feedback is No-code-found (2), until the code is found
            */
            int temp = 2;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Loop to get data when there is a change on Firebase
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    // Get the key of the data
                    // String key = data.getKey();
                    
                    // Transfer data into string then check
                    String value = data.getValue().toString();
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
            
            /* Firebase error*/
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("FIREBASE", "loadPost:onCancelled", databaseError.toException());
                alertDialog.showAlertDialog2(6,"Error!!!");
            }
        });
        return 0;
    }

    public int initDatabase(String areaCode) {
        // Get the FirebaseDatabase object 
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Connection to the node named areaCode, this node is defined by the Firebase database ('hanoi' or 'saigon')
        DatabaseReference myRef = database.getReference(areaCode);
        // Access and listen to data changes
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Loop to get data when there is a change on Firebasese
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    a0.add(data.getValue().toString());
                }
            }

            /* Firebase error*/
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("FIREBASE", "loadPost:onCancelled", databaseError.toException());
                alertDialog.showAlertDialog2(6, "Error");
            }
        });
        return 0;
    }
}
