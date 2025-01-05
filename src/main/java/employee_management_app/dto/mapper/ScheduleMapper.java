package employee_management_app.dto.mapper;

import java.util.List;

import org.mapstruct.Mapping;

import employee_management_app.dto.schedule.ScheduleDTO;
import employee_management_app.model.Schedule;

public interface ScheduleMapper {
// TODO create ScheduleMapper
	@Mapping(target = "employee", ignore = true)
    ScheduleDTO toDTO(Schedule schedule);
    
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "employee.id", source = "employeeId")
    Schedule toEntity(ScheduleDTO scheduleDTO);
	
	List<ScheduleDTO> toDtoList(List<Schedule> schedules);
}
