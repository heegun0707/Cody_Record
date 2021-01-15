package com.example.final_project.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.final_project.MyDBHelper;
import com.example.final_project.R;
import com.example.final_project.WriteDBActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    Bitmap bitmap;
    TextView tv_name, tv_date, tv_diary, tv_empty;
    ImageView iv_image;
    CalendarView calendarView;
    MyDBHelper myDBHelper;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        tv_name = root.findViewById(R.id.tv_name);
        tv_date = root.findViewById(R.id.tv_date);
        tv_diary = root.findViewById(R.id.tv_diary);
        tv_empty = root.findViewById(R.id.tv_empty);
        iv_image = root.findViewById(R.id.iv_image);
        calendarView = root.findViewById(R.id.calendarView);

        myDBHelper = new MyDBHelper(getContext());
        SQLiteDatabase SQL_Calendar = myDBHelper.getReadableDatabase();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {

                Integer p_month = month + 1;
                String sDate, sMonth;
                if(Integer.toString(dayOfMonth).length()<2) {
                    sDate = "0" + dayOfMonth;
                }else{
                    sDate="" + dayOfMonth;
                }
                if(Integer.toString(p_month).length()<2) {
                    sMonth = "0" + p_month;
                }else {
                    sMonth = "" + p_month;
                }
                String current_date = year + "-" + sMonth + "-" + sDate;

                tv_diary.setVisibility(View.INVISIBLE);
                iv_image.setVisibility(View.INVISIBLE);
                tv_name.setVisibility(View.INVISIBLE);

                tv_date.setVisibility(View.VISIBLE);
                tv_empty.setVisibility(View.VISIBLE);
                tv_date.setText(current_date);
                tv_empty.setText("해당 요일에는 코디가 없습니다. \n코디를 추가해주세요." );


                Cursor cursor = SQL_Calendar.rawQuery("select name, strftime('%Y-%m-%d',date), prefer, diary, image " +
                        "from my_Cody  where date='" + current_date + "'", null);

                while (cursor.moveToNext()) {
                    if(cursor.getString(0) != null) {
                        tv_name.setVisibility(View.VISIBLE);
                        tv_date.setVisibility(View.VISIBLE);
                        tv_diary.setVisibility(View.VISIBLE);
                        iv_image.setVisibility(View.VISIBLE);
                        tv_empty.setVisibility(View.INVISIBLE);

                        tv_name.setText(cursor.getString(0));
                        tv_date.setText(cursor.getString(1));
                        tv_diary.setText(cursor.getString(3));
                        if (cursor.getBlob(4) != null) {
                            byte[] image = cursor.getBlob(4);
                            bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                        } else {
                            bitmap = null;
                        }
                        iv_image.setImageBitmap(bitmap);

                    }

                }
            }
        });

        return root;

    }

}
