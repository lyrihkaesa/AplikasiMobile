package com.udinus.aplikasimobile.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.udinus.aplikasimobile.database.model.Mahasiswa;

import java.util.ArrayList;

public class MahasiswaDao {
    public static final String TABLE_NAME = "mhs";
    public static final String COLUMN_NIM = "nim";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PRODI = "prodi";
    public static final String COLUMN_DEGREE = "degre";
    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NIM + " TEXT PRIMARY KEY NOT NULL, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_PRODI + " TEXT, " +
                    COLUMN_DEGREE + " TEXT" +
                    ")";
    public static final String DROP_TABLE_QUERY =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    private final SQLiteDatabase database;

    public MahasiswaDao(SQLiteDatabase database) {
        this.database = database;
    }

    public static void createTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY);
    }
    public static void dropTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY);
    }

    public long insert(Mahasiswa mahasiswa) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NIM, mahasiswa.getNim());
        contentValues.put(COLUMN_NAME, mahasiswa.getName());
        contentValues.put(COLUMN_EMAIL, mahasiswa.getEmail());
        contentValues.put(COLUMN_PRODI, mahasiswa.getMajor());
        contentValues.put(COLUMN_DEGREE, mahasiswa.getDegree());
        return database.insert(TABLE_NAME, null, contentValues);
    }

    public int update(Mahasiswa mahasiswa) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, mahasiswa.getName());
        contentValues.put(COLUMN_EMAIL, mahasiswa.getEmail());
        contentValues.put(COLUMN_PRODI, mahasiswa.getMajor());
        contentValues.put(COLUMN_DEGREE, mahasiswa.getDegree());

        String whereClause = COLUMN_NIM + " = ?";
        String[] whereArgs = {String.valueOf(mahasiswa.getNim())};
        return database.update(TABLE_NAME, contentValues, whereClause, whereArgs);
    }

    public int delete(String nim) {
        String whereClause = COLUMN_NIM + " = ?";
        String[] whereArgs = {String.valueOf(nim)};
        return database.delete(TABLE_NAME, whereClause, whereArgs);
    }

    public ArrayList<Mahasiswa> getAll() {
        ArrayList<Mahasiswa> mahasiswaArrayList = new ArrayList<>();
        String[] columns = {COLUMN_NIM, COLUMN_NAME, COLUMN_EMAIL, COLUMN_PRODI, COLUMN_DEGREE};
        Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Mahasiswa mahasiswa = new Mahasiswa();
            mahasiswa.setNim(cursor.getString(0));
            mahasiswa.setName(cursor.getString(1));
            mahasiswa.setEmail(cursor.getString(2));
            mahasiswa.setMajor(cursor.getString(3));
            mahasiswa.setDegree(cursor.getString(4));
            mahasiswaArrayList.add(mahasiswa);
        }
        cursor.close();
        return mahasiswaArrayList;
    }

    public Mahasiswa findMahasiswaByNim(String nim) {
        String[] columns = {COLUMN_NIM, COLUMN_NAME, COLUMN_EMAIL, COLUMN_PRODI, COLUMN_DEGREE};
        String selection = COLUMN_NIM + " = ?";
        String[] selectionArgs = {nim};

        Cursor cursor = database.query(TABLE_NAME, columns, selection, selectionArgs,
                null, null, null);

        Mahasiswa mahasiswa = null;
        if (cursor.moveToFirst()) {
            mahasiswa = new Mahasiswa();
            mahasiswa.setNim(cursor.getString(0));
            mahasiswa.setName(cursor.getString(1));
            mahasiswa.setEmail(cursor.getString(2));
            mahasiswa.setMajor(cursor.getString(3));
            mahasiswa.setDegree(cursor.getString(4));
        }

        cursor.close();
        return mahasiswa;
    }

}
