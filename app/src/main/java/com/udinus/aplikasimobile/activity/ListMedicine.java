package com.udinus.aplikasimobile.activity;

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
import com.udinus.aplikasimobile.adapter.MedicineRvAdapter;
import com.udinus.aplikasimobile.database.model.Medicine;
import com.udinus.aplikasimobile.databinding.ActivityListMedicineBinding;

import java.util.ArrayList;
import java.util.Objects;

public class ListMedicine extends AppCompatActivity {
    private DatabaseReference databaseRef;
    private final ArrayList<Medicine> medicineArrayList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private  MedicineRvAdapter medicineRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityListMedicineBinding binding = ActivityListMedicineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mengubah judul yang ada pada App Bar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Daftar Obat");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        // Inisialisasi DatabaseReference
        databaseRef = FirebaseDatabase.getInstance().getReference("medicine");

        binding.rvMedicine.setHasFixedSize(true);
        // Mengatur LayoutManager pada RecycleView Medicine dengan LinearLayoutManager
        binding.rvMedicine.setLayoutManager(new LinearLayoutManager(this));
        // Deklarasi Adapter RecyclerView Medicine
        medicineRecyclerViewAdapter = new MedicineRvAdapter(medicineArrayList);

        medicineRecyclerViewAdapter.setOnItemClickCallback(medicine -> {
            Intent intentDetail = new Intent(ListMedicine.this, EditMedicine.class);
            // Mengirimkan data khs ke activity DetailKhs dengan key "key_khs"
            intentDetail.putExtra("key_medicine", medicine);
            startActivity(intentDetail);
        });
        // Menghubungkan RecyclerView dengan Adapter diatas.
        binding.rvMedicine.setAdapter(medicineRecyclerViewAdapter);

        // Tombol/Button yang melayang untuk navigasi ke EntryPage/Halaman Input Medicine
        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(ListMedicine.this, EntryMedicine.class);
            startActivity(intent);
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Menampilkan ProgressDialog saat operasi sedang berjalan
        progressDialog.show();
        // Mengambil daftar medicine dari Firebase Realtime Database
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                medicineArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Medicine medicine = snapshot.getValue(Medicine.class);
                    if (medicine != null) {
                        medicine.setKey(snapshot.getKey());
                        medicineArrayList.add(medicine);
                    }
                }
                // Memberitahu adapter bahwa data telah berubah
                medicineRecyclerViewAdapter.notifyDataSetChanged();

                // Menyembunyikan ProgressDialog setelah operasi selesai
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Menangani kesalahan jika terjadi
                AlertDialog.Builder builder = new AlertDialog.Builder(ListMedicine.this);
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