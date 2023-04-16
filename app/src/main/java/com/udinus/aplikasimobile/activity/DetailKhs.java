package com.udinus.aplikasimobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.udinus.aplikasimobile.R;
import com.udinus.aplikasimobile.databinding.ActivityDetailKhsBinding;

import java.util.Objects;

public class DetailKhs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_khs);
        // Mengganti setContentView dengan binding
        ActivityDetailKhsBinding binding = ActivityDetailKhsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mengubah judul yang ada pada App Bar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Update dan Delete KHS");
    }
}