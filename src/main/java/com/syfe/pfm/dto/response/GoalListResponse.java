package com.syfe.pfm.dto.response;

import java.util.ArrayList;
import java.util.List;

/**
 * List of savings goals response.
 */
public class GoalListResponse {

    private List<GoalResponse> goals = new ArrayList<>();

    public List<GoalResponse> getGoals() {
        return goals;
    }

    public void setGoals(List<GoalResponse> goals) {
        this.goals = goals;
    }
}
