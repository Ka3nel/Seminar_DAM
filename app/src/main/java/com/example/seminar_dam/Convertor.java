package com.example.seminar_dam;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Convertor { // clasa care converteste datele care se transmit intre aplicatie si baza de date
    @TypeConverter
    public String toDate(Date data) {
        return new SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.getDefault()).format(data);
    }

    @TypeConverter
    public Date toDate(String data) {
        try {
            return new SimpleDateFormat("yyyy-mm-dd HH:mm:ss", Locale.getDefault()).parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
