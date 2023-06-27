package com.udinus.aplikasimobile.activity.product;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udinus.aplikasimobile.adapter.BarangRvAdapter;
import com.udinus.aplikasimobile.databinding.ActivityProductListBinding;
import com.udinus.aplikasimobile.repository.model.Barang;

import java.util.ArrayList;
import java.util.Objects;

public class ProductList extends AppCompatActivity {
    private DatabaseReference databaseRef;
    private final ArrayList<Barang> barangArrayList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private  BarangRvAdapter barangRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProductListBinding binding = ActivityProductListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mengubah judul yang ada pada App Bar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Daftar Barang");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        // Inisialisasi DatabaseReference
        databaseRef = FirebaseDatabase.getInstance().getReference("barang");

        binding.rvBarang.setHasFixedSize(true);
        // Mengatur LayoutManager pada RecycleView Barang dengan LinearLayoutManager
        binding.rvBarang.setLayoutManager(new LinearLayoutManager(this));
        // Deklarasi Adapter RecyclerView Barang
        barangRecyclerViewAdapter = new BarangRvAdapter(barangArrayList);

        barangRecyclerViewAdapter.setOnItemClickCallback(barang -> {
            Intent intentDetail = new Intent(ProductList.this, ProductEdit.class);
            // Mengirimkan data khs ke activity DetailKhs dengan key "key_khs"
            intentDetail.putExtra("key_barang", barang);
            startActivity(intentDetail);
        });
        // Menghubungkan RecyclerView dengan Adapter diatas.
        binding.rvBarang.setAdapter(barangRecyclerViewAdapter);

        // Tombol/Button yang melayang untuk navigasi ke EntryPage/Halaman Input Barang
        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(ProductList.this, ProductEntry.class);
            startActivity(intent);
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Menampilkan ProgressDialog saat operasi sedang berjalan
        progressDialog.show();
        // Mengambil daftar barang dari Firebase Realtime Database
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                barangArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Barang barang = snapshot.getValue(Barang.class);
                    if (barang != null) {
                        barang.setKey(snapshot.getKey());
                        barangArrayList.add(barang);
                    }
                }
                // Memberitahu adapter bahwa data telah berubah
                barangRecyclerViewAdapter.notifyDataSetChanged();

                // Menyembunyikan ProgressDialog setelah operasi selesai
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Menangani kesalahan jika terjadi
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductList.this);
                builder.setTitle("Error")
                        .setMessage("Error: " + databaseError.getMessage())
                        .setPositiveButton("Refresh", (dialog, which) -> {
                            // Memperbarui aktivitas saat tombol Refresh diklik
                            recreate();
                        })
                        .show();
            }
        });
    }
}