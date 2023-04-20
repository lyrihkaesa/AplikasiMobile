package com.udinus.aplikasimobile.activity;

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
            // Pengecekan apakah EditText kosong atau tidak
            if (TextUtils.isEmpty(binding.regUsername.getText())) {
                // Tampilkan pesan kesalahan pada EditText
                binding.regUsername.setError("Username harus diisi!");
                return;
            }

            if (TextUtils.isEmpty(binding.regNim.getText())) {
                // Tampilkan pesan kesalahan pada EditText
                binding.regNim.setError("NIM harus diisi!");
                return;
            }

            if (TextUtils.isEmpty(binding.regName.getText())) {
                // Tampilkan pesan kesalahan pada EditText
                binding.regName.setError("Nama harus diisi!");
                return;
            }

            if (TextUtils.isEmpty(binding.regEmail.getText())) {
                // Tampilkan pesan kesalahan pada EditText
                binding.regEmail.setError("Email harus diisi!");
                return;
            }

            if (TextUtils.isEmpty(binding.regMajor.getText())) {
                // Tampilkan pesan kesalahan pada EditText
                binding.regMajor.setError("Jurusan harus diisi!");
                return;
            }

            if (TextUtils.isEmpty(binding.regDegree.getText())) {
                // Tampilkan pesan kesalahan pada EditText
                binding.regDegree.setError("Gelar harus diisi!");
                return;
            }

            if (TextUtils.isEmpty(binding.regPassword.getText())) {
                // Tampilkan pesan kesalahan pada TextInputLayout
                binding.tilPassword.setError("Password harus diisi!");
                return;
            }

            if(TextUtils.isEmpty(binding.regConfirmPassword.getText())){
                // Tampilkan pesan kesalahan pada TextInputLayout
                binding.tilConfirmPassword.setError("Confirm password harus diisi!");
                return;
            }

            // Pengecekan apakah EditText regConfirmPassword isinya sama dengan EditText regPassword
            if (!binding.regConfirmPassword.getText().toString().equals(binding.regPassword.getText().toString())) {
                // Tampilkan pesan kesalahan pada TextInputLayout
                binding.tilConfirmPassword.setError("Password dan Confirm Password harus sama!");
                return;
            }

            // Memastikan username belum dibuat pada database
            String username = binding.regUsername.getText().toString();
            if (userDao.findUserByNimOrUsername(username) != null) {
                binding.regUsername.setError("Username sudah ada!");
                return;
            }

            // Memastikan nim belum dibuat pada database
            String nim = binding.regNim.getText().toString();
            if (userDao.findUserByNimOrUsername(nim) != null && mahasiswaDao.findMahasiswaByNim(nim) != null) {
                binding.regNim.setError("NIM sudah ada!");
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
                startActivity(new Intent(Register.this, Login.class));
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