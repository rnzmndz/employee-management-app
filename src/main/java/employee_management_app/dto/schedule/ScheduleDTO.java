package employee_management_app.dto.schedule;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
	
    private Long id;
    private Long employeeId;
    private Date date;
    private LocalTime shiftStartTime;
	private LocalTime shiftEndTime;
	private String description;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}