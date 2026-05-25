package com.syfe.pfm.repository;

import com.syfe.pfm.entity.SavingsGoal;
import com.syfe.pfm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for {@link SavingsGoal} entities.
 */
public interface SavingsGoalRepository extends JpaRepository<SavingsGoal, Long> {

    List<SavingsGoal> findByUserOrderByIdAsc(User user);

    Optional<SavingsGoal> findByIdAndUser(Long id, User user);
}
