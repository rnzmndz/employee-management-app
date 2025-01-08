package employee_management_app.dto.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import employee_management_app.dto.schedule.ScheduleDTO;
import employee_management_app.model.Schedule;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduleMapper {
	
    ScheduleDTO toDTO(Schedule schedule);
    
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "employee.id", source = "employeeId")
    Schedule toEntity(ScheduleDTO scheduleDTO);
	
	List<ScheduleDTO> toDtoList(List<Schedule> schedules);
}
