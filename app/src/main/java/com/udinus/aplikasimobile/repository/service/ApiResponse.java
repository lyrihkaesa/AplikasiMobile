package com.udinus.aplikasimobile.repository.service;
import com.google.gson.annotations.SerializedName;

public class ApiResponse<T> {
    @SerializedName("data")
    private T data;

    @SerializedName("error")
    private String error;

    public T getData() {
        return data;
    }

    public String getError() {
        return error;
    }
}
