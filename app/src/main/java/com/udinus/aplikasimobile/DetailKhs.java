package com.udinus.aplikasimobile;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DetailKhs extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    Khs khs;
    private EditText edtCodeMatkul, edtNameMatkul, edtSks, edtGrade, edtLetterGrade, edtPredicate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_khs);
        TextView tvNim = findViewById(R.id.tv_nim);
        TextView tvFullName = findViewById(R.id.tv_full_name);
        Button btnSave = findViewById(R.id.btn_save);
        Button btnCancel = findViewById(R.id.btn_cancel);
        Button btnDelete = findViewById(R.id.btn_delete);
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

        khs = getIntent().getParcelableExtra("key_khs");

        edtCodeMatkul.setText(khs.getCodeMatkul());
        edtNameMatkul.setText(khs.getNameMatkul());
        edtSks.setText(String.valueOf(khs.getSks()));
        edtGrade.setText(String.valueOf(khs.getGrade()));
        edtLetterGrade.setText(khs.getLetterGrade());
        edtPredicate.setText(khs.getPredicate());

        edtCodeMatkul.setEnabled(false);
        edtCodeMatkul.setTextColor(getColor(R.color.red_500));

        btnSave.setOnClickListener(v -> editKhs());

        btnCancel.setOnClickListener(v -> finish());

        btnDelete.setOnClickListener(v -> {
            AlertDialog diaBox = AskOption();
            diaBox.show();
        });
    }

    private void editKhs() {
        // Membuat object khs
        Khs khs = new Khs();

        // Set/Input/Masukan nilai ke object khs
        khs.setCodeMatkul(edtCodeMatkul.getText().toString());
        khs.setNameMatkul(edtNameMatkul.getText().toString());
        khs.setSks(Integer.valueOf(edtSks.getText().toString()));
        khs.setGrade(Double.valueOf(edtGrade.getText().toString()));
        khs.setLetterGrade(edtLetterGrade.getText().toString());
        khs.setPredicate(edtPredicate.getText().toString());

        // insert object khs ke database
        if (databaseHelper.updateKhs(khs) > 0) {
            Toast.makeText(this, "Berhasil menambahkan mata kuliah " + khs.getNameMatkul(), Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private AlertDialog AskOption() {
        return new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Hapus").setMessage("Apakah anda ingin menghapus " + khs.getNameMatkul() + "?").setIcon(R.drawable.round_delete_24).setPositiveButton("Hapus", (dialog, whichButton) -> {
                    //your deleting code
                    if (databaseHelper.deleteKhs(edtCodeMatkul.getText().toString()) > 0) {
                        Toast.makeText(this, "Berhasil menghapus mata kuliah " + khs.getNameMatkul(), Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                    finish();
                }).setNegativeButton("Batal", (dialog, which) -> dialog.dismiss()).create();
    }
}