package com.udinus.aplikasimobile.repository.service;

import com.udinus.aplikasimobile.repository.model.Guru;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InterfaceGuru {
    @GET("guru")
    Call<ApiResponse<List<Guru>>> getGurus();
    @POST("guru")
    Call<ApiResponse<Guru>> postGuru(@Body Guru guru);
    @PUT("guru/{id}")
    Call<ApiResponse<Guru>> putGuru(@Path("id") String id, @Body Guru updatedGuru);
    @DELETE("guru/{id}")
    Call<ApiResponse<Void>> deleteGuru(@Path("id") String id);
}
