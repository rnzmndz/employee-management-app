package employee_management_app.dto.user;

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
public class UserPasswordUpdateDTO {
    @NotNull
    @Size(min = 6)
    private String currentPassword;
    
    @NotNull
    @Size(min = 6)
    private String newPassword;
    
    @NotNull
    @Size(min = 6)
    private String confirmPassword;
}
