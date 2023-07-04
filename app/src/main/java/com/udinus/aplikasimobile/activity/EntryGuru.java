package com.udinus.aplikasimobile.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.udinus.aplikasimobile.databinding.ActivityEntryGuruBinding;
import com.udinus.aplikasimobile.repository.ApiClient;
import com.udinus.aplikasimobile.repository.model.Guru;
import com.udinus.aplikasimobile.repository.service.ApiResponse;
import com.udinus.aplikasimobile.repository.service.InterfaceGuru;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EntryGuru extends AppCompatActivity {
    ActivityEntryGuruBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEntryGuruBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setTitle("Menambahkan Data Guru");

        binding.btnSave.setOnClickListener(view -> insert());
        binding.btnCancel.setOnClickListener(view -> finish());
    }
    private void insert() {
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
            binding.tilStatus.setError("Isi status guru");
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

        Guru guru = new Guru();
        guru.setNip(nip);
        guru.setNama(nama);
        guru.setStatus(status);
        guru.setGaji(gaji);

        postApiGuru(guru, this);
        finish();
    }

    private void postApiGuru(Guru guru, Context context){
        InterfaceGuru interfaceGuru = ApiClient.getClient().create(InterfaceGuru.class);
        Call<ApiResponse<Guru>> call = interfaceGuru.postGuru(guru);
        call.enqueue(new Callback<ApiResponse<Guru>>() {
            @Override
            public void onResponse(Call<ApiResponse<Guru>> call, Response<ApiResponse<Guru>> response) {
                if(response.isSuccessful()){
                    ApiResponse<Guru> apiResponse = response.body();
                    if(apiResponse != null){
                        Toast.makeText(context, "Data guru berhasil dibuat", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }  else {
                    Toast.makeText(context, "Gagal membuat data guru", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Guru>> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}