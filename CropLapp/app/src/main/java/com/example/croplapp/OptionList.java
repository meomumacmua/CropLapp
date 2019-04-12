package com.example.croplapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class OptionList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_list);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_option_list);

        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner0);
        //create a list of items for the spinner.
        String[] items = new String[]{"Hà Nội", "Sài Gòn", "Smt else"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);


    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        String text;
        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets seleted


                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
//                text = spinner0.getSelectedItem().toString();
//                Log.d("printf", text);
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
//                text = spinner0.getSelectedItem().toString();
//                Log.d("printf", text);
                break;

        }
    }
}
