package com.udinus.aplikasimobile.repository.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.udinus.aplikasimobile.repository.model.Barang;

import java.util.ArrayList;

public class BarangDao {
    public static final String TABLE_NAME = "barang";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SATUAN = "satuan";
    public static final String COLUMN_PRICE = "price";
    public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_CODE + " TEXT PRIMARY KEY NOT NULL, " + COLUMN_NAME + " TEXT, " + COLUMN_SATUAN + " TEXT, " + COLUMN_PRICE + " REAL" + ")";
    public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private final SQLiteDatabase database;


    public BarangDao(SQLiteDatabase database) {
        this.database = database;
    }

    public static void createTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY);
    }

    public static void dropTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY);
    }

    public long insert(Barang barang) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CODE, barang.getCode());
        contentValues.put(COLUMN_NAME, barang.getName());
        contentValues.put(COLUMN_SATUAN, barang.getSatuan());
        contentValues.put(COLUMN_PRICE, barang.getPrice());
        return database.insert(TABLE_NAME, null, contentValues);
    }

    public int update(Barang barang) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, barang.getName());
        contentValues.put(COLUMN_SATUAN, barang.getSatuan());
        contentValues.put(COLUMN_PRICE, barang.getPrice());

        String whereClause = COLUMN_CODE + " = ?";
        String[] whereArgs = {String.valueOf(barang.getCode())};
        return database.update(TABLE_NAME, contentValues, whereClause, whereArgs);
    }

    public int delete(String nim) {
        String whereClause = COLUMN_CODE + " = ?";
        String[] whereArgs = {String.valueOf(nim)};
        return database.delete(TABLE_NAME, whereClause, whereArgs);
    }

    public ArrayList<Barang> getAll() {
        ArrayList<Barang> barangArrayList = new ArrayList<>();
        String[] columns = {COLUMN_CODE, COLUMN_NAME, COLUMN_SATUAN, COLUMN_PRICE};
        Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Barang barang = new Barang();
            barang.setCode(cursor.getString(0));
            barang.setName(cursor.getString(1));
            barang.setSatuan(cursor.getString(2));
            barang.setPrice(cursor.getDouble(3));
            barangArrayList.add(barang);
        }
        cursor.close();
        return barangArrayList;
    }

    public Barang findBarangByCode(String code) {
        String[] columns = {COLUMN_CODE, COLUMN_NAME, COLUMN_SATUAN, COLUMN_PRICE};
        String selection = COLUMN_CODE + " = ?";
        String[] selectionArgs = {code};

        Cursor cursor = database.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        Barang barang = null;
        if (cursor.moveToFirst()) {
            barang = new Barang();
            barang.setCode(cursor.getString(0));
            barang.setName(cursor.getString(1));
            barang.setSatuan(cursor.getString(2));
            barang.setPrice(cursor.getDouble(3));
        }

        cursor.close();
        return barang;
    }
}
