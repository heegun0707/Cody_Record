package com.example.final_project.cody;

import android.graphics.Bitmap;

public class CodyListViewItem {

    private String name;
    private String date;
    private Integer prefer;
    private String diary;
    private Bitmap image;

    public CodyListViewItem(){


    }

    public CodyListViewItem(String name, String date, Integer prefer, String diary, Bitmap image) {
        this.name = name;
        this.date = date;
        this.prefer = prefer;
        this.diary = diary;
        this.image = image;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setDate(String date){
        this.date = date;
    }
    public void setPrefer(Integer prefer){
        this.prefer = prefer;
    }
    public void setDiary(String diary){
        this.diary = diary;
    }
    public void setImage(Bitmap image){
        this.image = image;
    }


    public String getName(){
        return name;
    }
    public String getDate(){
        return date;
    }
    public Integer getPrefer(){
        return prefer;
    }
    public String getDiary(){
        return diary;
    }
    public Bitmap getImage(){
        return image;
    }
}
