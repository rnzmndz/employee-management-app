package employee_management_app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import employee_management_app.model.AppUser;
import employee_management_app.repository.UserRepository;

@Component
@Primary
public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserSecurityService userSecurityService;

	@Override
	public Authentication authenticate(Authentication authentication) {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		
		AppUser user = userRepository.findOptionalByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		if (!user.isAccountNonLocked()) {
				if (userSecurityService.unlockWhenTimeExpired(user)) {
					return authenticateUser(user, password);
					}
				else {
					throw new LockedException("Account is locked");
					} 
		}
		return authenticateUser(user, password);
	}

	private Authentication authenticateUser(AppUser user, String password) {
        if (passwordEncoder.matches(password, passwordEncoder.encode(user.getPassword()))) {
            userSecurityService.resetFailedAttempts(user);
            userSecurityService.updateLastLogin(user);
           
            return new UsernamePasswordAuthenticationToken(
                user.getUsername(), 
                password,
                user.getAuthorities()
            );
        } else {
            userSecurityService.incrementFailedAttempts(user);
            throw new BadCredentialsException("Invalid password");
        }
    }
	
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
