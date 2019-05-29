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

    ArrayList<String> filmData = new ArrayList<String>();

    ProgressDialog progressDialog;

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

//        new CountDownTimer(4000, 1000) {
//            public void onTick(long millisUntilFinished) {
////                Log.d("print","seconds remaining: " + millisUntilFinished / 1000);
//            }
//
//            public void onFinish() {
////                Log.d("print","done!");
//                progressDialog.dismiss();
//
////                Log.d("print", filmData.size() + "");
//                FilmDetails item;
//                FilmDetails itemExtend;
//                addControl();
//
//                for (short i = 0; i < filmData.size(); i++) {
//                    String[] split = filmData.get(i).split(",");
//                    item = new FilmDetails(filmData.get(i));
//                    list.add(item);
//                }
//                adapter.notifyDataSetChanged();
//            }
//        }.start();

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

    public void getDatabase(String topic) {
        //Clear arrray buffer before retrieve data
        filmData.clear();
        // Get the FirebaseDatabase object
        DatabaseReference database;
        database = FirebaseDatabase.getInstance().getReference(topic);
        // Connection to the node named areaCode, this node is defined by the Firebase database ('hanoi' or 'saigon')
        // Access and listen to data changes
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Loop to get data when there is a change on Firebase
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    // Get the key of the data
                    String key = data.getKey().toString();
//                    Log.d("print", key);
                    // Transfer data into string then check
//                    String[] value = data.getValue().toString().split(",");
//                    Log.d("print", data.getValue().toString());

                    filmData.add(key + "," + data.getValue());
                }
            }
            /* Firebase error*/
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("FIREBASE", "loadPost:onCancelled", databaseError.toException());
                //showAlertDialog(6,"Error!!!");
            }
        });
    }

    public void addToAdapter(){
        FilmDetails item;
        addControl();

        for (short i = 0; i < filmData.size(); i++) {
            String[] split = filmData.get(i).split(",");
            item = new FilmDetails(filmData.get(i));
            list.add(item);
        }
        adapter.notifyDataSetChanged();
    }

    // Add adapter to recycleview
    public void addControl() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_listfilm);
        list = new ArrayList<>();
        adapter = new FilmStoreAdapter(this,list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
