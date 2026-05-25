package com.syfe.pfm.dto.request;

import java.math.BigDecimal;

/**
 * Request to update an existing transaction. Date changes are ignored.
 */
public class UpdateTransactionRequest {

    private BigDecimal amount;
    private String date;
    private String category;
    private String description;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
