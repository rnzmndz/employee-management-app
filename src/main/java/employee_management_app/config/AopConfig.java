package employee_management_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import employee_management_app.aspect.UserServiceAspect;

@Configuration
@EnableAspectJAutoProxy
public class AopConfig {

	@Bean
	UserServiceAspect userServiceAspect() {
		return new UserServiceAspect();
	}
}
