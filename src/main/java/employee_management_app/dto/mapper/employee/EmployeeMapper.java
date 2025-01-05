package employee_management_app.dto.mapper.employee;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import employee_management_app.dto.employee.EmployeeDTO;
import employee_management_app.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

	@Mapping(source = "department.name", target = "departmentName")
	@Mapping(source = "department.id", target = "departmentId")
	EmployeeDTO toDTO(Employee employee);
	
	@InheritInverseConfiguration
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "attendanceRecords", ignore = true)
	@Mapping(target = "leaveRequests", ignore = true)
	@Mapping(target = "schedules", ignore = true)
	Employee toEntity(EmployeeDTO employeeDTO);
	
	List<EmployeeDTO> toDtoList(List<Employee> employees);
}
