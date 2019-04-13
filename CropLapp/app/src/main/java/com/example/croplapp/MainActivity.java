package com.example.croplapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String loadArea;
    private static final int REQUEST_CODE = 1998;
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void showAlertDialog(final int response, String code) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("CropLab Thông báo!!!");
        switch (response) {
            case 4:
            {
                builder.setMessage("Bạn đang ngoại tuyến!!! \nKết nối mạng và thử lại");
            }
        }

        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
            }
        });
//
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadAppSetting()  {
        SharedPreferences sharedPreferences= this.getSharedPreferences("croplabSetting", Context.MODE_PRIVATE);
        if(sharedPreferences!= null) {
            loadArea = sharedPreferences.getString("area", getString(R.string.areaHanoi));
        }
    }

    public void saveAppSetting()  {
        // File chia sẻ sử dụng trong nội bộ ứng dụng, hoặc các ứng dụng được chia sẻ cùng User.
        SharedPreferences sharedPreferences= this.getSharedPreferences("croplabSetting", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("area", loadArea);
        // Save.
        editor.apply();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check internet
        if (isNetworkConnected()) {
            Log.e("print", "You're connected to us");
            //load saved area
            loadAppSetting();
        } else{
            Log.e("print", "You're alone");
            showAlertDialog(4,"4");
        }


    }
    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_main);

        TextView showArea = findViewById(R.id.textView);
        showArea.setText(loadArea);

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
                Bundle bundle = new Bundle();
                bundle.putString("area",loadArea);
                intent1.putExtras(bundle);
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

        ImageButton buttonoption = findViewById(R.id.button_option);
        buttonoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("OptionList", "onClick: ");
                Intent intent3 = new Intent(MainActivity.this, OptionList.class);
                Bundle bundle = new Bundle();
                bundle.putString("area",loadArea);
                intent3.putExtras(bundle);
//                startActivity(intent3);
                startActivityForResult(intent3, REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Kiểm tra requestCode có trùng với REQUEST_CODE vừa dùng
        if(requestCode == REQUEST_CODE) {

            // resultCode được set bởi DetailActivity
            // RESULT_OK chỉ ra rằng kết quả này đã thành công
            if(resultCode == AppCompatActivity.RESULT_OK) {
                // Nhận dữ liệu từ Intent trả về
                loadArea = data.getStringExtra(OptionList.EXTRA_DATA);

            } else {
                // DetailActivity không thành công, không có data trả về.
            }
        }
    }

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;
    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            saveAppSetting();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Nhấn phím back lần nữa để thoát", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }
}

