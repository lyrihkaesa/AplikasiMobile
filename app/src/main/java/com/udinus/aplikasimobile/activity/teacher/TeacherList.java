package com.udinus.aplikasimobile.activity.teacher;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private DatabaseReference databaseRef;
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

        databaseRef = FirebaseDatabase.getInstance().getReference("teacher");

        binding.rvTeacher.setHasFixedSize(true);
        binding.rvTeacher.setLayoutManager(new LinearLayoutManager(this));

        teacherRvAdapter = new TeacherRvAdapter(teacherArrayList);
        binding.rvTeacher.setAdapter(teacherRvAdapter);

        teacherRvAdapter.setOnItemClickCallback(teacher -> {
            Intent intent = new Intent(TeacherList.this, TeacherEdit.class);
            intent.putExtra("key_teacher", teacher);
            startActivity(intent);
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
        getFirebaseTeachers();
    }

    public void getApiTeachers() {
        progressDialog.show();
        TeacherService teacherService = ApiClient.getClient().create(TeacherService.class);
        Call<ApiResponse<List<Teacher>>> call = teacherService.getTeacher();
        call.enqueue(new Callback<ApiResponse<List<Teacher>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Teacher>>> call, Response<ApiResponse<List<Teacher>>> response) {
                if (response.isSuccessful()) {
                    ApiResponse<List<Teacher>> apiResponse = response.body();
                    if (apiResponse != null) {
                        teacherArrayList.clear();
                        teacherArrayList.addAll(apiResponse.getData());
                        teacherRvAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
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

    public void getFirebaseTeachers(){
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teacherArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Teacher teacher = snapshot.getValue(Teacher.class);
                    if (teacher != null) {
                        teacher.setKey(snapshot.getKey());
                        teacherArrayList.add(teacher);
                    }
                }
                // Memberitahu adapter bahwa data telah berubah
                teacherRvAdapter.notifyDataSetChanged();
                // Menyembunyikan ProgressDialog setelah operasi selesai
                progressDialog.dismiss();
//                AlertDialog.Builder builder = new AlertDialog.Builder(TeacherList.this);
//                builder.setTitle("Show API").setMessage("Mengubah daftar list ke api").setPositiveButton("Ubah", (dialog, which) -> {
//                    getApiTeachers();
//                }).setNegativeButton("Batal", (dialog, which) -> {
//
//                }).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TeacherList.this);
                builder.setTitle("Error").setMessage("Error: " + databaseError.getMessage()).setPositiveButton("Refresh", (dialog, which) -> {
                    recreate();
                }).show();
            }
        });
    }
}