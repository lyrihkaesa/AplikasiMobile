package com.udinus.aplikasimobile.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.udinus.aplikasimobile.database.model.User;

import java.util.ArrayList;

public class UserDao {
    public static final String TABLE_NAME = "user";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NIM = "nim";

    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_USERNAME + " TEXT PRIMARY KEY NOT NULL, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_NIM + " TEXT" +
                    ")";

    public static final String DROP_TABLE_QUERY =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    private final SQLiteDatabase database;

    public UserDao(SQLiteDatabase database) {
        this.database = database;
    }

    public static void createTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY);
    }

    public static void dropTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY);
    }

    public long insert(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, user.getUsername());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
        contentValues.put(COLUMN_NIM, user.getNim());
        return database.insert(TABLE_NAME, null, contentValues);
    }

    public int update(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
        contentValues.put(COLUMN_NIM, user.getNim());
        String whereClause = COLUMN_USERNAME + " = ?";
        String[] whereArgs = { String.valueOf(user.getUsername()) };
        return database.update(TABLE_NAME, contentValues, whereClause, whereArgs);
    }

    public int delete(String username) {
        String whereClause = COLUMN_USERNAME + " = ?";
        String[] whereArgs = { String.valueOf(username) };
        return database.delete(TABLE_NAME, whereClause, whereArgs);
    }

    public boolean isValid(String username, String password){
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE username = '" + username + "' AND password = '" + password + "'", null);
        if(cursor.moveToNext()) {
            return true;
        }
        cursor.close();
        return false;
    }

    public ArrayList<User> getAll() {
        ArrayList<User> userArrayList = new ArrayList<>();
        String[] columns = {COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_NIM};
        Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            User user = new User();
            user.setUsername(cursor.getString(0));
            user.setPassword(cursor.getString(1));
            user.setNim(cursor.getString(2));
            userArrayList.add(user);
        }
        cursor.close();
        return userArrayList;
    }

    public User getUserByNim(String nimOrUsername) {
        String[] columns = {COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_NIM};
        String selection = COLUMN_NIM + " = ? OR " + COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {nimOrUsername, nimOrUsername};

        Cursor cursor = database.query(TABLE_NAME, columns, selection, selectionArgs,
                null, null, null);

        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setUsername(cursor.getString(0));
            user.setPassword(cursor.getString(1));
            user.setNim(cursor.getString(2));
        }

        cursor.close();
        return user;
    }

}
