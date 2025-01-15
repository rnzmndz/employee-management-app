package employee_management_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import employee_management_app.aspect.ControllerLoggingAspect;
import employee_management_app.aspect.ServiceLoggingAspect;

@Configuration
@EnableAspectJAutoProxy
public class AopConfig {
	
	@Bean
	ControllerLoggingAspect controllerLoggingAspect() {
		return new ControllerLoggingAspect();
	}
	
	@Bean
	ServiceLoggingAspect serviceLoggingAspect() {
		return new ServiceLoggingAspect();
	}
}
