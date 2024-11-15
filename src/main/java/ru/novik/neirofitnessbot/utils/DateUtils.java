package ru.novik.neirofitnessbot.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm");

    public static String formatDate(LocalDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }
}
