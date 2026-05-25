package com.syfe.pfm.service;

import com.syfe.pfm.dto.request.RegisterRequest;
import com.syfe.pfm.dto.response.RegisterResponse;
import com.syfe.pfm.entity.User;
import com.syfe.pfm.exception.ConflictException;
import com.syfe.pfm.repository.UserRepository;
import com.syfe.pfm.security.AuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DefaultCategoryService defaultCategoryService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            DefaultCategoryService defaultCategoryService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultCategoryService = defaultCategoryService;
    }

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ConflictException("User already exists with this email");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhoneNumber(request.getPhoneNumber());
        user = userRepository.save(user);

        // give them Salary, Food, Rent etc on signup
        defaultCategoryService.createDefaultCategories(user);

        return new RegisterResponse("User registered successfully", user.getId());
    }

    // who is logged in right now
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof AuthUser authUser)) {
            throw new com.syfe.pfm.exception.BadRequestException("User not authenticated");
        }
        return authUser.getUser();
    }
}
