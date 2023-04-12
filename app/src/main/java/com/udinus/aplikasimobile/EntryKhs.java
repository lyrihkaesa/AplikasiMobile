package com.udinus.aplikasimobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.udinus.aplikasimobile.databinding.ActivityEntryKhsBinding;
import com.udinus.aplikasimobile.databinding.ActivityMainBinding;

import java.util.Objects;

public class EntryKhs extends AppCompatActivity {

    private ActivityEntryKhsBinding binding;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mengganti setContentView dengan binding
        binding = ActivityEntryKhsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mengubah judul yang ada pada App Bar
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.entry_khs);
        databaseHelper = new DatabaseHelper(this);

        binding.btnSave.setOnClickListener(v -> addKhs());

        binding.btnCancel.setOnClickListener(v -> {
            // Ganti dari halaman EntryKhs ke MainActivity
            Intent intent = new Intent(EntryKhs.this, MainActivity.class);
            startActivity(intent);
        });

    }

    private void addKhs() {
        // Membuat object khs
        ModelKhs khs = new ModelKhs();

        // Set/Input/Masukan nilai ke object khs
        khs.setCodeMatkul(binding.edtCodeMatkul.getText().toString());
        khs.setNameMatkul(binding.edtNameMatkul.getText().toString());
        khs.setGrade(Double.valueOf(binding.edtGrade.getText().toString()));
        khs.setLetterGrade(binding.edtGrade.getText().toString());
        khs.setSks(Integer.valueOf(binding.edtSks.getText().toString()));
        khs.setPredicate(binding.edtPredicate.getText().toString());

        // insert object khs ke database
        databaseHelper.insertKhs(khs);

        // Ganti dari halaman EntryKhs ke MainActivity
        Intent intent = new Intent(EntryKhs.this, MainActivity.class);
        startActivity(intent);
    }

}