package com.udinus.aplikasimobile.activity.teacher;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.udinus.aplikasimobile.databinding.ActivityTeacherEntryBinding;
import com.udinus.aplikasimobile.repository.ApiClient;
import com.udinus.aplikasimobile.repository.model.Teacher;
import com.udinus.aplikasimobile.repository.service.ApiResponse;
import com.udinus.aplikasimobile.repository.service.TeacherService;

import java.util.Objects;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherEntry extends AppCompatActivity {
    ActivityTeacherEntryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeacherEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Objects.requireNonNull(getSupportActionBar()).setTitle("Menambahkan Data Teacher");

        UUID uuid = UUID.randomUUID();
        String randomUUID8Char = uuid.toString().replaceAll("-", "").substring(0, 8);
        binding.edtCode.setText(randomUUID8Char);

        binding.btnSave.setOnClickListener(view -> insert());

        binding.btnCancel.setOnClickListener(view -> finish());
    }
    private void insert() {
        if (TextUtils.isEmpty(binding.edtCode.getText())) {
            binding.tilCode.setError("Isi kode teacher");
            binding.edtCode.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(binding.edtName.getText())) {
            binding.tilName.setError("Isi nama teacher");
            binding.edtName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(binding.edtPosition.getText())) {
            binding.tilPosition.setError("Isi jabatan teacher");
            binding.edtPosition.requestFocus();
            return;
        }

        String code = binding.edtCode.getText().toString().trim();
        String name = binding.edtName.getText().toString().trim();
        String position = binding.edtPosition.getText().toString().trim();

        Teacher teacher = new Teacher();
        teacher.setEmployeeCode(code);
        teacher.setEmployeeName(name);
        teacher.setPosition(position);

        TeacherService teacherService = ApiClient.getClient().create(TeacherService.class);
        Call<ApiResponse<Teacher>> call = teacherService.postTeacher(teacher);
        call.enqueue(new Callback<ApiResponse<Teacher>>() {
            @Override
            public void onResponse(Call<ApiResponse<Teacher>> call, Response<ApiResponse<Teacher>> response) {
                if(response.isSuccessful()){
                    ApiResponse<Teacher> apiResponse = response.body();
                    if(apiResponse != null){
                        Toast.makeText(TeacherEntry.this, "Teacher created successfully", Toast.LENGTH_SHORT).show();
                    }
                }  else {
                    Toast.makeText(TeacherEntry.this, "Failed to create teacher", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Teacher>> call, Throwable t) {
                Toast.makeText(TeacherEntry.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        finish();
    }
}