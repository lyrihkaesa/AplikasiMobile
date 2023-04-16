package com.udinus.aplikasimobile.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.udinus.aplikasimobile.R;
import com.udinus.aplikasimobile.database.DatabaseHelper;
import com.udinus.aplikasimobile.database.dao.KhsDao;
import com.udinus.aplikasimobile.database.model.Khs;
import com.udinus.aplikasimobile.databinding.ActivityEntryKhsBinding;

import java.util.Objects;

public class EntryKhs extends AppCompatActivity {

    private ActivityEntryKhsBinding binding;
    DatabaseHelper databaseHelper;
    private KhsDao khsDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mengganti setContentView dengan binding
        binding = ActivityEntryKhsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mengubah judul yang ada pada App Bar
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.entry_khs);
        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        khsDao = new KhsDao(database);

        binding.btnSave.setOnClickListener(v -> addKhs());

        binding.btnCancel.setOnClickListener(v -> finish());

    }

    private void addKhs() {
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
        if(khsDao.insert(khs)>0){
            Toast.makeText(this, "Berhasil menambahkan mata kuliah " + khs.getNameMatkul(), Toast.LENGTH_SHORT).show();
        }

       finish();
    }

}