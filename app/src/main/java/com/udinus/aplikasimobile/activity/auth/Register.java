package com.udinus.aplikasimobile.activity.auth;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.udinus.aplikasimobile.R;
import com.udinus.aplikasimobile.database.DatabaseHelper;
import com.udinus.aplikasimobile.database.dao.MahasiswaDao;
import com.udinus.aplikasimobile.database.dao.UserDao;
import com.udinus.aplikasimobile.database.model.Mahasiswa;
import com.udinus.aplikasimobile.database.model.User;
import com.udinus.aplikasimobile.databinding.ActivityRegisterBinding;

public class Register extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    DatabaseHelper databaseHelper;
    SQLiteDatabase database;
    private UserDao userDao;
    private MahasiswaDao mahasiswaDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mengganti setContentView dengan binding
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inisialisasi database dan DAO
        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();
        userDao = new UserDao(database);
        mahasiswaDao = new MahasiswaDao(database);

        // Memberikan default nilai pada EditText
        binding.regMajor.setText(R.string.default_major);
        binding.regDegree.setText(R.string.default_degree);
        binding.regNim.setText(R.string.default_nim);

        // Membuat kursor autofokus
        binding.regUsername.requestFocus();

        // Saat TextInputEditText regPassword diklik
        binding.regPassword.setOnClickListener(v -> {
            // Maka hilangkan pesan error pada TextInputLayout tilPassword
            binding.tilPassword.setError(null);
        });

        // Saat TextInputEditText regConfirmPassword diklik
        binding.regConfirmPassword.setOnClickListener(v -> {
            // Maka hilangkan pesan error pada TextInputLayout tilConfirmPassword
            binding.tilConfirmPassword.setError(null);
        });

        // Saat Button btnRegister diklik
        binding.btnRegister.setOnClickListener(v -> {
            // Pengecekan apakah TextInputEditText kosong atau tidak
            if (TextUtils.isEmpty(binding.regUsername.getText())) {
                binding.tilUsername.setError("Username harus diisi!");
            }

            if (TextUtils.isEmpty(binding.regNim.getText())) {
                binding.tilNim.setError("NIM harus diisi!");
            }

            if (TextUtils.isEmpty(binding.regName.getText())) {
                binding.tilName.setError("Nama harus diisi!");
            }

            if (TextUtils.isEmpty(binding.regEmail.getText())) {
                binding.tilEmail.setError("Email harus diisi!");
            }

            if (TextUtils.isEmpty(binding.regMajor.getText())) {
                binding.tilMajor.setError("Jurusan harus diisi!");
            }

            if (TextUtils.isEmpty(binding.regDegree.getText())) {
                binding.tilDegree.setError("Gelar harus diisi!");
            }

            if (TextUtils.isEmpty(binding.regPassword.getText())) {
                binding.tilPassword.setError("Password harus diisi!");
            }

            if (TextUtils.isEmpty(binding.regConfirmPassword.getText())) {
                binding.tilConfirmPassword.setError("Confirm password harus diisi!");
            }

            // Pengecekan apakah EditText regConfirmPassword isinya sama dengan EditText regPassword
            if (!binding.regConfirmPassword.getText().toString().equals(binding.regPassword.getText().toString())) {
                binding.tilConfirmPassword.setError("Password dan Confirm Password harus sama!");
                binding.regConfirmPassword.requestFocus();
                return;
            }

            // Memastikan username belum dibuat pada database
            String username = binding.regUsername.getText().toString();
            if (userDao.findUserByNimOrUsername(username) != null) {
                binding.tilUsername.setError("Username sudah ada!");
                binding.regUsername.requestFocus();
                return;
            }

            // Memastikan nim belum dibuat pada database
            String nim = binding.regNim.getText().toString();
            if (userDao.findUserByNimOrUsername(nim) != null && mahasiswaDao.findMahasiswaByNim(nim) != null) {
                binding.tilNim.setError("NIM sudah ada!");
                binding.regNim.requestFocus();
                return;
            }

            // Memasukan semua data pada EditText ke Object User
            User user = new User();
            user.setUsername(username);
            user.setPassword(binding.regPassword.getText().toString());
            user.setNim(nim);

            // Memasukan semua data pada EditText ke Object Mahasiswa
            Mahasiswa mahasiswa = new Mahasiswa();
            mahasiswa.setNim(nim);
            mahasiswa.setName(binding.regName.getText().toString());
            mahasiswa.setEmail(binding.regEmail.getText().toString());
            mahasiswa.setMajor(binding.regMajor.getText().toString());
            mahasiswa.setDegree(binding.regDegree.getText().toString());

            // Memasukan object mahasiswa ke user
            user.setMahasiswa(mahasiswa);

            // Mengecek apakah berhasil insert data user dan mahasiswa ke table user dan mhs pada database
            if (userDao.insert(user) > 0 && mahasiswaDao.insert(mahasiswa) > 0) {
                Toast.makeText(this, mahasiswa.getName() + " sukses mendaftarkan", Toast.LENGTH_SHORT).show();
                // Jika berhasil maka pindah ke activity Login
                finish();
            }
        });

        // Saat TextView tvSwitchLogin diklik akan berpindah ke activity Register
        binding.tvSwitchLogin.setOnClickListener(v -> startActivity(new Intent(Register.this, Login.class)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Memastikan tidak ada kebocoran memori (memory leak) saat menggunakan database
        databaseHelper.close();
        database.close();
    }
}