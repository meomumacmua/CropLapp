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

    ArrayList<String> a0 = new ArrayList<String>();

    ProgressDialog progressDialog;

    int arrayListsize = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_store);

//        Handler handler = new Handler();
//
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                finish();
//            }
//        }, 20000);
    }

    protected void onStart() {
        super.onStart();
        setContentView(R.layout.list_store);

        getDatabase("hanoifilm");

        progressDialog = new ProgressDialog(this);
        // Setting Title
        progressDialog.setTitle("ProgressDialog");
        // Setting Message
        progressDialog.setMessage("Loading...");
        // Progress Dialog Style Spinner
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // Display Progress Dialog
        progressDialog.show();
        // Cannot Cancel Progress Dialog
        progressDialog.setCancelable(false);

        new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) {
                Log.d("print","seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Log.d("print","done!");
                progressDialog.dismiss();

                Log.d("print", a0.size() + "");
                FilmDetails item;
                addControl();

                for (short i = 0; i < a0.size(); i++) {
                    String[] split = a0.get(i).toString().split(",");
                    split[3] = split[3].replaceAll("\\s", "").toString();
//                    Log.d("print", "/" + split[3] + "/");

//                    String filmStateDecode = "...";
//                    if(split[4].contains("0000")) {
//                        filmStateDecode = "Hết hàng";
//                    } else if (split[4].contains("0001")) {
//                        filmStateDecode = "Còn hàng";
//                    }
                    item = new FilmDetails(split[0], split[2], split[3], split[4]);
                    list.add(item);
                }
                adapter.notifyDataSetChanged();
            }
        }.start();

        ImageButton buttonRefresh = findViewById(R.id.btn_refresh);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRestart();
            }
        });
    }


    @Override
    protected void onRestart() {

        // TODO Auto-generated method stub
        super.onRestart();
        Intent i1 = new Intent(FilmStoreActivity.this, FilmStoreActivity.class);  //your class
        startActivity(i1);
        finish();

    }

    public void getDatabase(String areaCode) {
        //Clear arrray buffer before retrieve data
        a0.clear();
        // Get the FirebaseDatabase object
        DatabaseReference database;
        database = FirebaseDatabase.getInstance().getReference(areaCode);
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
                    String[] value = data.getValue().toString().split(",");
//                    Log.d("print", data.getValue().toString());

                    a0.add(key + ", " + data.getValue());
//                    Log.d("print", "a0 " + a0);
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

    public void addControl() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_listfilm);
        list = new ArrayList<>();
        adapter = new FilmStoreAdapter(this,list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
