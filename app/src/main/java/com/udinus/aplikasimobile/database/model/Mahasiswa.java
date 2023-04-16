package com.udinus.aplikasimobile.database.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Mahasiswa implements Parcelable {

    private String nim;
    private String name;
    private String email;
    private String prodi;

    public Mahasiswa() {}

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.nim);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.prodi);
    }

    protected Mahasiswa(Parcel in) {
        this.nim = in.readString();
        this.name = in.readString();
        this.email = in.readString();
        this.prodi = in.readString();

    }

    public static final Creator<Mahasiswa> CREATOR = new Creator<Mahasiswa>() {
        @Override
        public Mahasiswa createFromParcel(Parcel in) {
            return new Mahasiswa(in);
        }

        @Override
        public Mahasiswa[] newArray(int size) {
            return new Mahasiswa[size];
        }
    };
}
