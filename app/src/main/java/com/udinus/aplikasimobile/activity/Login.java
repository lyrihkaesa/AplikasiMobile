package com.udinus.aplikasimobile.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.udinus.aplikasimobile.MainActivity;
import com.udinus.aplikasimobile.database.DatabaseHelper;
import com.udinus.aplikasimobile.database.dao.UserDao;
import com.udinus.aplikasimobile.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {

    private ActivityLoginBinding binding;

    DatabaseHelper databaseHelper;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        userDao = new UserDao(database);

        binding.btnLogin.setOnClickListener(v -> {
            String username = binding.logUsername.getText().toString();
            String password = binding.logPassword.getText().toString();
            if (userDao.isValid(username, password)) {
                startActivity(new Intent(Login.this, MainActivity.class));
            } else {
                Toast.makeText(this, "Username dan Password salah", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnSwitchRegister.setOnClickListener(v -> startActivity(new Intent(Login.this, Register.class)));
    }
}