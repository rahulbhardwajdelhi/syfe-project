package com.syfe.pfm.repository;

import com.syfe.pfm.entity.Category;
import com.syfe.pfm.entity.Transaction;
import com.syfe.pfm.entity.User;
import com.syfe.pfm.model.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository for {@link Transaction} entities.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByIdAndUserAndDeletedFalse(Long id, User user);

    List<Transaction> findByUserAndDeletedFalseOrderByDateDescIdDesc(User user);

    boolean existsByCategoryAndDeletedFalse(Category category);

    @Query("""
            SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t
            WHERE t.user = :user AND t.deleted = false
            AND t.date >= :startDate
            AND t.category.type = :type
            """)
    BigDecimal sumAmountByUserAndTypeSince(
            @Param("user") User user,
            @Param("type") CategoryType type,
            @Param("startDate") LocalDate startDate);

    @Query("""
            SELECT t FROM Transaction t
            WHERE t.user = :user AND t.deleted = false
            AND (:startDate IS NULL OR t.date >= :startDate)
            AND (:endDate IS NULL OR t.date <= :endDate)
            AND (:category IS NULL OR t.category = :category)
            AND (:type IS NULL OR t.category.type = :type)
            ORDER BY t.date DESC, t.id DESC
            """)
    List<Transaction> findFiltered(
            @Param("user") User user,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("category") Category category,
            @Param("type") CategoryType type);

    @Query("""
            SELECT t.category.name, SUM(t.amount) FROM Transaction t
            WHERE t.user = :user AND t.deleted = false
            AND t.category.type = :type
            AND t.date >= :startDate AND t.date <= :endDate
            GROUP BY t.category.name
            """)
    List<Object[]> sumByCategoryForPeriod(
            @Param("user") User user,
            @Param("type") CategoryType type,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
