package employee_management_app.dto.mapper.employee;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

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
	
	@Mapping(target = "recentAttendance", source = "attendanceRecords", qualifiedByName = "toRecentAttendance")
	@Mapping(target = "currentSchedules", source = "schedules", qualifiedByName = "toCurrentSchedules")
	@Mapping(target = "activeLeaveRequests", source = "leaveRequests", qualifiedByName = "toActiveLeaveRequests")
	EmployeeDetailDTO toDTO(Employee employee);
	
	@Named("toRecentAttendance")
	default Set<AttendanceDTO> mapRecentAttendance(Set<Attendance> attendance) {
		return attendance.stream()
				.filter(a -> a.getDate().isAfter(LocalDateTime.now().minusDays(30)))
				.map(a -> this.mapAttendance(a))
				.collect(Collectors.toSet());
	}
	
	@Named("toCurrentSchedules")
	default Set<ScheduleDTO> mapCurrentSchedules(Set<Schedule> schedule) {
		return schedule.stream()
				.filter(s -> s.getShiftEndTime().isAfter(LocalTime.now()))
				.map(s -> this.mapSchedule(s))
				.collect(Collectors.toSet());
	}
	
	@Named("toActiveLeaveRequests")
	default Set<LeaveRequestDTO> mapActiveLeaveRequests(Set<LeaveRequest> leaveRequests) {
		return leaveRequests.stream()
				.filter(lr -> lr.getStatus() == LeaveRequestStatus.PENDING ||
                (lr.getStatus() == LeaveRequestStatus.APPROVED &&
                 lr.getEndDate().isAfter(LocalDateTime.now())))
				.map(lr -> this.mapLeaveRequest(lr))
	            .collect(Collectors.toSet());
   
	}
	
	@Named("mapAttendance")
	@Mapping(target = "employee",ignore = true)
	AttendanceDTO mapAttendance(Attendance attendance);
	
	@Named("mapSchedule")
	@Mapping(target = "employee",ignore = true)
	ScheduleDTO mapSchedule(Schedule schedule);
	
	@Named("mapLeaveRequest")
	@Mapping(target = "employee",ignore = true)
	LeaveRequestDTO mapLeaveRequest(LeaveRequest leaveRequest);
}
