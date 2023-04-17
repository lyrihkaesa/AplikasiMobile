package com.udinus.aplikasimobile.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.udinus.aplikasimobile.database.dao.KhsDao;
import com.udinus.aplikasimobile.database.dao.UserDao;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        KhsDao.createTable(sqLiteDatabase);
        UserDao.createTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        KhsDao.dropTable(sqLiteDatabase);
        UserDao.dropTable(sqLiteDatabase);
        onCreate(sqLiteDatabase);
    }
}
