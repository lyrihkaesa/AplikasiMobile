package com.udinus.aplikasimobile.activity.medicine;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.udinus.aplikasimobile.R;
import com.udinus.aplikasimobile.database.model.Medicine;
import com.udinus.aplikasimobile.databinding.ActivityEntryMedicineBinding;
import com.udinus.aplikasimobile.utils.AppUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class EntryMedicine extends AppCompatActivity {
    private DatabaseReference databaseRef;
    ActivityEntryMedicineBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEntryMedicineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mengubah judul yang ada pada App Bar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Menambahkan Data Medicine");

        // Inisialisasi DatabaseReference
        databaseRef = FirebaseDatabase.getInstance().getReference("medicine");

        UUID uuid = UUID.randomUUID();
        String randomUUID8Char = uuid.toString().replaceAll("-", "").substring(0, 8);
        binding.edtCodeMedicine.setText(randomUUID8Char);

        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        binding.tvDateExp.setText(String.format(getResources().getString(R.string.format_date), day, month + 1, year));

        binding.imgbtnDate.setOnClickListener(view ->
                new DatePickerDialog(EntryMedicine.this, (view1, year1, monthOfYear, dayOfMonth)
                         -> binding.tvDateExp.setText(String.format(getResources().getString(R.string.format_date), dayOfMonth, monthOfYear + 1, year1)),
                        year, month, day).show());

        binding.btnSimpan.setOnClickListener(view -> insertMedicine());

        binding.btnBatal.setOnClickListener(view -> finish());
    }

    private void insertMedicine() {
        if (TextUtils.isEmpty(binding.edtCodeMedicine.getText())) {
            binding.tilCodeMedicine.setError("Isi kode medicine");
            binding.edtCodeMedicine.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(binding.edtNameMedicine.getText())) {
            binding.tilNameMedicine.setError("Isi nama medicine");
            binding.edtNameMedicine.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(binding.edtSatuanMedicine.getText())) {
            binding.tilSatuanMedicine.setError("Isi satuan medicine");
            binding.edtSatuanMedicine.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(binding.edtPriceMedicine.getText())) {
            binding.tilPriceMedicine.setError("Isi harga medicine");
            binding.edtPriceMedicine.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(binding.edtAmountMedicine.getText())) {
            binding.tilAmountMedicine.setError("Isi jumlah medicine");
            binding.edtAmountMedicine.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(binding.edtPackagingMedicine.getText())) {
            binding.tilPackagingMedicine.setError("Isi kemasan medicine");
            binding.edtPackagingMedicine.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(binding.edtTypeMedicine.getText())) {
            binding.tilTypeMedicine.setError("Isi tipe medicine");
            binding.edtTypeMedicine.requestFocus();
            return;
        }

        String code = binding.edtCodeMedicine.getText().toString().trim();
        String name = binding.edtNameMedicine.getText().toString().trim();
        String satuan = binding.edtSatuanMedicine.getText().toString().trim();
        String priceText = binding.edtPriceMedicine.getText().toString().trim();
        String amountText = binding.edtAmountMedicine.getText().toString().trim();
        String packaging = binding.edtPackagingMedicine.getText().toString().trim();
        String type = binding.edtTypeMedicine.getText().toString().trim();

        double price = Double.parseDouble(priceText);
        int amount = Integer.parseInt(amountText);
        long epochDateExperied = 0L;

        try {
            Date date = AppUtils.simpleDateFormat.parse(binding.tvDateExp.getText().toString());
            assert date != null;
            epochDateExperied = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        // Membuat obyek medicine/Medicine
        Medicine medicine = new Medicine();
        medicine.setCode(code);
        medicine.setName(name);
        medicine.setSatuan(satuan);
        medicine.setPrice(price);
        medicine.setAmount(amount);
        medicine.setExpired(epochDateExperied);
        medicine.setPackaging(packaging);
        medicine.setType(type);

        // Mengambil referensi baru untuk medicine di Firebase Realtime Database
        DatabaseReference newMedicineRef = databaseRef.push();
        String medicineKey = newMedicineRef.getKey();

        if (medicineKey != null) {
            // Menyimpan obyek medicine ke Firebase Realtime Database
            newMedicineRef.setValue(medicine)
                    .addOnSuccessListener(aVoid -> {
                        // Menampilkan pesan berhasil dan kembali ke activity sebelumnya
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        // Menampilkan pesan kesalahan jika gagal menyimpan ke database
                        showError("Gagal menyimpan medicine: " + e.getMessage());
                    });
        } else {
            // Menampilkan pesan kesalahan jika gagal mendapatkan kunci medicine
            showError("Gagal mendapatkan kunci medicine");
        }
    }

    private void showError(String errorMessage) {
        // Menampilkan AlertDialog untuk menampilkan pesan kesalahan
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(errorMessage)
                .setPositiveButton("OK", null)
                .show();
    }
}