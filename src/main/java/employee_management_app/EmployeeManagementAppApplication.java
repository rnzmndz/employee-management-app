package employee_management_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import employee_management_app.config.WebSecurityConfig;

@SpringBootApplication
@Import(WebSecurityConfig.class)
public class EmployeeManagementAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementAppApplication.class, args);
	}

}
