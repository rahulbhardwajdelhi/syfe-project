package com.syfe.pfm.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyUtilsTest {

    @Test
    void scaleMoney_roundsToTwoDecimals() {
        assertEquals(new BigDecimal("10.56"), MoneyUtils.scaleMoney(new BigDecimal("10.555")));
    }

    @Test
    void scaleProgressPercentage_formatsExpectedValues() {
        assertEquals(new BigDecimal("65.5"),
                MoneyUtils.scaleProgressPercentage(new BigDecimal("6550.00"), new BigDecimal("10000.00")));
        assertEquals(new BigDecimal("0.0"),
                MoneyUtils.scaleProgressPercentage(BigDecimal.ZERO, new BigDecimal("5000.00")));
        assertEquals(new BigDecimal("50.0"),
                MoneyUtils.scaleProgressPercentage(new BigDecimal("2500.00"), new BigDecimal("5000.00")));
        assertEquals(new BigDecimal("60.33"),
                MoneyUtils.scaleProgressPercentage(new BigDecimal("9050.00"), new BigDecimal("15000.00")));
        assertEquals(new BigDecimal("60.0"),
                MoneyUtils.scaleProgressPercentage(new BigDecimal("3000.00"), new BigDecimal("5000.00")));
    }

    @Test
    void remainingAmount_neverNegative() {
        assertEquals(new BigDecimal("0.00"),
                MoneyUtils.remainingAmount(new BigDecimal("12000.00"), new BigDecimal("10000.00")));
        assertEquals(new BigDecimal("3450.00"),
                MoneyUtils.remainingAmount(new BigDecimal("6550.00"), new BigDecimal("10000.00")));
    }
}
