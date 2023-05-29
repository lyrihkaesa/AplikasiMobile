package com.udinus.aplikasimobile.database.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.text.NumberFormat;
import java.util.Locale;

public class Barang implements Parcelable {

    private String code;
    private String name;
    private String satuan;
    private Double price;

    public Barang() {

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

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public Double getPrice() {
        return price;
    }

    public String getPriceRp() {
        // Mengubah format price tipe data double "1000" ke string currency indonesia "Rp1.000"
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        format.setMaximumFractionDigits(0);
        return format.format(this.price);
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    protected Barang(Parcel in) {
        this.code = in.readString();
        this.name = in.readString();
        this.satuan = in.readString();
        this.price = in.readDouble();
    }

    public static final Creator<Barang> CREATOR = new Creator<Barang>() {
        @Override
        public Barang createFromParcel(Parcel in) {
            return new Barang(in);
        }

        @Override
        public Barang[] newArray(int size) {
            return new Barang[size];
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
        parcel.writeString(this.satuan);
        parcel.writeDouble(this.price);
    }
}