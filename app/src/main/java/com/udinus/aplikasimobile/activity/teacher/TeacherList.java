package com.udinus.aplikasimobile.activity.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.udinus.aplikasimobile.adapter.TeacherRvAdapter;
import com.udinus.aplikasimobile.databinding.ActivityTeacherListBinding;
import com.udinus.aplikasimobile.repository.ApiClient;
import com.udinus.aplikasimobile.repository.model.Teacher;
import com.udinus.aplikasimobile.repository.service.ApiResponse;
import com.udinus.aplikasimobile.repository.service.TeacherService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherList extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private TeacherRvAdapter teacherRvAdapter;

    private ArrayList<Teacher> teacherArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTeacherListBinding binding = ActivityTeacherListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Mengubah judul yang ada pada App Bar
        Objects.requireNonNull(getSupportActionBar()).setTitle("Daftar Guru");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        binding.rvTeacher.setHasFixedSize(true);
        binding.rvTeacher.setLayoutManager(new LinearLayoutManager(this));

        teacherRvAdapter = new TeacherRvAdapter(teacherArrayList);
        binding.rvTeacher.setAdapter(teacherRvAdapter);

        teacherRvAdapter.setOnItemClickCallback(teacher -> {

        });
        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(TeacherList.this, TeacherEntry.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.show();
        TeacherService teacherService = ApiClient.getClient().create(TeacherService.class);
        Call<ApiResponse<List<Teacher>>> call = teacherService.getTeacher();
        call.enqueue(new Callback<ApiResponse<List<Teacher>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Teacher>>> call, Response<ApiResponse<List<Teacher>>> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()){
                    ApiResponse<List<Teacher>> apiResponse = response.body();
                    if(apiResponse != null){
                        teacherArrayList.addAll(apiResponse.getData());
                        teacherRvAdapter.notifyItemInserted(teacherArrayList.size());
                    }
                } else {
                    Toast.makeText(TeacherList.this, "Failed to fetch teachers", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Teacher>>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(TeacherList.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}