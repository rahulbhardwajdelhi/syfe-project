package com.syfe.pfm.util;

import com.syfe.pfm.exception.BadRequestException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateUtilsTest {

    @Test
    void parseDate_validFormat() {
        assertEquals(LocalDate.of(2024, 1, 15), DateUtils.parseDate("2024-01-15", "date"));
    }

    @Test
    void parseDate_invalidFormatThrows() {
        assertThrows(BadRequestException.class, () -> DateUtils.parseDate("01-15-2024", "date"));
    }
}
