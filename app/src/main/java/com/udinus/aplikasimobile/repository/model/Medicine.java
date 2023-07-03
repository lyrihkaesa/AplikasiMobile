package com.udinus.aplikasimobile.repository.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Medicine implements Parcelable {
    @SerializedName("firebase_key")
    private String key;
    @SerializedName("code")
    private String code;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private Double price;

    public Medicine() {}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    protected Medicine(Parcel in) {
        this.code = in.readString();
        this.name = in.readString();
        this.price = in.readDouble();
        this.key = in.readString();
    }

    public static final Creator<Medicine> CREATOR = new Creator<Medicine>() {
        @Override
        public Medicine createFromParcel(Parcel in) {
            return new Medicine(in);
        }

        @Override
        public Medicine[] newArray(int size) {
            return new Medicine[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(this.code);
        parcel.writeString(this.name);
        parcel.writeDouble(this.price);
        parcel.writeString(this.key);
    }
}