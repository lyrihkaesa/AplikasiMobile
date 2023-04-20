package com.udinus.aplikasimobile.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.udinus.aplikasimobile.R;
import com.udinus.aplikasimobile.database.DatabaseHelper;
import com.udinus.aplikasimobile.database.dao.KhsDao;
import com.udinus.aplikasimobile.database.model.Khs;
import com.udinus.aplikasimobile.database.model.User;
import com.udinus.aplikasimobile.databinding.ActivityDetailKhsBinding;

public class DetailKhs extends AppCompatActivity {

    private ActivityDetailKhsBinding binding;
    DatabaseHelper databaseHelper;
    SQLiteDatabase database;
    private KhsDao khsDao;
    Khs khs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mengganti setContentView dengan binding
        binding = ActivityDetailKhsBinding.inflate(getLayoutInflater());
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

        // Mengambil/mendapatkan data khs yang dirim dari activity sebelumnya dengan key "key_khs"
        khs = getIntent().getParcelableExtra("key_khs");

        // Memasukan data khs dari activity sebelumnya ke EditText
        binding.edtCodeMatkul.setText(khs.getCodeMatkul());
        binding.edtNameMatkul.setText(khs.getNameMatkul());
        binding.edtSks.setText(String.valueOf(khs.getSks()));
        binding.edtGrade.setText(String.valueOf(khs.getGrade()));
        binding.edtLetterGrade.setText(khs.getLetterGrade());
        binding.edtPredicate.setText(khs.getPredicate());

        // Membuat EditText edtCodeMatkul tidak dapat diubah
        binding.edtCodeMatkul.setEnabled(false);
        // Mengubah warna text pada EditText edtCodeMatkul dengan warna merah
        binding.edtCodeMatkul.setTextColor(getColor(R.color.red_500));

        // Saat Button save diklik menjalankan/memanggil method/function editKhs()
        binding.btnSave.setOnClickListener(v -> editKhs());

        // Saat Button cancel diklik menjalankan/memangil method/function finish()
        // Method finish() melakukan mengeluarkan/pop activity sekarang dan kemabli ke activity sebelumnya.
        binding.btnCancel.setOnClickListener(v -> finish());

        // Saat button delete diklik menjalakan/memangil AlertDialog/PopUp/Modal pemberitahuan
        // penghapusan khs pada database
        binding.btnDelete.setOnClickListener(v -> {
            AlertDialog diaBox = AskOption();
            diaBox.show();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Memastikan tidak ada kebocoran memori (memory leak) saat menggunakan database
        databaseHelper.close();
        database.close();
    }

    private void editKhs() {
        // Membuat object khs
        Khs khs = new Khs();

        // Set/Input/Masukan nilai ke object khs
        khs.setCodeMatkul(binding.edtCodeMatkul.getText().toString());
        khs.setNameMatkul(binding.edtNameMatkul.getText().toString());
        khs.setSks(Integer.valueOf(binding.edtSks.getText().toString()));
        khs.setGrade(Double.valueOf(binding.edtGrade.getText().toString()));
        khs.setLetterGrade(binding.edtLetterGrade.getText().toString());
        khs.setPredicate(binding.edtPredicate.getText().toString());

        // update object khs ke database
        if (khsDao.update(khs) > 0) {
            Toast.makeText(this, "Berhasil menambahkan mata kuliah " + khs.getNameMatkul(), Toast.LENGTH_SHORT).show();
        }
        // Method finish() melakukan mengeluarkan/pop activity sekarang dan kemabli ke activity sebelumnya.
        finish();
    }

    private AlertDialog AskOption() {
        return new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Hapus").setMessage("Apakah anda ingin menghapus " + khs.getNameMatkul() + "?").setIcon(R.drawable.round_delete_24).setPositiveButton("Hapus", (dialog, whichButton) -> {
                    // Jika "hapus" diklik maka menjalankan kode debawah ini
                    if (khsDao.delete(binding.edtCodeMatkul.getText().toString()) > 0) {
                        Toast.makeText(this, "Berhasil menghapus mata kuliah " + khs.getNameMatkul(), Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                    // Method finish() melakukan mengeluarkan/pop activity sekarang dan kemabli ke activity sebelumnya.
                    finish();
                    // Jika batal diklik maka menjalankan kode dibawah ini
                }).setNegativeButton("Batal", (dialog, which) -> dialog.dismiss()).create();
    }
}