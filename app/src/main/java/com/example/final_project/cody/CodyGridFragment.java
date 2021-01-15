package com.example.final_project.cody;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.final_project.MyDBHelper;
import com.example.final_project.R;

public class CodyGridFragment extends Fragment {

    MyDBHelper myDBHelper;
    Bitmap bitmap;
    private GridView gridView;
    private CodyGridViewAdapter gridViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cody_gridview, container, false);

        gridView = root.findViewById(R.id.gridview);
        gridViewAdapter = new CodyGridViewAdapter(getContext());

        myDBHelper = new MyDBHelper(getContext());
        SQLiteDatabase inboDB = myDBHelper.getReadableDatabase();

        Cursor cursor = inboDB.rawQuery("select image from my_Cody", null);

        while (cursor.moveToNext()) {
            if (cursor.getBlob(0) != null) {
                byte[] image = cursor.getBlob(0);
                bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                gridViewAdapter.addItem(bitmap);
                gridView.setAdapter(gridViewAdapter);
            }
        }

        inboDB.close();

        return root;
    }
}