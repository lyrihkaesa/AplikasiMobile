package com.udinus.aplikasimobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public final static String databaseName = "db_khs_kaesa";
    public final static String tableKhs = "khs";
    public final static String fieldCodeMatkul = "code_matkul";
    public final static String fieldNameMatkul = "name_matkul";
    public final static String fieldSKS = "sks";
    public final static String fieldGrade = "grades";
    public final static String fieldLetterGrade = "letter_grades";
    public final static String fieldPredicate = "predicate";


    public DatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + tableKhs + "("
                + fieldCodeMatkul + " PRIMARY KEY NOT NULL, "
                + fieldNameMatkul + " TEXT, "
                + fieldSKS + " INT, "
                + fieldGrade + " REAL, "
                + fieldLetterGrade + " TEXT, "
                + fieldPredicate + " TEXT"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tableKhs);
        onCreate(sqLiteDatabase);
    }

    public void insertKhs(ModelKhs khs) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(fieldCodeMatkul, khs.getCodeMatkul());
        contentValues.put(fieldNameMatkul, khs.getNameMatkul());
        contentValues.put(fieldSKS, khs.getSks());
        contentValues.put(fieldGrade, khs.getGrade());
        contentValues.put(fieldLetterGrade, khs.getLetterGrade());
        contentValues.put(fieldPredicate, khs.getPredicate());

        db.insert(tableKhs, null, contentValues);
        db.close();
    }

    public ArrayList<ModelKhs> getAllKhs() {
        ArrayList<ModelKhs> khsArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableKhs, null);
        while (cursor.moveToNext()) {
            ModelKhs khs = new ModelKhs();
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

    public void updateKhs(ModelKhs khs) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(fieldNameMatkul, khs.getNameMatkul());
        contentValues.put(fieldSKS, khs.getSks());
        contentValues.put(fieldGrade, khs.getGrade());
        contentValues.put(fieldLetterGrade, khs.getLetterGrade());
        contentValues.put(fieldPredicate, khs.getPredicate());

        db.update(tableKhs, contentValues, fieldCodeMatkul + "=?", new String[]{khs.getCodeMatkul()});
        db.close();
    }

    public void deleteKhs(String codeMatkul) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableKhs, fieldCodeMatkul + "=?", new String[]{codeMatkul});
        db.close();
    }
}
