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
    String areaChosed;

    public static final String EXTRA_DATA = "EXTRA_DATA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_list);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            areaChosed = bundle.getString("area", "");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_option_list);

        TextView showArea = findViewById(R.id.textView2);
        showArea.setText("Khu vực hiện tại: " + areaChosed);

        //get the spinner from the xml.
        final Spinner dropdown = findViewById(R.id.spinner0);
        //create a list of items for the spinner.
        String[] items = new String[]{getString(R.string.areaHanoi), getString(R.string.areaSaigon)};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        //get the text when sellect
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                areaChosed = dropdown.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


    public void onBackPressed()
    {

        // Tạo một Intent mới để chứa dữ liệu trả về
        final Intent data = new Intent();

        // Truyền data vào intent
        data.putExtra(EXTRA_DATA, areaChosed);

        // Đặt resultCode là Activity.RESULT_OK to
        // thể hiện đã thành công và có chứa kết quả trả về
        setResult(AppCompatActivity.RESULT_OK, data);

        // gọi hàm finish() để đóng Activity hiện tại và trở về MainActivity.
        finish();
    }
}
