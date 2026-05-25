package com.syfe.pfm.controller;

import com.syfe.pfm.dto.request.CreateGoalRequest;
import com.syfe.pfm.dto.request.UpdateGoalRequest;
import com.syfe.pfm.dto.response.GoalListResponse;
import com.syfe.pfm.dto.response.GoalResponse;
import com.syfe.pfm.dto.response.MessageResponse;
import com.syfe.pfm.entity.User;
import com.syfe.pfm.service.AuthService;
import com.syfe.pfm.service.GoalService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    private final GoalService goalService;
    private final AuthService authService;

    public GoalController(GoalService goalService, AuthService authService) {
        this.goalService = goalService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<GoalResponse> create(@Valid @RequestBody CreateGoalRequest request) {
        User user = authService.getCurrentUser();
        GoalResponse response = goalService.createGoal(user, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public GoalListResponse getAll() {
        User user = authService.getCurrentUser();
        return goalService.getAllGoals(user);
    }

    @GetMapping("/{id}")
    public GoalResponse getById(@PathVariable Long id) {
        User user = authService.getCurrentUser();
        return goalService.getGoal(user, id);
    }

    @PutMapping("/{id}")
    public GoalResponse update(@PathVariable Long id, @RequestBody UpdateGoalRequest request) {
        User user = authService.getCurrentUser();
        return goalService.updateGoal(user, id, request);
    }

    @DeleteMapping("/{id}")
    public MessageResponse delete(@PathVariable Long id) {
        User user = authService.getCurrentUser();
        return goalService.deleteGoal(user, id);
    }
}
