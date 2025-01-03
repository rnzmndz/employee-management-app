package employee_management_app.dto.employee;

import employee_management_app.model.enums.EmployeeStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUpdateDTO {
    @Size(min = 2, max = 50)
    private String firstName;
    
    @Size(min = 2, max = 50)
    private String lastName;
    
    @Email
    private String email;
    
    private String phone;
    private String position;
    private Long departmentId;
    private String role;
    private EmployeeStatus status;
}
