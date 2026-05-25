package com.syfe.pfm.repository;

import com.syfe.pfm.entity.Category;
import com.syfe.pfm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for {@link Category} entities.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUserOrderByNameAsc(User user);

    Optional<Category> findByUserAndName(User user, String name);

    boolean existsByUserAndName(User user, String name);
}
