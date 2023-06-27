package com.udinus.aplikasimobile.repository.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * Class User merepresentasikan objek User dengan informasi seperti username, nim, password, dan obect mahasiswa
 * Class ini mengimplementasikan interface Parcelable sehingga objek user dapat dikirimkan melalui Intent atau Bundle dalam Android.
 */
public class User implements Parcelable {

    private String username; // Username pengguna
    private String nim; // NIM (Nomor Induk Mahasiswa) pengguna
    private String password; // Password pengguna
    private Mahasiswa mahasiswa; // Object mahasiswa

    /**
     * Konstruktor tanpa parameter untuk class User.
     */
    public User() {
    }

    /**
     * Getter username pengguna dari objek User.
     *
     * @return username pengguna
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter username pengguna untuk objek User.
     *
     * @param username username pengguna
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter NIM (Nomor Induk Mahasiswa) dari objek User.
     *
     * @return NIM mahasiswa
     */
    public String getNim() {
        return nim;
    }

    /**
     * Setter NIM (Nomor Induk Mahasiswa) untuk objek User.
     *
     * @param nim NIM mahasiswa
     */
    public void setNim(String nim) {
        this.nim = nim;
    }

    /**
     * Getter password pengguna dari objek User.
     *
     * @return password pengguna
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter password pengguna untuk objek User.
     *
     * @param password password pengguna
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter object mahasiswa pengguna dari objek User.
     *
     * @return object mahasiswa
     */
    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    /**
     * Setter object mahasiswa untuk objek User.
     *
     * @param mahasiswa object mahasiswa
     */
    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
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
     * Metode ini digunakan untuk menulis data objek User ke dalam parcel.
     *
     * @param dest  Parcel tujuan
     * @param flags Flag untuk operasi khusus pada objek parcel
     */
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.nim);
        dest.writeString(this.password);
        dest.writeParcelable(this.mahasiswa, flags);
    }

    /**
     * Konstruktor protected User(Parcel).
     * Digunakan untuk membuat objek User dari Parcel.
     *
     * @param in Parcel input
     */
    protected User(Parcel in) {
        this.username = in.readString();
        this.nim = in.readString();
        this.password = in.readString();
        this.mahasiswa = in.readParcelable(Mahasiswa.class.getClassLoader());
    }

    /**
     * Implementasi dari Parcelable.Creator.
     * Digunakan untuk membuat objek User dari Parcel.
     */
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
