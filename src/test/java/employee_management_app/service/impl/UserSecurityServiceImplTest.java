package employee_management_app.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import employee_management_app.model.AppUser;
import employee_management_app.repository.UserRepository;

public class UserSecurityServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserSecurityServiceImpl userSecurityService;

    private AppUser user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new AppUser();
        user.setFailedAttempt(0);
        user.setAccountNonLocked(true);
    }

    @Test
    void incrementFailedAttempts_ShouldIncrementAndNotLock() {
        // Given
        user.setFailedAttempt(1);
        when(userRepository.save(any(AppUser.class))).thenReturn(user);

        // When
        userSecurityService.incrementFailedAttempts(user);

        // Then
        assertEquals(2, user.getFailedAttempt());
        assertTrue(user.isAccountNonLocked());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void incrementFailedAttempts_ShouldLockUserAfterMaxAttempts() {
        // Given
        user.setFailedAttempt(2);
        when(userRepository.save(any(AppUser.class))).thenReturn(user);

        // When
        userSecurityService.incrementFailedAttempts(user);

        // Then
        assertEquals(3, user.getFailedAttempt());
        assertFalse(user.isAccountNonLocked());
        assertNotNull(user.getLockTime());
        // Changed to verify 2 saves since both incrementFailedAttempts and lockUser save the user
        verify(userRepository, times(2)).save(user);
    }

    @Test
    void unlockWhenTimeExpired_ShouldUnlockWhenTimeExpired() {
        // Given
        LocalDateTime lockTime = LocalDateTime.now().minusHours(25);
        user.setLockTime(lockTime);
        user.setAccountNonLocked(false);
        user.setFailedAttempt(3);
        when(userRepository.save(any(AppUser.class))).thenReturn(user);

        // When
        boolean result = userSecurityService.unlockWhenTimeExpired(user);

        // Then
        assertTrue(result);
        assertTrue(user.isAccountNonLocked());
        assertNull(user.getLockTime());
        assertEquals(0, user.getFailedAttempt());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void unlockWhenTimeExpired_ShouldNotUnlockWhenTimeNotExpired() {
        // Given
        LocalDateTime lockTime = LocalDateTime.now().minusHours(23);
        user.setLockTime(lockTime);
        user.setAccountNonLocked(false);

        // When
        boolean result = userSecurityService.unlockWhenTimeExpired(user);

        // Then
        assertFalse(result);
        assertFalse(user.isAccountNonLocked());
        verify(userRepository, never()).save(user);
    }

    @Test
    void resetFailedAttempts_ShouldResetCounter() {
        // Given
        user.setFailedAttempt(2);
        when(userRepository.save(any(AppUser.class))).thenReturn(user);

        // When
        userSecurityService.resetFailedAttempts(user);

        // Then
        assertEquals(0, user.getFailedAttempt());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateLastLogin_ShouldUpdateLoginTime() {
        // Given
        when(userRepository.save(any(AppUser.class))).thenReturn(user);

        // When
        userSecurityService.updateLastLogin(user);

        // Then
        assertNotNull(user.getLastLogin());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void generatePasswordResetToken_ShouldGenerateToken() {
        // Given
        when(userRepository.save(any(AppUser.class))).thenReturn(user);

        // When
        String token = userSecurityService.generatePasswordResetToken(user);

        // Then
        assertNotNull(token);
        assertEquals(token, user.getPassword());
        assertNotNull(user.getResetTokenExpiry());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void changePassword_ShouldUpdatePasswordAndClearToken() {
        // Given
        String newPassword = "newPassword";
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(AppUser.class))).thenReturn(user);

        // When
        userSecurityService.changePassword(user, newPassword);

        // Then
        assertEquals(encodedPassword, user.getPassword());
        assertNull(user.getPasswordResetToken());
        assertNull(user.getResetTokenExpiry());
        verify(userRepository, times(1)).save(user);
        verify(passwordEncoder, times(1)).encode(newPassword);
    }
}

