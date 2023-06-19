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
import com.udinus.aplikasimobile.databinding.ActivityEditMedicineBinding;
import com.udinus.aplikasimobile.utils.AppUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class EditMedicine extends AppCompatActivity {
    ActivityEditMedicineBinding binding;
    private DatabaseReference databaseRef;
    private Medicine medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditMedicineBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mengubah judul yang ada pada App Bar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Mengubah Data Medicine");

        // Mengambil data dari intent dengan ParcelableExtra
        medicine = getIntent().getParcelableExtra("key_medicine");

        databaseRef = FirebaseDatabase.getInstance().getReference("medicine");

        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        binding.edtCodeMedicine.setText(medicine.getCode());
        binding.edtNameMedicine.setText(medicine.getName());
        binding.edtSatuanMedicine.setText(medicine.getSatuan());
        binding.edtPriceMedicine.setText(AppUtils.convertPriceToText(medicine.getPrice()));
        binding.edtAmountMedicine.setText(String.valueOf(medicine.getAmount()));
        binding.tvDateExp.setText(String.valueOf(AppUtils.simpleDateFormat.format(medicine.getExpired())));
        binding.edtPackagingMedicine.setText(medicine.getPackaging());
        binding.edtTypeMedicine.setText(medicine.getType());

        binding.imgbtnDate.setOnClickListener(view ->
                new DatePickerDialog(EditMedicine.this, (view1, year1, monthOfYear, dayOfMonth)
                        -> binding.tvDateExp.setText(String.format(getResources().getString(R.string.format_date), dayOfMonth, monthOfYear + 1, year1)),
                        year, month, day).show());
        binding.btnSimpan.setOnClickListener(view -> editMedicine());
        binding.btnBatal.setOnClickListener(view -> finish());
        binding.btnHapus.setOnClickListener(view -> showDeleteConfirmationDialog());
    }

    private void editMedicine() {
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

        medicine.setCode(code);
        medicine.setName(name);
        medicine.setSatuan(satuan);
        medicine.setPrice(price);
        medicine.setAmount(amount);
        medicine.setExpired(epochDateExperied);
        medicine.setPackaging(packaging);
        medicine.setType(type);

        databaseRef.child(medicine.getKey()).setValue(medicine);
        finish();
    }

    private void deleteMedicine() {
        databaseRef.child(medicine.getKey()).removeValue();
        finish();
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Hapus")
                .setMessage("Apakah Anda ingin menghapus " + medicine.getName() + "?")
                .setIcon(R.drawable.round_delete_24)
                .setPositiveButton("Hapus", (dialog, whichButton) -> {
                    deleteMedicine();
                    dialog.dismiss();
                })
                .setNegativeButton("Batal", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }
}
