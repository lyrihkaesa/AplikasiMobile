package com.udinus.aplikasimobile.repository.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Guru implements Parcelable {
    @SerializedName("nip")
    private String nip;
    @SerializedName("nama")
    private String nama;

    @SerializedName("status")
    private String status;

    @SerializedName("gaji")
    private int gaji;

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setGaji(int gaji) {
        this.gaji = gaji;
    }

    public int getGaji() {
        return gaji;
    }

    public Guru() {
    }

    protected Guru(Parcel in) {
        this.nip = in.readString();
        this.gaji = in.readInt();
        this.nama = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<Guru> CREATOR = new Parcelable.Creator<Guru>() {
        @Override
        public Guru createFromParcel(Parcel in) {
            return new Guru(in);
        }

        @Override
        public Guru[] newArray(int size) {
            return new Guru[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(this.nip);
        parcel.writeInt(this.gaji);
        parcel.writeString(this.nama);
        parcel.writeString(this.status);
    }
}