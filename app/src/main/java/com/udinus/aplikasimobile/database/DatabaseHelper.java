package com.udinus.aplikasimobile.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.udinus.aplikasimobile.database.dao.KhsDao;
import com.udinus.aplikasimobile.database.dao.MahasiswaDao;
import com.udinus.aplikasimobile.database.dao.UserDao;

/**
 * DatabaseHelper adalah class yang mengatur create/pembuatan, upgrade/peningkatan, dan drop/penghapusan database SQLite.
 * <p>
 * Kelas ini adalah subclass dari SQLiteOpenHelper dan menyediakan implementasi method onCreate dan onUpgrade
 * untuk membuat dan mengubah skema database, serta menyimpan nama database dan versi.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    /**
     * Nama database yang akan dibuat atau diakses.
     */
    private static final String DATABASE_NAME = "my_database.db";
    /**
     * Versi database. Jika versi database diubah, method onUpgrade akan dipanggil.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Konstruktor untuk membuat objek DatabaseHelper.
     *
     * @param context Konteks aplikasi saat ini.
     */
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Method/function yang akan dipanggil ketika database pertama kali dibuat.
     * Method/function ini digunakan untuk membuat tabel pada database menggunakan class Data Aacces Object [DAO].
     *
     * @param sqLiteDatabase Objek SQLiteDatabase yang merepresentasikan database yang dibuat.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        KhsDao.createTable(sqLiteDatabase);
        UserDao.createTable(sqLiteDatabase);
        MahasiswaDao.createTable(sqLiteDatabase);
    }

    /**
     * Method/function yang akan dipanggil ketika versi database berubah.
     * Method/function ini digunakan untuk menghapus tabel yang lama dan membuat tabel baru menggunakan class Data Aacces Object [DAO].
     *
     * @param sqLiteDatabase Objek SQLiteDatabase yang merepresentasikan database yang dibuat.
     * @param oldVersion     Versi database sebelum diupgrade.
     * @param newVersion     Versi database setelah diupgrade.
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        KhsDao.dropTable(sqLiteDatabase);
        UserDao.dropTable(sqLiteDatabase);
        MahasiswaDao.dropTable(sqLiteDatabase);
        onCreate(sqLiteDatabase);
    }
}
