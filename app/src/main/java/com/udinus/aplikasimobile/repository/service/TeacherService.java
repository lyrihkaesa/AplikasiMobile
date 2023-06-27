package com.udinus.aplikasimobile.repository.service;

import com.udinus.aplikasimobile.repository.model.Teacher;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TeacherService {
    @GET("teachers")
    Call<ApiResponse<List<Teacher>>> getTeacher();
}
