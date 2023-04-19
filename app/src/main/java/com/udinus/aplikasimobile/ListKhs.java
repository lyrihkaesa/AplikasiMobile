package com.udinus.aplikasimobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListKhs extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    private final ArrayList<Khs> khsArrayList = new ArrayList<>();
    private KhsRvAdapter khsRvAdapter;
    private TextView tvIpk, tvTotalSks, tvTotalMatkul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_khs);

        databaseHelper = new DatabaseHelper(this);

        TextView tvNim = findViewById(R.id.tv_nim);
        TextView tvFullName = findViewById(R.id.tv_full_name);
        RecyclerView rvKhs = findViewById(R.id.rv_khs);
        ImageButton btnEntryKhs = findViewById(R.id.btn_entry_khs);
        tvIpk = findViewById(R.id.tv_ipk);
        tvTotalSks = findViewById(R.id.tv_total_sks);
        tvTotalMatkul = findViewById(R.id.tv_total_matkul);

        Mahasiswa mahasiswa = getIntent().getParcelableExtra("key_mhs");
        tvNim.setText(mahasiswa.getNim());
        tvFullName.setText(mahasiswa.getName());


        rvKhs.setHasFixedSize(true);
        // Mengatur layout linear pada RecyclerView
        rvKhs.setLayoutManager(new LinearLayoutManager(this));

        khsArrayList.clear();
        khsArrayList.addAll(databaseHelper.getAllKhs());

        // Inisialisasi adapter untuk RecyclerView
        khsRvAdapter = new KhsRvAdapter(khsArrayList);

        // Menyambungkan adapter diatas ke RecyclerView pada XML.
        rvKhs.setAdapter(khsRvAdapter);

        // onClick pindah ke DetailMedicine, dengan membawa data: Medicine
        khsRvAdapter.setOnItemClickCallback(khs -> {
            Intent intentDetail = new Intent(ListKhs.this, DetailKhs.class);
            intentDetail.putExtra("key_khs", khs);
            intentDetail.putExtra("key_mahasiswa", mahasiswa);
            startActivity(intentDetail);
        });

        btnEntryKhs.setOnClickListener(v -> {
            Intent intent = new Intent(ListKhs.this, EntryKhs.class);
            intent.putExtra("key_mahasiswa", mahasiswa);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        khsArrayList.clear();
        khsArrayList.addAll(databaseHelper.getAllKhs());
        khsRvAdapter.notifyItemRemoved(0);
        khsRvAdapter.notifyItemRangeChanged(0, khsArrayList.size());
        countFooter();
    }

    private void countFooter() {
        Double totalGrades = 0.0;
        Integer totalSks = 0;
        Double ipk = 0.0;
        Integer totalMatkul = 0;
        if (khsArrayList != null && khsArrayList.size() > 0) {
            for (Khs khs : khsArrayList) {
                totalGrades += khs.getGrade();
                totalSks += khs.getSks();
            }
            ipk = totalGrades / khsArrayList.size();
            totalMatkul = khsArrayList.size();
        }
        tvIpk.setText(String.valueOf(ipk));
        tvTotalSks.setText(String.valueOf(totalSks));
        tvTotalMatkul.setText(String.valueOf(totalMatkul));
    }
}