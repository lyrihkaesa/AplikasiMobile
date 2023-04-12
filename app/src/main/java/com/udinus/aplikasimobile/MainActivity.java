package com.udinus.aplikasimobile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.udinus.aplikasimobile.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    DatabaseHelper databaseHelper;
    private final ArrayList<ModelKhs> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mengganti setContentView dengan binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mengubah judul yang ada pada App Bar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Kartu Hasil Studi");

        databaseHelper = new DatabaseHelper(this);

        binding.rvKhs.setHasFixedSize(true);
        list.clear();
        list.addAll(databaseHelper.getAllKhs());
        showRecyclerList();

//        binding.fab.setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, EntryKhs.class);
//            startActivity(intent);
//        });
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