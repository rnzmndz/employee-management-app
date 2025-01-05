package employee_management_app.dto.mapper.employee;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import employee_management_app.dto.attendance.AttendanceDTO;
import employee_management_app.dto.employee.EmployeeDetailDTO;
import employee_management_app.dto.leaverequest.LeaveRequestDTO;
import employee_management_app.dto.mapper.AttendanceMapper;
import employee_management_app.dto.mapper.DepartmentMapper;
import employee_management_app.dto.mapper.LeaveRequestMapper;
import employee_management_app.dto.mapper.ScheduleMapper;
import employee_management_app.dto.mapper.user.UserMapper;
import employee_management_app.dto.schedule.ScheduleDTO;
import employee_management_app.model.Attendance;
import employee_management_app.model.Employee;
import employee_management_app.model.LeaveRequest;
import employee_management_app.model.Schedule;
import employee_management_app.model.enums.LeaveRequestStatus;

@Mapper(
		componentModel = "spring", 
		uses = {
				DepartmentMapper.class,
				UserMapper.class,
				AttendanceMapper.class,
				ScheduleMapper.class,
				LeaveRequestMapper.class
		},
		imports = LocalDateTime.class)
public interface EmployeeDetailMapper {
//	XXX Review EmployeeDetailMapper
	
	EmployeeDetailMapper INSTANCE = Mappers.getMapper(EmployeeDetailMapper.class);
	
	@Mapping(source = "attendanceRecords", target = "recentAttendance", qualifiedByName = "toRecentAttendance")
    @Mapping(source = "schedules", target = "currentSchedules", qualifiedByName = "toCurrentSchedules")
    @Mapping(source = "leaveRequests", target = "activeLeaveRequests", qualifiedByName = "toActiveLeaveRequests")
    EmployeeDetailDTO toDTO(Employee employee);
    
    @Named("toRecentAttendance")
    default Set<AttendanceDTO> toRecentAttendance(Set<Attendance> attendanceRecords) {
        if (attendanceRecords == null) {
            return null;
        }
        // Filter for recent attendance records (e.g., last 30 days)
        return attendanceRecords.stream()
            .filter(this::isRecent)
            .map(attendance -> Mappers.getMapper(AttendanceMapper.class).toDTO(attendance))
            .collect(Collectors.toSet());
    }
    
    @Named("toCurrentSchedules")
    default Set<ScheduleDTO> toCurrentSchedules(Set<Schedule> schedules) {
        if (schedules == null) {
            return null;
        }
        // Filter for current/upcoming schedules
        return schedules.stream()
            .filter(this::isCurrent)
            .map(schedule -> Mappers.getMapper(ScheduleMapper.class).toDTO(schedule))
            .collect(Collectors.toSet());
    }

    @Named("toActiveLeaveRequests")
    default Set<LeaveRequestDTO> toActiveLeaveRequests(Set<LeaveRequest> leaveRequests) {
        if (leaveRequests == null) {
            return null;
        }
        // Filter for active leave requests
        return leaveRequests.stream()
            .filter(this::isActive)
            .map(leaveRequest -> Mappers.getMapper(LeaveRequestMapper.class).toDTO(leaveRequest))
            .collect(Collectors.toSet());
    }
	
    default boolean isRecent(Attendance attendance) {
        // Check if attendance record is recent
    	return attendance.getDate().isAfter(LocalDate.now().minusDays(30));
    }

    default boolean isCurrent(Schedule schedule) {
        // Check if schedule is current/upcoming
        return schedule.getShiftEndTime().isAfter(LocalTime.now());
    }

    default boolean isActive(LeaveRequest leaveRequest) {
        // Check if leave request is active
        return leaveRequest.getStatus() == LeaveRequestStatus.PENDING || 
                leaveRequest.getStatus() == LeaveRequestStatus.APPROVED;
    }
}
