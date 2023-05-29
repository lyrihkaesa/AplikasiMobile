package com.udinus.aplikasimobile.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.udinus.aplikasimobile.database.DatabaseHelper;
import com.udinus.aplikasimobile.database.dao.BarangDao;
import com.udinus.aplikasimobile.database.model.Barang;
import com.udinus.aplikasimobile.databinding.ActivityEntryBarangBinding;

import java.util.Objects;
import java.util.UUID;

public class EntryBarang extends AppCompatActivity {
    ActivityEntryBarangBinding binding;
    DatabaseHelper databaseHelper;
    private BarangDao barangDao;

    public EntryBarang() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEntryBarangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mengubah judul yang ada pada App Bar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Menambahkan Data Barang");

        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        barangDao = new BarangDao(database);


        UUID uuid = UUID.randomUUID();
        String randomUUID8Char = uuid.toString().replaceAll("-", "").substring(0, 8);
        binding.edtKode.setText(randomUUID8Char);

        binding.btnSimpan.setOnClickListener(view -> insertBarang());

        binding.btnBatal.setOnClickListener(view -> batal());
    }

    private void insertBarang() {
        // Membuat obyek barang/Barang
        Barang barang = new Barang();

        // Set/Input/Masukan nilai ke obyek barang/Barang
        barang.setCode(binding.edtKode.getText().toString());
        barang.setName(binding.edtNama.getText().toString());
        barang.setSatuan(binding.edtSatuan.getText().toString());
        barang.setPrice(Double.valueOf(binding.edtHarga.getText().toString()));

        // insert obyek barang yang sudah ada nilainya diatas ke database
        long result = barangDao.insert(barang);
        if(result > 0){
            // Pindah dari halaman EntryBarang/InputBarang ke MainActivity/DaftarBarang
            Intent intent = new Intent(EntryBarang.this, ListBarang.class);
            startActivity(intent);
        }
    }

    private void batal() {
        // Pindah dari halaman EntryBarang/InputBarang ke MainActivity/DaftarBarang
        Intent intent = new Intent(EntryBarang.this, ListBarang.class);
        startActivity(intent);
    }
}