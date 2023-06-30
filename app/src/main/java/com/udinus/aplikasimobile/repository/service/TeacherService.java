package com.udinus.aplikasimobile.repository.service;

import com.udinus.aplikasimobile.repository.model.Teacher;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TeacherService {
    @GET("teachers")
    Call<ApiResponse<List<Teacher>>> getTeacher();
    @POST("teachers")
    Call<ApiResponse<Teacher>> postTeacher(@Body Teacher teacher);
    @PUT("teachers/{id}")
    Call<ApiResponse<Teacher>> putTeacher(@Path("id") String id, @Body Teacher updatedTeacher);
    @DELETE("teachers/{id}")
    Call<ApiResponse<Void>> deleteTeacher(@Path("id") String id);
}
