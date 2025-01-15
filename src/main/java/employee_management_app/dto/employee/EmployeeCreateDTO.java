package employee_management_app.dto.employee;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreateDTO {

	@NotNull
	@Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z\\s-']+$", message = "First name can only contain letters, spaces, hyphens and apostrophes")
	private String firstName;
	
	@NotNull
	@Size(min = 2, max = 50)
    @Pattern(regexp = "^[a-zA-Z\\s-']+$", message = "First name can only contain letters, spaces, hyphens and apostrophes")
	private String lastName;
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	private String phone;
	
	@NotNull
	private String position;
	
	@NotNull
	private Long departmentId;
	
	@NotNull
    @Pattern(regexp = "^(ADMIN|MANAGER|EMPLOYEE|HR)$", message = "Invalid role. Allowed values: ADMIN, MANAGER, EMPLOYEE, HR")
	private String role;
	
	@NotNull
    @PastOrPresent(message = "Date hired cannot be in the future")
	private LocalDateTime dateHired;

}
