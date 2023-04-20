package com.udinus.aplikasimobile.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.udinus.aplikasimobile.database.model.Khs;

import java.util.ArrayList;

/**
 * Class ini merepresentasikan Objek Akses Data (Data Access Object) [DAO] untuk tabel khs pada database.
 */
public class KhsDao {
    /**
     * Nama tabel pada database.
     */
    public static final String TABLE_NAME = "khs";
    /**
     * Nama kolom code_matkul pada tabel khs.
     * TipeData: TEXT PRIMARY KEY
     */
    public static final String COLUMN_CODE_MATKUL = "code_matkul";
    /**
     * Nama kolom name_matkul pada tabel khs.
     * TipeData: TEXT
     */
    public static final String COLUMN_NAME_MATKUL = "name_matkul";
    /**
     * Nama kolom sks pada tabel khs.
     * TipeData: INTEGER
     */
    public static final String COLUMN_SKS = "sks";
    /**
     * Nama kolom grades pada tabel khs.
     * TipeData: REAL
     */
    public static final String COLUMN_GRADES = "grades";
    /**
     * Nama kolom letter_grades pada tabel khs.
     * TipeData: TEXT
     */
    public static final String COLUMN_LETTER_GRADES = "letter_grades";
    /**
     * Nama kolom predikat pada tabel khs.
     * TipeData: TEXT
     */
    public static final String COLUMN_PREDICATE = "predicate";
    /**
     * Query untuk membuat tabel khs dalam database
     */
    public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_CODE_MATKUL + " TEXT PRIMARY KEY NOT NULL, " + COLUMN_NAME_MATKUL + " TEXT, " + COLUMN_SKS + " INTEGER, " + COLUMN_GRADES + " REAL, " + COLUMN_LETTER_GRADES + " TEXT, " + COLUMN_PREDICATE + " TEXT" + ")";
    /**
     * Query untuk menghapus tabel khs dalam database
     */
    public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private final SQLiteDatabase database;

    /**
     * Konstruktor untuk class KhsDao.
     *
     * @param database Objek SQLiteDatabase yang digunakan untuk mengakses database.
     */
    public KhsDao(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * Method/function untuk membuat tabel khs dalam database.
     *
     * @param sqLiteDatabase Objek SQLiteDatabase untuk eksekusi query.
     */
    public static void createTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY);
    }

    /**
     * Method/function untuk menghapus tabel khs dalam database.
     *
     * @param sqLiteDatabase Objek SQLiteDatabase untuk eksekusi query.
     */
    public static void dropTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY);
    }

    /**
     * Method/function untuk insert/post/memasukan/input data khs pada tabel khs.
     *
     * @param khs Objek Khs yang akan dimasukan.
     * @return long ID dari baris yang telah dimasukan, atau -1 jika terjadi kesalahan.
     */
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

    /**
     * Method/function untuk update/put/mengubah/mengedit data khs pada tabel khs.
     *
     * @param khs Objek Khs yang akan diubah.
     * @return int jumlah baris yang berhasil diubah pada tabel khs.
     */
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

    /**
     * Method/function untuk delete/menghapus data khs pada tabel khs berdasarkan kode matkul.
     *
     * @param codeMatkul Kode mata kuliah yang akan dihapus.
     * @return int jumlah baris yang terhapus pada tabel khs.
     */
    public int delete(String codeMatkul) {
        String whereClause = COLUMN_CODE_MATKUL + " = ?";
        String[] whereArgs = {String.valueOf(codeMatkul)};
        return database.delete(TABLE_NAME, whereClause, whereArgs);
    }

    /**
     * Method/function select/get/mengambil semua data khs dari tabel khs.
     *
     * @return ArrayList<Khs> berisi semua data khs.
     */
    public ArrayList<Khs> getAll() {
        ArrayList<Khs> khsArrayList = new ArrayList<>();
        String[] columns = {COLUMN_CODE_MATKUL, COLUMN_NAME_MATKUL, COLUMN_SKS, COLUMN_GRADES, COLUMN_LETTER_GRADES, COLUMN_PREDICATE};
        Cursor cursor = database.query(TABLE_NAME, columns, null, null, null, null, null);
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
        return khsArrayList;
    }

    /**
     * Find/Search/Get/Mencari/Mendapatkan data khs berdasarkan kode mata kuliah pada tabel khs
     *
     * @param codeMatkul kode mata kuliah dari khs yang akan dicari
     * @return objek Khs yang memiliki kode mata kuliah yang sesuai, <code>null<code/> jika tidak ditemukan.
     */
    public Khs findKhsByCodeMatkul(String codeMatkul) {
        String[] columns = {COLUMN_CODE_MATKUL, COLUMN_NAME_MATKUL, COLUMN_SKS, COLUMN_GRADES, COLUMN_LETTER_GRADES, COLUMN_PREDICATE};
        String selection = COLUMN_CODE_MATKUL + "=?";
        String[] selectionArgs = {codeMatkul};
        Cursor cursor = database.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        Khs khs = null;
        if (cursor.moveToFirst()) {
            khs = new Khs();
            khs.setCodeMatkul(cursor.getString(0));
            khs.setNameMatkul(cursor.getString(1));
            khs.setSks(cursor.getInt(2));
            khs.setGrade(cursor.getDouble(3));
            khs.setLetterGrade(cursor.getString(4));
            khs.setPredicate(cursor.getString(5));
        }
        cursor.close();
        return khs;
    }
}

