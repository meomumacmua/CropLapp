package com.example.croplapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrackingActivity extends AppCompatActivity {

    String TAG = "FIREBASE";
    String area = "hanoi";
    /*
     * 0 = khởi tạo
     * 1 = không hợp lệ
     * 2 = không tìm thấy
     * 3 = đã nhận
     * 4 = đang xử lý
     * 5 = đã hoàn thành
     * 6 = lỗi CSDL
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking_layout);

//        initDatabase();
        if(initDatabase(area) == 6){
            showAlertDialog(6,"Error");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.tracking_layout);

        ImageButton button1 = findViewById(R.id.seachButton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(view);
                EditText inputText = findViewById(R.id.editText);
                final String text = inputText.getText().toString();
                char c = text.charAt(0);

                if ((text.trim().length() <= 4)  || (Character.isDigit(c))) {
                    showAlertDialog(1, text);
                } else{
                    int temp = getDatabase(area,text);
                    //showAlertDialog(temp, text);
                }
            }
        });
    }

    public void hideKeyboard(View view) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch(Exception ignored) {
        }
    }

    public void showAlertDialog(int response ,String code) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("CropLab Thông báo!!!");
        Log.d("Printf", "catchalert " + response);

        switch (response) {
            case 1: {
                builder.setMessage("Mã hóa đơn '" + code + "' không hợp lệ! \nMời nhập lại chính xác mã hoá đơn!!!");
                break;
            }
            case 2: {
                builder.setMessage("Mã hóa đơn '" + code + "' không tìm thấy, vui lòng kiểm tra và nhập lại chính xác mã hoá đơn!");
                break;
            }
            case 3: {
                builder.setMessage("Mã hóa đơn '" + code + "' của bạn đang trong hàng chờ để được xử lý, cứ ngồi im rồi film sẽ được tráng! ♥  ");
                break;
            }
            case 4: {
                builder.setMessage("Mã hóa đơn '" + code + "' : film của bạn đang được xử lý, bạn cứ bình tĩnh rồi hình sẽ tới nhé! ♥  ");
                break;
            }
            case 5: {
                builder.setMessage("Mã hóa đơn '" + code + "' : film của bạn đã hoàn thành, bạn có thể tới lấy lại âm bản trong vòng 3 tháng nhé! ♥ ");
                break;
            }
            case 6: {
                builder.setMessage("Gặp sự cố khi kết nối đến cơ sở dữ liệu \nBạn vui lòng quay lại sau!!!");
                break;
            }
        }

        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public int getDatabase(String areaCode, final String compareText) {

        //lấy đối tượng FempStringirebaseDatabase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Kết nối tới node có tên là contacts (node này do ta định nghĩa trong CSDL Firebase)
        DatabaseReference myRef = database.getReference(areaCode);
        //truy suất và lắng nghe sự thay đổi dữ liệu
        myRef.addValueEventListener(new ValueEventListener() {
            int temp = 2;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //vòng lặp để lấy dữ liệu khi có sự thay đổi trên Firebase
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    //lấy key của dữ liệu
                    //String key = data.getKey();

                        String value = data.getValue().toString();

                        if (value.contains(compareText)) {

                            temp = 3;           //Đã nhận
                            Log.d("Printf", value);
                            if (value.contains("...")) {
                                temp = 4;       // Đang xử lý
                                if (value.contains("OK")) {
                                    temp = 5;       //Đã hoàn thành
                                    Log.d("Printf", "catch1 " + temp);
                                }
                            }
                        }
                    }
                    showAlertDialog(temp, compareText);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                showAlertDialog(6,"Error!!!");
            }
        });
        return 0;
    }

    public int initDatabase(String areaCode) {
        //lấy đối tượng FirebaseDatabase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Kết nối tới node có tên là contacts (node này do ta định nghĩa trong CSDL Firebase)
        DatabaseReference myRef = database.getReference(areaCode);
        //truy suất và lắng nghe sự thay đổi dữ liệu
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //vòng lặp để lấy dữ liệu khi có sự thay đổi trên Firebase
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                showAlertDialog(6, "Error");
            }
        });
        return 0;
    }
}