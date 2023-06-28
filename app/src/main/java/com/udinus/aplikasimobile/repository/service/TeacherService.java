package com.udinus.aplikasimobile.repository.service;

import com.udinus.aplikasimobile.repository.model.Teacher;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TeacherService {
    @GET("teachers")
    Call<ApiResponse<List<Teacher>>> getTeacher();
    @POST("teachers")
    Call<ApiResponse<Teacher>> postTeacher(@Body Teacher teacher);
}
