package com.udinus.aplikasimobile.activity.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.udinus.aplikasimobile.R;
import com.udinus.aplikasimobile.databinding.ActivityTeacherEditBinding;
import com.udinus.aplikasimobile.repository.ApiClient;
import com.udinus.aplikasimobile.repository.model.Teacher;
import com.udinus.aplikasimobile.repository.service.ApiResponse;
import com.udinus.aplikasimobile.repository.service.TeacherService;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherEdit extends AppCompatActivity {
    ActivityTeacherEditBinding binding;
    private Teacher teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeacherEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setTitle("Mengubah Data Teacher");

        teacher = getIntent().getParcelableExtra("key_teacher");

        binding.edtCode.setText(teacher.getEmployeeCode());
        binding.edtCode.setEnabled(false);
        
        binding.edtName.setText(teacher.getEmployeeName());
        binding.edtPosition.setText(teacher.getPosition());

        binding.btnSave.setOnClickListener(view -> editTeacher());
        binding.btnCancel.setOnClickListener(view ->finish());
        binding.btnHapus.setOnClickListener(view -> showDeleteConfirmationDialog());
    }

    private void editTeacher(){
        if (TextUtils.isEmpty(binding.edtCode.getText())) {
            binding.tilName.setError("Isi kode teacher");
            binding.edtCode.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(binding.edtName.getText())) {
            binding.tilName.setError("Isi nama teacher");
            binding.edtName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(binding.edtPosition.getText())) {
            binding.tilPosition.setError("Isi satuan teacher");
            binding.edtPosition.requestFocus();
            return;
        }

        String code = binding.edtCode.getText().toString().trim();
        String name = binding.edtName.getText().toString().trim();
        String position = binding.edtPosition.getText().toString().trim();

        teacher.setEmployeeCode(code);
        teacher.setEmployeeName(name);
        teacher.setPosition(position);

        TeacherService teacherService = ApiClient.getClient().create(TeacherService.class);
        Call<ApiResponse<Teacher>> call = teacherService.putTeacher(code, teacher);

        call.enqueue(new Callback<ApiResponse<Teacher>>() {
            @Override
            public void onResponse(Call<ApiResponse<Teacher>> call, Response<ApiResponse<Teacher>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<Teacher> teacherResponse = response.body();
                    if (teacherResponse != null) {
                        Teacher updateTeacher = teacherResponse.getData();
                        Toast.makeText(TeacherEdit.this, "Success: " + updateTeacher, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorMessage = response.errorBody().string();
                            Toast.makeText(TeacherEdit.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Teacher>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(TeacherEdit.this, "Terjadi kesalahan koneksi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteTeacher() {
        TeacherService teacherService = ApiClient.getClient().create(TeacherService.class);
        Call<ApiResponse<Void>> call = teacherService.deleteTeacher(teacher.getEmployeeCode());

        call.enqueue(new Callback<ApiResponse<Void>>() {
            @Override
            public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TeacherEdit.this, "Data guru berhasil dihapus", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String errorMessage = response.errorBody().string();
                            Toast.makeText(TeacherEdit.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(TeacherEdit.this, "Terjadi kesalahan koneksi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDeleteConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Hapus")
                .setMessage("Apakah Anda ingin menghapus " + teacher.getEmployeeName() + "?")
                .setIcon(R.drawable.round_delete_24)
                .setPositiveButton("Hapus", (dialog, whichButton) -> {
                    deleteTeacher();
                    dialog.dismiss();
                })
                .setNegativeButton("Batal", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }
}