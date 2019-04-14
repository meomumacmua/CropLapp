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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class TrackingActivity extends AppCompatActivity {
    /* key specifying the search area: "hanoi" or "saigon" */
    String areaToFind;
    /* String specifying the search area: "Hà Nội" or "Sài Gòn" */
    String textReceiver;
    
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
            showAlertDialog(6,"Error");
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

        /* Seach button */
        ImageButton button1 = findViewById(R.id.seachButton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call to hide keyboard when clicked
                hideKeyboard(view);
                // Get string input
                EditText inputText = findViewById(R.id.editText);
                final String text = inputText.getText().toString();
                /*
                * If frist letter is digit or string length < 4, notice invalid input 
                * If not, seach in database
                */
                char c = text.charAt(0);
                if ((text.trim().length() <= 4)  || (Character.isDigit(c))) {   // Invalid code (1)
                    showAlertDialog(1, text);
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

    /* Alert dialog funtiion*/
    public void showAlertDialog(int feedBack ,String code) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("CropLab Thông báo!!!");
        Log.d("Printf", "catchalert " + feedBack);

        // Set the message
        switch (feedBack) {
            case 1: {   // Invalid Code (1)
                builder.setMessage("Mã hóa đơn '" + code + "' không hợp lệ! \nMời nhập lại chính xác mã hoá đơn!!!");
                break;
            }
            case 2: {   // No code found (2)
                builder.setMessage("Mã hóa đơn '" + code + "' không tìm thấy, vui lòng kiểm tra và nhập lại chính xác mã hoá đơn!");
                break;
            }
            case 3: {   // Received (3)
                builder.setMessage("Mã hóa đơn '" + code + "' của bạn đang trong hàng chờ để được xử lý, cứ ngồi im rồi film sẽ được tráng! ♥  ");
                break;
            }
            case 4: {   // Processing (4)
                builder.setMessage("Mã hóa đơn '" + code + "' : film của bạn đang được xử lý, bạn cứ bình tĩnh rồi hình sẽ tới nhé! ♥  ");
                break;
            }
            case 5: {   // Finished (5)
                builder.setMessage("Mã hóa đơn '" + code + "' : film của bạn đã hoàn thành, bạn có thể tới lấy lại âm bản trong vòng 3 tháng nhé! ♥ ");
                break;
            }
            case 6: {   // Database error (6)
                builder.setMessage("Gặp sự cố khi kết nối đến cơ sở dữ liệu \nBạn vui lòng quay lại sau!!!");
                break;
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
            }
        });
        /* Creat alert on buffer */
        AlertDialog alertDialog = builder.create();
        /* Show alert dialog */
        alertDialog.show();

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
            public void onDataChange(DataSnapshot dataSnapshot) {
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
                showAlertDialog(temp, compareText);
            }
            
            /* Firebase error*/
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("FIREBASE", "loadPost:onCancelled", databaseError.toException());
                showAlertDialog(6,"Error!!!");
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
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Loop to get data when there is a change on Firebasese
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                }
            }

            /* Firebase error*/
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("FIREBASE", "loadPost:onCancelled", databaseError.toException());
                showAlertDialog(6, "Error");
            }
        });
        return 0;
    }
}
