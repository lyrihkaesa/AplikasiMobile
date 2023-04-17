package com.udinus.aplikasimobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.udinus.aplikasimobile.MainActivity;
import com.udinus.aplikasimobile.R;
import com.udinus.aplikasimobile.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(v -> startActivity(new Intent(Login.this, MainActivity.class)));
        binding.btnSwitchRegister.setOnClickListener(v -> startActivity(new Intent(Login.this, Register.class)));
    }
}