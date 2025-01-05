package employee_management_app.dto.leaverequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import employee_management_app.model.enums.LeaveRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequestDTO {
    private Long id;
    private Long requestedByEmployeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private LeaveRequestStatus status;
    private LocalDateTime appliedDate;
    private Long approvedByEmployeeId;
}