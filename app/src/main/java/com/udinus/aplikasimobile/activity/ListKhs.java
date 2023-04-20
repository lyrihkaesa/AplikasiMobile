package com.udinus.aplikasimobile.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.udinus.aplikasimobile.adapter.KhsRvAdapter;
import com.udinus.aplikasimobile.database.DatabaseHelper;
import com.udinus.aplikasimobile.database.dao.KhsDao;
import com.udinus.aplikasimobile.database.model.Khs;
import com.udinus.aplikasimobile.database.model.User;
import com.udinus.aplikasimobile.databinding.ActivityListKhsBinding;

import java.util.ArrayList;

public class ListKhs extends AppCompatActivity {
    ActivityListKhsBinding binding;
    DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private KhsDao khsDao;
    private final ArrayList<Khs> list = new ArrayList<>();
    private KhsRvAdapter khsRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mengganti setContentView dengan binding
        binding = ActivityListKhsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mengambil/mendapatkan data user yang dikirim dari activity sebelumnya dengan key "key_user"
        User user = getIntent().getParcelableExtra("key_user");

        // Mengubah nilai/value yang ada pada TableIdentity
        binding.tvNim.setText(user.getNim());
        binding.tvFullName.setText(user.getMahasiswa().getName());
        binding.tvMajor.setText(String.format("%s - %s", user.getMahasiswa().getMajor(), user.getMahasiswa().getDegree()));

        // Inisialisasi database dan DAO
        databaseHelper = new DatabaseHelper(this);
        database = databaseHelper.getWritableDatabase();
        khsDao = new KhsDao(database);

        binding.rvKhs.setHasFixedSize(true);
        // Mengatur layout linear pada RecyclerView
        binding.rvKhs.setLayoutManager(new LinearLayoutManager(this));

        list.clear();
        list.addAll(khsDao.getAll());

        // Inisialisasi adapter untuk RecyclerView
        khsRvAdapter = new KhsRvAdapter(list);

        // Menyambungkan adapter diatas ke RecyclerView pada XML.
        binding.rvKhs.setAdapter(khsRvAdapter);

        // Saat salah satu item diklik pindah ke activity DetailKhs, dengan membawa data: Khs
        khsRvAdapter.setOnItemClickCallback(khs -> {
            Intent intentDetail = new Intent(ListKhs.this, DetailKhs.class);
            // Mengirimkan data khs ke activity DetailKhs dengan key "key_khs"
            intentDetail.putExtra("key_khs", khs);
            // Mengirimkan data user ke activity DetailKhs dengan key "key_user"
            intentDetail.putExtra("key_user", user);
            startActivity(intentDetail);
        });

        // Saat ImageButton btnEntryKhs diklik pindah activity EntryKhs, dengan membawa data: User
        binding.btnEntryKhs.setOnClickListener(v -> {
            Intent intent = new Intent(ListKhs.this, EntryKhs.class);
            // Mengirimkan data user ke activity EntryKhs dengan key "key_user"
            intent.putExtra("key_user", user);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        /* Saat kembali lagi ke activity sekarang method/function onStart dipanggil
         * misalnya activity sebelumnya menjalankan method/function finish()
         * */
        list.clear();
        list.addAll(khsDao.getAll());
        // Memberikan notifikasi ke adapater RecycleView ada perubahan data
        // Berupa penghapusan atau perubahan jumlah item pada RecycleView
        khsRvAdapter.notifyItemRemoved(0);
        khsRvAdapter.notifyItemRangeChanged(0, list.size());
        // Memanggil/menjalankan method/fucntion countFooter()
        countFooter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Memastikan tidak ada kebocoran memori (memory leak) saat menggunakan database
        databaseHelper.close();
        database.close();
    }

    private void countFooter() {
        Double totalGrades = 0.0;
        Integer totalSks = 0;
        Double ipk = 0.0;
        Integer totalMatkul = 0;
        // Mengecek apakah dalam list ada data atau tidak
        if (list != null && list.size() > 0) {
            // Jika kondisi diatas terpenuhi menjalankan perulangan/for loop dari data ArrayListKhs
            // Lalu menjumlahkan satu persatu grade dan sks
            for (Khs khs : list) {
                totalGrades += khs.getGrade();
                totalSks += khs.getSks();
            }
            // Menghitung ipk total nilai setiap matkul dibagi jumlah matakuliah
            ipk = totalGrades / list.size();
            totalMatkul = list.size();
        }
        // Mengubah text pada TextView dengan hasil perhitungan diatas
        binding.tvIpk.setText(String.valueOf(ipk));
        binding.tvTotalSks.setText(String.valueOf(totalSks));
        binding.tvTotalMatkul.setText(String.valueOf(totalMatkul));
    }
}