package com.syfe.pfm.service;

import com.syfe.pfm.dto.request.RegisterRequest;
import com.syfe.pfm.dto.response.RegisterResponse;
import com.syfe.pfm.entity.User;
import com.syfe.pfm.exception.ConflictException;
import com.syfe.pfm.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private DefaultCategoryService defaultCategoryService;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_createsUserAndDefaultCategories() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("user@example.com");
        request.setPassword("password123");
        request.setFullName("John Doe");
        request.setPhoneNumber("+1234567890");

        when(userRepository.existsByUsername("user@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1L);
            return user;
        });

        RegisterResponse response = authService.register(request);

        assertEquals("User registered successfully", response.getMessage());
        assertEquals(1L, response.getUserId());
        verify(defaultCategoryService).createDefaultCategories(any(User.class));
    }

    @Test
    void register_duplicateUsernameThrowsConflict() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("user@example.com");
        when(userRepository.existsByUsername("user@example.com")).thenReturn(true);

        assertThrows(ConflictException.class, () -> authService.register(request));
    }
}
