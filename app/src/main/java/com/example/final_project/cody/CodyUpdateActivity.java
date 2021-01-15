package com.example.final_project.cody;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project.MainActivity;
import com.example.final_project.MyDBHelper;
import com.example.final_project.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;

public class CodyUpdateActivity extends Activity {

    EditText et_name, et_date, et_diary;
    ImageView iv_image;
    Button btn_insert;
    DatePicker datePicker;
    RatingBar rating_prefer;
    SQLiteDatabase db;
    Intent intent;

    Bitmap bitmap;
    Bitmap bitmap1;
    String sDate, sMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cody_update);

        et_name = findViewById(R.id.et_name);
        //et_date = findViewById(R.id.et_date);
        et_diary = findViewById(R.id.et_diary);
        iv_image = findViewById(R.id.iv_image);
        rating_prefer = findViewById(R.id.rating_prefer);

        btn_insert = findViewById(R.id.btn_insert);

        datePicker = findViewById(R.id.datePicker);

        MyDBHelper helper = new MyDBHelper(this);

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

        intent = getIntent();
        String getname = intent.getExtras().getString("setname");
        String getdiary = intent.getExtras().getString("setdiary");
        Integer getprefer = intent.getExtras().getInt("setprefer");
        String getdate = intent.getExtras().getString("setdate");
        //Integer pos = intent.getExtras().getInt("setpos");
        byte[] L_image = intent.getExtras().getByteArray("setimage");
        bitmap = BitmapFactory.decodeByteArray(L_image,0, L_image.length);

        SQLiteDatabase setting = helper.getReadableDatabase();
        Cursor cursor = setting.rawQuery("select name, strftime('%Y-%m-%d',date), prefer, diary, image from my_Cody where date='" + getdate + "'", null);

        while (cursor.moveToNext()) {
            if (cursor.getString(0) != null) {

                et_name.setText(cursor.getString(0));

                rating_prefer.setRating(getprefer);
                et_diary.setText(cursor.getString(3));
                if (cursor.getBlob(4) != null) {
                    byte[] image = cursor.getBlob(4);
                    bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                } else {
                    bitmap = null;
                }
                iv_image.setImageBitmap(bitmap);

            }
        }

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDBHelper helper = new MyDBHelper(view.getContext());

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream);

                byte[] iv_data = stream.toByteArray();
                String prefer = String.valueOf(rating_prefer.getRating());
                String name = et_name.getText().toString();
                String date = datePicker.getYear() + "-" + sMonth + "-" + sDate;
                String diary = et_diary.getText().toString();

                db = helper.getWritableDatabase();

                String updateSQL = "UPDATE my_Cody SET name =" + "'" + name + "', date =" + "'" + date + "', prefer ="
                        + "'" + prefer + "', diary =" + "'" + diary + "' WHERE date =" + "'" + getdate + "'";

                Log.e("업데이트", updateSQL);
                db.execSQL(updateSQL);

                /*
                ContentValues values = new ContentValues();

                values.put("name", name);
                values.put("date", date);
                values.put("prefer", prefer);
                values.put("diary", diary);
                values.put("image", iv_data);

                String nameArr[] = {name};

                db.update("my_Cody", values, "name = ?", nameArr);*/

                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "코디 내용이 수정되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        Log.e("이름", getname);
        Log.e("일기", getdiary);
        Log.e("선호도", getprefer.toString());
        Log.e("날짜", getdate);
        Log.e("이미지", bitmap.toString());
    }

}
