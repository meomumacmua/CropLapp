package com.example.croplapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
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

    public void showAlertDialog(final int kindofAlert, String code) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("CropLab Thông báo!!!");
        if (kindofAlert == 1)
            builder.setMessage("Mã hóa đơn '" + code + "' : film của bạn đã hoàn thành, bạn có thể tới lấy lại âm bản trong vòng 3 tháng nhé! ♥ ");
        if (kindofAlert == 2)
            builder.setMessage("Mã hóa đơn '" + code + "' : film của bạn đang được xử lý, bạn cứ bình tĩnh rồi hình sẽ tới nhé! ♥  ");
        if (kindofAlert == 3)
            builder.setMessage("Mã hóa đơn '" + code + "' không tìm thấy, vui lòng kiểm tra và nhập lại chính xác mã hoá đơn!");
        if (kindofAlert == 4)
            builder.setMessage("Bạn đang ngoại tuyến!!! \nKết nối mạng và thử lại");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (kindofAlert == 4 ){
                    finish();
                }
                //Toast.makeText(MainActivity.this, "Không thoát được", Toast.LENGTH_SHORT).show();
            }
        });
//        builder.setNegativeButton("Timf", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //check internet
        if (isNetworkConnected()) {
            Log.e("print", "You're connected to us");
        } else{
            Log.e("print", "You're alone");
            showAlertDialog(4,"4");
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_main);

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

