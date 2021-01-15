package com.example.final_project.cody;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project.MyDBHelper;
import com.example.final_project.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class    CodyListViewAdapter extends BaseAdapter {

    private ArrayList<CodyListViewItem> codyListViewItems = new ArrayList<>();
    ArrayList<CodyListViewItem> items;
    ImageView iv_image, iv_menu;
    TextView tv_name, tv_date, tv_diary, tv_prefer;
    Context context;
    Bitmap bitmap;
    MyDBHelper helper;

    MyDBHelper myDBHelper;
    SQLiteDatabase db;

    CodyListViewAdapter(Context context){

    }

    @Override
    public int getCount() {
        return codyListViewItems.size();
    }

    @Override
    public Object getItem(int i) {
        return codyListViewItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Context context = viewGroup.getContext();
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cody_listview_item,viewGroup,false);
        }

        CodyListViewItem codyListViewItem = codyListViewItems.get(i);

        iv_image = view.findViewById(R.id.iv_image);
        iv_menu = view.findViewById(R.id.iv_menu);
        tv_name = view.findViewById(R.id.tv_name);
        tv_prefer = view.findViewById(R.id.tv_prefer);
        tv_date = view.findViewById(R.id.tv_date);
        tv_diary = view.findViewById(R.id.tv_diary);

        tv_name.setText(codyListViewItem.getName());
        tv_diary.setText(codyListViewItem.getDiary());
        tv_prefer.setText("(" + codyListViewItem.getPrefer().toString() + ")");
        tv_date.setText(codyListViewItem.getDate());
        iv_image.setImageBitmap(codyListViewItem.getImage());

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                codyListViewItem.getImage().compress(Bitmap.CompressFormat.JPEG, 50, stream);

                PopupMenu popupMenu = new PopupMenu(context, view);
                MenuInflater inf = popupMenu.getMenuInflater();
                inf.inflate(R.menu.up_de_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_update:
                                Intent intent = new Intent(context, CodyUpdateActivity.class);
                                intent.putExtra("setname",codyListViewItem.getName());
                                intent.putExtra("setdiary",codyListViewItem.getDiary());
                                intent.putExtra("setprefer",codyListViewItem.getPrefer());
                                intent.putExtra("setpos",i);
                                intent.putExtra("setdate",codyListViewItem.getDate());
                                //intent.putExtra("setimage",codyListViewItem.getImage());
                                intent.putExtra("setimage", stream.toByteArray());

                                context.startActivity(intent);
                                break;

                            case R.id.item_delete:
                                helper   = new MyDBHelper(context);
                                db = helper.getWritableDatabase();

                                String nameArr[] = {codyListViewItem.getName()};
                                db.delete("my_Cody", "NAME = ?", nameArr);

                                SQLiteDatabase Order_prefer = helper.getReadableDatabase();
                                Cursor cursor = Order_prefer.rawQuery("select name, strftime('%Y-%m-%d',date), prefer, diary, image from my_Cody Order By date desc", null);

                                items = new ArrayList<>();
                                while (cursor.moveToNext()) {
                                    String name = cursor.getString(0);
                                    String date = cursor.getString(1);
                                    Integer prefer = cursor.getInt(2);
                                    String diary = cursor.getString(3);
                                    if (cursor.getBlob(4) != null) {
                                        byte[] image = cursor.getBlob(4);
                                        bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                                    } else {
                                        bitmap = null;
                                    }
                                    CodyListViewItem item = new CodyListViewItem(name, date, prefer, diary, bitmap); // item class 생성
                                    items.add(item); // 리스트뷰아이템 ArrayList에 추가
                                }
                                updateItem(items);
                                break;

                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        return view;
    }

    public void updateItem(ArrayList<CodyListViewItem> items){
        codyListViewItems = items; // 새로운 아이템으로 변경
        this.notifyDataSetChanged(); // 리스트뷰 새로고침
    }


    public void addItem(String name, String date, Integer prefer, String diary, Bitmap image) {
        CodyListViewItem item = new CodyListViewItem();

        item.setName(name);
        item.setDate(date);
        item.setPrefer(prefer);
        item.setDiary(diary);
        item.setImage(image);

        codyListViewItems.add(item);
    }


}
