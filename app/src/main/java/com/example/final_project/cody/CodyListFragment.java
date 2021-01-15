package com.example.final_project.cody;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.final_project.MyDBHelper;
import com.example.final_project.R;
import com.example.final_project.WriteDBActivity;
import com.example.final_project.home.ItemPopUpActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class
CodyListFragment extends Fragment {

    MyDBHelper myDBHelper;
    Button btn_filter;
    FloatingActionButton btn_insert;
    Intent intent;
    Bitmap bitmap;
    private ListView listView;
    private CodyListViewAdapter listViewAdapter;
    ArrayList<CodyListViewItem> items;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cody_listview, container, false);

        btn_filter = root.findViewById(R.id.btn_filter);
        btn_insert = root.findViewById(R.id.btn_insert);

        listViewAdapter = new CodyListViewAdapter(getContext());
        listView = root.findViewById(R.id.listview);
        listView.setAdapter(listViewAdapter);

        //DB
        myDBHelper = new MyDBHelper(getContext());
        SQLiteDatabase inboDB = myDBHelper.getReadableDatabase();

        Cursor cursor = inboDB.rawQuery("select name, strftime('%Y-%m-%d',date), prefer, diary, image from my_Cody Order By date desc", null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String date = cursor.getString(1);
            Integer prefer = cursor.getInt(2);
            String diary = cursor.getString(3);
            if (cursor.getBlob(4) != null) {
                byte[] image = cursor.getBlob(4);
                bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                Log.e("호오오오옴", bitmap.toString());
            } else {
                bitmap = null;
            }
            listViewAdapter.addItem(name, date, prefer, diary, bitmap);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int i, long l) {

                CodyListViewItem item = (CodyListViewItem) adapterView.getItemAtPosition(i);

                String L_name = item.getName();
                String L_date = item.getDate();
                String L_diary = item.getDiary();
                Bitmap L_image = item.getImage();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                L_image.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                //byte[] byte_image = stream.toByteArray();

                Intent intent = new Intent(getContext(), ItemPopUpActivity.class);

                intent.putExtra("name", L_name);
                intent.putExtra("date", L_date);
                intent.putExtra("diary", L_diary);
                intent.putExtra("image", stream.toByteArray());

                startActivity(intent);
            }
        });
        //db닫기
        inboDB.close();

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Btn_Click(view);
            }
        });

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), WriteDBActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    public void Btn_Click(View view){
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        MenuInflater inf = popupMenu.getMenuInflater();
        inf.inflate(R.menu.filter_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_name:
                        btn_filter.setText("제목순");
                        OrderbyName();
                        break;

                    case R.id.item_prefer:
                        btn_filter.setText("선호도순");
                        OrderbyPrefer();
                        break;

                    case R.id.item_date:
                        btn_filter.setText("날짜순");
                        OrderbyDate();
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    // 제목순
    public void OrderbyName(){
        SQLiteDatabase Order_name = myDBHelper.getReadableDatabase();
        Cursor cursor = Order_name.rawQuery("select name, strftime('%Y-%m-%d',date), prefer, diary, image from my_Cody Order By name", null);

        items = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String date = cursor.getString(1);
            Integer prefer = cursor.getInt(2);
            String diary = cursor.getString(3);
            if (cursor.getBlob(4) != null) {
                byte[] image = cursor.getBlob(4);
                bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                Log.e("제목순", bitmap.toString());
            } else {
                bitmap = null;
            }
            CodyListViewItem item = new CodyListViewItem(name, date, prefer, diary, bitmap); // item class 생성
            items.add(item); // 리스트뷰아이템 ArrayList에 추가
        }
        listViewAdapter.updateItem(items); // 새로운 아이템으로 리스트뷰 업데이트
    }

    // 선호도순 정렬
    public void OrderbyPrefer(){
        SQLiteDatabase Order_prefer = myDBHelper.getReadableDatabase();
        Cursor cursor = Order_prefer.rawQuery("select name, strftime('%Y-%m-%d',date), prefer, diary, image from my_Cody Order By prefer desc", null);

        items = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String date = cursor.getString(1);
            Integer prefer = cursor.getInt(2);
            String diary = cursor.getString(3);
            if (cursor.getBlob(4) != null) {
                byte[] image = cursor.getBlob(4);
                bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                Log.e("호오오오옴", bitmap.toString());
            } else {
                bitmap = null;
            }
            CodyListViewItem item = new CodyListViewItem(name, date, prefer, diary, bitmap); // item class 생성
            items.add(item); // 리스트뷰아이템 ArrayList에 추가
        }
        listViewAdapter.updateItem(items); // 새로운 아이템으로 리스트뷰 업데이트
    }

    // 날짜순 정렬
    public void OrderbyDate(){
        SQLiteDatabase Order_date = myDBHelper.getReadableDatabase();
        Cursor cursor = Order_date.rawQuery("select name, strftime('%Y-%m-%d',date), prefer, diary, image from my_Cody Order By date desc", null);

        items = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String date = cursor.getString(1);
            Integer prefer = cursor.getInt(2);
            String diary = cursor.getString(3);
            if (cursor.getBlob(4) != null) {
                byte[] image = cursor.getBlob(4);
                bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                Log.e("호오오오옴", bitmap.toString());
            } else {
                bitmap = null;
            }
            CodyListViewItem item = new CodyListViewItem(name, date, prefer, diary, bitmap); // item class 생성
            items.add(item); // 리스트뷰아이템 ArrayList에 추가
        }
        listViewAdapter.updateItem(items); // 새로운 아이템으로 리스트뷰 업데이트
    }
}
