package com.syfe.pfm.service;

import com.syfe.pfm.dto.request.CreateTransactionRequest;
import com.syfe.pfm.dto.request.UpdateTransactionRequest;
import com.syfe.pfm.dto.response.MessageResponse;
import com.syfe.pfm.dto.response.TransactionListResponse;
import com.syfe.pfm.dto.response.TransactionResponse;
import com.syfe.pfm.entity.Category;
import com.syfe.pfm.entity.Transaction;
import com.syfe.pfm.entity.User;
import com.syfe.pfm.exception.BadRequestException;
import com.syfe.pfm.exception.ResourceNotFoundException;
import com.syfe.pfm.model.CategoryType;
import com.syfe.pfm.repository.TransactionRepository;
import com.syfe.pfm.util.DateUtils;
import com.syfe.pfm.util.MoneyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// handles all transaction stuff for one user
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryService categoryService;

    public TransactionService(TransactionRepository transactionRepository, CategoryService categoryService) {
        this.transactionRepository = transactionRepository;
        this.categoryService = categoryService;
    }

    @Transactional
    public TransactionResponse createTransaction(User user, CreateTransactionRequest request) {
        LocalDate date = DateUtils.parseDate(request.getDate(), "date");
        validateTransactionDate(date);

        Category category = categoryService.findCategoryByName(user, request.getCategory());

        Transaction transaction = new Transaction();
        transaction.setAmount(MoneyUtils.scaleMoney(request.getAmount()));
        transaction.setDate(date);
        transaction.setCategory(category);
        transaction.setDescription(request.getDescription());
        transaction.setUser(user);
        transaction.setDeleted(false);

        transaction = transactionRepository.save(transaction);
        return toResponse(transaction);
    }

    public TransactionListResponse getTransactions(
            User user,
            LocalDate startDate,
            LocalDate endDate,
            Long categoryId,
            String categoryName,
            CategoryType type) {
        Category category = null;
        if (categoryId != null) {
            category = categoryService.findCategoryById(user, categoryId);
        } else if (categoryName != null && !categoryName.isBlank()) {
            category = categoryService.findCategoryByName(user, categoryName);
        }

        List<Transaction> transactions = transactionRepository.findFiltered(
                user, startDate, endDate, category, type);

        TransactionListResponse response = new TransactionListResponse();
        response.setTransactions(transactions.stream().map(this::toResponse).toList());
        return response;
    }

    @Transactional
    public TransactionResponse updateTransaction(User user, Long id, UpdateTransactionRequest request) {
        Transaction transaction = transactionRepository.findByIdAndUserAndDeletedFalse(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        if (request.getAmount() != null) {
            if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new BadRequestException("Amount must be positive");
            }
            transaction.setAmount(MoneyUtils.scaleMoney(request.getAmount()));
        }

        if (request.getCategory() != null && !request.getCategory().isBlank()) {
            Category category = categoryService.findCategoryByName(user, request.getCategory());
            transaction.setCategory(category);
        }

        if (request.getDescription() != null) {
            transaction.setDescription(request.getDescription());
        }

        // assignment says date cant change - we just dont touch it even if client sends date
        transaction = transactionRepository.save(transaction);
        return toResponse(transaction);
    }

    @Transactional
    public MessageResponse deleteTransaction(User user, Long id) {
        Transaction transaction = transactionRepository.findByIdAndUserAndDeletedFalse(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        // soft delete so goals/reports ignore it but row stays in db
        transaction.setDeleted(true);
        transactionRepository.save(transaction);
        return new MessageResponse("Transaction deleted successfully");
    }

    // income minus expense since a date - used for goal progress
    public BigDecimal calculateNetSavingsSince(User user, LocalDate startDate) {
        BigDecimal income = MoneyUtils.scaleMoney(
                transactionRepository.sumAmountByUserAndTypeSince(user, CategoryType.INCOME, startDate));
        BigDecimal expenses = MoneyUtils.scaleMoney(
                transactionRepository.sumAmountByUserAndTypeSince(user, CategoryType.EXPENSE, startDate));
        BigDecimal net = income.subtract(expenses);
        // test script wants plain 0 not 0.00 when nothing there
        if (net.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return MoneyUtils.scaleMoney(net);
    }

    private void validateTransactionDate(LocalDate date) {
        if (date.isAfter(LocalDate.now())) {
            throw new BadRequestException("Transaction date cannot be in the future");
        }
    }

    private TransactionResponse toResponse(Transaction transaction) {
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setAmount(MoneyUtils.scaleMoney(transaction.getAmount()));
        response.setDate(transaction.getDate().toString());
        response.setCategory(transaction.getCategory().getName());
        response.setDescription(transaction.getDescription());
        response.setType(transaction.getCategory().getType());
        return response;
    }
}
