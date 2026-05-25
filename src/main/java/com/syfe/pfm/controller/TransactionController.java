package com.syfe.pfm.controller;

import com.syfe.pfm.dto.request.CreateTransactionRequest;
import com.syfe.pfm.dto.request.UpdateTransactionRequest;
import com.syfe.pfm.dto.response.MessageResponse;
import com.syfe.pfm.dto.response.TransactionListResponse;
import com.syfe.pfm.dto.response.TransactionResponse;
import com.syfe.pfm.entity.User;
import com.syfe.pfm.exception.BadRequestException;
import com.syfe.pfm.model.CategoryType;
import com.syfe.pfm.service.AuthService;
import com.syfe.pfm.service.TransactionService;
import com.syfe.pfm.util.DateUtils;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final AuthService authService;

    public TransactionController(TransactionService transactionService, AuthService authService) {
        this.transactionService = transactionService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> create(@Valid @RequestBody CreateTransactionRequest request) {
        User user = authService.getCurrentUser();
        TransactionResponse response = transactionService.createTransaction(user, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public TransactionListResponse getAll(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String type) {
        User user = authService.getCurrentUser();
        LocalDate start = parseOptionalDate(startDate, "startDate");
        LocalDate end = parseOptionalDate(endDate, "endDate");
        CategoryType categoryType = parseOptionalType(type);
        return transactionService.getTransactions(user, start, end, categoryId, category, categoryType);
    }

    @PutMapping("/{id}")
    public TransactionResponse update(@PathVariable Long id, @RequestBody UpdateTransactionRequest request) {
        User user = authService.getCurrentUser();
        return transactionService.updateTransaction(user, id, request);
    }

    @DeleteMapping("/{id}")
    public MessageResponse delete(@PathVariable Long id) {
        User user = authService.getCurrentUser();
        return transactionService.deleteTransaction(user, id);
    }

    private LocalDate parseOptionalDate(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return DateUtils.parseDate(value, fieldName);
    }

    private CategoryType parseOptionalType(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return CategoryType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Invalid transaction type: " + value);
        }
    }
}
