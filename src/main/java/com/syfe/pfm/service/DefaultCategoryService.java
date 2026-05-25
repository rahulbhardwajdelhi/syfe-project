package com.syfe.pfm.service;

import com.syfe.pfm.entity.Category;
import com.syfe.pfm.entity.User;
import com.syfe.pfm.model.CategoryType;
import com.syfe.pfm.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

// the 7 built-in categories from the assignment pdf
@Service
public class DefaultCategoryService {

    private static final Map<String, CategoryType> DEFAULT_CATEGORIES = Map.ofEntries(
            Map.entry("Salary", CategoryType.INCOME),
            Map.entry("Food", CategoryType.EXPENSE),
            Map.entry("Rent", CategoryType.EXPENSE),
            Map.entry("Transportation", CategoryType.EXPENSE),
            Map.entry("Entertainment", CategoryType.EXPENSE),
            Map.entry("Healthcare", CategoryType.EXPENSE),
            Map.entry("Utilities", CategoryType.EXPENSE)
    );

    private final CategoryRepository categoryRepository;

    public DefaultCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void createDefaultCategories(User user) {
        DEFAULT_CATEGORIES.forEach((name, type) -> {
            Category category = new Category();
            category.setName(name);
            category.setType(type);
            category.setCustom(false);
            category.setUser(user);
            categoryRepository.save(category);
        });
    }

    public List<String> getDefaultCategoryNames() {
        return List.copyOf(DEFAULT_CATEGORIES.keySet());
    }
}
