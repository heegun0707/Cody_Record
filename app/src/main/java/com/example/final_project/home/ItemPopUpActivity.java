package com.example.final_project.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.final_project.R;

public class ItemPopUpActivity extends Activity {

    TextView tv_name, tv_date, tv_diary;
    ImageView iv_image, iv_cancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //UI 객체생성
        setContentView(R.layout.listview_popup);

        tv_name = findViewById(R.id.tv_name);
        tv_date = findViewById(R.id.tv_date);
        tv_diary = findViewById(R.id.tv_diary);
        iv_cancle = findViewById(R.id.iv_cancle);
        iv_image = findViewById(R.id.iv_image);

        Intent intent = getIntent();

        String L_name = intent.getStringExtra("name");
        String L_date = intent.getStringExtra("date");
        String L_diary = intent.getStringExtra("diary");
        byte[] L_image = intent.getByteArrayExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(L_image,0, L_image.length);


        //Bitmap bitmap_image = BitmapFactory.decodeByteArray(L_image, 0, L_image.length);

        tv_name.setText(L_name);
        tv_date.setText(L_date);
        tv_diary.setText(L_diary);
        iv_image.setImageBitmap(bitmap);

        iv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

}
