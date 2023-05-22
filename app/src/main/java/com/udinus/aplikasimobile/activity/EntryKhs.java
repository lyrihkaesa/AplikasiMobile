package com.udinus.aplikasimobile.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.udinus.aplikasimobile.R;
import com.udinus.aplikasimobile.database.DatabaseHelper;
import com.udinus.aplikasimobile.database.dao.KhsDao;
import com.udinus.aplikasimobile.database.model.Khs;
import com.udinus.aplikasimobile.database.model.User;
import com.udinus.aplikasimobile.databinding.ActivityEntryKhsBinding;
import com.udinus.aplikasimobile.utils.KhsUtils;

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

        binding.edtCodeMatkul.requestFocus();

        // Membuat EditText edtLetterGrade tidak dapat diubah
        binding.edtLetterGrade.setEnabled(false);
        // Mengubah warna text pada EditText edtLetterGrade dengan warna merah
        binding.edtLetterGrade.setTextColor(getColor(R.color.red));

        // Mengubah EditText edtLetterGrade jika text pada edtGrade berubah.
        binding.edtGrade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Memastikan inputan nilai angka benar
                if(!s.toString().isEmpty()){
                    double grade;
                    try {
                        grade = Double.parseDouble(s.toString().trim());
                        if (grade < 0 || grade > 100) {
                            binding.tilGrade.setError("Nilai harus di antara 0 dan 100!");
                            binding.btnSave.setEnabled(false);
                            binding.btnSave.setBackgroundColor(getColor(R.color.grey));
                            return;
                        }
                    } catch (NumberFormatException e) {
                        binding.tilGrade.setError("Nilai angka harus diisi dengan angka bulat/desimal!");
                        return;
                    }
                    binding.tilGrade.setError(null);
                    // Memodifikasi letterGrade sesuai dengan grade yang dimasukan
                    String letterGrade = KhsUtils.convertGradetoLetterGrade(grade);
                    binding.edtLetterGrade.setText(letterGrade);
                    binding.btnSave.setEnabled(true);
                    binding.btnSave.setBackgroundColor(getColor(R.color.green));
                } else {
                    binding.edtLetterGrade.setText("");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

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
            binding.tilCodeMatkul.setError("Kode mata kuliah tidak boleh kosong!");
        }

        if (TextUtils.isEmpty(binding.edtNameMatkul.getText())) {
            binding.tilNameMatkul.setError("Nama mata kuliah tidak boleh kosong!");
        }

        if (TextUtils.isEmpty(binding.edtSks.getText())) {
            binding.tilSks.setError("SKS tidak boleh kosong!");
        }

        if (TextUtils.isEmpty(binding.edtGrade.getText())) {
            binding.tilGrade.setError("Nilai angka tidak boleh kosong!");
        }

        if (TextUtils.isEmpty(binding.edtLetterGrade.getText())) {
            binding.tilLetterGrade.setError("Nilai huruf tidak boleh kosong!");
        }

        if (TextUtils.isEmpty(binding.edtPredicate.getText())) {
            binding.tilPredicate.setError("Predikat tidak boleh kosong!");
            return;
        }

        // Memastikan inputan sks benar
        int sks;
        try {
            sks = Integer.parseInt(binding.edtSks.getText().toString().trim());
        } catch (NumberFormatException e) {
            binding.tilSks.setError("SKS harus diisi dengan angka bulat!");
            return;
        }

        // Memastikan kode mata kuliah tidak ada pada database
        String codeMatkul = binding.edtCodeMatkul.getText().toString();
        if (khsDao.findKhsByCodeMatkul(codeMatkul) != null) {
            binding.tilCodeMatkul.setError("Kode matkul sudah ada pada database!");
            return;
        }

        // Membuat object khs
        Khs khs = new Khs();

        // Set/Input/Masukan nilai ke object khs
        khs.setCodeMatkul(codeMatkul);
        khs.setNameMatkul(binding.edtNameMatkul.getText().toString());
        khs.setSks(sks);
        khs.setGrade(Double.valueOf(binding.edtGrade.getText().toString()));
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