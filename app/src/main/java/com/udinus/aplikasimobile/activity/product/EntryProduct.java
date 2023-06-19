package com.udinus.aplikasimobile.activity.product;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.udinus.aplikasimobile.database.model.Barang;
import com.udinus.aplikasimobile.databinding.ActivityEntryBarangBinding;

import java.util.Objects;
import java.util.UUID;

public class EntryProduct extends AppCompatActivity {
    private DatabaseReference databaseRef;
    ActivityEntryBarangBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEntryBarangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mengubah judul yang ada pada App Bar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Menambahkan Data Barang");

        // Inisialisasi DatabaseReference
        databaseRef = FirebaseDatabase.getInstance().getReference("barang");

        UUID uuid = UUID.randomUUID();
        String randomUUID8Char = uuid.toString().replaceAll("-", "").substring(0, 8);
        binding.edtKode.setText(randomUUID8Char);

        binding.btnSimpan.setOnClickListener(view -> insertBarang());

        binding.btnBatal.setOnClickListener(view -> finish());
    }

    private void insertBarang() {
        String kode = binding.edtKode.getText().toString().trim();
        String nama = binding.edtNama.getText().toString().trim();
        String satuan = binding.edtSatuan.getText().toString().trim();
        String hargaText = binding.edtHarga.getText().toString().trim();

        if (TextUtils.isEmpty(kode)) {
            binding.edtKode.setError("Isi kode barang");
            binding.edtKode.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(nama)) {
            binding.edtNama.setError("Isi nama barang");
            binding.edtNama.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(satuan)) {
            binding.edtSatuan.setError("Isi satuan barang");
            binding.edtSatuan.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(hargaText)) {
            binding.edtHarga.setError("Isi harga barang");
            binding.edtHarga.requestFocus();
            return;
        }

        double harga = Double.parseDouble(hargaText);

        // Membuat obyek barang/Barang
        Barang barang = new Barang();
        barang.setCode(kode);
        barang.setName(nama);
        barang.setSatuan(satuan);
        barang.setPrice(harga);

        // Mengambil referensi baru untuk barang di Firebase Realtime Database
        DatabaseReference newBarangRef = databaseRef.push();
        String barangKey = newBarangRef.getKey();

        if (barangKey != null) {
            // Menyimpan obyek barang ke Firebase Realtime Database
            newBarangRef.setValue(barang)
                    .addOnSuccessListener(aVoid -> {
                        // Menampilkan pesan berhasil dan kembali ke activity sebelumnya
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        // Menampilkan pesan kesalahan jika gagal menyimpan ke database
                        showError("Gagal menyimpan barang: " + e.getMessage());
                    });
        } else {
            // Menampilkan pesan kesalahan jika gagal mendapatkan kunci barang
            showError("Gagal mendapatkan kunci barang");
        }
    }
    private void showError(String errorMessage) {
        // Menampilkan AlertDialog untuk menampilkan pesan kesalahan
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(errorMessage)
                .setPositiveButton("OK", null)
                .show();
    }
}