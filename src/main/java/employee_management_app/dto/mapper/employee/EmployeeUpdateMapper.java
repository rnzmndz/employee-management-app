package employee_management_app.dto.mapper.employee;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import employee_management_app.dto.employee.EmployeeUpdateDTO;
import employee_management_app.model.Employee;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeUpdateMapper {
	
	@Autowired
	
//	TODO Should only update provided fields, leaving others unchanged
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
	@Mapping(target = "department", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "attendanceRecords", ignore = true)
	@Mapping(target = "leaveRequests", ignore = true)
	@Mapping(target = "schedules", ignore = true)
	void updateEmployeeFromDto(EmployeeUpdateDTO dto, @MappingTarget Employee employee);
	
	EmployeeUpdateDTO toDTO(Employee employee);
    
}
