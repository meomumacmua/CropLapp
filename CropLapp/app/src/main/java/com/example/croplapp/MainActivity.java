package com.example.croplapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //check internet
        if (isNetworkConnected()==true) {
            Log.e("print", "You're connected to us");
        }
        else Log.e("print", "You're alone");



        Button buttonfilmstore = findViewById(R.id.button_film_store);
        buttonfilmstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("FilmstoreActivity", "onClick: ");
                Intent intent = new Intent(MainActivity.this, FilmStoreActivity.class);
                startActivity(intent);
            }
        });

        Button buttontracking = findViewById(R.id.button_tracking);
        buttontracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TrackingActivity", "onClick: ");
                Intent intent1 = new Intent(MainActivity.this, TrackingActivity.class);
                startActivity(intent1);


            }
        });


        Button buttonnews = findViewById(R.id.button_news);
        buttonnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("WebViewActivity", "onClick: ");
                Intent intent2 = new Intent(MainActivity.this, WebViewActivity.class);
                startActivity(intent2);
            }
        });
    }
}

