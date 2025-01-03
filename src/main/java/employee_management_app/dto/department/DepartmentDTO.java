package employee_management_app.dto.department;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

	private Long id;
	private String name;
	private String description;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
}
