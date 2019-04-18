package com.example.croplapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FilmStoreActivity extends AppCompatActivity {
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<FilmDetails> list;
    FilmStoreAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_store);

        recyclerView = (RecyclerView) findViewById(R.id.rv_listfilm);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*
        reference = FirebaseDatabase.getInstance().getReference().child("hanoifilm");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d()



                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    FilmDetails fd = dataSnapshot1.getValue(FilmDetails.class);
                    list.add(fd);
                }

                adapter = new FilmStoreAdapter(FilmStoreActivity.this,list);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(FilmStoreActivity.this, "Oops...Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });
        */
        Log.d("print", "Start get");
        getDatabase("hanoifilm", "colorfilm");
        Log.d("print", "End get");



    }

    public int getDatabase(String areaCode, final String compareText) {
        // Get the FirebaseDatabase object
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Connection to the node named areaCode, this node is defined by the Firebase database ('hanoi' or 'saigon')
        DatabaseReference myRef = database.getReference(areaCode);
        DatabaseReference myRefChild = myRef.child(compareText);
        // Access and listen to data changes
        myRefChild.addValueEventListener(new ValueEventListener() {
            /*
             * Default Feedback is No-code-found (2), until the code is found
             */
            int temp = 2;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Loop to get data when there is a change on Firebase
                list = new ArrayList<FilmDetails>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                        // Get the key of the data
                        String key = data.getKey();
                        Log.d("print", key);
                        // Transfer data into string then check
                        String value = data.getValue().toString();
                        Log.d("print", value);

                    FilmDetails fd = data.getValue(FilmDetails.class);
                    list.add(fd);
                }
                adapter = new FilmStoreAdapter(FilmStoreActivity.this,list);
                recyclerView.setAdapter(adapter);
                //showAlertDialog(temp, compareText);
            }

            /* Firebase error*/
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("FIREBASE", "loadPost:onCancelled", databaseError.toException());
                //showAlertDialog(6,"Error!!!");
            }
        });
        return 0;
    }
}
