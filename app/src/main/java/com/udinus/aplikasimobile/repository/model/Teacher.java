package com.udinus.aplikasimobile.repository.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Teacher implements Parcelable {

    @SerializedName("employee_name")
    private String employeeName;

    @SerializedName("position")
    private String position;

    @SerializedName("employee_code")
    private String employeeCode;

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public Teacher(){}

    protected Teacher(Parcel in) {
        this.employeeCode = in.readString();
        this.employeeName = in.readString();
        this.position = in.readString();
    }

    public static final Parcelable.Creator<Teacher> CREATOR = new Parcelable.Creator<Teacher>() {
        @Override
        public Teacher createFromParcel(Parcel in) {
            return new Teacher(in);
        }

        @Override
        public Teacher[] newArray(int size) {
            return new Teacher[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(this.employeeCode);
        parcel.writeString(this.employeeName);
        parcel.writeString(this.position);
    }
}