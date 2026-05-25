package com.syfe.pfm.controller;

import com.syfe.pfm.dto.request.CreateCategoryRequest;
import com.syfe.pfm.dto.response.CategoryListResponse;
import com.syfe.pfm.dto.response.CategoryResponse;
import com.syfe.pfm.dto.response.MessageResponse;
import com.syfe.pfm.entity.User;
import com.syfe.pfm.service.AuthService;
import com.syfe.pfm.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final AuthService authService;

    public CategoryController(CategoryService categoryService, AuthService authService) {
        this.categoryService = categoryService;
        this.authService = authService;
    }

    @GetMapping
    public CategoryListResponse getAll() {
        User user = authService.getCurrentUser();
        return categoryService.getAllCategories(user);
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CreateCategoryRequest request) {
        User user = authService.getCurrentUser();
        CategoryResponse response = categoryService.createCategory(user, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{name}")
    public MessageResponse delete(@PathVariable String name) {
        User user = authService.getCurrentUser();
        return categoryService.deleteCategory(user, name);
    }
}
