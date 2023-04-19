package com.udinus.aplikasimobile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    private EditText logNim, logPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);

        Button btnLogin = findViewById(R.id.btn_login);
        TextView tvSwitchRegister = findViewById(R.id.tv_switch_register);
        logNim = findViewById(R.id.log_nim);
        logPassword = findViewById(R.id.log_password);

        btnLogin.setOnClickListener(v -> {
            if (TextUtils.isEmpty(logNim.getText())) {
                logNim.setError("NIM tidak boleh kosong!");
                return;
            }
            if (TextUtils.isEmpty(logPassword.getText())) {
                logPassword.setError("Password tidak boleh kosong!");
                return;
            }

            Mahasiswa mahasiswa = databaseHelper.findMhsByNim(logNim.getText().toString());
            if (mahasiswa == null) {
                logNim.setError("NIM " + logNim.getText().toString() + " tidak ditemukan!");
                return;
            }

            String password = logPassword.getText().toString();
            if (mahasiswa.getPassword().equals(password)) {
                Intent intent = new Intent(Login.this, ListKhs.class);
                intent.putExtra("key_mhs", mahasiswa);
                startActivity(intent);
            } else {
                logPassword.setError("Password anda salah!");
            }
        });
        tvSwitchRegister.setOnClickListener(v -> startActivity(new Intent(Login.this, Register.class)));
    }
}