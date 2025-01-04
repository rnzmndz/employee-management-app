package employee_management_app.dto.mapper;

import org.mapstruct.Mapping;

import employee_management_app.dto.schedule.ScheduleDTO;
import employee_management_app.model.Schedule;

public interface ScheduleMapper {
// TODO create ScheduleMapper
	@Mapping(target = "employee", ignore = true) // Avoid circular reference
    ScheduleDTO toDTO(Schedule schedule);
    
    @Mapping(target = "employee.id", source = "employeeId")
    Schedule toEntity(ScheduleDTO scheduleDTO);
}
