package com.example.croplapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrackingActivity extends AppCompatActivity {

    String TAG="FIREBASE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking_layout);

        ImageButton button1 = findViewById(R.id.seachButton);
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                EditText inputText = findViewById(R.id.editText);
                final String text = inputText.getText().toString();

                if(text.trim().length() <= 4)
                {
                    showAlertDialog(4, text);
                }
                else {
                    Log.d("Printf", "Start loging");
                    //lấy đối tượng FirebaseDatabase
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    //Kết nối tới node có tên là contacts (node này do ta định nghĩa trong CSDL Firebase)
                    DatabaseReference myRef = database.getReference("contacts");
                    //truy suất và lắng nghe sự thay đổi dữ liệu
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //adapter.clear();
                            Boolean checkExist = false;
                            //vòng lặp để lấy dữ liệu khi có sự thay đổi trên Firebase
                            Log.d("Printf", "Start for loop");
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                //lấy key của dữ liệu
                                //String key = data.getKey();
                                //Log.d("Printf", key + 1);
                                //if(key.compareTo("0") == 0) {
                                //Log.d("Printf", key + " #");
                                //lấy giá trị của key (nội dung)
                                String value = data.getValue().toString();
                                if (value.contains(text)) {
                                    Log.d("Printf", text);
                                    checkExist = true;
                                    //Log.d("Printf", text);
                                    //adapter.add(key + "\n" + value);
                                    if (value.contains("OK")) {
                                        //Log.d("Printf", "OK");
                                        showAlertDialog(1, text);
                                    } else {
                                        //                                  Log.d("Printf", "Đang xử lý");
                                        showAlertDialog(2, text);
                                    }
                                    break;
                                }
                            }
                            Log.d("Printf", "end for loop");
                            if(checkExist == false) showAlertDialog(3,text);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        }
                    });
                    Log.d("Printf", "End loging");

                }
            }
        });
    }

    public void showAlertDialog(int kindofAlert, String code){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Croblap");
        if (kindofAlert == 1) builder.setMessage("Mã hóa đơn '" + code + "' đã được hoàn thành, bạn có thể đến lấy bất kỳ lúc nào!");
        if (kindofAlert == 2) builder.setMessage("Mã hóa đơn '" + code + "' đang được thực hiện, bạn vui lòng đợi chúng mình hoàn thành nhé!");
        if (kindofAlert == 3) builder.setMessage("Mã hóa đơn '" + code + "' không tìm thấy, vui lòng kiểm tra lại mã và thực hiện lại!");
        if (kindofAlert == 4) builder.setMessage("Mã hóa đơn '"   + code + "' không hợp lệ! \nMời nhập lại");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
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

}
