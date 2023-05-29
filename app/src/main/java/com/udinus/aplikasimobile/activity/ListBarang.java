package com.udinus.aplikasimobile.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.udinus.aplikasimobile.adapter.BarangRvAdapter;
import com.udinus.aplikasimobile.database.DatabaseHelper;
import com.udinus.aplikasimobile.database.dao.BarangDao;
import com.udinus.aplikasimobile.database.model.Barang;
import com.udinus.aplikasimobile.databinding.ActivityListBarangBinding;

import java.util.ArrayList;
import java.util.Objects;

public class ListBarang extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    private final ArrayList<Barang> barangArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityListBarangBinding binding = ActivityListBarangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mengubah judul yang ada pada App Bar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Daftar Barang");

        // Inisialisasi database dan DAO
        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        BarangDao barangDao = new BarangDao(database);

        binding.rvBarang.setHasFixedSize(true);
        // Mengatur LayoutManager pada RecycleView Barang dengan LinearLayoutManager
        binding.rvBarang.setLayoutManager(new LinearLayoutManager(this));
        barangArrayList.clear();
        barangArrayList.addAll(barangDao.getAll());
        // Deklarasi Adapter RecyclerView Barang
        BarangRvAdapter barangRecyclerViewAdapter = new BarangRvAdapter(barangArrayList);

        barangRecyclerViewAdapter.setOnItemClickCallback(barang -> {
            Intent intentDetail = new Intent(ListBarang.this, EditBarang.class);
            // Mengirimkan data khs ke activity DetailKhs dengan key "key_khs"
            intentDetail.putExtra("key_barang", barang);
            startActivity(intentDetail);
        });
        // Menghubungkan RecyclerView dengan Adapter diatas.
        binding.rvBarang.setAdapter(barangRecyclerViewAdapter);

        // Tombol/Button yang melayang untuk navigasi ke EntryPage/Halaman Input Barang
        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(ListBarang.this, EntryBarang.class);
            startActivity(intent);
        });
    }
}