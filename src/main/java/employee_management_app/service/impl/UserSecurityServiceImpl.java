package employee_management_app.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import employee_management_app.model.AppUser;
import employee_management_app.repository.UserRepository;
import employee_management_app.service.UserSecurityService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserSecurityServiceImpl implements UserSecurityService{
	
	private static final int MAX_FAILED_ATTEMPTS = 3;
	private static final long LOCK_TIME_DURATION = 24;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void incrementFailedAttempts(AppUser user) {
		user.setFailedAttempt(user.getFailedAttempt() + 1);
		if (user.getFailedAttempt() >= MAX_FAILED_ATTEMPTS) {
			lockUser(user);
		}
		userRepository.save(user);
	}

	@Override
	public void lockUser(AppUser user) {
		user.setAccountNonLocked(false);
		user.setLockTime(LocalDateTime.now());
		userRepository.save(user);
	}

	@Override
	public boolean unlockWhenTimeExpired(AppUser user) {
		if (user.getLockTime() != null) {
			LocalDateTime lockTime = user.getLockTime();
			LocalDateTime unlockTime = lockTime.plusHours(LOCK_TIME_DURATION);
			
			if (LocalDateTime.now().isAfter(unlockTime)) {
				user.setAccountNonLocked(true);
				user.setLockTime(null);
				user.setFailedAttempt(0);
				userRepository.save(user);
				return true;
			}
		}
		return false;
	}

	@Override
	public void resetFailedAttempts(AppUser user) {
		user.setFailedAttempt(0);
		userRepository.save(user);
		
	}

	@Override
	public void updateLastLogin(AppUser user) {
		user.setLastLogin(LocalDateTime.now());
		userRepository.save(user);
	}

	@Override
	public String generatePasswordResetToken(AppUser user) {
		String token = UUID.randomUUID().toString();
		user.setPassword(token);
		user.setResetTokenExpiry(LocalDateTime.now().plusHours(24));
		userRepository.save(user);
		return token;
	}

	@Override
	public void changePassword(AppUser user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		user.setPasswordResetToken(null);
		user.setResetTokenExpiry(null);
		userRepository.save(user);
	}

}
