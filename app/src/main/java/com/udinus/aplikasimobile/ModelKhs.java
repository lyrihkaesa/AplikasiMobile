package com.udinus.aplikasimobile;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelKhs implements Parcelable {

    private String codeMatkul;
    private String nameMatkul;
    private Integer sks;
    private Double grade;
    private String letterGrade;
    private String predicate;

    public String getCodeMatkul() {
        return codeMatkul;
    }

    public void setCodeMatkul(String codeMatkul) {
        this.codeMatkul = codeMatkul;
    }

    public String getNameMatkul() {
        return nameMatkul;
    }

    public void setNameMatkul(String nameMatkul) {
        this.nameMatkul = nameMatkul;
    }

    public Integer getSks() {
        return sks;
    }

    public void setSks(Integer sks) {
        this.sks = sks;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public String getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.codeMatkul);
        dest.writeString(this.nameMatkul);
        dest.writeInt(this.sks);
        dest.writeDouble(this.grade);
        dest.writeString(this.letterGrade);
        dest.writeString(this.predicate);

    }

    public ModelKhs() {
    }

    private ModelKhs(Parcel in) {
        this.codeMatkul = in.readString();
        this.nameMatkul = in.readString();
        this.sks = in.readInt();
        this.grade = in.readDouble();
        this.letterGrade = in.readString();
        this.predicate = in.readString();
    }

    public static final Parcelable.Creator<ModelKhs> CREATOR = new Parcelable.Creator<ModelKhs>() {
        @Override
        public ModelKhs createFromParcel(Parcel source) {
            return new ModelKhs(source);
        }

        @Override
        public ModelKhs[] newArray(int size) {
            return new ModelKhs[size];
        }
    };
}