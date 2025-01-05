package employee_management_app.dto.mapper.employee;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import employee_management_app.dto.employee.EmployeeListDTO;
import employee_management_app.model.Employee;

@Mapper(componentModel = "spring")
public interface EmployeeListMapper {

	@Mapping(target = "departmentName", source = "department.name")
	EmployeeListDTO toDTO(Employee employee);
	
	List<EmployeeListDTO> toDtoList(List<Employee> employees);
}
