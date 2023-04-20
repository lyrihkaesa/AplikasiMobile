package com.udinus.aplikasimobile.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
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

        // Mengambil/mendapatkan data user yang dikirim dari activity sebelumnya dengan key "key_user"
        User user = getIntent().getParcelableExtra("key_user");

        // Mengubah nilai/value yang ada pada TableIdentity
        binding.tvNim.setText(user.getNim());
        binding.tvFullName.setText(user.getMahasiswa().getName());
        binding.tvMajor.setText(String.format("%s - %s", user.getMahasiswa().getMajor(), user.getMahasiswa().getDegree()));

        // Inisialisasi database dan DAO
        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();
        khsDao = new KhsDao(database);

        // Saat Button save diklik menjalankan/memanggil method/function addKhs()
        binding.btnSave.setOnClickListener(v -> addKhs());

        // Saat Button cancel diklik menjalankan/memangil method/function finish()
        // Method finish() melakukan mengeluarkan/pop activity sekarang dan kemabli ke activity sebelumnya.
        binding.btnCancel.setOnClickListener(v -> finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Memastikan tidak ada kebocoran memori (memory leak) saat menggunakan database
        databaseHelper.close();
        database.close();
    }

    private void addKhs() {
        // Pengecekan TextView tidak boleh kosong/empty
        if (TextUtils.isEmpty(binding.edtCodeMatkul.getText())) {
            // Tampilkan pesan kesalahan pada EditText
            binding.edtCodeMatkul.setError("Kode mata kuliah tidak boleh kosong");
            return;
        }

        if (TextUtils.isEmpty(binding.edtNameMatkul.getText())) {
            // Tampilkan pesan kesalahan pada EditText
            binding.edtNameMatkul.setError("Nama mata kuliah tidak boleh kosong");
            return;
        }

        if (TextUtils.isEmpty(binding.edtGrade.getText())) {
            // Tampilkan pesan kesalahan pada EditText
            binding.edtGrade.setError("Nilai angka tidak boleh kosong");
            return;
        }

        if (TextUtils.isEmpty(binding.edtLetterGrade.getText())) {
            // Tampilkan pesan kesalahan pada EditText
            binding.edtLetterGrade.setError("Nilai huruf tidak boleh kosong");
            return;
        }

        if (TextUtils.isEmpty(binding.edtSks.getText())) {
            // Tampilkan pesan kesalahan pada EditText
            binding.edtSks.setError("SKS tidak boleh kosong");
            return;
        }

        if (TextUtils.isEmpty(binding.edtPredicate.getText())) {
            // Tampilkan pesan kesalahan pada EditText
            binding.edtPredicate.setError("Predikat tidak boleh kosong");
            return;
        }

        // Memastikan inputan sks benar
        int sks;
        try {
            sks = Integer.parseInt(binding.edtSks.getText().toString().trim());
        } catch (NumberFormatException e) {
            binding.edtSks.setError("SKS harus diisi dengan angka bulat");
            return;
        }

        // Memastikan inputan nilai angka benar
        double grade;
        try {
            grade = Double.parseDouble(binding.edtGrade.getText().toString().trim());
        } catch (NumberFormatException e) {
            binding.edtGrade.setError("Nilai angka harus diisi dengan angka bulat/desimal");
            return;
        }

        // Memastikan kode mata kuliah tidak ada pada database
        String codeMatkul = binding.edtCodeMatkul.getText().toString();
        if (khsDao.findKhsByCodeMatkul(codeMatkul) != null) {
            binding.edtCodeMatkul.setError("Kode matkul sudah ada pada database!");
            return;
        }

        // Membuat object khs
        Khs khs = new Khs();

        // Set/Input/Masukan nilai ke object khs
        khs.setCodeMatkul(codeMatkul);
        khs.setNameMatkul(binding.edtNameMatkul.getText().toString());
        khs.setSks(sks);
        khs.setGrade(grade);
        khs.setLetterGrade(binding.edtLetterGrade.getText().toString());
        khs.setPredicate(binding.edtPredicate.getText().toString());

        // insert object khs ke database
        if (khsDao.insert(khs) > 0) {
            Toast.makeText(this, "Berhasil menambahkan mata kuliah " + khs.getNameMatkul(), Toast.LENGTH_SHORT).show();
        }
        // Method finish() melakukan mengeluarkan/pop activity sekarang dan kemabli ke activity sebelumnya.
        finish();
    }

}