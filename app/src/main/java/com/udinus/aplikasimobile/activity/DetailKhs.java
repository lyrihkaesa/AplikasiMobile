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
        binding = ActivityDetailKhsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Mengganti setContentView dengan binding
        binding = ActivityDetailKhsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        User user = getIntent().getParcelableExtra("key_user");

        binding.tvNim.setText(user.getNim());
        binding.tvFullName.setText(user.getMahasiswa().getName());
        binding.tvMajor.setText(String.format("%s - %s", user.getMahasiswa().getMajor(), user.getMahasiswa().getDegree()));


        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();
        khsDao = new KhsDao(database);

        khs = getIntent().getParcelableExtra("key_khs");

        binding.edtCodeMatkul.setText(khs.getCodeMatkul());
        binding.edtNameMatkul.setText(khs.getNameMatkul());
        binding.edtSks.setText(String.valueOf(khs.getSks()));
        binding.edtGrade.setText(String.valueOf(khs.getGrade()));
        binding.edtLetterGrade.setText(khs.getLetterGrade());
        binding.edtPredicate.setText(khs.getPredicate());

        binding.edtCodeMatkul.setEnabled(false);
        binding.edtCodeMatkul.setTextColor(getColor(R.color.red_500));

        binding.btnSave.setOnClickListener(v -> editKhs());

        binding.btnCancel.setOnClickListener(v -> finish());

        binding.btnDelete.setOnClickListener(v -> {
            AlertDialog diaBox = AskOption();
            diaBox.show();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

        // insert object khs ke database
        if (khsDao.update(khs) > 0) {
            Toast.makeText(this, "Berhasil menambahkan mata kuliah " + khs.getNameMatkul(), Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private AlertDialog AskOption() {
        return new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Hapus").setMessage("Apakah anda ingin menghapus " + khs.getNameMatkul() + "?").setIcon(R.drawable.round_delete_24).setPositiveButton("Hapus", (dialog, whichButton) -> {
                    //your deleting code
                    if (khsDao.delete(binding.edtCodeMatkul.getText().toString()) > 0) {
                        Toast.makeText(this, "Berhasil menghapus mata kuliah " + khs.getNameMatkul(), Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                    finish();
                }).setNegativeButton("Batal", (dialog, which) -> dialog.dismiss()).create();
    }
}