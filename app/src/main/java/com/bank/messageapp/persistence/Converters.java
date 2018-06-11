package com.bank.messageapp.persistence;

import android.arch.persistence.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Converters {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");

    @TypeConverter
    public static String fromOffsetDateTime(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(formatter);
    }

    @TypeConverter
    public static LocalDateTime toOffsetDateTime(String value) {
        return value == null ? null : formatter.parse(value, LocalDateTime::from);
    }

}
