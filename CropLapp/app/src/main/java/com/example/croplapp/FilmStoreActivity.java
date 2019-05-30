/*
* FilmItem.java
* Author: Nguyen Duc Tien 16020175
* Purpose: Film store activity
* Include: initDatabaseStore, interact with filmitem
*/
package com.example.croplapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FilmStoreActivity extends AppCompatActivity {
    List<FilmDetails> list;
    RecyclerView recyclerView;
    FilmStoreAdapter adapter;

    // Data from firebase save into here
    ArrayList<String> filmData = new ArrayList<String>();

    ProgressDialog progressDialog;

    //custom lib
    MyLib myLib = new MyLib(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_store);
    }

    protected void onStart() {
        super.onStart();
        setContentView(R.layout.list_store);

        // Get store data from database
        myLib.initDatabaseStore(getString(R.string.filmHanoi));
        myLib.OnRecievedStoreListener(new MyLib.OnRecievedStore() {
            @Override
            public void onReceivedStore(ArrayList<String> list) {
                progressDialog.dismiss();
                filmData = list;
                for (int i = 0; i < filmData.size(); i++) {
                }
                addToAdapter();
            }
        });

        progressDialog = new ProgressDialog(this);
        // Setting Title
        progressDialog.setTitle(getString(R.string.storeGetData));
        // Setting Message
        progressDialog.setMessage(getString(R.string.storeLoaing));
        // Progress Dialog Style Spinner
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // Display Progress Dialog
        progressDialog.show();
        // Cannot Cancel Progress Dialog
        progressDialog.setCancelable(true);

        ImageButton buttonRefresh = findViewById(R.id.button_refresh);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRestart();
            }
        });

    }

    //Reload store
    @Override
    protected void onRestart() {
        super.onRestart();
        Intent i1 = new Intent(FilmStoreActivity.this, FilmStoreActivity.class);  //your class
        startActivity(i1);
        finish();

    }

    public void addToAdapter(){
        FilmDetails item;
        creatRecycleview();

        for (short i = 0; i < filmData.size(); i++) {
            String[] split = filmData.get(i).split(",");
            item = new FilmDetails(filmData.get(i));
            list.add(item);
        }
        adapter.notifyDataSetChanged();
    }

    // Add adapter to recycleview
    public void creatRecycleview
    () {
        recyclerView = (RecyclerView) findViewById(R.id.rv_listfilm);
        list = new ArrayList<>();
        adapter = new FilmStoreAdapter(this,list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
