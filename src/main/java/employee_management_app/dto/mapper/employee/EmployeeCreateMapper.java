package employee_management_app.dto.mapper.employee;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import employee_management_app.dto.employee.EmployeeCreateDTO;
import employee_management_app.model.Employee;
// XXX Review this later on
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeCreateMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "department.id", source = "departmentId")
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	Employee toEntity(EmployeeCreateDTO employeeCreateDTO);
}
