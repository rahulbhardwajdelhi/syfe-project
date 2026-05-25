package com.syfe.pfm.service;

import com.syfe.pfm.dto.request.CreateGoalRequest;
import com.syfe.pfm.dto.request.UpdateGoalRequest;
import com.syfe.pfm.dto.response.GoalListResponse;
import com.syfe.pfm.dto.response.GoalResponse;
import com.syfe.pfm.dto.response.MessageResponse;
import com.syfe.pfm.entity.SavingsGoal;
import com.syfe.pfm.entity.User;
import com.syfe.pfm.exception.BadRequestException;
import com.syfe.pfm.exception.ResourceNotFoundException;
import com.syfe.pfm.repository.SavingsGoalRepository;
import com.syfe.pfm.util.DateUtils;
import com.syfe.pfm.util.MoneyUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

// savings goals + progress math
@Service
public class GoalService {

    private final SavingsGoalRepository savingsGoalRepository;
    private final TransactionService transactionService;

    public GoalService(SavingsGoalRepository savingsGoalRepository, TransactionService transactionService) {
        this.savingsGoalRepository = savingsGoalRepository;
        this.transactionService = transactionService;
    }

    @Transactional
    public GoalResponse createGoal(User user, CreateGoalRequest request) {
        LocalDate targetDate = DateUtils.parseDate(request.getTargetDate(), "targetDate");
        // no start date in request = use today
        LocalDate startDate = request.getStartDate() == null || request.getStartDate().isBlank()
                ? LocalDate.now()
                : DateUtils.parseDate(request.getStartDate(), "startDate");

        validateGoalDates(targetDate, startDate);

        SavingsGoal goal = new SavingsGoal();
        goal.setGoalName(request.getGoalName());
        goal.setTargetAmount(MoneyUtils.scaleMoney(request.getTargetAmount()));
        goal.setTargetDate(targetDate);
        goal.setStartDate(startDate);
        goal.setUser(user);

        goal = savingsGoalRepository.save(goal);
        return toResponse(goal);
    }

    public GoalListResponse getAllGoals(User user) {
        GoalListResponse response = new GoalListResponse();
        response.setGoals(savingsGoalRepository.findByUserOrderByIdAsc(user).stream()
                .map(this::toResponse)
                .toList());
        return response;
    }

    public GoalResponse getGoal(User user, Long id) {
        SavingsGoal goal = findGoal(user, id);
        return toResponse(goal);
    }

    @Transactional
    public GoalResponse updateGoal(User user, Long id, UpdateGoalRequest request) {
        SavingsGoal goal = findGoal(user, id);

        if (request.getTargetAmount() != null) {
            if (request.getTargetAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new BadRequestException("Target amount must be positive");
            }
            goal.setTargetAmount(MoneyUtils.scaleMoney(request.getTargetAmount()));
        }

        if (request.getTargetDate() != null && !request.getTargetDate().isBlank()) {
            LocalDate targetDate = DateUtils.parseDate(request.getTargetDate(), "targetDate");
            if (!targetDate.isAfter(LocalDate.now())) {
                throw new BadRequestException("Target date must be in the future");
            }
            if (targetDate.isBefore(goal.getStartDate())) {
                throw new BadRequestException("Target date must be after start date");
            }
            goal.setTargetDate(targetDate);
        }

        goal = savingsGoalRepository.save(goal);
        return toResponse(goal);
    }

    @Transactional
    public MessageResponse deleteGoal(User user, Long id) {
        SavingsGoal goal = findGoal(user, id);
        savingsGoalRepository.delete(goal);
        return new MessageResponse("Goal deleted successfully");
    }

    private SavingsGoal findGoal(User user, Long id) {
        return savingsGoalRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found"));
    }

    private void validateGoalDates(LocalDate targetDate, LocalDate startDate) {
        if (!targetDate.isAfter(LocalDate.now())) {
            throw new BadRequestException("Target date must be in the future");
        }
        if (startDate.isAfter(targetDate)) {
            throw new BadRequestException("Start date cannot be after target date");
        }
    }

    private GoalResponse toResponse(SavingsGoal goal) {
        BigDecimal currentProgress = transactionService.calculateNetSavingsSince(goal.getUser(), goal.getStartDate());
        if (currentProgress.compareTo(BigDecimal.ZERO) != 0) {
            currentProgress = MoneyUtils.scaleMoney(currentProgress);
        }

        GoalResponse response = new GoalResponse();
        response.setId(goal.getId());
        response.setGoalName(goal.getGoalName());
        response.setTargetAmount(MoneyUtils.scaleMoney(goal.getTargetAmount()));
        response.setTargetDate(goal.getTargetDate().toString());
        response.setStartDate(goal.getStartDate().toString());
        response.setCurrentProgress(currentProgress);
        // percentage formatting is a bit picky becuase of the test script
        response.setProgressPercentage(MoneyUtils.scaleProgressPercentage(currentProgress, goal.getTargetAmount()));
        response.setRemainingAmount(MoneyUtils.remainingAmount(currentProgress, goal.getTargetAmount()));
        return response;
    }
}
