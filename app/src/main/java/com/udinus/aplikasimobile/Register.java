package com.udinus.aplikasimobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    private EditText regNim, regName, regPassword, regConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseHelper = new DatabaseHelper(this);

        Button btnRegister = findViewById(R.id.btn_register);
        TextView tvSwitchRegsiter = findViewById(R.id.tv_switch_login);
        regNim = findViewById(R.id.reg_nim);
        regName = findViewById(R.id.reg_name);
        regPassword = findViewById(R.id.reg_password);
        regConfirmPassword = findViewById(R.id.reg_confirm_password);

        btnRegister.setOnClickListener(v -> {
            if (TextUtils.isEmpty(regNim.getText())) {
                // Tampilkan pesan kesalahan pada EditText
                regNim.setError("NIM harus diisi!");
                return;
            }

            if (TextUtils.isEmpty(regName.getText())) {
                // Tampilkan pesan kesalahan pada EditText
                regName.setError("Nama harus diisi!");
                return;
            }

            if (TextUtils.isEmpty(regPassword.getText())) {
                // Tampilkan pesan kesalahan pada EditText
                regPassword.setError("Password harus diisi!");
                return;
            }

            if (!regConfirmPassword.getText().toString().equals(regPassword.getText().toString())) {
                regConfirmPassword.setError("Password dan Confirm Password harus sama!");
                return;
            }

            Mahasiswa mahasiswa = new Mahasiswa();
            mahasiswa.setNim(regNim.getText().toString());
            mahasiswa.setName(regName.getText().toString());
            mahasiswa.setPassword(regPassword.getText().toString());

            if (databaseHelper.insertMhs(mahasiswa) > 0) {
                Toast.makeText(this, mahasiswa.getName() + " sukses mendaftarkan", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Register.this, Login.class));
            }
        });
        tvSwitchRegsiter.setOnClickListener(v -> startActivity(new Intent(Register.this, Login.class)));
    }
}