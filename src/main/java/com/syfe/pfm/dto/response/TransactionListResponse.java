package com.syfe.pfm.dto.response;

import java.util.ArrayList;
import java.util.List;

/**
 * List of transactions response.
 */
public class TransactionListResponse {

    private List<TransactionResponse> transactions = new ArrayList<>();

    public List<TransactionResponse> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionResponse> transactions) {
        this.transactions = transactions;
    }
}
