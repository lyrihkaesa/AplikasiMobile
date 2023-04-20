package com.udinus.aplikasimobile.database.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Class Mahasiswa merepresentasikan objek Mahasiswa dengan informasi seperti NIM, nama, email, jurusan/prodi, dan gelar.
 * Class ini mengimplementasikan interface Parcelable sehingga objek mahasiswa dapat dikirimkan melalui Intent atau Bundle dalam Android.
 */
public class Mahasiswa implements Parcelable {

    private String nim; // NIM (Nomor Induk Mahasiswa)
    private String name; // Nama lengkap mahasiswa
    private String email; // Email mahasiswa
    private String major; // Jurusan/prodi mahasiswa
    private String degree; // Gelar mahsiswa s1/s2/s3

    /**
     * Konstruktor tanpa parameter untuk class Mahasiswa.
     */
    public Mahasiswa() {
    }

    /**
     * Getter NIM (Nomor Induk Mahasiswa) dari objek Mahasiswa.
     *
     * @return NIM mahasiswa
     */
    public String getNim() {
        return nim;
    }

    /**
     * Setter NIM (Nomor Induk Mahasiswa) untuk objek Mahasiswa.
     *
     * @param nim NIM mahasiswa
     */
    public void setNim(String nim) {
        this.nim = nim;
    }

    /**
     * Getter nama lengkap mahasiswa dari objek Mahasiswa.
     *
     * @return Nama lengkap mahasiswa
     */
    public String getName() {
        return name;
    }

    /**
     * Setter nama lengkap mahasiswa untuk objek Mahasiswa.
     *
     * @param name Nama lengkap mahasiswa
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter email mahasiswa dari objek Mahasiswa.
     *
     * @return Email mahasiswa
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter email mahasiswa untuk objek Mahasiswa.
     *
     * @param email Email mahasiswa
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter jurusan/prodi mahasiswa dari objek Mahasiswa.
     *
     * @return Jurusan/prodi mahasiswa
     */
    public String getMajor() {
        return major;
    }

    /**
     * Setter jurusan/prodi mahasiswa untuk objek Mahasiswa.
     *
     * @param major Jurusan/prodi mahasiswa
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * Getter gelar mahasiswa (s1/s2/s3) dari objek Mahasiswa.
     *
     * @return Gelar mahasiswa
     */
    public String getDegree() {
        return degree;
    }

    /**
     * Setter gelar mahasiswa (s1/s2/s3) untuk objek Mahasiswa.
     *
     * @param degree Gelar mahasiswa
     */
    public void setDegree(String degree) {
        this.degree = degree;
    }

    /**
     * Implementasi dari Parcelable.describeContents().
     * Metode ini selalu mengembalikan nilai 0.
     *
     * @return Nilai 0
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Implementasi dari Parcelable.writeToParcel(Parcel, int).
     * Metode ini digunakan untuk menulis data objek Mahasiswa ke dalam parcel.
     *
     * @param dest  Parcel tujuan
     * @param flags Flag untuk operasi khusus pada objek parcel
     */
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.nim);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.major);
        dest.writeString(this.degree);
    }

    /**
     * Konstruktor protected Mahasiswa(Parcel).
     * Digunakan untuk membuat objek Mahasiswa dari Parcel.
     *
     * @param in Parcel input
     */
    protected Mahasiswa(Parcel in) {
        this.nim = in.readString();
        this.name = in.readString();
        this.email = in.readString();
        this.major = in.readString();
        this.degree = in.readString();
    }

    /**
     * Implementasi dari Parcelable.Creator.
     * Digunakan untuk membuat objek Mahasiswa dari Parcel.
     */
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
