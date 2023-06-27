package com.udinus.aplikasimobile.activity.khs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.udinus.aplikasimobile.R;
import com.udinus.aplikasimobile.adapter.KhsRvAdapter;
import com.udinus.aplikasimobile.databinding.ActivityKhsListBinding;
import com.udinus.aplikasimobile.repository.DatabaseHelper;
import com.udinus.aplikasimobile.repository.dao.KhsDao;
import com.udinus.aplikasimobile.repository.model.Khs;
import com.udinus.aplikasimobile.repository.model.User;
import com.udinus.aplikasimobile.utils.KhsUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class KhsList extends AppCompatActivity {
    ActivityKhsListBinding binding;
    DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private KhsDao khsDao;
    private final ArrayList<Khs> list = new ArrayList<>();
    private KhsRvAdapter khsRvAdapter;
    private static final String PREFS = "prefs_aplikasi_mobile";
    private static final String PREF_APP_FIRST_RUN = "isAppFirstRun";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Mengganti setContentView dengan binding
        binding = ActivityKhsListBinding.inflate(getLayoutInflater());
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


        // Menginisialisasi SharedPreferences dan memeriksa apakah aplikasi telah dijalankan untuk pertama kali.
        SharedPreferences settings = this.getSharedPreferences(PREFS, 0);
        // Mengambil nilai boolean dari kunci PREF_APP_FIRST_RUN, nilai default true jika belum disimpan sebelumnya
        boolean firstRun = settings.getBoolean(PREF_APP_FIRST_RUN, true);
        if (firstRun) {
            // Menambahkan data KHS awal ke database
            addListKhsToDatabase();
            // Menyimpan nilai false pada kunci PREF_APP_FIRST_RUN untuk mencegah penambahan data yang tidak perlu pada penggunaan selanjutnya.
            settings.edit().putBoolean(PREF_APP_FIRST_RUN, false).apply();
        }


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
            Intent intentDetail = new Intent(KhsList.this, KhsEdit.class);
            // Mengirimkan data khs ke activity DetailKhs dengan key "key_khs"
            intentDetail.putExtra("key_khs", khs);
            // Mengirimkan data user ke activity DetailKhs dengan key "key_user"
            intentDetail.putExtra("key_user", user);
            startActivity(intentDetail);
        });

        // Saat ImageButton btnEntryKhs diklik pindah activity EntryKhs, dengan membawa data: User
        binding.btnEntryKhs.setOnClickListener(v -> {
            Intent intent = new Intent(KhsList.this, KhsEntry.class);
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

    /**
     * Method untuk menghitung total SKS, IPK, dan jumlah mata kuliah pada suatu list Khs.
     * Jika list tidak null, maka method akan menghitung total SKS, IPK, dan jumlah mata kuliah.
     * Hasil perhitungan kemudian akan ditampilkan pada TextView yang sesuai.
     */
    private void countFooter() {
        Integer totalSks = 0;
        Double ipk = 0.0;
        Integer totalMatkul = 0;
        // Mengecek apakah dalam list null atau tidak
        if (list != null) {
            // Menghitung IPK
            ipk = KhsUtils.countIpk(list);
            // Menghitung total SKS
            totalSks = KhsUtils.countTotalSks(list);
            // Menghitung jumlah mata kuliah
            totalMatkul = list.size();
        }
        // Mengubah text pada TextView dengan hasil perhitungan diatas
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        binding.tvIpk.setText(String.valueOf(decimalFormat.format(ipk)));
        binding.tvTotalSks.setText(String.valueOf(totalSks));
        binding.tvTotalMatkul.setText(String.valueOf(totalMatkul));
    }

    /**
     * Menambahkan daftar data KHS ke dalam database.
     * Method ini mengambil data dari resource value > string dan integer yang didefinisikan
     * dalam file resources aplikasi, dan memasukkan data tersebut ke dalam objek Khs.
     * Setelah itu, objek Khs dimasukkan ke dalam database menggunakan objek khsDao.
     *
     * @see Khs
     * @see KhsDao
     * @see KhsUtils#convertGradetoLetterGrade(Double)
     */
    private void addListKhsToDatabase() {
        String[] dataCodeMatkul = getResources().getStringArray(R.array.data_code_matkul);
        String[] dataNameMatkul = getResources().getStringArray(R.array.data_name_matkul);
        int[] dataSks = getResources().getIntArray(R.array.data_sks);
        int[] dataGrade = getResources().getIntArray(R.array.data_grade);

        for (int i = 0; i < dataNameMatkul.length; i++) {
            Khs khs = new Khs();

            khs.setCodeMatkul(dataCodeMatkul[i]);
            khs.setNameMatkul(dataNameMatkul[i]);
            khs.setSks(dataSks[i]);
            khs.setGrade((double) dataGrade[i]);
            khs.setLetterGrade(KhsUtils.convertGradetoLetterGrade((double) dataGrade[i]));
            khs.setPredicate("Auto Generate");

            // insert object khs ke database
            khsDao.insert(khs);
        }
    }
}