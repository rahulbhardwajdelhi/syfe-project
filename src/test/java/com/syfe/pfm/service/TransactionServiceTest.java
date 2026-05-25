package com.syfe.pfm.service;

import com.syfe.pfm.dto.request.CreateTransactionRequest;
import com.syfe.pfm.dto.request.UpdateTransactionRequest;
import com.syfe.pfm.entity.Category;
import com.syfe.pfm.entity.Transaction;
import com.syfe.pfm.entity.User;
import com.syfe.pfm.exception.BadRequestException;
import com.syfe.pfm.exception.ResourceNotFoundException;
import com.syfe.pfm.model.CategoryType;
import com.syfe.pfm.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private TransactionService transactionService;

    private User user;
    private Category salaryCategory;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        salaryCategory = new Category();
        salaryCategory.setName("Salary");
        salaryCategory.setType(CategoryType.INCOME);
    }

    @Test
    void createTransaction_success() {
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setAmount(new BigDecimal("5000"));
        request.setDate("2024-01-15");
        request.setCategory("Salary");
        request.setDescription("Salary payment");

        when(categoryService.findCategoryByName(user, "Salary")).thenReturn(salaryCategory);
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> {
            Transaction tx = invocation.getArgument(0);
            tx.setId(10L);
            return tx;
        });

        var response = transactionService.createTransaction(user, request);

        assertEquals(10L, response.getId());
        assertEquals(new BigDecimal("5000.00"), response.getAmount());
        assertEquals(CategoryType.INCOME, response.getType());
    }

    @Test
    void createTransaction_futureDateFails() {
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setAmount(new BigDecimal("100"));
        request.setDate(LocalDate.now().plusDays(1).toString());
        request.setCategory("Salary");

        assertThrows(BadRequestException.class, () -> transactionService.createTransaction(user, request));
    }

    @Test
    void updateTransaction_ignoresDateField() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(new BigDecimal("5000"));
        transaction.setDate(LocalDate.of(2024, 1, 15));
        transaction.setCategory(salaryCategory);
        transaction.setUser(user);

        when(transactionRepository.findByIdAndUserAndDeletedFalse(1L, user)).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UpdateTransactionRequest request = new UpdateTransactionRequest();
        request.setAmount(new BigDecimal("5500"));
        request.setDate("2024-01-20");

        var response = transactionService.updateTransaction(user, 1L, request);

        assertEquals("2024-01-15", response.getDate());
        assertEquals(new BigDecimal("5500.00"), response.getAmount());
    }

    @Test
    void deleteTransaction_notFound() {
        when(transactionRepository.findByIdAndUserAndDeletedFalse(99L, user)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> transactionService.deleteTransaction(user, 99L));
    }
}
