package employee_management_app.service;

import employee_management_app.model.AppUser;

public interface UserSecurityService {

	void incrementFailedAttempts(AppUser user);
	void lockUser(AppUser user);
	boolean unlockWhenTimeExpired(AppUser user);
	void resetFailedAttempts(AppUser user);
	void updateLastLogin(AppUser user);
	String generatePasswordResetToken(AppUser user);
	void changePassword(AppUser user, String newPassword);
}
