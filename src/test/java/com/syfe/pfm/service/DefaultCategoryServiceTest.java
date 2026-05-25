package com.syfe.pfm.service;

import com.syfe.pfm.entity.User;
import com.syfe.pfm.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DefaultCategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private DefaultCategoryService defaultCategoryService;

    @Test
    void createDefaultCategories_seedsSevenCategories() {
        User user = new User();
        user.setId(1L);

        defaultCategoryService.createDefaultCategories(user);

        verify(categoryRepository, times(7)).save(argThat(category ->
                category.getUser() == user && !category.isCustom()));
        assertEquals(7, defaultCategoryService.getDefaultCategoryNames().size());
    }
}
