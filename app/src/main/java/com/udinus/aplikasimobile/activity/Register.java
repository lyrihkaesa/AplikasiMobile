package com.udinus.aplikasimobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.udinus.aplikasimobile.R;
import com.udinus.aplikasimobile.databinding.ActivityRegisterBinding;

public class Register extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnRegister.setOnClickListener(v -> startActivity(new Intent(Register.this, Login.class)));
        binding.btnChangeLogin.setOnClickListener(v -> startActivity(new Intent(Register.this, Login.class)));
    }
}