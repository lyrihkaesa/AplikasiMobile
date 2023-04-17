package com.udinus.aplikasimobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.udinus.aplikasimobile.R;
import com.udinus.aplikasimobile.database.DatabaseHelper;
import com.udinus.aplikasimobile.database.dao.KhsDao;
import com.udinus.aplikasimobile.database.model.Khs;
import com.udinus.aplikasimobile.databinding.ActivityDetailKhsBinding;
import com.udinus.aplikasimobile.databinding.ActivityEntryKhsBinding;

import java.util.Objects;

public class DetailKhs extends AppCompatActivity {

    private ActivityDetailKhsBinding binding;
    DatabaseHelper databaseHelper;
    private KhsDao khsDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailKhsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Mengganti setContentView dengan binding
        ActivityDetailKhsBinding binding = ActivityDetailKhsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mengubah judul yang ada pada App Bar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Update dan Delete KHS");
        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        khsDao = new KhsDao(database);



        binding.updateButton.setOnClickListener(v ->editKhs());

        binding.cancelButton.setOnClickListener(v -> finish());

        binding.deleteButton.setOnClickListener(v -> {
            khsDao.delete(binding.edtCodeMatkul.getText().toString());
        });




    }

    private void editKhs() {
        // Membuat object khs
        Khs khs = new Khs();

        // Set/Input/Masukan nilai ke object khs
        khs.setCodeMatkul(binding.edtCodeMatkul.getText().toString());
        khs.setNameMatkul(binding.edtNameMatkul.getText().toString());
        khs.setGrade(Double.valueOf(binding.edtGrade.getText().toString()));
        khs.setLetterGrade(binding.edtGrade.getText().toString());
        khs.setSks(Integer.valueOf(binding.edtSks.getText().toString()));
        khs.setPredicate(binding.edtPredicate.getText().toString());

        // insert object khs ke database
        if(khsDao.update(khs)>0){
            Toast.makeText(this, "Berhasil menambahkan mata kuliah " + khs.getNameMatkul(), Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}