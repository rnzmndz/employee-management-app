package employee_management_app.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
		
		httpSecurity.authorizeHttpRequests((authz) -> authz
				.requestMatchers(HttpMethod.GET, "/api/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.GET, "/api/employees").hasAuthority("EMPLOYEE_READ")
				.anyRequest().denyAll())
		.httpBasic(withDefaults())
		.csrf(CsrfConfigurer::disable);
		
		return httpSecurity.build();
	}
	
//	@Bean
//	public InMemoryUserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder) {
//		UserDetails admin = User.withUsername("admin").password(passwordEncoder.encode("admin")).roles("USER", "ADMIN").build();
//		
//		return new InMemoryUserDetailsManager(admin);
//	}
	
	@Bean
    public PasswordEncoder passwordEncoder() {
    	return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
