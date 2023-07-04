package com.udinus.aplikasimobile.utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AppUtils {

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
    public static String convertPriceToRpText(Double price) {
        // Mengubah format price tipe data double "1000" ke string currency indonesia "Rp1.000"
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        format.setMaximumFractionDigits(0);
        return format.format(price);
    }

    public static String convertPriceToText(Double price) {
        String priceText = String.valueOf(price);
        if (priceText.endsWith(".0")) {
            priceText = priceText.substring(0, priceText.length() - 2);
        }
        return priceText;
    }

    public static String convertGajiToRpText(int gaji) {
        // Mengubah format price tipe data double "1000" ke string currency indonesia "Rp1.000"
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        format.setMaximumFractionDigits(0);
        return format.format(gaji);
    }

    public static String convertGajiToText(int gaji) {
        String priceText = String.valueOf(gaji);
        if (priceText.endsWith(".0")) {
            priceText = priceText.substring(0, priceText.length() - 2);
        }
        return priceText;
    }
}
