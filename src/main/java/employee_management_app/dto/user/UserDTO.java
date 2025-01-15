package employee_management_app.dto.user;

import java.time.LocalDateTime;
import java.util.Set;

import employee_management_app.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private Long id;
    private String username;
    private LocalDateTime lastLogin;
    private UserStatus status;
    private Set<String> permissions;
    private Long employeeId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
