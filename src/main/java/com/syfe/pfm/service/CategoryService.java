package com.syfe.pfm.service;

import com.syfe.pfm.dto.request.CreateCategoryRequest;
import com.syfe.pfm.dto.response.CategoryListResponse;
import com.syfe.pfm.dto.response.CategoryResponse;
import com.syfe.pfm.dto.response.MessageResponse;
import com.syfe.pfm.entity.Category;
import com.syfe.pfm.entity.User;
import com.syfe.pfm.exception.BadRequestException;
import com.syfe.pfm.exception.ConflictException;
import com.syfe.pfm.exception.ForbiddenException;
import com.syfe.pfm.exception.ResourceNotFoundException;
import com.syfe.pfm.repository.CategoryRepository;
import com.syfe.pfm.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    public CategoryService(CategoryRepository categoryRepository, TransactionRepository transactionRepository) {
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
    }

    public CategoryListResponse getAllCategories(User user) {
        CategoryListResponse response = new CategoryListResponse();
        List<CategoryResponse> categories = categoryRepository.findByUserOrderByNameAsc(user).stream()
                .map(this::toResponse)
                .toList();
        response.setCategories(categories);
        return response;
    }

    @Transactional
    public CategoryResponse createCategory(User user, CreateCategoryRequest request) {
        if (categoryRepository.existsByUserAndName(user, request.getName())) {
            throw new ConflictException("Category with this name already exists");
        }

        Category category = new Category();
        category.setName(request.getName());
        category.setType(request.getType());
        category.setCustom(true);
        category.setUser(user);
        category = categoryRepository.save(category);
        return toResponse(category);
    }

    @Transactional
    public MessageResponse deleteCategory(User user, String name) {
        // category name might have spaces etc in url
        String decodedName = URLDecoder.decode(name, StandardCharsets.UTF_8);
        Category category = categoryRepository.findByUserAndName(user, decodedName)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        if (!category.isCustom()) {
            throw new ForbiddenException("Default categories cannot be deleted");
        }

        if (transactionRepository.existsByCategoryAndDeletedFalse(category)) {
            throw new BadRequestException("Category is in use and cannot be deleted");
        }

        categoryRepository.delete(category);
        return new MessageResponse("Category deleted successfully");
    }

    public Category findCategoryByName(User user, String name) {
        return categoryRepository.findByUserAndName(user, name)
                .orElseThrow(() -> new BadRequestException("Invalid category: " + name));
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getName(), category.getType(), category.isCustom());
    }
}
