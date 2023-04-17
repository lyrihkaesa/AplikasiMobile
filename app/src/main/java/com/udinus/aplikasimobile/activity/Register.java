package com.udinus.aplikasimobile.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.udinus.aplikasimobile.R;
import com.udinus.aplikasimobile.database.DatabaseHelper;
import com.udinus.aplikasimobile.database.dao.KhsDao;
import com.udinus.aplikasimobile.database.dao.UserDao;
import com.udinus.aplikasimobile.database.model.User;
import com.udinus.aplikasimobile.databinding.ActivityRegisterBinding;

public class Register extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    DatabaseHelper databaseHelper;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        userDao = new UserDao(database);

        binding.btnRegister.setOnClickListener(v -> {
            User user = new User();

            user.setUsername(binding.regUsername.getText().toString());
            user.setPassword(binding.regPassword.getText().toString());
            user.setEmail("kaesalyrih@gmail.com");
            userDao.insert(user);

            startActivity(new Intent(Register.this, Login.class));
        });
        binding.btnChangeLogin.setOnClickListener(v -> startActivity(new Intent(Register.this, Login.class)));


    }
}