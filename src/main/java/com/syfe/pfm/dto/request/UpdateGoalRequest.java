package com.syfe.pfm.dto.request;

import java.math.BigDecimal;

/**
 * Request to update a savings goal.
 */
public class UpdateGoalRequest {

    private BigDecimal targetAmount;
    private String targetDate;

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }
}
