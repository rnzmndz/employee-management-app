package employee_management_app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import employee_management_app.model.AppUser;
import employee_management_app.model.enums.UserStatus;
import employee_management_app.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUserName(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            
        return new User(user.getUserName(), 
                       user.getPassword(), 
                       user.getStatus() == UserStatus.ACTIVE,
                       true, true, true,
                       user.getAuthorities());
    }
}
