package employee_management_app.dto.user;

import java.util.HashSet;
import java.util.Set;

import employee_management_app.model.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {
    @NotNull
    @Size(min = 3, max = 50)
    private String userName;
    
    @NotNull
    @Size(min = 6)
    private String password;
    
    @NotNull
    private UserRole role;
    
    @NotNull
    private Long employeeId;
    
    private Set<String> permissions = new HashSet<>();
}

