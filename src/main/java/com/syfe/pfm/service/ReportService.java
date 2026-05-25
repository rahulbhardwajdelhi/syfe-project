package com.syfe.pfm.service;

import com.syfe.pfm.dto.response.MonthlyReportResponse;
import com.syfe.pfm.dto.response.YearlyReportResponse;
import com.syfe.pfm.entity.User;
import com.syfe.pfm.exception.BadRequestException;
import com.syfe.pfm.model.CategoryType;
import com.syfe.pfm.repository.TransactionRepository;
import com.syfe.pfm.util.MoneyUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// monthly and yearly reports
@Service
public class ReportService {

    private final TransactionRepository transactionRepository;

    public ReportService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public MonthlyReportResponse getMonthlyReport(User user, int year, int month) {
        validateMonth(month);
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();

        MonthlyReportResponse response = new MonthlyReportResponse();
        response.setYear(year);
        response.setMonth(month);
        response.setTotalIncome(buildCategoryTotals(user, CategoryType.INCOME, start, end));
        response.setTotalExpenses(buildCategoryTotals(user, CategoryType.EXPENSE, start, end));
        response.setNetSavings(calculateNetSavings(response.getTotalIncome(), response.getTotalExpenses()));
        return response;
    }

    public YearlyReportResponse getYearlyReport(User user, int year) {
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);

        YearlyReportResponse response = new YearlyReportResponse();
        response.setYear(year);
        response.setTotalIncome(buildCategoryTotals(user, CategoryType.INCOME, start, end));
        response.setTotalExpenses(buildCategoryTotals(user, CategoryType.EXPENSE, start, end));
        response.setNetSavings(calculateNetSavings(response.getTotalIncome(), response.getTotalExpenses()));
        return response;
    }

    private Map<String, BigDecimal> buildCategoryTotals(
            User user, CategoryType type, LocalDate start, LocalDate end) {
        Map<String, BigDecimal> totals = new LinkedHashMap<>();
        List<Object[]> rows = transactionRepository.sumByCategoryForPeriod(user, type, start, end);
        for (Object[] row : rows) {
            String name = (String) row[0];
            BigDecimal amount = MoneyUtils.scaleMoney((BigDecimal) row[1]);
            totals.put(name, amount);
        }
        return totals;
    }

    private BigDecimal calculateNetSavings(Map<String, BigDecimal> income, Map<String, BigDecimal> expenses) {
        BigDecimal totalIncome = income.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalExpenses = expenses.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal net = totalIncome.subtract(totalExpenses);
        if (net.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return MoneyUtils.scaleMoney(net);
    }

    private void validateMonth(int month) {
        if (month < 1 || month > 12) {
            throw new BadRequestException("Month must be between 1 and 12");
        }
    }
}
