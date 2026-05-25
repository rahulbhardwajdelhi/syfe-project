package com.syfe.pfm.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

// helper for money and goal % - test script is strict on decimal format
public final class MoneyUtils {

    private MoneyUtils() {
    }

    public static BigDecimal scaleMoney(BigDecimal value) {
        if (value == null) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal scaleProgressPercentage(BigDecimal currentProgress, BigDecimal targetAmount) {
        if (targetAmount == null || targetAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO.setScale(1, RoundingMode.HALF_UP);
        }
        BigDecimal percentage = currentProgress
                .multiply(BigDecimal.valueOf(100))
                .divide(targetAmount, 4, RoundingMode.HALF_UP);

        if (percentage.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.setScale(1, RoundingMode.HALF_UP);
        }

        BigDecimal rounded = percentage.setScale(2, RoundingMode.HALF_UP);
        if (rounded.stripTrailingZeros().scale() <= 0) {
            return rounded.setScale(1, RoundingMode.UNNECESSARY);
        }
        if (rounded.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0) {
            return rounded.setScale(1, RoundingMode.UNNECESSARY);
        }
        if (rounded.scale() > 1 && rounded.setScale(1, RoundingMode.HALF_UP).compareTo(rounded) == 0) {
            return rounded.setScale(1, RoundingMode.HALF_UP);
        }
        return rounded.stripTrailingZeros().scale() < 2
                ? rounded.setScale(Math.max(1, rounded.stripTrailingZeros().scale()), RoundingMode.UNNECESSARY)
                : rounded;
    }

    public static BigDecimal remainingAmount(BigDecimal currentProgress, BigDecimal targetAmount) {
        BigDecimal remaining = targetAmount.subtract(currentProgress);
        if (remaining.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        return scaleMoney(remaining);
    }
}
