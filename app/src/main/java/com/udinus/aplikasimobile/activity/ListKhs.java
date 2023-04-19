package com.udinus.aplikasimobile.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.udinus.aplikasimobile.adapter.KhsRvAdapter;
import com.udinus.aplikasimobile.database.DatabaseHelper;
import com.udinus.aplikasimobile.database.dao.KhsDao;
import com.udinus.aplikasimobile.database.model.Khs;
import com.udinus.aplikasimobile.databinding.ActivityListKhsBinding;

import java.util.ArrayList;
import java.util.Objects;

public class ListKhs extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private KhsDao khsDao;
    private final ArrayList<Khs> list = new ArrayList<>();
    private KhsRvAdapter khsRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("CHECK", "onCreate: ListKhs");
        // Mengganti setContentView dengan binding
        ActivityListKhsBinding binding = ActivityListKhsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();
        khsDao = new KhsDao(database);

        binding.rvKhs.setHasFixedSize(true);
        // Mengatur layout linear pada RecyclerView
        binding.rvKhs.setLayoutManager(new LinearLayoutManager(this));

        list.clear();
        list.addAll(khsDao.getAll());

        // Inisialisasi adapter untuk RecyclerView
        khsRvAdapter = new KhsRvAdapter(list);

        // Menyambungkan adapter diatas ke RecyclerView pada XML.
        binding.rvKhs.setAdapter(khsRvAdapter);

        // onClick pindah ke DetailMedicine, dengan membawa data: Medicine
        khsRvAdapter.setOnItemClickCallback(khs -> {
            Intent intentDetail = new Intent(ListKhs.this, DetailKhs.class);
            intentDetail.putExtra("key_khs", khs);
            startActivity(intentDetail);
        });

        binding.addKhs.setOnClickListener(v -> startActivity(new Intent(ListKhs.this, EntryKhs.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();
        list.clear();
        list.addAll(khsDao.getAll());
        khsRvAdapter.notifyItemRangeChanged(0, list.size());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
        database.close();
    }
}