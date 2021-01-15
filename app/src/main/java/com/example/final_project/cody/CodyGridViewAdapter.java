package com.example.final_project.cody;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.final_project.R;

import java.util.ArrayList;

public class CodyGridViewAdapter extends BaseAdapter {

    private ArrayList<CodyGridViewItem> codyGridViewItems = new ArrayList<>();
    ImageView iv_image;

    public CodyGridViewAdapter(Context context){

    }
    @Override
    public int getCount() {
        return codyGridViewItems.size();
    }

    @Override
    public Object getItem(int i) {
        return codyGridViewItems.get(i);
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
            view = inflater.inflate(R.layout.cody_gridview_item,viewGroup,false);
        }

        CodyGridViewItem gridViewItem = codyGridViewItems.get(i);
        iv_image = view.findViewById(R.id.iv_image);

        iv_image.setImageBitmap(gridViewItem.getImage());

        return view;
    }

    public void addItem(Bitmap image) {
        CodyGridViewItem item = new CodyGridViewItem();

        item.setImage(image);

        codyGridViewItems.add(item);
    }
}
