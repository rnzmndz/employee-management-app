package employee_management_app.service;

import employee_management_app.model.User;

public interface UserSecurityService {

	void incrementFailedAttempts(User user);
	void lockUser(User user);
	boolean unlockWhenTimeExpired(User user);
	void resetFailedAttempts(User user);
	void updateLastLogin(User user);
	String generatePasswordResetToken(User user);
	void changePassword(User user, String newPassword);
}
