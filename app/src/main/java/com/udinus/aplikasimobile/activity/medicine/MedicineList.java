package com.udinus.aplikasimobile.activity.medicine;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udinus.aplikasimobile.adapter.MedicineRvAdapter;
import com.udinus.aplikasimobile.databinding.ActivityMedicineListBinding;
import com.udinus.aplikasimobile.repository.ApiClient;
import com.udinus.aplikasimobile.repository.model.Medicine;
import com.udinus.aplikasimobile.repository.service.ApiResponse;
import com.udinus.aplikasimobile.repository.service.MedicineService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicineList extends AppCompatActivity {
    private DatabaseReference databaseRef;
    private ProgressDialog progressDialog;
    private  MedicineRvAdapter medicineRvAdapter;
    private final ArrayList<Medicine> medicineArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMedicineListBinding binding = ActivityMedicineListBinding.inflate(getLayoutInflater());
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
        medicineRvAdapter = new MedicineRvAdapter(medicineArrayList);

        medicineRvAdapter.setOnItemClickCallback(medicine -> {
            Intent intentDetail = new Intent(MedicineList.this, MedicineEdit.class);
            // Mengirimkan data khs ke activity DetailKhs dengan key "key_khs"
            intentDetail.putExtra("key_medicine", medicine);
            startActivity(intentDetail);
        });
        // Menghubungkan RecyclerView dengan Adapter diatas.
        binding.rvMedicine.setAdapter(medicineRvAdapter);

        // Tombol/Button yang melayang untuk navigasi ke EntryPage/Halaman Input Medicine
        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(MedicineList.this, MedicineEntry.class);
            startActivity(intent);
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Menampilkan ProgressDialog saat operasi sedang berjalan
        progressDialog.show();
        // Mengambil daftar medicine dari Firebase Realtime Database
        getFirebaseMedicines();
    }
    public void getApiMedicines() {
        progressDialog.show();
        MedicineService medicineService = ApiClient.getClient().create(MedicineService.class);
        Call<ApiResponse<List<Medicine>>> call = medicineService.getMedicine();
        call.enqueue(new Callback<ApiResponse<List<Medicine>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Medicine>>> call, Response<ApiResponse<List<Medicine>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Medicine>> apiResponse = response.body();
                    if (apiResponse != null) {
                        medicineArrayList.clear();
                        medicineArrayList.addAll(apiResponse.getData());
                        medicineRvAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(MedicineList.this, "Failed to fetch medicines", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<Medicine>>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MedicineList.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getFirebaseMedicines(){
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
                medicineRvAdapter.notifyDataSetChanged();

                // Menyembunyikan ProgressDialog setelah operasi selesai
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Menangani kesalahan jika terjadi
                AlertDialog.Builder builder = new AlertDialog.Builder(MedicineList.this);
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