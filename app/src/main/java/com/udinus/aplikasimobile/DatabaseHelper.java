package com.udinus.aplikasimobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.udinus.aplikasimobile.Khs;
import com.udinus.aplikasimobile.Mahasiswa;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_database.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME_KHS = "khs";
    public static final String COLUMN_CODE_MATKUL = "code_matkul";
    public static final String COLUMN_NAME_MATKUL = "name_matkul";
    public static final String COLUMN_SKS = "sks";
    public static final String COLUMN_GRADES = "grades";
    public static final String COLUMN_LETTER_GRADES = "letter_grades";
    public static final String COLUMN_PREDICATE = "predicate";
    public static final String CREATE_TABLE_KHS_QUERY =
            "CREATE TABLE " + TABLE_NAME_KHS + " (" +
                    COLUMN_CODE_MATKUL + " TEXT PRIMARY KEY NOT NULL, " +
                    COLUMN_NAME_MATKUL + " TEXT, " +
                    COLUMN_SKS + " INTEGER, " +
                    COLUMN_GRADES + " REAL, " +
                    COLUMN_LETTER_GRADES + " TEXT, " +
                    COLUMN_PREDICATE + " TEXT" +
                    ")";
    public static final String DROP_TABLE_KHS_QUERY =
            "DROP TABLE IF EXISTS " + TABLE_NAME_KHS;
    public static final String TABLE_NAME_MHS = "mhs";
    public static final String COLUMN_NIM = "nim";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PASSWORD = "email";
    public static final String CREATE_TABLE_MHS_QUERY =
            "CREATE TABLE " + TABLE_NAME_MHS + " (" +
                    COLUMN_NIM + " TEXT PRIMARY KEY NOT NULL, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT " +
                    ")";
    public static final String DROP_TABLE_MHS_QUERY =
            "DROP TABLE IF EXISTS " + TABLE_NAME_MHS;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_KHS_QUERY);
        sqLiteDatabase.execSQL(CREATE_TABLE_MHS_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DROP_TABLE_KHS_QUERY);
        sqLiteDatabase.execSQL(DROP_TABLE_MHS_QUERY);
        onCreate(sqLiteDatabase);
    }

    public long insertKhs(Khs khs) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CODE_MATKUL, khs.getCodeMatkul());
        contentValues.put(COLUMN_NAME_MATKUL, khs.getNameMatkul());
        contentValues.put(COLUMN_SKS, khs.getSks());
        contentValues.put(COLUMN_GRADES, khs.getGrade());
        contentValues.put(COLUMN_LETTER_GRADES, khs.getLetterGrade());
        contentValues.put(COLUMN_PREDICATE, khs.getPredicate());
        long result = database.insert(TABLE_NAME_KHS, null, contentValues);
        database.close();
        return result;
    }

    public int updateKhs(Khs khs) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_MATKUL, khs.getNameMatkul());
        contentValues.put(COLUMN_NAME_MATKUL, khs.getNameMatkul());
        contentValues.put(COLUMN_SKS, khs.getSks());
        contentValues.put(COLUMN_GRADES, khs.getGrade());
        contentValues.put(COLUMN_LETTER_GRADES, khs.getLetterGrade());
        contentValues.put(COLUMN_PREDICATE, khs.getPredicate());

        String whereClause = COLUMN_CODE_MATKUL + " = ?";
        String[] whereArgs = {String.valueOf(khs.getCodeMatkul())};
        int result = database.update(TABLE_NAME_KHS, contentValues, whereClause, whereArgs);
        database.close();
        return result;
    }

    public int deleteKhs(String codeMatkul) {
        SQLiteDatabase database = this.getWritableDatabase();
        String whereClause = COLUMN_CODE_MATKUL + " = ?";
        String[] whereArgs = {String.valueOf(codeMatkul)};
        int result =database.delete(TABLE_NAME_KHS, whereClause, whereArgs);
        database.close();
        return result;
    }

    public ArrayList<Khs> getAllKhs() {
        SQLiteDatabase database = this.getWritableDatabase();
        ArrayList<Khs> khsArrayList = new ArrayList<>();
        String[] columns = {COLUMN_CODE_MATKUL, COLUMN_NAME_MATKUL, COLUMN_SKS, COLUMN_GRADES, COLUMN_LETTER_GRADES, COLUMN_PREDICATE};
        Cursor cursor = database.query(TABLE_NAME_KHS, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Khs khs = new Khs();
            khs.setCodeMatkul(cursor.getString(0));
            khs.setNameMatkul(cursor.getString(1));
            khs.setSks(cursor.getInt(2));
            khs.setGrade(cursor.getDouble(3));
            khs.setLetterGrade(cursor.getString(4));
            khs.setPredicate(cursor.getString(5));
            khsArrayList.add(khs);
        }
        cursor.close();
        database.close();
        return khsArrayList;
    }

    public long insertMhs(Mahasiswa mahasiswa) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NIM, mahasiswa.getNim());
        contentValues.put(COLUMN_NAME, mahasiswa.getName());
        contentValues.put(COLUMN_PASSWORD, mahasiswa.getPassword());
        long result =database.insert(TABLE_NAME_MHS, null, contentValues);
        database.close();
        return result;
    }

    public int updateMhs(Mahasiswa mahasiswa) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, mahasiswa.getName());
        contentValues.put(COLUMN_PASSWORD, mahasiswa.getPassword());

        String whereClause = COLUMN_NIM + " = ?";
        String[] whereArgs = {String.valueOf(mahasiswa.getNim())};
        int result =database.update(TABLE_NAME_MHS, contentValues, whereClause, whereArgs);
        database.close();
        return result;
    }

    public int deleteMhs(String nim) {
        SQLiteDatabase database = this.getWritableDatabase();
        String whereClause = COLUMN_NIM + " = ?";
        String[] whereArgs = {String.valueOf(nim)};
        int result =database.delete(TABLE_NAME_MHS, whereClause, whereArgs);
        database.close();
        return result;
    }

    public ArrayList<Mahasiswa> getAllMhs() {
        SQLiteDatabase database = this.getWritableDatabase();
        ArrayList<Mahasiswa> mahasiswaArrayList = new ArrayList<>();
        String[] columns = {COLUMN_NIM, COLUMN_NAME, COLUMN_PASSWORD};
        Cursor cursor = database.query(TABLE_NAME_MHS, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Mahasiswa mahasiswa = new Mahasiswa();
            mahasiswa.setNim(cursor.getString(0));
            mahasiswa.setName(cursor.getString(1));
            mahasiswa.setPassword(cursor.getString(2));
            mahasiswaArrayList.add(mahasiswa);
        }
        cursor.close();
        database.close();
        return mahasiswaArrayList;
    }

    public Mahasiswa findMhsByNim(String nim) {
        SQLiteDatabase database = this.getWritableDatabase();
        String[] columns = {COLUMN_NIM, COLUMN_NAME, COLUMN_PASSWORD};
        String selection = COLUMN_NIM + " = ?";
        String[] selectionArgs = {nim};

        Cursor cursor = database.query(TABLE_NAME_MHS, columns, selection, selectionArgs,
                null, null, null);

        Mahasiswa mahasiswa = null;
        if (cursor.moveToFirst()) {
            mahasiswa = new Mahasiswa();
            mahasiswa.setNim(cursor.getString(0));
            mahasiswa.setName(cursor.getString(1));
            mahasiswa.setPassword(cursor.getString(2));
        }
        cursor.close();
        database.close();
        return mahasiswa;
    }
}
