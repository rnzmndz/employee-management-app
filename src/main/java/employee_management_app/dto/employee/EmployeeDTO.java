package employee_management_app.dto.employee;

import java.time.LocalDateTime;

import employee_management_app.model.enums.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String position;
	private Long departmentId;
	private String departmentName;
	private String role;
	private LocalDateTime dateHired;
	private EmployeeStatus status;
	private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
