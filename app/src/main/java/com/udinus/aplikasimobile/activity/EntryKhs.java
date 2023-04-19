package com.udinus.aplikasimobile.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.udinus.aplikasimobile.database.DatabaseHelper;
import com.udinus.aplikasimobile.database.dao.KhsDao;
import com.udinus.aplikasimobile.database.model.Khs;
import com.udinus.aplikasimobile.database.model.User;
import com.udinus.aplikasimobile.databinding.ActivityEntryKhsBinding;

public class EntryKhs extends AppCompatActivity {

    private ActivityEntryKhsBinding binding;
    DatabaseHelper databaseHelper;
    SQLiteDatabase database;
    private KhsDao khsDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mengganti setContentView dengan binding
        binding = ActivityEntryKhsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        User user = getIntent().getParcelableExtra("key_user");

        binding.tvNim.setText(user.getNim());
        binding.tvFullName.setText(user.getMahasiswa().getName());
        binding.tvMajor.setText(String.format("%s - %s", user.getMahasiswa().getMajor(), user.getMahasiswa().getDegree()));

        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();
        khsDao = new KhsDao(database);

        binding.btnSave.setOnClickListener(v -> addKhs());

        binding.btnCancel.setOnClickListener(v -> finish());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
        database.close();
    }

    private void addKhs() {
        // Membuat object khs
        Khs khs = new Khs();

        // Set/Input/Masukan nilai ke object khs
        khs.setCodeMatkul(binding.edtCodeMatkul.getText().toString());
        khs.setNameMatkul(binding.edtNameMatkul.getText().toString());
        khs.setGrade(Double.valueOf(binding.edtGrade.getText().toString()));
        khs.setLetterGrade(binding.edtLetterGrade.getText().toString());
        khs.setSks(Integer.valueOf(binding.edtSks.getText().toString()));
        khs.setPredicate(binding.edtPredicate.getText().toString());

        // insert object khs ke database
        if (khsDao.insert(khs) > 0) {
            Toast.makeText(this, "Berhasil menambahkan mata kuliah " + khs.getNameMatkul(), Toast.LENGTH_SHORT).show();
        }
        finish();
    }

}