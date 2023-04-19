package com.udinus.aplikasimobile.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.udinus.aplikasimobile.database.DatabaseHelper;
import com.udinus.aplikasimobile.database.dao.MahasiswaDao;
import com.udinus.aplikasimobile.database.dao.UserDao;
import com.udinus.aplikasimobile.database.model.Mahasiswa;
import com.udinus.aplikasimobile.database.model.User;
import com.udinus.aplikasimobile.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {
    private ActivityLoginBinding binding;
    DatabaseHelper databaseHelper;
    SQLiteDatabase database;
    private UserDao userDao;
    private MahasiswaDao mahasiswaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getReadableDatabase();
        userDao = new UserDao(database);
        mahasiswaDao = new MahasiswaDao(database);

        binding.btnLogin.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.logNimOrUsername.getText())) {
                binding.logNimOrUsername.setError("NIM atau Username tidak boleh kosong!");
                return;
            }
            if (TextUtils.isEmpty(binding.logPassword.getText())) {
                binding.logPassword.setError("Password tidak boleh kosong!");
                return;
            }

            String nimOrUsername = binding.logNimOrUsername.getText().toString();
            User user = userDao.getUserByNim(nimOrUsername);
            if (user == null) {
                binding.logNimOrUsername.setError("NIM atau Username " + nimOrUsername + " tidak ditemukan!");
                return;
            }
            String password = binding.logPassword.getText().toString();
            if (user.getPassword().equals(password)) {
                Intent intent = new Intent(Login.this, ListKhs.class);
                Mahasiswa mahasiswa = mahasiswaDao.findMahasiswaByNim(user.getNim());
                user.setMahasiswa(mahasiswa);
                intent.putExtra("key_user", user);
                startActivity(intent);
            } else {
                binding.logPassword.setError("Password anda salah!");
            }
        });
        binding.tvSwitchRegister.setOnClickListener(v -> startActivity(new Intent(Login.this, Register.class)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseHelper.close();
        database.close();
    }
}