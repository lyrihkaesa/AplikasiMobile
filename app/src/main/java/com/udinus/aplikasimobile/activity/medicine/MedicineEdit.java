package com.udinus.aplikasimobile.activity.medicine;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.udinus.aplikasimobile.R;
import com.udinus.aplikasimobile.databinding.ActivityMedicineEditBinding;
import com.udinus.aplikasimobile.repository.ApiClient;
import com.udinus.aplikasimobile.repository.model.Medicine;
import com.udinus.aplikasimobile.repository.service.ApiResponse;
import com.udinus.aplikasimobile.repository.service.MedicineService;
import com.udinus.aplikasimobile.utils.AppUtils;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicineEdit extends AppCompatActivity {
    ActivityMedicineEditBinding binding;
    private DatabaseReference databaseRef;
    private Medicine medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMedicineEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setTitle("Mengubah Data Medicine");

        medicine = getIntent().getParcelableExtra("key_medicine");

        databaseRef = FirebaseDatabase.getInstance().getReference("medicine");

        binding.edtCodeMedicine.setText(medicine.getCode());
        binding.edtCodeMedicine.setEnabled(false);

        binding.edtNameMedicine.setText(medicine.getName());
        binding.edtPriceMedicine.setText(AppUtils.convertPriceToText(medicine.getPrice()));
        binding.btnSave.setOnClickListener(view -> editMedicine());
        binding.btnCancel.setOnClickListener(view -> finish());
        binding.btnHapus.setOnClickListener(view -> showDeleteConfirmationDialog());
    }

    private void editMedicine() {
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
        medicine.setCode(code);
        medicine.setName(name);
        medicine.setPrice(price);

        if (!TextUtils.isEmpty(medicine.getKey())) {
            databaseRef.child(medicine.getKey()).setValue(medicine);
        }
        putApiMedicine(medicine, this);
        finish();
    }

    private void deleteMedicine() {
        if (!TextUtils.isEmpty(medicine.getKey())) {
            databaseRef.child(medicine.getKey()).removeValue();
        }
        deleteApiMedicine();
        finish();
    }

    private void putApiMedicine(Medicine medicine, Context context) {
        MedicineService medicineService = ApiClient.getClient().create(MedicineService.class);
        Call<ApiResponse<Medicine>> call = medicineService.putMedicine(medicine.getCode(), medicine);

        call.enqueue(new Callback<ApiResponse<Medicine>>() {
            @Override
            public void onResponse(Call<ApiResponse<Medicine>> call, Response<ApiResponse<Medicine>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<Medicine> medicineResponse = response.body();
                    if (medicineResponse != null) {
                        Medicine updateMedicine = medicineResponse.getData();
                        Toast.makeText(context, "Success: " + updateMedicine, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(context, "Failed to update medicine", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Medicine>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, "Terjadi kesalahan koneksi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteApiMedicine() {
        MedicineService medicineService = ApiClient.getClient().create(MedicineService.class);
        Call<ApiResponse<Void>> call = medicineService.deleteMedicine(medicine.getCode());
        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MedicineEdit.this, "Data guru berhasil dihapus", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(MedicineEdit.this, "Error delete medicine", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MedicineEdit.this, "Terjadi kesalahan koneksi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Hapus")
                .setMessage("Apakah Anda ingin menghapus " + medicine.getName() + "?")
                .setIcon(R.drawable.round_delete_24)
                .setPositiveButton("Hapus", (dialog, whichButton) -> {
                    deleteMedicine();
                    dialog.dismiss();
                })
                .setNegativeButton("Batal", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }
}
