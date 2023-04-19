package com.udinus.aplikasimobile.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.udinus.aplikasimobile.adapter.KhsRvAdapter;
import com.udinus.aplikasimobile.database.DatabaseHelper;
import com.udinus.aplikasimobile.database.dao.KhsDao;
import com.udinus.aplikasimobile.database.model.Khs;
import com.udinus.aplikasimobile.databinding.ActivityListKhsBinding;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class ListKhs extends AppCompatActivity {
    ActivityListKhsBinding binding;
    DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private KhsDao khsDao;
    private final ArrayList<Khs> list = new ArrayList<>();
    private KhsRvAdapter khsRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mengganti setContentView dengan binding
        binding = ActivityListKhsBinding.inflate(getLayoutInflater());
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
        countFooter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
        database.close();
    }

    private void countFooter(){
        Double totalGrades = 0.0;
        Integer totalSks = 0;
        for (Khs khs : list) {
            totalGrades += khs.getGrade();
            totalSks += khs.getSks();
        }
        Double ipk = totalGrades/list.size();
        binding.tvIpk.setText(String.valueOf(ipk));
        binding.tvTotalSks.setText(String.valueOf(totalSks));
        binding.tvTotalMatkul.setText(String.valueOf(list.size()));
    }
}