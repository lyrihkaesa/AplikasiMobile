package com.udinus.aplikasimobile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EntryKhs extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    private EditText edtCodeMatkul, edtNameMatkul, edtSks, edtGrade, edtLetterGrade, edtPredicate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_khs);

        TextView tvNim = findViewById(R.id.tv_nim);
        TextView tvFullName = findViewById(R.id.tv_full_name);
        Button btnSave = findViewById(R.id.btn_save);
        Button btnCancel = findViewById(R.id.btn_cancel);
        edtCodeMatkul = findViewById(R.id.edt_code_matkul);
        edtNameMatkul = findViewById(R.id.edt_name_matkul);
        edtSks = findViewById(R.id.edt_sks);
        edtGrade = findViewById(R.id.edt_grade);
        edtLetterGrade = findViewById(R.id.edt_letter_grade);
        edtPredicate = findViewById(R.id.edt_predicate);

        Mahasiswa mahasiswa = getIntent().getParcelableExtra("key_mahasiswa");

        tvNim.setText(mahasiswa.getNim());
        tvFullName.setText(mahasiswa.getName());

        databaseHelper = new DatabaseHelper(this);

        btnSave.setOnClickListener(v -> addKhs());

        btnCancel.setOnClickListener(v -> finish());

    }

    private void addKhs() {
        // Membuat object khs
        Khs khs = new Khs();

        // Set/Input/Masukan nilai ke object khs
        khs.setCodeMatkul(edtCodeMatkul.getText().toString());
        khs.setNameMatkul(edtNameMatkul.getText().toString());
        khs.setGrade(Double.valueOf(edtGrade.getText().toString()));
        khs.setLetterGrade(edtLetterGrade.getText().toString());
        khs.setSks(Integer.valueOf(edtSks.getText().toString()));
        khs.setPredicate(edtPredicate.getText().toString());

        // insert object khs ke database
        if (databaseHelper.insertKhs(khs) > 0) {
            Toast.makeText(this, "Berhasil menambahkan mata kuliah " + khs.getNameMatkul(), Toast.LENGTH_SHORT).show();
        }
        finish();
    }

}