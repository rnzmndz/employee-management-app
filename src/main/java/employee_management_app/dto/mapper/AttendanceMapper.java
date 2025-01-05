package employee_management_app.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import employee_management_app.dto.attendance.AttendanceDTO;
import employee_management_app.model.Attendance;
// FIXME this is not yet final
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AttendanceMapper {

	@Mapping(target = "id", ignore = true)
	Attendance toEntity(AttendanceDTO attendanceDTO);
	
	AttendanceDTO toDTO(Attendance attendance);
	
	List<AttendanceDTO> toList(List<Attendance> attendance);
}
