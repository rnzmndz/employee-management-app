package employee_management_app.dto.attendance;

import java.time.LocalDateTime;

import employee_management_app.model.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private LocalDateTime date;
    private LocalDateTime timeIn;
    private LocalDateTime timeOut;
    private AttendanceStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}