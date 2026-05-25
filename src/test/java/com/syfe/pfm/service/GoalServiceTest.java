package com.syfe.pfm.service;

import com.syfe.pfm.dto.request.CreateGoalRequest;
import com.syfe.pfm.entity.SavingsGoal;
import com.syfe.pfm.entity.User;
import com.syfe.pfm.exception.BadRequestException;
import com.syfe.pfm.repository.SavingsGoalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GoalServiceTest {

    @Mock
    private SavingsGoalRepository savingsGoalRepository;
    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private GoalService goalService;

    @Test
    void createGoal_calculatesProgress() {
        User user = new User();
        user.setId(1L);

        CreateGoalRequest request = new CreateGoalRequest();
        request.setGoalName("Emergency Fund");
        request.setTargetAmount(new BigDecimal("10000"));
        request.setTargetDate(LocalDate.now().plusYears(1).toString());
        request.setStartDate("2024-01-01");

        when(savingsGoalRepository.save(any(SavingsGoal.class))).thenAnswer(invocation -> {
            SavingsGoal goal = invocation.getArgument(0);
            goal.setId(1L);
            goal.setUser(user);
            return goal;
        });
        when(transactionService.calculateNetSavingsSince(user, LocalDate.of(2024, 1, 1)))
                .thenReturn(new BigDecimal("6550.00"));

        var response = goalService.createGoal(user, request);

        assertEquals(new BigDecimal("6550.00"), response.getCurrentProgress());
        assertEquals(new BigDecimal("65.5"), response.getProgressPercentage());
        assertEquals(new BigDecimal("3450.00"), response.getRemainingAmount());
    }

    @Test
    void createGoal_pastTargetDateFails() {
        User user = new User();
        CreateGoalRequest request = new CreateGoalRequest();
        request.setGoalName("Invalid");
        request.setTargetAmount(new BigDecimal("1000"));
        request.setTargetDate("2020-01-01");

        assertThrows(BadRequestException.class, () -> goalService.createGoal(user, request));
    }
}
