package com.udinus.aplikasimobile.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.udinus.aplikasimobile.database.model.Mahasiswa;

import java.util.ArrayList;

/**
 * Class ini merepresentasikan Objek Akses Data (Data Access Object) [DAO] untuk tabel mhs pada database.
 */
public class MahasiswaDao {
    /**
     * Nama tabel pada database.
     */
    public static final String TABLE_NAME = "mhs";
    /**
     * Nama kolom code_nim pada tabel mhs.
     * TipeData: TEXT PRIMARY KEY
     */
    public static final String COLUMN_NIM = "nim";
    /**
     * Nama kolom code_name pada tabel mhs.
     * TipeData: TEXT
     */
    public static final String COLUMN_NAME = "name";
    /**
     * Nama kolom code_email pada tabel mhs.
     * TipeData: TEXT
     */
    public static final String COLUMN_EMAIL = "email";
    /**
     * Nama kolom code_major atau prodi pada tabel mhs.
     * TipeData: TEXT
     */
    public static final String COLUMN_MAJOR = "major";
    /**
     * Nama kolom code_degree atau s1/s1/s3 pada tabel mhs.
     * TipeData: TEXT
     */
    public static final String COLUMN_DEGREE = "degree";
    /**
     * Query untuk membuat tabel mhs dalam database
     */
    public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_NIM + " TEXT PRIMARY KEY NOT NULL, " + COLUMN_NAME + " TEXT, " + COLUMN_EMAIL + " TEXT, " + COLUMN_MAJOR + " TEXT, " + COLUMN_DEGREE + " TEXT" + ")";
    /**
     * Query untuk menghapus tabel mhs dalam database
     */
    public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private final SQLiteDatabase database;

    /**
     * Konstruktor untuk class MahasiswaDao.
     *
     * @param database Objek SQLiteDatabase yang digunakan untuk mengakses database.
     */
    public MahasiswaDao(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * Method/function untuk membuat tabel mhs dalam database.
     *
     * @param sqLiteDatabase Objek SQLiteDatabase untuk eksekusi query.
     */
    public static void createTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY);
    }

    /**
     * Method/function untuk menghapus tabel mhs dalam database.
     *
     * @param sqLiteDatabase Objek SQLiteDatabase untuk eksekusi query.
     */
    public static void dropTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY);
    }

    /**
     * Method/function untuk insert/post/memasukan/input data mahasiswa pada tabel mhs.
     *
     * @param mahasiswa Objek Mahsiswa yang akan dimasukan.
     * @return long ID dari baris yang telah dimasukan, atau -1 jika terjadi kesalahan.
     */
    public long insert(Mahasiswa mahasiswa) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NIM, mahasiswa.getNim());
        contentValues.put(COLUMN_NAME, mahasiswa.getName());
        contentValues.put(COLUMN_EMAIL, mahasiswa.getEmail());
        contentValues.put(COLUMN_MAJOR, mahasiswa.getMajor());
        contentValues.put(COLUMN_DEGREE, mahasiswa.getDegree());
        return database.insert(TABLE_NAME, null, contentValues);
    }

    /**
     * Method/function untuk update/put/mengubah/mengedit data mahasiswa pada tabel mhs.
     *
     * @param mahasiswa Objek Mahasiswa yang akan diubah.
     * @return int jumlah baris yang berhasil diubah pada tabel mhs.
     */
    public int update(Mahasiswa mahasiswa) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, mahasiswa.getName());
        contentValues.put(COLUMN_EMAIL, mahasiswa.getEmail());
        contentValues.put(COLUMN_MAJOR, mahasiswa.getMajor());
        contentValues.put(COLUMN_DEGREE, mahasiswa.getDegree());

        String whereClause = COLUMN_NIM + " = ?";
        String[] whereArgs = {String.valueOf(mahasiswa.getNim())};
        return database.update(TABLE_NAME, contentValues, whereClause, whereArgs);
    }

    /**
     * Method/function untuk delete/menghapus data mahasiswa pada tabel mhs berdasarkan NIM.
     *
     * @param nim NIM dari mahasiswa yang akan dihapus.
     * @return int jumlah baris yang terhapus pada tabel mhs.
     */
    public int delete(String nim) {
        String whereClause = COLUMN_NIM + " = ?";
        String[] whereArgs = {String.valueOf(nim)};
        return database.delete(TABLE_NAME, whereClause, whereArgs);
    }

    /**
     * Method/function select/get/mengambil semua data mahasiswa dari tabel mhs.
     *
     * @return ArrayList<Mahasiswa> berisi semua data mahasiswa.
     */
    public ArrayList<Mahasiswa> getAll() {
        ArrayList<Mahasiswa> mahasiswaArrayList = new ArrayList<>();
        String[] columns = {COLUMN_NIM, COLUMN_NAME, COLUMN_EMAIL, COLUMN_MAJOR, COLUMN_DEGREE};
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

    /**
     * Find/Search/Get/Mencari/Mendapatkan data mahasiswa berdasarkan NIM pada tabel mhs
     *
     * @param nim NIM dari mahasiswa yang akan dicari
     * @return objek Mahasiswa yang memiliki NIM yang sesuai, <code>null<code/> jika tidak ditemukan.
     */
    public Mahasiswa findMahasiswaByNim(String nim) {
        String[] columns = {COLUMN_NIM, COLUMN_NAME, COLUMN_EMAIL, COLUMN_MAJOR, COLUMN_DEGREE};
        String selection = COLUMN_NIM + " = ?";
        String[] selectionArgs = {nim};

        Cursor cursor = database.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

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
