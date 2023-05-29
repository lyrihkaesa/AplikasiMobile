package com.udinus.aplikasimobile.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.udinus.aplikasimobile.R;
import com.udinus.aplikasimobile.database.DatabaseHelper;
import com.udinus.aplikasimobile.database.dao.BarangDao;
import com.udinus.aplikasimobile.database.model.Barang;
import com.udinus.aplikasimobile.databinding.ActivityEditBarangBinding;

import java.util.Objects;

public class EditBarang extends AppCompatActivity {
    ActivityEditBarangBinding binding;
    DatabaseHelper databaseHelper;
    private BarangDao barangDao;

    Barang barang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityEditBarangBinding binding = ActivityEditBarangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mengubah judul yang ada pada App Bar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Mengubah Data Barang");

        // Mengambil data dari intent dengan ParcelableExtra
        barang = getIntent().getParcelableExtra("key_barang");

        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        barangDao = new BarangDao(database);

        binding.tvKode.setText(barang.getCode());

        binding.edtNama.setText(barang.getName());

        binding.edtSatuan.setText(barang.getSatuan());

        binding.edtHarga.setText(String.format(String.valueOf(barang.getPrice())));

        binding.btnSimpan.setOnClickListener(view -> editBarang());

        binding.btnBatal.setOnClickListener(view -> batal());

        binding.btnHapus.setOnClickListener(view -> {
            AlertDialog diaBox = AskOption();
            diaBox.show();
        });
    }

    private void editBarang() {
        // Membuat obyek barang/Barang
        Barang barang = new Barang();

        // Set/Input/Masukan nilai ke obyek barang/Barang
        barang.setCode(binding.tvKode.getText().toString());
        barang.setName(binding.edtNama.getText().toString());
        barang.setSatuan(binding.edtSatuan.getText().toString());
        barang.setPrice(Double.valueOf(binding.edtHarga.getText().toString()));

        // insert obyek barang yang sudah ada nilainya diatas ke database
        barangDao.update(barang);

        // Pindah dari halaman EntryBarang/InputBarang ke ListBarang
        Intent intent = new Intent(EditBarang.this, ListBarang.class);
        startActivity(intent);
    }

    private void deleteBarang() {
        // insert obyek barang yang sudah ada nilainya diatas ke database
        int result = barangDao.delete(barang.getCode());

        if(result > 0) {
            // Pindah dari halaman EntryBarang/InputBarang ke ListBarang
            Intent intent = new Intent(EditBarang.this, ListBarang.class);
            startActivity(intent);
        }
    }

    private void batal() {
        // Pindah dari halaman EntryBarang/InputBarang ke ListBarang
        Intent intent = new Intent(EditBarang.this, ListBarang.class);
        startActivity(intent);
    }

    private AlertDialog AskOption() {
        return new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Hapus")
                .setMessage("Apakah anda ingin menghapus " + barang.getName() + "?")
                .setIcon(R.drawable.round_delete_24)
                .setPositiveButton("Hapus", (dialog, whichButton) -> {
                    //your deleting code
                    deleteBarang();
                    dialog.dismiss();
                })
                .setNegativeButton("Batal", (dialog, which) -> dialog.dismiss())
                .create();
    }
}