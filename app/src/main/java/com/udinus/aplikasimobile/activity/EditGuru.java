package com.udinus.aplikasimobile.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.udinus.aplikasimobile.R;
import com.udinus.aplikasimobile.databinding.ActivityEditGuruBinding;
import com.udinus.aplikasimobile.repository.ApiClient;
import com.udinus.aplikasimobile.repository.model.Guru;
import com.udinus.aplikasimobile.repository.service.ApiResponse;
import com.udinus.aplikasimobile.repository.service.InterfaceGuru;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditGuru extends AppCompatActivity {
    ActivityEditGuruBinding binding;
    private Guru guru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditGuruBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setTitle("Mengubah Data Guru");

        guru = getIntent().getParcelableExtra("key_guru");

        binding.edtNip.setText(guru.getNip());
        binding.edtNip.setEnabled(false);
        binding.edtNama.setText(guru.getNama());
        binding.edtStatus.setText(guru.getStatus());
        binding.edtGaji.setText(String.valueOf(guru.getGaji()));

        binding.btnSave.setOnClickListener(view -> editGuru());
        binding.btnCancel.setOnClickListener(view -> finish());
        binding.btnHapus.setOnClickListener(view -> showDeleteConfirmationDialog());
    }

    private void editGuru() {
        if (TextUtils.isEmpty(binding.edtNip.getText())) {
            binding.tilNip.setError("Isi nip guru");
            binding.edtNip.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(binding.edtNama.getText())) {
            binding.tilNama.setError("Isi nama guru");
            binding.edtNama.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(binding.edtStatus.getText())) {
            binding.tilStatus.setError("Isi satuan guru");
            binding.edtStatus.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(binding.edtGaji.getText())) {
            binding.tilGaji.setError("Isi jabatan guru");
            binding.edtGaji.requestFocus();
            return;
        }

        String nip = binding.edtNip.getText().toString().trim();
        String nama = binding.edtNama.getText().toString().trim();
        String status = binding.edtStatus.getText().toString().trim();
        String gajiText = binding.edtGaji.getText().toString().trim();

        int gaji = Integer.parseInt(gajiText);

        guru.setNip(nip);
        guru.setNama(nama);
        guru.setStatus(status);
        guru.setGaji(gaji);

        putApiGuru(guru, this);
    }

    private void putApiGuru(Guru guru, Context context){
        InterfaceGuru interfaceGuru = ApiClient.getClient().create(InterfaceGuru.class);
        Call<ApiResponse<Guru>> call = interfaceGuru.putTeacher(guru.getNip(), guru);

        call.enqueue(new Callback<ApiResponse<Guru>>() {
            @Override
            public void onResponse(Call<ApiResponse<Guru>> call, Response<ApiResponse<Guru>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<Guru> guruResponse = response.body();
                    if (guruResponse != null) {
                        Guru updateGuru = guruResponse.getData();
                        Toast.makeText(context, "Success: " + updateGuru, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(context, "Failed to update guru", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Guru>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(context, "Terjadi kesalahan koneksi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteGuru() {
        InterfaceGuru interfaceGuru = ApiClient.getClient().create(InterfaceGuru.class);
        Call<ApiResponse<Void>> call = interfaceGuru.deleteTeacher(guru.getNip());

        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditGuru.this, "Data guru berhasil dihapus", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditGuru.this, "Gagal menghapus data guru", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(EditGuru.this, "Terjadi kesalahan koneksi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this).setTitle("Hapus").setMessage("Apakah Anda ingin menghapus " + guru.getNama() + "?").setIcon(R.drawable.round_delete_24).setPositiveButton("Hapus", (dialog, whichButton) -> {
            deleteGuru();
            dialog.dismiss();
        }).setNegativeButton("Batal", (dialog, which) -> dialog.dismiss()).create().show();
    }
}