package com.udinus.aplikasimobile.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.udinus.aplikasimobile.MainActivity;
import com.udinus.aplikasimobile.R;
import com.udinus.aplikasimobile.database.DatabaseHelper;
import com.udinus.aplikasimobile.database.dao.KhsDao;
import com.udinus.aplikasimobile.database.model.Khs;
import com.udinus.aplikasimobile.databinding.ActivityDetailKhsBinding;

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

        Khs khs = getIntent().getParcelableExtra("key_khs");

        binding.edtCodeMatkul.setText(khs.getCodeMatkul());
        binding.edtNameMatkul.setText(khs.getNameMatkul());
        binding.edtSks.setText(String.valueOf(khs.getSks()));
        binding.edtGrade.setText(String.valueOf(khs.getGrade()));
        binding.edtLetterGrade.setText(khs.getLetterGrade());
        binding.edtPredicate.setText(khs.getPredicate());

        binding.edtCodeMatkul.setEnabled(false);
        binding.edtCodeMatkul.setTextColor(getColor(R.color.red_500));

        binding.updateButton.setOnClickListener(v -> editKhs());

        binding.cancelButton.setOnClickListener(v -> finish());

        binding.deleteButton.setOnClickListener(v -> {
            if(khsDao.delete(binding.edtCodeMatkul.getText().toString())>0){
                Toast.makeText(this, "Berhasil menghapus mata kuliah " + khs.getNameMatkul(), Toast.LENGTH_SHORT).show();
            }
            back();
        });
    }

    private void editKhs() {
        // Membuat object khs
        Khs khs = new Khs();

        // Set/Input/Masukan nilai ke object khs
        khs.setCodeMatkul(binding.edtCodeMatkul.getText().toString());
        khs.setNameMatkul(binding.edtNameMatkul.getText().toString());
        Log.d("DEBUG", binding.edtGrade.getText().toString());
        khs.setGrade(90.0);
        khs.setLetterGrade(binding.edtLetterGrade.getText().toString());
        khs.setSks(Integer.valueOf(binding.edtSks.getText().toString()));
        khs.setPredicate(binding.edtPredicate.getText().toString());

        // insert object khs ke database
        if(khsDao.update(khs)>0){
            Toast.makeText(this, "Berhasil menambahkan mata kuliah " + khs.getNameMatkul(), Toast.LENGTH_SHORT).show();
        }
        back();
    }

    private void back(){
        startActivity(new Intent(DetailKhs.this, MainActivity.class));
    }
}