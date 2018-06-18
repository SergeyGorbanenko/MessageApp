package com.bank.messageapp.persistence;

import android.arch.persistence.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * Класс-конвертер для базы данных
 */
public class Converters {

    /**
     * Поле формат даты и времени
     */
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");

    /**
     * Перевести LocalDateTime в String
     * @param dateTime дата и время
     * @return строковая переменая даты и времени
     */
    @TypeConverter
    public static String fromOffsetDateTime(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(formatter);
    }

    /**
     * Перевести String в LocalDateTime
     * @param value дата и время
     * @return переменая даты и времени
     */
    @TypeConverter
    public static LocalDateTime toOffsetDateTime(String value) {
        return value == null ? null : formatter.parse(value, LocalDateTime::from);
    }

}
