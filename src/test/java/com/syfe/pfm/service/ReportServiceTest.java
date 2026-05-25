package com.syfe.pfm.service;

import com.syfe.pfm.entity.User;
import com.syfe.pfm.exception.BadRequestException;
import com.syfe.pfm.model.CategoryType;
import com.syfe.pfm.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private ReportService reportService;

    @Test
    void getMonthlyReport_calculatesNetSavings() {
        User user = new User();
        when(transactionRepository.sumByCategoryForPeriod(
                eq(user), eq(CategoryType.INCOME), eq(LocalDate.of(2024, 1, 1)), eq(LocalDate.of(2024, 1, 31))))
                .thenReturn(List.<Object[]>of(new Object[]{"Salary", new BigDecimal("5500")}));
        when(transactionRepository.sumByCategoryForPeriod(
                eq(user), eq(CategoryType.EXPENSE), eq(LocalDate.of(2024, 1, 1)), eq(LocalDate.of(2024, 1, 31))))
                .thenReturn(List.<Object[]>of(new Object[]{"Food", new BigDecimal("450")}));

        var response = reportService.getMonthlyReport(user, 2024, 1);

        assertEquals(1, response.getMonth());
        assertEquals(2024, response.getYear());
        assertEquals(new BigDecimal("5050.00"), response.getNetSavings());
    }

    @Test
    void getMonthlyReport_invalidMonthFails() {
        assertThrows(BadRequestException.class, () -> reportService.getMonthlyReport(new User(), 2024, 13));
    }

    @Test
    void getYearlyReport_emptyYearReturnsZeroNetSavings() {
        User user = new User();
        when(transactionRepository.sumByCategoryForPeriod(
                eq(user), eq(CategoryType.INCOME), eq(LocalDate.of(2023, 1, 1)), eq(LocalDate.of(2023, 12, 31))))
                .thenReturn(List.of());
        when(transactionRepository.sumByCategoryForPeriod(
                eq(user), eq(CategoryType.EXPENSE), eq(LocalDate.of(2023, 1, 1)), eq(LocalDate.of(2023, 12, 31))))
                .thenReturn(List.of());

        var response = reportService.getYearlyReport(user, 2023);

        assertEquals(2023, response.getYear());
        assertEquals(0, response.getNetSavings().compareTo(java.math.BigDecimal.ZERO));
    }
}
