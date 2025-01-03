package employee_management_app.dto.employee;

import java.time.LocalDateTime;
import java.util.Set;

import employee_management_app.dto.attendance.AttendanceDTO;
import employee_management_app.dto.department.DepartmentDTO;
import employee_management_app.dto.leaverequest.LeaveRequestDTO;
import employee_management_app.dto.schedule.ScheduleDTO;
import employee_management_app.dto.user.UserDTO;
import employee_management_app.model.enums.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetailDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String position;
    private DepartmentDTO department;
    private String role;
    private LocalDateTime dateHired;
    private EmployeeStatus status;
    private UserDTO user;
    private Set<AttendanceDTO> recentAttendance;
    private Set<ScheduleDTO> currentSchedules;
    private Set<LeaveRequestDTO> activeLeaveRequests;
}
