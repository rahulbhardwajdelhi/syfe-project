package com.syfe.pfm.util;

import com.syfe.pfm.exception.BadRequestException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateUtils {

    private static final DateTimeFormatter ISO_DATE = DateTimeFormatter.ISO_LOCAL_DATE;

    private DateUtils() {
    }

    // expects yyyy-mm-dd like 2024-01-15
    public static LocalDate parseDate(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new BadRequestException(fieldName + " is required");
        }
        try {
            return LocalDate.parse(value, ISO_DATE);
        } catch (DateTimeParseException ex) {
            throw new BadRequestException("Invalid date format for " + fieldName + ". Use YYYY-MM-DD");
        }
    }
}
