package com.udinus.aplikasimobile.database.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class User implements Parcelable {

    private String username;
    private String email;
    private String password;
    private Mahasiswa mahasiswa;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeParcelable(this.mahasiswa, flags);
    }

    protected User(Parcel in) {
        this.username = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.mahasiswa = in.readParcelable(Mahasiswa.class.getClassLoader());
    }

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
