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
        // Mengganti setContentView dengan binding
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inisialisasi database dan DAO
        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getReadableDatabase();
        userDao = new UserDao(database);
        mahasiswaDao = new MahasiswaDao(database);

        // Membuat kursor autofokus
        binding.logNimOrUsername.requestFocus();

        // Saat TextInputEditText logPassword diklik
        binding.logPassword.setOnClickListener(v -> {
            // Maka hilangkan pesan error pada TextInputLayout tilPassword
            binding.tilPassword.setError(null);
        });

        // Saat Button btnLogin diklik
        binding.btnLogin.setOnClickListener(v -> {
            // Pengecekan apakah EditText kosong atau tidak
            if (TextUtils.isEmpty(binding.logPassword.getText())) {
                binding.tilPassword.setError("Password tidak boleh kosong!");
                binding.logPassword.requestFocus();
            }
            if (TextUtils.isEmpty(binding.logNimOrUsername.getText())) {
                binding.tilNimOrUsername.setError("NIM atau Username tidak boleh kosong!");
                binding.logNimOrUsername.requestFocus();
                return;
            }

            // Mendapatkan dan mencari user berdasarkan NIM dan username mahasiswa pada tabel user
            String nimOrUsername = binding.logNimOrUsername.getText().toString();
            User user = userDao.findUserByNimOrUsername(nimOrUsername);

            // Pengecekan apakah user tidak ditemukan atau null
            if (user == null) {
                // Jika user tidak ditemukan atau null menampilkan pesan kesalahan pada EditText
                binding.tilNimOrUsername.setError("NIM atau Username " + nimOrUsername + " tidak ditemukan!");
                binding.logNimOrUsername.requestFocus();
                return;
            }

            // Mengecek apakah password yang dimasukan sama dengan password pada object user yang sudah didapatkan.
            String password = binding.logPassword.getText().toString();
            if (user.getPassword().equals(password)) {
                // Jika sama maka akan berpindah ke activity ListKhs
                Intent intent = new Intent(Login.this, ListKhs.class);
                // Dan sekaligus mendapatkan data mahasiswa pada tabel mhs
                Mahasiswa mahasiswa = mahasiswaDao.findMahasiswaByNim(user.getNim());
                // Serta memasukannya ke object user
                user.setMahasiswa(mahasiswa);

                // Mengirimkan data user ke Activity selanjutnya dengan key "key_user"
                intent.putExtra("key_user", user);
                startActivity(intent);
            } else {
                // Jika tidak sama maka akan menampilkan pesan kesalahan pada TextInputLayout
                binding.tilPassword.setError("Password anda salah!");
                binding.logPassword.requestFocus();
            }
        });

        // Saat TextView tvSwitchRegister diklik akan berpindah ke activity Login
        binding.tvSwitchRegister.setOnClickListener(v -> startActivity(new Intent(Login.this, Register.class)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Memastikan tidak ada kebocoran memori (memory leak) saat menggunakan database
        databaseHelper.close();
        database.close();
    }
}