package com.syfe.pfm.service;

import com.syfe.pfm.dto.request.CreateCategoryRequest;
import com.syfe.pfm.entity.Category;
import com.syfe.pfm.entity.User;
import com.syfe.pfm.exception.BadRequestException;
import com.syfe.pfm.exception.ConflictException;
import com.syfe.pfm.exception.ForbiddenException;
import com.syfe.pfm.model.CategoryType;
import com.syfe.pfm.repository.CategoryRepository;
import com.syfe.pfm.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void createCategory_duplicateNameThrowsConflict() {
        User user = new User();
        CreateCategoryRequest request = new CreateCategoryRequest();
        request.setName("Freelance");
        request.setType(CategoryType.INCOME);

        when(categoryRepository.existsByUserAndName(user, "Freelance")).thenReturn(true);

        assertThrows(ConflictException.class, () -> categoryService.createCategory(user, request));
    }

    @Test
    void deleteCategory_defaultCategoryForbidden() {
        User user = new User();
        Category category = new Category();
        category.setName("Food");
        category.setCustom(false);
        category.setUser(user);

        when(categoryRepository.findByUserAndName(user, "Food")).thenReturn(Optional.of(category));

        assertThrows(ForbiddenException.class, () -> categoryService.deleteCategory(user, "Food"));
    }

    @Test
    void deleteCategory_inUseThrowsBadRequest() {
        User user = new User();
        Category category = new Category();
        category.setName("Freelance");
        category.setCustom(true);
        category.setUser(user);

        when(categoryRepository.findByUserAndName(user, "Freelance")).thenReturn(Optional.of(category));
        when(transactionRepository.existsByCategoryAndDeletedFalse(category)).thenReturn(true);

        assertThrows(BadRequestException.class, () -> categoryService.deleteCategory(user, "Freelance"));
    }

    @Test
    void deleteCategory_success() {
        User user = new User();
        Category category = new Category();
        category.setName("Freelance");
        category.setCustom(true);
        category.setUser(user);

        when(categoryRepository.findByUserAndName(user, "Freelance")).thenReturn(Optional.of(category));
        when(transactionRepository.existsByCategoryAndDeletedFalse(category)).thenReturn(false);

        var response = categoryService.deleteCategory(user, "Freelance");
        assertTrue(response.getMessage().contains("deleted"));
    }
}
