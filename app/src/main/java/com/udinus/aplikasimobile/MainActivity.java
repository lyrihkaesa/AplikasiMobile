package com.udinus.aplikasimobile;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.udinus.aplikasimobile.activity.DetailKhs;
import com.udinus.aplikasimobile.activity.EntryKhs;
import com.udinus.aplikasimobile.adapter.KhsRvAdapter;
import com.udinus.aplikasimobile.database.dao.KhsDao;
import com.udinus.aplikasimobile.database.model.Khs;
import com.udinus.aplikasimobile.databinding.ActivityMainBinding;
import com.udinus.aplikasimobile.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    DatabaseHelper databaseHelper;
    private final ArrayList<Khs> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mengganti setContentView dengan binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mengubah judul yang ada pada App Bar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Kartu Hasil Studi");

        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        KhsDao khsDao = new KhsDao(database);

        binding.rvKhs.setHasFixedSize(true);
        list.clear();
        list.addAll(khsDao.getAll());
        showRecyclerList();

        binding.addKhs.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, EntryKhs.class)));
    }

    private void showRecyclerList() {
        // Mengatur layout linear pada RecyclerView
        binding.rvKhs.setLayoutManager(new LinearLayoutManager(this));

        // Inisialisasi adapter untuk RecyclerView
        KhsRvAdapter khsRvAdapter = new KhsRvAdapter(list);

        // Menyambungkan adapter diatas ke RecyclerView pada XML.
        binding.rvKhs.setAdapter(khsRvAdapter);

        // onClick pindah ke DetailMedicine, dengan membawa data: Medicine
        khsRvAdapter.setOnItemClickCallback(data -> {
            Intent intentDetail = new Intent(MainActivity.this, DetailKhs.class);
            intentDetail.putExtra("key_khs", data);
            startActivity(intentDetail);
        });
    }

}