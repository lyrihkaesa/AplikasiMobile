package com.udinus.aplikasimobile.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.udinus.aplikasimobile.database.model.Khs;

import java.util.ArrayList;

public class KhsDao {
    public static final String TABLE_NAME = "khs";
    public static final String COLUMN_CODE_MATKUL = "code_matkul";
    public static final String COLUMN_NAME_MATKUL = "name_matkul";
    public static final String COLUMN_SKS = "sks";
    public static final String COLUMN_GRADES = "grades";
    public static final String COLUMN_LETTER_GRADES = "letter_grades";
    public static final String COLUMN_PREDICATE = "predicate";
    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_CODE_MATKUL + " TEXT PRIMARY KEY NOT NULL, " +
                    COLUMN_NAME_MATKUL + " TEXT, " +
                    COLUMN_SKS + " INTEGER, " +
                    COLUMN_GRADES + " REAL, " +
                    COLUMN_LETTER_GRADES + " TEXT, " +
                    COLUMN_PREDICATE + " TEXT" +
                    ")";
    public static final String DROP_TABLE_QUERY =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    private final SQLiteDatabase database;

    public KhsDao(SQLiteDatabase database) {
        this.database = database;
    }

    public static void createTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY);
    }
    public static void dropTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY);
    }

    public long insert(Khs khs) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CODE_MATKUL, khs.getCodeMatkul());
        contentValues.put(COLUMN_NAME_MATKUL, khs.getNameMatkul());
        contentValues.put(COLUMN_SKS, khs.getSks());
        contentValues.put(COLUMN_GRADES, khs.getGrade());
        contentValues.put(COLUMN_LETTER_GRADES, khs.getLetterGrade());
        contentValues.put(COLUMN_PREDICATE, khs.getPredicate());
        return database.insert(TABLE_NAME, null, contentValues);
    }

    public int update(Khs khs) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_MATKUL, khs.getNameMatkul());
        contentValues.put(COLUMN_NAME_MATKUL, khs.getNameMatkul());
        contentValues.put(COLUMN_SKS, khs.getSks());
        contentValues.put(COLUMN_GRADES, khs.getGrade());
        contentValues.put(COLUMN_LETTER_GRADES, khs.getLetterGrade());
        contentValues.put(COLUMN_PREDICATE, khs.getPredicate());

        String whereClause = COLUMN_CODE_MATKUL + " = ?";
        String[] whereArgs = {String.valueOf(khs.getCodeMatkul())};
        return database.update(TABLE_NAME, contentValues, whereClause, whereArgs);
    }

    public int delete(String codeMatkul) {
        String whereClause = COLUMN_CODE_MATKUL + " = ?";
        String[] whereArgs = {String.valueOf(codeMatkul)};
        return database.delete(TABLE_NAME, whereClause, whereArgs);
    }

    public ArrayList<Khs> getAll() {
        ArrayList<Khs> khsArrayList = new ArrayList<>();
        String[] columns = {COLUMN_CODE_MATKUL, COLUMN_NAME_MATKUL, COLUMN_SKS, COLUMN_GRADES, COLUMN_LETTER_GRADES, COLUMN_PREDICATE};
        Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Khs khs = new Khs();
            khs.setCodeMatkul(cursor.getString(0));
            khs.setNameMatkul(cursor.getString(1));
            khs.setSks(Integer.valueOf(cursor.getString(2)));
            khs.setGrade(Double.valueOf(cursor.getString(3)));
            khs.setLetterGrade(cursor.getString(4));
            khs.setPredicate(cursor.getString(5));
            khsArrayList.add(khs);
        }
        cursor.close();
        return khsArrayList;
    }
}

