package com.example.final_project.cody;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.final_project.MyDBHelper;
import com.example.final_project.R;
import com.google.android.material.tabs.TabLayout;

public class  CodyFragment extends Fragment {

    MyDBHelper myDBHelper;
    Bitmap bitmap;
    private GridView gridView;
    private CodyGridViewAdapter gridViewAdapter;

    ViewPager vp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_cody, container, false);

        vp = root.findViewById(R.id.view_pager);
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getChildFragmentManager());
        vp.setAdapter(tabPagerAdapter);
        vp.setOffscreenPageLimit(2);
        vp.setSaveEnabled(false);

        TabLayout tabLayout = root.findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#1D1D1D"));
        tabLayout.addTab(tabLayout.newTab().setText("리스트 목록"),0,true);
        tabLayout.addTab(tabLayout.newTab().setText("코디 목록"),1);

        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        /*

        gridView = root.findViewById(R.id.gridview);
        gridViewAdapter = new CodyGridViewAdapter(getContext());

        myDBHelper = new MyDBHelper(getContext());
        SQLiteDatabase inboDB = myDBHelper.getReadableDatabase();

        Cursor cursor = inboDB.rawQuery("select image from myCody", null);

        while (cursor.moveToNext()){
            if (cursor.getBlob(0) != null) {
                byte[] image = cursor.getBlob(0);
                bitmap = BitmapFactory.decodeByteArray(image,0, image.length);
                gridViewAdapter.addItem(bitmap);
                gridView.setAdapter(gridViewAdapter);
            }
        }

        inboDB.close();
*/

        return root;
    }
}
