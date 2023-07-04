package com.udinus.aplikasimobile.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.udinus.aplikasimobile.adapter.GuruRecycleViewAdapter;
import com.udinus.aplikasimobile.databinding.ActivityDaftarGuruBinding;
import com.udinus.aplikasimobile.repository.ApiClient;
import com.udinus.aplikasimobile.repository.model.Guru;
import com.udinus.aplikasimobile.repository.service.ApiResponse;
import com.udinus.aplikasimobile.repository.service.InterfaceGuru;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarGuru extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private GuruRecycleViewAdapter guruRecycleViewAdapter;
    private ArrayList<Guru> guruArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDaftarGuruBinding binding = ActivityDaftarGuruBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setTitle("Daftar Guru");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        binding.rvGuru.setHasFixedSize(true);
        binding.rvGuru.setLayoutManager(new LinearLayoutManager(this));

        guruRecycleViewAdapter = new GuruRecycleViewAdapter(guruArrayList);
        binding.rvGuru.setAdapter(guruRecycleViewAdapter);

        guruRecycleViewAdapter.setOnItemClickCallback(guru -> {
            Intent intent = new Intent(DaftarGuru.this, EditGuru.class);
            intent.putExtra("key_guru", guru);
            startActivity(intent);
        });
        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(DaftarGuru.this, EntryGuru.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.show();
        getApiGuru();
    }

    public void getApiGuru() {
        progressDialog.show();
        InterfaceGuru interfaceGuru = ApiClient.getClient().create(InterfaceGuru.class);
        Call<ApiResponse<List<Guru>>> call = interfaceGuru.getGurus();
        call.enqueue(new Callback<ApiResponse<List<Guru>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Guru>>> call, Response<ApiResponse<List<Guru>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Guru>> apiResponse = response.body();
                    if (apiResponse != null) {
                        guruArrayList.clear();
                        guruArrayList.addAll(apiResponse.getData());
                        guruRecycleViewAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(DaftarGuru.this, "Gagal mengambil data guru", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ApiResponse<List<Guru>>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(DaftarGuru.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}