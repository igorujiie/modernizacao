package com.luizalabs.modernizacao.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatarData {

    public static String formatDate(String date) {
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
            Date parsedDate = originalFormat.parse(date);
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
            return newFormat.format(parsedDate);
        } catch (ParseException e) {
            return date;

        }
    }
}
