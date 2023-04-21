package com.udinus.aplikasimobile.utils;

import android.util.Log;

import com.udinus.aplikasimobile.database.model.Khs;

import java.util.ArrayList;

public class KhsUtils {
    /**
     * Mengkonversi nilai angka (grade) ke nilai huruf.
     *
     * @param grade nilai angkat (grade) yang akan diubah ke nilai huruf
     * @return nilai huruf yang sesuai dengan nilai angka (grade) yang diterima
     */
    public static String convertGradetoLetterGrade(Double grade) {
        String letterGrade;
        if (grade >= 85 && grade <= 100) {
            letterGrade = "A";
        } else if (grade >= 80 && grade <= 84.9) {
            letterGrade = "AB";
        } else if (grade >= 70 && grade <= 79.9) {
            letterGrade = "B";
        } else if (grade >= 65 && grade <= 69.9) {
            letterGrade = "BC";
        } else if (grade >= 60 && grade <= 64.9) {
            letterGrade = "C";
        } else if (grade >= 50 && grade <= 59.9) {
            letterGrade = "D";
        } else if (grade >= 0 && grade <= 49.9) {
            letterGrade = "E";
        } else {
            letterGrade = "Unknown";
        }
        return letterGrade;
    }

    /**
     * Mengonversi nilai huruf menjadi nilai IPK tiap mata kuliah berdasarkan skala berikut:
     * A = 4
     * AB = 3,5
     * B = 3
     * BC = 2,5
     * C = 2
     * D = 1
     * E = 0
     *
     * @param letterGrade nilai huruf yang akan dikonversi
     * @return nilai IPK yang sesuai sebagai Double
     */
    public static Double convertLetterGradeToIpkPoint(String letterGrade) {
        double ipk = 0;
        switch (letterGrade) {
            case "A":
                ipk = 4;
                break;
            case "AB":
                ipk = 3.5;
                break;
            case "B":
                ipk = 3;
                break;
            case "BC":
                ipk = 2.5;
                break;
            case "C":
                ipk = 2;
                break;
            case "D":
                ipk = 1;
                break;
            case "E":
                ipk = 0;
                break;
        }
        return ipk;
    }

    /**
     * Menghitung IPK berdasarkan list data Khs.
     *
     * @param khsArrayList ArrayList data Khs
     * @return IPK (Indeks Prestasi Kumulatif) dalam bentuk Double
     */
    public static Double countIpk(ArrayList<Khs> khsArrayList) {
        Double ipkTotalPoint = 0.0;
        if (!khsArrayList.isEmpty()) {
            for (Khs khs : khsArrayList) {
                ipkTotalPoint += convertLetterGradeToIpkPoint(khs.getLetterGrade());
            }
        }
        return ipkTotalPoint / khsArrayList.size();
    }

    /**
     * Menghitung total SKS dari array list khs.
     *
     * @param khsArrayList ArrayList yang berisi data KHS.
     * @return Integer yang menunjukkan total SKS dari array list khs.
     */
    public static Integer countTotalSks(ArrayList<Khs> khsArrayList) {
        Integer totalSks = 0;
        if (!khsArrayList.isEmpty()) {
            for (Khs khs : khsArrayList) {
                totalSks += khs.getSks();
            }
        }
        return totalSks;
    }
}
