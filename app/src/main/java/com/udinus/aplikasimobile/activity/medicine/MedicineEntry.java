package com.udinus.aplikasimobile.activity.medicine;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.udinus.aplikasimobile.databinding.ActivityMedicineEntryBinding;
import com.udinus.aplikasimobile.repository.ApiClient;
import com.udinus.aplikasimobile.repository.model.Medicine;
import com.udinus.aplikasimobile.repository.service.ApiResponse;
import com.udinus.aplikasimobile.repository.service.MedicineService;

import java.util.Objects;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicineEntry extends AppCompatActivity {
    ActivityMedicineEntryBinding binding;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedicineEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mengubah judul yang ada pada App Bar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Menambahkan Data Medicine");

        // Inisialisasi DatabaseReference
        databaseRef = FirebaseDatabase.getInstance().getReference("medicine");

        UUID uuid = UUID.randomUUID();
        String randomUUID8Char = uuid.toString().replaceAll("-", "").substring(0, 8);
        binding.edtCodeMedicine.setText(randomUUID8Char);

        binding.btnSave.setOnClickListener(view -> insert());

        binding.btnCancel.setOnClickListener(view -> finish());
    }

    private void insert() {
        if (TextUtils.isEmpty(binding.edtCodeMedicine.getText())) {
            binding.tilCodeMedicine.setError("Isi kode medicine");
            binding.edtCodeMedicine.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(binding.edtNameMedicine.getText())) {
            binding.tilNameMedicine.setError("Isi nama medicine");
            binding.edtNameMedicine.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(binding.edtPriceMedicine.getText())) {
            binding.tilPriceMedicine.setError("Isi harga medicine");
            binding.edtPriceMedicine.requestFocus();
            return;
        }

        String code = binding.edtCodeMedicine.getText().toString().trim();
        String name = binding.edtNameMedicine.getText().toString().trim();
        String priceText = binding.edtPriceMedicine.getText().toString().trim();

        double price = Double.parseDouble(priceText);

        // Membuat obyek medicine/Medicine
        Medicine medicine = new Medicine();
        medicine.setCode(code);
        medicine.setName(name);
        medicine.setPrice(price);

        DatabaseReference newMedicineRef = databaseRef.push();
        String medicineKey = newMedicineRef.getKey();

        if (medicineKey != null) {
            newMedicineRef.setValue(medicine)
                    .addOnSuccessListener(aVoid -> {
                        medicine.setKey(medicineKey);
                        postApiMedicine(medicine, this);
                        finish();
                    })
                    .addOnFailureListener(e -> showError("Gagal menyimpan medicine: " + e.getMessage()));
        } else {
            showError("Gagal mendapatkan kunci medicine");
        }
    }

    private void postApiMedicine(Medicine medicine, Context context) {
        MedicineService medicineService = ApiClient.getClient().create(MedicineService.class);
        Call<ApiResponse<Medicine>> call = medicineService.postMedicine(medicine);
        call.enqueue(new Callback<ApiResponse<Medicine>>() {
            @Override
            public void onResponse(Call<ApiResponse<Medicine>> call, Response<ApiResponse<Medicine>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<Medicine> apiResponse = response.body();
                    if (apiResponse != null) {
                        Toast.makeText(context, "Medicine created successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(context, "Failed to create medicine", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Medicine>> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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