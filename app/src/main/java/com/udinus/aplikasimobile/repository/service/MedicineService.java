package com.udinus.aplikasimobile.repository.service;

import com.udinus.aplikasimobile.repository.model.Medicine;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MedicineService {
    @GET("medicines")
    Call<ApiResponse<List<Medicine>>> getMedicine();
    @POST("medicines")
    Call<ApiResponse<Medicine>> postMedicine(@Body Medicine medicine);
    @PUT("medicines/{id}")
    Call<ApiResponse<Medicine>> putMedicine(@Path("id") String id, @Body Medicine updatedMedicine);
    @DELETE("medicines/{id}")
    Call<ApiResponse<Void>> deleteMedicine(@Path("id") String id);
}
