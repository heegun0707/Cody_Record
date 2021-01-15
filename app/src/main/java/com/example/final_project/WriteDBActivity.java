package com.example.final_project;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class WriteDBActivity extends AppCompatActivity {

    EditText et_name, et_date, et_diary;
    ImageView iv_image;
    Button btn_insert;
    Button btn_test;
    Calendar calendar;
    DatePicker datePicker;
    RatingBar rating_prefer;
    SQLiteDatabase db;
    Intent intent;

    String sDate, sMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_db);

        et_name = findViewById(R.id.et_name);
        //et_date = findViewById(R.id.et_date);
        et_diary = findViewById(R.id.et_diary);
        iv_image = findViewById(R.id.iv_image);
        rating_prefer = findViewById(R.id.rating_prefer);

        btn_insert = findViewById(R.id.btn_insert);

        datePicker = findViewById(R.id.datePicker);

        MyDBHelper helper = new MyDBHelper(this);
        db = helper.getWritableDatabase();

        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });

        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                Integer p_month = datePicker.getMonth() + 1;

                if(Integer.toString(datePicker.getDayOfMonth()).length()<2) {
                    sDate = "0" + datePicker.getDayOfMonth();
                }else{
                    sDate="" + datePicker.getDayOfMonth();
                }
                if(Integer.toString(p_month).length()<2) {
                    sMonth = "0" + p_month;
                }else {
                    sMonth = "" + p_month;
                }
            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    iv_image.setImageBitmap(img);
                    Log.e("롸롸이트", img.toString());
                    btn_insert.setOnClickListener(view -> {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        img.compress(Bitmap.CompressFormat.JPEG, 20, stream);
                        byte[] iv_data = stream.toByteArray();

                        String prefer = String.valueOf(rating_prefer.getRating());
                        String name = et_name.getText().toString();
                        String date = datePicker.getYear() + "-" + sMonth + "-" + sDate;
                        String diary = et_diary.getText().toString();

                        ContentValues values = new ContentValues();
                        values.put("image", iv_data);
                        values.put("name", name);
                        values.put("date", date);
                        values.put("prefer", prefer);
                        values.put("diary", diary);

                        db.insert("my_Cody", null, values);

                        db.close();

                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "새로운 코디 내용이 추가되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }
}