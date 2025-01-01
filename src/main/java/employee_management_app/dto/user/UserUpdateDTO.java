package employee_management_app.dto.user;

import java.util.Set;

import employee_management_app.model.enums.UserRole;
import employee_management_app.model.enums.UserStatus;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    @Size(min = 3, max = 50)
    private String userName;
    
    private UserRole role;
    private UserStatus status;
    private Set<String> permissions;
    private Long employeeId;
}