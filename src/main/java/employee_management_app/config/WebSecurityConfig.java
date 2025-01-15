package employee_management_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import employee_management_app.security.UserSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
	private UserSecurity userSecurity;
	
	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/home", "/public/**").permitAll()
                .requestMatchers("/api/**").hasRole("ADMIN")
                .requestMatchers("/api/employees/**").hasRole("HR")
                .requestMatchers("/api/attendance/**").hasRole("HR")
                .requestMatchers("/api/schedules/**").hasRole("MANAGER")
                .requestMatchers(HttpMethod.POST, "/api/users").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "/api/users/{id}").access(userSecurity)
                .requestMatchers(HttpMethod.PUT, "/api/users/{id}").access(userSecurity)
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .defaultSuccessUrl("/users", true)
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}