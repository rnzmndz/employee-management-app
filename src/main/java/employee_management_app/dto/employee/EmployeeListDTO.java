package employee_management_app.dto.employee;

import employee_management_app.model.enums.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeListDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String position;
    private String departmentName;
    private EmployeeStatus status;
}