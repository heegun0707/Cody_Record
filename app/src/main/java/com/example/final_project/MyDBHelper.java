package com.example.final_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION=5;

    public MyDBHelper(@Nullable Context context) {
        super(context, "final_project", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL = "create table my_Cody(" +
                "_id integer primary key autoincrement," +
                "image blob,"+
                "name text," +
                "date datetime default CURRENT_TIMESTAMP," +
                "prefer integer,"+
                "diary text)";
        sqLiteDatabase.execSQL(SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i1 == DATABASE_VERSION){
            sqLiteDatabase.execSQL("drop table my_Cody");
            onCreate(sqLiteDatabase);
        }
    }
}