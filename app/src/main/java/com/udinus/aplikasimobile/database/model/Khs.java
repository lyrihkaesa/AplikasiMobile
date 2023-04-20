package com.udinus.aplikasimobile.database.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class Khs (Kartu Hasil Studi) digunakan untuk merepresentasikan data khs mata kuliah
 * yang akan ditampilkan dalam suatu aplikasi. Class ini mengimplementasikan interface Parcelable
 * sehingga objek Khs dapat dikirimkan melalui Intent atau Bundle dalam Android.
 */
public class Khs implements Parcelable {

    private String codeMatkul; // Kode mata kuliah
    private String nameMatkul; // Nama mata kuliah
    private Integer sks; // Jumlah SKS (Satuan Kredit Semester) mata kuliah
    private Double grade; // Nilai angka mata kuliah
    private String letterGrade; // Nilai huruf mata kuliah
    private String predicate; // Predikat nilai mata kuliah

    /**
     * Konstruktor tanpa parameter untuk class Khs.
     */
    public Khs() {
    }

    /**
     * Getter untuk mendapatkan kode mata kuliah.
     *
     * @return Kode mata kuliah
     */
    public String getCodeMatkul() {
        return codeMatkul;
    }

    /**
     * Setter untuk mengatur kode mata kuliah.
     *
     * @param codeMatkul Kode mata kuliah
     */
    public void setCodeMatkul(String codeMatkul) {
        this.codeMatkul = codeMatkul;
    }

    /**
     * Getter untuk mendapatkan nama mata kuliah.
     *
     * @return Nama mata kuliah
     */
    public String getNameMatkul() {
        return nameMatkul;
    }

    /**
     * Setter untuk mengatur nama mata kuliah.
     *
     * @param nameMatkul Nama mata kuliah
     */
    public void setNameMatkul(String nameMatkul) {
        this.nameMatkul = nameMatkul;
    }

    /**
     * Getter untuk mendapatkan jumlah SKS (Satuan Kredit Semester).
     *
     * @return Jumlah SKS
     */
    public Integer getSks() {
        return sks;
    }

    /**
     * Setter untuk mengatur jumlah SKS (Satuan Kredit Semester).
     *
     * @param sks Jumlah SKS
     */
    public void setSks(Integer sks) {
        this.sks = sks;
    }

    /**
     * Getter untuk mendapatkan nilai angka.
     *
     * @return Nilai angka
     */
    public Double getGrade() {
        return grade;
    }

    /**
     * Setter untuk mengatur nilai angka.
     *
     * @param grade Nilai angka
     */
    public void setGrade(Double grade) {
        this.grade = grade;
    }

    /**
     * Getter untuk mendapatkan nilai huruf.
     *
     * @return Nilai huruf
     */
    public String getLetterGrade() {
        return letterGrade;
    }

    /**
     * Setter untuk mengatur nilai huruf.
     *
     * @param letterGrade Nilai huruf
     */
    public void setLetterGrade(String letterGrade) {
        this.letterGrade = letterGrade;
    }

    /**
     * Getter untuk mendapatkan predikat.
     *
     * @return Predikat
     */
    public String getPredicate() {
        return predicate;
    }

    /**
     * Setter untuk mengatur predikat.
     *
     * @param predicate Predikat
     */
    public void setPredicate(String predicate) {
        this.predicate = predicate;
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
     * Metode ini digunakan untuk menulis data objek Khs ke dalam parcel.
     *
     * @param dest  Parcel tujuan
     * @param flags Flag untuk operasi khusus pada objek parcel
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.codeMatkul);
        dest.writeString(this.nameMatkul);
        dest.writeInt(this.sks);
        dest.writeDouble(this.grade);
        dest.writeString(this.letterGrade);
        dest.writeString(this.predicate);

    }

    /**
     * Konstruktor protected Khs(Parcel).
     * Digunakan untuk membuat objek Khs dari Parcel.
     *
     * @param in Parcel input
     */
    protected Khs(Parcel in) {
        this.codeMatkul = in.readString();
        this.nameMatkul = in.readString();
        this.sks = in.readInt();
        this.grade = in.readDouble();
        this.letterGrade = in.readString();
        this.predicate = in.readString();
    }

    /**
     * Implementasi dari Parcelable.Creator.
     * Digunakan untuk membuat objek Khs dari Parcel.
     */
    public static final Parcelable.Creator<Khs> CREATOR = new Parcelable.Creator<Khs>() {
        @Override
        public Khs createFromParcel(Parcel source) {
            return new Khs(source);
        }

        @Override
        public Khs[] newArray(int size) {
            return new Khs[size];
        }
    };
}