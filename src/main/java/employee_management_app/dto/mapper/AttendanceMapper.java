package employee_management_app.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import employee_management_app.dto.attendance.AttendanceDTO;
import employee_management_app.model.Attendance;
// FIXME this is not yet final
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AttendanceMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "employee.firstName", expression = "java(splitEmployeeName(attendanceDTO.getEmployeeName())[0])")
	@Mapping(target = "employee.lastName", expression = "java(splitEmployeeName(attendanceDTO.getEmployeeName())[1])")
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	Attendance toEntity(AttendanceDTO attendanceDTO);
	
	AttendanceDTO toDto(Attendance attendance);
	
	default String[] splitEmployeeName(String employeeName) {
		return employeeName.split(" ");
	}
	
	default String combineEmployeeName(String firstName, String lastName) {
		return firstName + " " + lastName;
	}
}
