package com.udinus.aplikasimobile.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.udinus.aplikasimobile.database.model.User;

import java.util.ArrayList;

/**
 * Class ini merepresentasikan Objek Akses Data (Data Access Object) [DAO] untuk tabel user pada database.
 */
public class UserDao {
    /**
     * Nama tabel pada database.
     */
    public static final String TABLE_NAME = "user";
    /**
     * Nama kolom username pada tabel user.
     * TipeData: TEXT PRIMARY KEY
     */
    public static final String COLUMN_USERNAME = "username";
    /**
     * Nama kolom password pada tabel user.
     * TipeData: TEXT
     */
    public static final String COLUMN_PASSWORD = "password";
    /**
     * Nama kolom nim pada tabel user.
     * TipeData: TEXT
     */
    public static final String COLUMN_NIM = "nim";
    /**
     * Query untuk membuat tabel user dalam database
     */
    public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_USERNAME + " TEXT PRIMARY KEY NOT NULL, " + COLUMN_PASSWORD + " TEXT, " + COLUMN_NIM + " TEXT" + ")";
    /**
     * Query untuk menghapus tabel user dalam database
     */
    public static final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private final SQLiteDatabase database;

    /**
     * Konstruktor untuk class UserDao.
     *
     * @param database Objek SQLiteDatabase yang digunakan untuk mengakses database.
     */
    public UserDao(SQLiteDatabase database) {
        this.database = database;
    }

    /**
     * Method/function untuk membuat tabel user dalam database.
     *
     * @param sqLiteDatabase Objek SQLiteDatabase untuk eksekusi query.
     */
    public static void createTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY);
    }

    /**
     * Method/function untuk menghapus tabel user dalam database.
     *
     * @param sqLiteDatabase Objek SQLiteDatabase untuk eksekusi query.
     */
    public static void dropTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY);
    }

    /**
     * Method/function untuk insert/post/memasukan/input data user pada tabel user.
     *
     * @param user Objek User yang akan dimasukan.
     * @return long ID dari baris yang telah dimasukan, atau -1 jika terjadi kesalahan.
     */
    public long insert(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, user.getUsername());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
        contentValues.put(COLUMN_NIM, user.getNim());
        return database.insert(TABLE_NAME, null, contentValues);
    }

    /**
     * Method/function untuk update/put/mengubah/mengedit data user pada tabel user.
     *
     * @param user Objek User yang akan diubah.
     * @return int jumlah baris yang berhasil diubah pada tabel user.
     */
    public int update(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
        contentValues.put(COLUMN_NIM, user.getNim());
        String whereClause = COLUMN_USERNAME + " = ?";
        String[] whereArgs = {String.valueOf(user.getUsername())};
        return database.update(TABLE_NAME, contentValues, whereClause, whereArgs);
    }

    /**
     * Method/function untuk delete/menghapus data user pada tabel user berdasarkan NIM.
     *
     * @param username username dari user yang akan dihapus.
     * @return int jumlah baris yang terhapus pada tabel user.
     */
    public int delete(String username) {
        String whereClause = COLUMN_USERNAME + " = ?";
        String[] whereArgs = {String.valueOf(username)};
        return database.delete(TABLE_NAME, whereClause, whereArgs);
    }

    /**
     * Method/function select/get/mengambil semua data user dari tabel user.
     *
     * @return ArrayList<User> berisi semua data user.
     */
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

    /**
     * Find/Search/Get/Mencari/Mendapatkan data user berdasarkan NIM atau username pada tabel user
     *
     * @param nimOrUsername NIM atau username dari user yang akan dicari
     * @return objek User yang memiliki NIM yang sesuai, <code>null<code/> jika tidak ditemukan.
     */
    public User findUserByNimOrUsername(String nimOrUsername) {
        String[] columns = {COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_NIM};
        String selection = COLUMN_NIM + " = ? OR " + COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {nimOrUsername, nimOrUsername};

        Cursor cursor = database.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

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
